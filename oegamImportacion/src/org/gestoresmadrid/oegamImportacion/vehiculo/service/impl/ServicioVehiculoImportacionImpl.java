package org.gestoresmadrid.oegamImportacion.vehiculo.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoPK;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaFabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamConversiones.conversion.Conversion;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioEvolucionVehiculoImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioFabricanteImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioMarcaDgtImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioMarcaFabricanteImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioTipoVehiculoImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioVehiculoImportacion;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioVehiculoImportacionImpl implements ServicioVehiculoImportacion {

	private static final long serialVersionUID = -6155327515645901570L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioVehiculoImportacionImpl.class);

	@Autowired
	VehiculoDao vehiculoDao;

	@Autowired
	ServicioDireccionImportacion servicioDireccion;

	@Autowired
	ServicioEvolucionVehiculoImportacion servicioEvolucionVehiculo;

	@Autowired
	ServicioTramiteTraficoImportacion servicioTramiteTrafico;

	@Autowired
	Conversion conversor;

	@Autowired
	ServicioFabricanteImportacion servicioFabricante;

	@Autowired
	ServicioPersonaImportacion servicioPersona;

	@Autowired
	ServicioMarcaFabricanteImportacion servicioMarcaFabricante;

	@Autowired
	ServicioMarcaDgtImportacion servicioMarcaDgt;

	@Autowired
	ServicioTipoVehiculoImportacion servicioTipoVehiculo;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public ResultadoBean guardarVehiculo(VehiculoVO vehiculo, BigDecimal numExpediente, ContratoVO contrato, Long idUsuario, String tipoTramite, boolean prever) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			VehiculoVO vehiculoBBDD = null;
			if (vehiculo.getBastidor() != null && !vehiculo.getBastidor().isEmpty()) {
				vehiculoBBDD = getVehiculoVO(null, vehiculo.getNumColegiado(), null, vehiculo.getBastidor(), null, EstadoVehiculo.Activo);
			} else if (vehiculo.getMatricula() != null && !vehiculo.getMatricula().isEmpty()) {
				vehiculoBBDD = getVehiculoVO(null, vehiculo.getNumColegiado(), vehiculo.getMatricula(), null, null, EstadoVehiculo.Activo);
			} else if (vehiculo.getNive() != null && !vehiculo.getNive().isEmpty()) {
				vehiculoBBDD = getVehiculoVO(null, vehiculo.getNumColegiado(), null, null, vehiculo.getNive(), EstadoVehiculo.Activo);
			} else {
				resultado.setError(true);
				resultado.setMensaje("Faltan datos obligatorios para realizar la búsqueda (Matrícula o bastidor)");
				return resultado;
			}
			if (vehiculoBBDD != null && vehiculoBBDD.getDireccion() != null && vehiculoBBDD.getDireccion().getIdDireccion() != null) {
				if (vehiculo.getDireccion() != null) {
					vehiculo.getDireccion().setIdDireccion(vehiculoBBDD.getDireccion().getIdDireccion());
				} else {
					vehiculo.setDireccion(new DireccionVO());
					vehiculo.getDireccion().setIdDireccion(vehiculoBBDD.getDireccion().getIdDireccion());
				}
			}

			if (vehiculo.getDireccion() != null) {
				ResultadoBean resultDirVehiculo = servicioDireccion.guardarActualizarDireccionVeh(vehiculo.getDireccion(), vehiculo.getMatricula(), tipoTramite);
				if (!resultDirVehiculo.getError()) {
					vehiculo.getDireccion().setIdDireccion(resultDirVehiculo.getIdDirVehiculo());
				} else {
					vehiculo.setDireccion(null);
					resultado.addListaMensaje(resultDirVehiculo.getMensaje());
				}
			}

			if (vehiculoBBDD == null) {
				vehiculo.setActivo(EstadoVehiculo.Activo.getValorEnum());
				vehiculoDao.guardar(vehiculo);
				resultado.setIdVehiculo(vehiculo.getIdVehiculo());
				servicioEvolucionVehiculo.guardarEvolucion(vehiculo.getIdVehiculo(), contrato.getColegiado().getNumColegiado(), numExpediente, tipoTramite,
						ServicioEvolucionVehiculoImportacion.TIPO_ACTUALIZACION_CRE, idUsuario, null);
			} else {
				VehiculoVO vehiculoCompleto = (VehiculoVO) vehiculoBBDD.clone();
				conversor.transform(vehiculo, vehiculoCompleto, getConversion(tipoTramite, vehiculo, prever));
				ResultadoBean resultadoModificacion = esModificadoVehiculo(vehiculoCompleto, vehiculoBBDD);
				if (!resultadoModificacion.getError()) {
					if (resultadoModificacion.getValorModificado() != 0) {
						if (resultadoModificacion.getValorModificado() == 2 && servicioTramiteTrafico.getNumTramitePorVehiculo(numExpediente, vehiculoCompleto.getIdVehiculo()) > 0) {
							Long idVehiculoAntiguo = vehiculoCompleto.getIdVehiculo();
							vehiculoDao.evict(vehiculoCompleto);
							Long idVehiculo = copiaVehiculo(vehiculoCompleto, idVehiculoAntiguo, numExpediente, tipoTramite, idUsuario);
							desactivarVehiculo(vehiculoBBDD, numExpediente, tipoTramite, idVehiculo, idUsuario, resultadoModificacion.getEvolucionVehiculo());
							vehiculoCompleto.setIdVehiculo(idVehiculo);
						} else {
							vehiculoDao.evict(vehiculoBBDD);
							vehiculoDao.guardarOActualizar(vehiculoCompleto);
							servicioEvolucionVehiculo.guardarEvolucionVO(resultadoModificacion.getEvolucionVehiculo(), vehiculoCompleto.getIdVehiculo(), contrato.getColegiado().getNumColegiado(),
									numExpediente, tipoTramite, ServicioEvolucionVehiculoImportacion.TIPO_ACTUALIZACION_MOD, idUsuario);
						}
					} else {
						vehiculoDao.evict(vehiculoCompleto);
					}
					resultado.setIdVehiculo(vehiculoCompleto.getIdVehiculo());
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultadoModificacion.getMensaje());
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de guardar el vehiculo para el expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el vehiculo.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultadoBean guardarVehiculoMatriculacion(VehiculoVO vehiculoVO, BigDecimal numExpediente, ContratoVO contrato, Long idUsuario, Date fechaAlta) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			if (vehiculoVO.getVehiculoPrever() != null) {
				vehiculoVO.getVehiculoPrever().setNumColegiado(vehiculoVO.getNumColegiado());
				ResultadoBean resultPrever = guardarVehiculo(vehiculoVO.getVehiculoPrever(), numExpediente, contrato, idUsuario, TipoTramiteTrafico.Matriculacion.getValorEnum(), true);
				if (!resultPrever.getError()) {
					if (resultPrever.getIdVehiculo() != null) {
						vehiculoVO.setIdVehiculoPrever(new BigDecimal(resultPrever.getIdVehiculo()));
					}
				}
			}

			if (vehiculoVO.getPersona() != null && vehiculoVO.getPersona().getId() != null && vehiculoVO.getPersona().getId().getNif() != null && !vehiculoVO.getPersona().getId().getNif().isEmpty()) {
				ResultadoBean resultPersona = servicioPersona.guardarActualizarImportacion(vehiculoVO.getPersona(), numExpediente, TipoTramiteTrafico.Matriculacion.getValorEnum(), idUsuario,
						ServicioPersonaImportacion.CONVERSION_PERSONA_INTEGRADOR);
				if (resultPersona.getError()) {
					resultado.addListaMensaje(resultPersona.getMensaje());
				} else {
					if (resultPersona.getPersona() != null) {
						vehiculoVO.setPersona(resultPersona.getPersona());
					}
					vehiculoVO.setNifIntegrador(vehiculoVO.getPersona().getId().getNif());
				}
			}

			resultado = guardarVehiculo(vehiculoVO, numExpediente, contrato, idUsuario, TipoTramiteTrafico.Matriculacion.getValorEnum(), false);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el vehiculo para el expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el vehiculo.");
		}
		return resultado;
	}

	private void desactivarVehiculo(VehiculoVO vehiculoBBDD, BigDecimal numExpediente, String tipoTramite, Long idVehiculoNuevo, Long idUsuario, EvolucionVehiculoVO evolucionVehiculo)
			throws OegamExcepcion {
		try {
			vehiculoBBDD.setActivo(EstadoVehiculo.Desactivo.getValorEnum());
			vehiculoDao.guardarOActualizar(vehiculoBBDD);
			String otros = evolucionVehiculo.getOtros();
			evolucionVehiculo.setOtros("Vehículo Desactivado. Id nuevo: " + idVehiculoNuevo);
			if (otros != null && !otros.isEmpty()) {
				evolucionVehiculo.setOtros(evolucionVehiculo.getOtros() + " -- " + otros);
			}
			servicioEvolucionVehiculo.guardarEvolucionVO(evolucionVehiculo, vehiculoBBDD.getIdVehiculo(), vehiculoBBDD.getNumColegiado(), numExpediente, tipoTramite,
					ServicioEvolucionVehiculoImportacion.TIPO_ACTUALIZACION_MOD, idUsuario);
		} catch (Exception e) {
			log.error("Error al desactivar el vehículo", e, numExpediente.toString());
			throw new OegamExcepcion("Error al desactivar el vehículo");
		}

	}

	private Long copiaVehiculo(VehiculoVO vehiculo, Long idVehiculoAntiguo, BigDecimal numExpediente, String tipoTramite, Long idUsuario) throws OegamExcepcion {
		Long idVehiculo = null;
		try {
			vehiculo.setIdVehiculo(null);
			vehiculo.setActivo(EstadoVehiculo.Activo.getValorEnum());
			idVehiculo = (Long) vehiculoDao.guardar(vehiculo);
			servicioEvolucionVehiculo.guardarEvolucion(idVehiculo, vehiculo.getNumColegiado(), numExpediente, tipoTramite, ServicioEvolucionVehiculoImportacion.TIPO_ACTUALIZACION_COP, idUsuario,
					idVehiculoAntiguo);
		} catch (Exception e) {
			log.error("Error al copiar el vehículo", e, numExpediente.toString());
			throw new OegamExcepcion("Error al copiar el vehículo");
		}
		return idVehiculo;
	}

	private String getConversion(String tipoTramite, VehiculoVO vehiculo, boolean prever) {
		if (prever) {
			return ServicioVehiculoImportacion.CONVERSION_VEHICULO_PREVER;
		} else {
			if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
				return ServicioVehiculoImportacion.CONVERSION_VEHICULO_BAJAS;
			} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
				return ServicioVehiculoImportacion.CONVERSION_VEHICULO_MATRICULACION;
			} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
				return ServicioVehiculoImportacion.CONVERSION_VEHICULO_CTIT;
			} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
				return ServicioVehiculoImportacion.CONVERSION_VEHICULO_DUPLICADO;
			} else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(tipoTramite)) {
				if (StringUtils.isNotBlank(vehiculo.getMatricula())) {
					return ServicioVehiculoImportacion.CONVERSION_VEHICULO_SOLICITUD_MATR;
				} else if (StringUtils.isNotBlank(vehiculo.getMatricula())) {
					return ServicioVehiculoImportacion.CONVERSION_VEHICULO_SOLICITUD_BAST;
				}
			}
		}
		return null;
	}

	/**
	 * return: 0 - No modificado; 1 - Modificado; 2 - Modificado y creación de copia
	 */
	private ResultadoBean esModificadoVehiculo(VehiculoVO vehiculoVO, VehiculoVO vehiculoBBDD) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			EvolucionVehiculoVO evolucion = new EvolucionVehiculoVO();
			EvolucionVehiculoPK id = new EvolucionVehiculoPK();
			id.setIdVehiculo(vehiculoBBDD.getIdVehiculo());
			evolucion.setId(id);
			evolucion.setOtros("");
			resultado.setValorModificado(new Long(0));
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getFichaTecnicaRd750(), vehiculoBBDD.getFichaTecnicaRd750())) {
				evolucion.setOtros(evolucion.getOtros() + "Ficha TecnicaRD 750,");
				if (vehiculoBBDD.getFichaTecnicaRd750() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getSubastado(), vehiculoBBDD.getSubastado())) {
				evolucion.setOtros(evolucion.getOtros() + "Subastado,");
				if (vehiculoBBDD.getSubastado() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getVehiculoAgricola(), vehiculoBBDD.getVehiculoAgricola())) {
				evolucion.setOtros(evolucion.getOtros() + "Vehiculo Agricola,");
				if (vehiculoBBDD.getVehiculoAgricola() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getVehiculoTransporte(), vehiculoBBDD.getVehiculoTransporte())) {
				evolucion.setOtros(evolucion.getOtros() + "Vehiculo Transporte,");
				if (vehiculoBBDD.getVehiculoTransporte() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getAnioFabrica(), vehiculoBBDD.getAnioFabrica())) {
				evolucion.setOtros(evolucion.getOtros() + "Vehiculo Transporte,");
				if (vehiculoBBDD.getAnioFabrica() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getAutonomiaElectrica(), vehiculoBBDD.getAutonomiaElectrica())) {
				evolucion.setOtros(evolucion.getOtros() + "Año Fabrica,");
				if (vehiculoBBDD.getAutonomiaElectrica() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getBastidor() != null ? vehiculoVO.getBastidor().toUpperCase() : null, vehiculoBBDD.getBastidor() != null ? vehiculoBBDD.getBastidor().toUpperCase()
					: null)) {
				evolucion.setBastidorAnt(vehiculoBBDD.getBastidor());
				if (vehiculoBBDD.getBastidor() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
				evolucion.setBastidorNue(vehiculoVO.getBastidor());
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getBastidorMatriculado(), vehiculoBBDD.getBastidorMatriculado())) {
				evolucion.setOtros(evolucion.getOtros() + "Bastidor Matriculado,");
				if (vehiculoBBDD.getBastidorMatriculado() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCaracteristicas(), vehiculoBBDD.getCaracteristicas())) {
				evolucion.setOtros(evolucion.getOtros() + "Caracteristicas,");
				if (vehiculoBBDD.getCaracteristicas() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdCarburante(), vehiculoBBDD.getIdCarburante())) {
				evolucion.setOtros(evolucion.getOtros() + "Carburante,");
				if (vehiculoBBDD.getIdCarburante() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getCarroceria(), vehiculoBBDD.getCarroceria())) {
				evolucion.setOtros(evolucion.getOtros() + "Carroceria,");
				if (vehiculoBBDD.getCarroceria() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCategoriaElectrica(), vehiculoBBDD.getCategoriaElectrica())) {
				evolucion.setOtros(evolucion.getOtros() + "Categoria Electrica,");
				if (vehiculoBBDD.getCategoriaElectrica() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCdmarca(), vehiculoBBDD.getCdmarca())) {
				evolucion.setOtros(evolucion.getOtros() + "Marca Vehiculo,");
				if (vehiculoBBDD.getCdmarca() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCdmodveh(), vehiculoBBDD.getCdmodveh())) {
				evolucion.setOtros(evolucion.getOtros() + "Modelo Vehiculo,");
				if (vehiculoBBDD.getCdmodveh() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCheckFechaCaducidadItv(), vehiculoBBDD.getCheckFechaCaducidadItv())) {
				evolucion.setOtros(evolucion.getOtros() + "Check Fecha Caducidad,");
				if (vehiculoBBDD.getCheckFechaCaducidadItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCilindrada(), vehiculoBBDD.getCilindrada())) {
				evolucion.setOtros(evolucion.getOtros() + "Cilindrada,");
				if (vehiculoBBDD.getCilindrada() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getClasificacionItv(), vehiculoBBDD.getClasificacionItv())) {
				evolucion.setOtros(evolucion.getOtros() + "Clasificación Itv,");
				if (vehiculoBBDD.getClasificacionItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCo2(), vehiculoBBDD.getCo2())) {
				evolucion.setOtros(evolucion.getOtros() + "Co2,");
				if (vehiculoBBDD.getCo2() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCodigoEco(), vehiculoBBDD.getCodigoEco())) {
				evolucion.setOtros(evolucion.getOtros() + "Codigo Eco,");
				if (vehiculoBBDD.getCodigoEco() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCoditv(), vehiculoBBDD.getCoditv())) {
				evolucion.setCodigoItvAnt(vehiculoBBDD.getCoditv());
				if (vehiculoBBDD.getCoditv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
				evolucion.setCodigoItvNue(vehiculoVO.getCoditv());
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdColor(), vehiculoBBDD.getIdColor())) {
				evolucion.setOtros(evolucion.getOtros() + "Color,");
				if (vehiculoBBDD.getIdColor() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getConceptoItv(), vehiculoBBDD.getConceptoItv())) {
				evolucion.setOtros(evolucion.getOtros() + "Concepto Itv,");
				if (vehiculoBBDD.getConceptoItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getConsumo(), vehiculoBBDD.getConsumo())) {
				evolucion.setOtros(evolucion.getOtros() + "Consumo,");
				if (vehiculoBBDD.getConsumo() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdCriterioConstruccion(), vehiculoBBDD.getIdCriterioConstruccion())) {
				evolucion.setOtros(evolucion.getOtros() + "Criterio Construcción,");
				if (vehiculoBBDD.getIdCriterioConstruccion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdCriterioUtilizacion(), vehiculoBBDD.getIdCriterioUtilizacion())) {
				evolucion.setOtros(evolucion.getOtros() + "Cristerion Utilización,");
				if (vehiculoBBDD.getIdCriterioUtilizacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getDiplomatico(), vehiculoBBDD.getDiplomatico())) {
				evolucion.setOtros(evolucion.getOtros() + "Diplomatico,");
				if (vehiculoBBDD.getDiplomatico() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.diferenciaNulls(vehiculoVO.getDireccion(), vehiculoBBDD.getDireccion()) || (vehiculoVO.getDireccion() != null && vehiculoBBDD.getDireccion() != null && !utiles
					.sonIgualesObjetos(vehiculoVO.getDireccion().getIdDireccion(), vehiculoBBDD.getDireccion().getIdDireccion()))) {
				evolucion.setOtros(evolucion.getOtros() + "Direccion,");
				if (vehiculoBBDD.getDireccion() != null && vehiculoBBDD.getDireccion().getIdDireccion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdDirectivaCee(), vehiculoBBDD.getIdDirectivaCee())) {
				evolucion.setOtros(evolucion.getOtros() + "Directiva Cee,");
				if (vehiculoBBDD.getIdDirectivaCee() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getDistanciaEjes(), vehiculoBBDD.getDistanciaEjes())) {
				evolucion.setOtros(evolucion.getOtros() + "Distancia Ejes,");
				if (vehiculoBBDD.getDistanciaEjes() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getEcoInnovacion(), vehiculoBBDD.getEcoInnovacion())) {
				evolucion.setOtros(evolucion.getOtros() + "Eco Innovación,");
				if (vehiculoBBDD.getEcoInnovacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdEpigrafe(), vehiculoBBDD.getIdEpigrafe())) {
				evolucion.setOtros(evolucion.getOtros() + "Epigrafe,");
				if (vehiculoBBDD.getIdEpigrafe() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getEstacionItv(), vehiculoBBDD.getEstacionItv())) {
				evolucion.setOtros(evolucion.getOtros() + "Estación Itv,");
				if (vehiculoBBDD.getEstacionItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getExcesoPeso(), vehiculoBBDD.getExcesoPeso())) {
				evolucion.setOtros(evolucion.getOtros() + "Exceso Peso,");
				if (vehiculoBBDD.getExcesoPeso() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getFabricacion(), vehiculoBBDD.getFabricacion())) {
				evolucion.setOtros(evolucion.getOtros() + "Fabricación,");
				if (vehiculoBBDD.getFabricacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getFabricante(), vehiculoBBDD.getFabricante())) {
				evolucion.setOtros(evolucion.getOtros() + "Fabricante,");
				if (vehiculoBBDD.getFabricante() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getFabricanteBase(), vehiculoBBDD.getFabricanteBase())) {
				evolucion.setOtros(evolucion.getOtros() + "Fabricante Base,");
				if (vehiculoBBDD.getFabricanteBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFecdesde(), vehiculoBBDD.getFecdesde()) != 0) {
				evolucion.setOtros(evolucion.getOtros() + "Fecha desde,");
				if (vehiculoBBDD.getFecdesde() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFechaInspeccion(), vehiculoBBDD.getFechaInspeccion()) != 0) {
				evolucion.setOtros(evolucion.getOtros() + "Fecha Inspección,");
				if (vehiculoBBDD.getFechaInspeccion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFechaItv(), vehiculoBBDD.getFechaItv()) != 0) {
				evolucion.setOtros(evolucion.getOtros() + "Fecha Itv,");
				if (vehiculoBBDD.getFechaItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFechaMatriculacion(), vehiculoBBDD.getFechaMatriculacion()) != 0) {
				evolucion.setOtros(evolucion.getOtros() + "Fecha Matriculación,");
				if (vehiculoBBDD.getFechaMatriculacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getIdVehiculoPrever(), vehiculoBBDD.getIdVehiculoPrever())) {
				evolucion.setOtros(evolucion.getOtros() + "Vehiculo Prever,");
				if (vehiculoBBDD.getIdVehiculoPrever() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdLugarAdquisicion(), vehiculoBBDD.getIdLugarAdquisicion())) {
				evolucion.setOtros(evolucion.getOtros() + "Lugar Adquisición,");
				if (vehiculoBBDD.getIdLugarAdquisicion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCodigoMarca(), vehiculoBBDD.getCodigoMarca())) {
				evolucion.setOtros(evolucion.getOtros() + "Marca,");
				if (vehiculoBBDD.getCodigoMarca() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCodigoMarcaBase(), vehiculoBBDD.getCodigoMarcaBase())) {
				evolucion.setOtros(evolucion.getOtros() + "Marca Base,");
				if (vehiculoBBDD.getCodigoMarcaBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatricula() != null ? vehiculoVO.getMatricula().toUpperCase() : null, vehiculoBBDD.getMatricula() != null ? vehiculoBBDD.getMatricula()
					.toUpperCase() : null)) {
				evolucion.setMatriculaAnt(vehiculoBBDD.getMatricula() != null ? vehiculoBBDD.getMatricula().toUpperCase() : null);
				if (vehiculoBBDD.getMatricula() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
				evolucion.setMatriculaNue(vehiculoVO.getMatricula() != null ? vehiculoVO.getMatricula() : null);
			}
			if (!utiles.sonIgualesString(vehiculoVO.getModelo() != null ? vehiculoVO.getModelo().toUpperCase() : null, vehiculoBBDD.getModelo() != null ? vehiculoBBDD.getModelo().toUpperCase()
					: null)) {
				evolucion.setOtros(evolucion.getOtros() + "Modelo,");
				if (vehiculoBBDD.getModelo() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getMom(), vehiculoBBDD.getMom())) {
				evolucion.setOtros(evolucion.getOtros() + "Mom,");
				if (vehiculoBBDD.getMom() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getMomBase(), vehiculoBBDD.getMomBase())) {
				evolucion.setOtros(evolucion.getOtros() + "Mom Base,");
				if (vehiculoBBDD.getMomBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdMotivoItv(), vehiculoBBDD.getIdMotivoItv())) {
				evolucion.setOtros(evolucion.getOtros() + "Motivo Itv,");
				if (vehiculoBBDD.getIdMotivoItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMtmaItv(), vehiculoBBDD.getMtmaItv())) {
				evolucion.setOtros(evolucion.getOtros() + "Mtma Itv,");
				if (vehiculoBBDD.getMtmaItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getnCilindros(), vehiculoBBDD.getnCilindros())) {
				evolucion.setOtros(evolucion.getOtros() + "Numero Cilindros,");
				if (vehiculoBBDD.getnCilindros() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getnHomologacion() != null ? vehiculoVO.getnHomologacion().toUpperCase() : null, vehiculoBBDD.getnHomologacion() != null ? vehiculoBBDD
					.getnHomologacion().toUpperCase() : null)) {
				evolucion.setOtros(evolucion.getOtros() + "Número Homologación,");
				if (vehiculoBBDD.getnHomologacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getnHomologacionBase() != null ? vehiculoVO.getnHomologacionBase().toUpperCase() : null, vehiculoBBDD.getnHomologacionBase() != null ? vehiculoBBDD
					.getnHomologacionBase().toUpperCase() : null)) {
				evolucion.setOtros(evolucion.getOtros() + "Número Homologación Base,");
				if (vehiculoBBDD.getnHomologacionBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getNifIntegrador() != null ? vehiculoVO.getNifIntegrador().toUpperCase() : null, vehiculoBBDD.getNifIntegrador() != null ? vehiculoBBDD
					.getNifIntegrador().toUpperCase() : null)) {
				evolucion.setOtros(evolucion.getOtros() + "Nif Integrador,");
				if (vehiculoBBDD.getNifIntegrador() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getNive(), vehiculoBBDD.getNive())) {
				evolucion.setOtros(evolucion.getOtros() + "Nive,");
				if (vehiculoBBDD.getNive() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getNivelEmisiones(), vehiculoBBDD.getNivelEmisiones())) {
				evolucion.setOtros(evolucion.getOtros() + "Nivel Emisiones,");
				if (vehiculoBBDD.getNivelEmisiones() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getnPlazasPie(), vehiculoBBDD.getnPlazasPie())) {
				evolucion.setOtros(evolucion.getOtros() + "Num Plazas de Pie,");
				if (vehiculoBBDD.getnPlazasPie() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getnRuedas(), vehiculoBBDD.getnRuedas())) {
				evolucion.setOtros(evolucion.getOtros() + "Número Ruedas,");
				if (vehiculoBBDD.getnRuedas() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getnSerie(), vehiculoBBDD.getnSerie())) {
				evolucion.setOtros(evolucion.getOtros() + "Número serie,");
				if (vehiculoBBDD.getnSerie() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getPaisFabricacion(), vehiculoBBDD.getPaisFabricacion())) {
				evolucion.setOtros(evolucion.getOtros() + "Pais Fabricación,");
				if (vehiculoBBDD.getPaisFabricacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getPaisImportacion(), vehiculoBBDD.getPaisImportacion())) {
				evolucion.setOtros(evolucion.getOtros() + "Pais Importación,");
				if (vehiculoBBDD.getPaisImportacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getPesoMma(), vehiculoBBDD.getPesoMma())) {
				evolucion.setOtros(evolucion.getOtros() + "Peso Mma,");
				if (vehiculoBBDD.getPesoMma() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getPlazas(), vehiculoBBDD.getPlazas())) {
				evolucion.setOtros(evolucion.getOtros() + "Plazas,");
				if (vehiculoBBDD.getPlazas() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesBigDecimal(vehiculoVO.getPotenciaFiscal(), vehiculoBBDD.getPotenciaFiscal())) {
				evolucion.setOtros(evolucion.getOtros() + "Potencia Fiscal,");
				if (vehiculoBBDD.getPotenciaFiscal() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesBigDecimal(vehiculoVO.getPotenciaNeta(), vehiculoBBDD.getPotenciaNeta())) {
				evolucion.setOtros(evolucion.getOtros() + "Potencia Neta,");
				if (vehiculoBBDD.getPotenciaNeta() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesBigDecimal(vehiculoVO.getPotenciaPeso(), vehiculoBBDD.getPotenciaPeso())) {
				evolucion.setOtros(evolucion.getOtros() + "Potencia Peso,");
				if (vehiculoBBDD.getPotenciaPeso() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getProcedencia(), vehiculoBBDD.getProcedencia())) {
				evolucion.setOtros(evolucion.getOtros() + "Procedencia,");
				if (vehiculoBBDD.getProcedencia() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getProvinciaPrimeraMatricula(), vehiculoBBDD.getProvinciaPrimeraMatricula())) {
				evolucion.setOtros(evolucion.getOtros() + "Provincia Primera Matricula,");
				if (vehiculoBBDD.getProvinciaPrimeraMatricula() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getReduccionEco(), vehiculoBBDD.getReduccionEco())) {
				evolucion.setOtros(evolucion.getOtros() + "Reducción Eco,");
				if (vehiculoBBDD.getReduccionEco() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdServicio(), vehiculoBBDD.getIdServicio())) {
				evolucion.setOtros(evolucion.getOtros() + "Servicio Trafico,");
				if (vehiculoBBDD.getIdServicio() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdServicioAnterior(), vehiculoBBDD.getIdServicioAnterior())) {
				evolucion.setOtros(evolucion.getOtros() + "Servicio Trafico Anterior,");
				if (vehiculoBBDD.getIdServicioAnterior() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTara(), vehiculoBBDD.getTara())) {
				evolucion.setOtros(evolucion.getOtros() + "Tara,");
				if (vehiculoBBDD.getTara() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoAlimentacion(), vehiculoBBDD.getTipoAlimentacion())) {
				evolucion.setOtros(evolucion.getOtros() + "Tipo Alimentación,");
				if (vehiculoBBDD.getTipoAlimentacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoBase(), vehiculoBBDD.getTipoBase())) {
				evolucion.setOtros(evolucion.getOtros() + "Tipo Base,");
				if (vehiculoBBDD.getTipoBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoIndustria(), vehiculoBBDD.getTipoIndustria())) {
				evolucion.setOtros(evolucion.getOtros() + "Tipo Industria,");
				if (vehiculoBBDD.getTipoIndustria() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoItv(), vehiculoBBDD.getTipoItv())) {
				evolucion.setOtros(evolucion.getOtros() + "Tipo Itv,");
				if (vehiculoBBDD.getTipoItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdTipoTarjetaItv(), vehiculoBBDD.getIdTipoTarjetaItv())) {
				evolucion.setOtros(evolucion.getOtros() + "Tipo Tarjeta ITV,");
				if (vehiculoBBDD.getIdTipoTarjetaItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getTipoVehiculo(), vehiculoBBDD.getTipoVehiculo())) {
				evolucion.setTipoVehiculoAnt(utiles.convertirCombo(vehiculoBBDD.getTipoVehiculo()));
				if (vehiculoBBDD.getTipoVehiculo() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
				evolucion.setTipoVehiculoNue(utiles.convertirCombo(vehiculoVO.getTipoVehiculo()));
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipvehi(), vehiculoBBDD.getTipvehi())) {
				evolucion.setOtros(evolucion.getOtros() + "Tipo Vehículo cam,");
				if (vehiculoBBDD.getTipvehi() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVariante(), vehiculoBBDD.getVariante())) {
				evolucion.setOtros(evolucion.getOtros() + "Variante,");
				if (vehiculoBBDD.getVariante() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVarianteBase(), vehiculoBBDD.getVarianteBase())) {
				evolucion.setOtros(evolucion.getOtros() + "Variante Base,");
				if (vehiculoBBDD.getVarianteBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getVehiUsado(), vehiculoBBDD.getVehiUsado())) {
				evolucion.setOtros(evolucion.getOtros() + "Vehículo Usado,");
				if (vehiculoBBDD.getVehiUsado() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatriAyuntamiento(), vehiculoBBDD.getMatriAyuntamiento())) {
				evolucion.setOtros(evolucion.getOtros() + "Matriculacion Ayuntamiento,");
				if (vehiculoBBDD.getMatriAyuntamiento() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getLimiteMatrTuris(), vehiculoBBDD.getLimiteMatrTuris()) != 0) {
				evolucion.setOtros(evolucion.getOtros() + "Limite Matriculación Turismo,");
				if (vehiculoBBDD.getLimiteMatrTuris() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFechaPrimMatri(), vehiculoBBDD.getFechaPrimMatri()) != 0) {
				evolucion.setOtros(evolucion.getOtros() + "Fecha Primera Matriculación,");
				if (vehiculoBBDD.getFechaPrimMatri() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdLugarMatriculacion(), vehiculoBBDD.getIdLugarMatriculacion())) {
				evolucion.setOtros(evolucion.getOtros() + "Lugar Primera Matriculación,");
				if (vehiculoBBDD.getIdLugarMatriculacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatriculaOrigen(), vehiculoBBDD.getMatriculaOrigen())) {
				evolucion.setOtros(evolucion.getOtros() + "Matricula Origen,");
				if (vehiculoBBDD.getMatriculaOrigen() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getKmUso(), vehiculoBBDD.getKmUso())) {
				evolucion.setOtros(evolucion.getOtros() + "Km Uso,");
				if (vehiculoBBDD.getKmUso() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getHorasUso(), vehiculoBBDD.getHorasUso())) {
				evolucion.setOtros(evolucion.getOtros() + "Horas uso,");
				if (vehiculoBBDD.getHorasUso() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getImportado(), vehiculoBBDD.getImportado())) {
				evolucion.setOtros(evolucion.getOtros() + "Importado,");
				if (vehiculoBBDD.getImportado() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdProcedencia(), vehiculoBBDD.getIdProcedencia())) {
				evolucion.setOtros(evolucion.getOtros() + "Procedencia,");
				if (vehiculoBBDD.getIdProcedencia() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVersion(), vehiculoBBDD.getVersion())) {
				evolucion.setOtros(evolucion.getOtros() + "Versión,");
				if (vehiculoBBDD.getVersion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVersionBase(), vehiculoBBDD.getVersionBase())) {
				evolucion.setOtros(evolucion.getOtros() + "Versión Base,");
				if (vehiculoBBDD.getVersionBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getViaAnterior(), vehiculoBBDD.getViaAnterior())) {
				evolucion.setOtros(evolucion.getOtros() + "Via Anterior,");
				if (vehiculoBBDD.getViaAnterior() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getViaPosterior(), vehiculoBBDD.getViaPosterior())) {
				evolucion.setOtros(evolucion.getOtros() + "Via Posterior,");
				if (vehiculoBBDD.getViaPosterior() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatriculaOrigExtr(), vehiculoBBDD.getMatriculaOrigExtr())) {
				evolucion.setOtros(evolucion.getOtros() + "Matricula Origen Extranjera,");
				if (vehiculoBBDD.getMatriculaOrigExtr() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			resultado.setEvolucionVehiculo(evolucion);
		} catch (Exception e) {
			log.error("Error al comparar los vehiculo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al comparar los vehiculo.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public VehiculoVO getVehiculoVO(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estadoVehiculo) {
		try {
			return vehiculoDao.getVehiculo(idVehiculo, numColegiado, matricula, bastidor, nive, estadoVehiculo);
		} catch (Exception e) {
			log.error("Error el obtener el vehiculo", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerNombreMarca(String codigoMarca, boolean versionMatw) {
		try {
			if (StringUtils.isNotBlank(codigoMarca)) {
				MarcaDgtVO marcaDgtVO = servicioMarcaDgt.getMarcaDgt(codigoMarca, null, versionMatw);
				if (marcaDgtVO != null) {
					return marcaDgtVO.getMarca();
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre de la marca", e);
		}
		return "";
	}

	@Override
	@Transactional(readOnly = true)
	public TipoVehiculoVO obtenerTipoVehiculo(String tipoVehiculo) {
		try {
			if (StringUtils.isNotBlank(tipoVehiculo)) {
				TipoVehiculoVO tipoVehiculoVO = servicioTipoVehiculo.getTipoVehiculo(tipoVehiculo);
				if (tipoVehiculoVO != null) {
					return tipoVehiculoVO;
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo vehiculo", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isBastidorDuplicado(String bastidor, String colegiado) {
		List<Object[]> listaBastidores = vehiculoDao.getBastidor(bastidor, colegiado);
		boolean bastidorDuplicado = false;
		for (int i = 0; i < listaBastidores.size(); i++) {
			if (!listaBastidores.isEmpty() && listaBastidores.size() > 1) {
				bastidorDuplicado = true;
			}
		}
		return bastidorDuplicado;
	}

	@Override
	@Transactional(readOnly = true)
	public MarcaFabricanteVO obtenerMarcaFabricante(String fabricante, String codigoMarca) {
		try {
			if (fabricante != null && !fabricante.isEmpty() && codigoMarca != null && !codigoMarca.isEmpty()) {
				FabricanteVO fabricanteVO = servicioFabricante.getFabricante(fabricante);
				if (fabricanteVO != null) {
					return servicioMarcaFabricante.getMarcaFabricante(codigoMarca, fabricanteVO.getCodFabricante());
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar la marca fabricante", e);
		}
		return null;
	}
}
