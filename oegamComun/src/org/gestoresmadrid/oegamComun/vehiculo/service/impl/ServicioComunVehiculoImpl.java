package org.gestoresmadrid.oegamComun.vehiculo.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.constantes.ConstantesTrafico;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.vehiculo.model.dao.EvolucionVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.dao.MarcaDgtDao;
import org.gestoresmadrid.core.vehiculo.model.dao.MotivoItvDao;
import org.gestoresmadrid.core.vehiculo.model.dao.ServicioTraficoDao;
import org.gestoresmadrid.core.vehiculo.model.dao.TipoVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoPK;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MotivoItvVO;
import org.gestoresmadrid.core.vehiculo.model.vo.ServicioTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.direccion.view.bean.ResultadoDireccionBean;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunFabricante;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioPersistenciaVehiculo;
import org.gestoresmadrid.oegamComun.vehiculo.view.bean.ResultadoValVehiculoBean;
import org.gestoresmadrid.oegamComun.vehiculo.view.bean.ResultadoVehiculoBean;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioComunVehiculoImpl implements ServicioComunVehiculo {

	private static final long serialVersionUID = -3790581307083710873L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunVehiculoImpl.class);

	@Autowired
	TipoVehiculoDao tipoVehiculoDao;

	@Autowired
	MarcaDgtDao marcaDgtDao;

	@Autowired
	MotivoItvDao motivoItvDao;

	@Autowired
	ServicioTraficoDao servicioTraficoDao;

	@Autowired
	EvolucionVehiculoDao evolucionVehiculoDao;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioPersistenciaVehiculo servicioPersistenciaVehiculo;

	@Autowired
	ServicioComunDireccion servicioComunDireccion;

	@Autowired
	ServicioComunFabricante servicioComunFabricante;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioValidacionVehiculo servicioValidacionVehiculo;

	@Autowired
	ServicioComunTramiteTrafico servicioComunTramiteTrafico;

	@Autowired
	ServicioComunPersona servicioComunPersona;

	@Override
	public ResultadoVehiculoBean getVehiculo(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estado) {
		ResultadoVehiculoBean resultado = new ResultadoVehiculoBean(Boolean.FALSE);
		try {
			VehiculoVO vehiculoBBDD = servicioPersistenciaVehiculo.getVehiculoVO(idVehiculo, numColegiado, matricula, bastidor, nive, estado);
			if (vehiculoBBDD != null) {
				resultado.setVehiculo(vehiculoBBDD);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del vehiculo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del vehiculo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los datos del vehiculo.");
		}
		return resultado;
	}

	@Override
	public ResultadoVehiculoBean guardarVehiculoConPrever(VehiculoVO vehiculoVO, BigDecimal numExpediente, String tipoTramite, Long idUsuario, Date fechaPresentacion, String conversionVehiculo,
			VehiculoDto vehiculoPreverDto) {
		ResultadoVehiculoBean resultado = new ResultadoVehiculoBean(Boolean.FALSE);
		try {
			Boolean guardarIntegrador = Boolean.FALSE;
			if (vehiculoPreverDto != null && StringUtils.isNotBlank(vehiculoPreverDto.getBastidor())) {
				// Guardar vehiculo prever
				VehiculoVO vehiculoPreverVO = conversor.transform(vehiculoPreverDto, VehiculoVO.class);
				vehiculoPreverVO.setNumColegiado(vehiculoVO.getNumColegiado());
				ResultadoVehiculoBean resultVehPrever = guardarVehiculo(vehiculoPreverVO, numExpediente, tipoTramite, idUsuario, fechaPresentacion, CONVERSION_VEHICULO_PREVER, Boolean.TRUE,
						guardarIntegrador, null);
				if (!resultVehPrever.getError() && resultVehPrever.getVehiculo() != null) {
					vehiculoVO.setIdVehiculoPrever(new BigDecimal(resultVehPrever.getVehiculo().getIdVehiculo()));
				} else {
					resultado.addListaMensaje(resultVehPrever.getMensaje());
				}
			}
			EvolucionPersonaVO evolucionPersonaVO = null;
			if (vehiculoVO.getPersona() != null && vehiculoVO.getPersona().getId() != null && StringUtils.isNotBlank(vehiculoVO.getPersona().getId().getNif())) {
				ResultadoPersonaBean resultIntegrador = servicioComunPersona.tratarGuardadoPersona(vehiculoVO.getPersona(), numExpediente, tipoTramite, idUsuario,
						ServicioComunPersona.CONVERSION_PERSONA_INTEGRADOR);
				if (resultIntegrador.getError()) {
					resultado.addListaMensaje(resultIntegrador.getMensaje());
				} else {
					guardarIntegrador = Boolean.TRUE;
					vehiculoVO.setNifIntegrador(resultIntegrador.getPersona().getId().getNif());
					vehiculoVO.setPersona(resultIntegrador.getPersona());
					evolucionPersonaVO = resultIntegrador.getEvolucionPersona();
				}
			}
			ResultadoVehiculoBean resultVehiculo = guardarVehiculo(vehiculoVO, numExpediente, tipoTramite, idUsuario, fechaPresentacion, conversionVehiculo, Boolean.FALSE, guardarIntegrador,
					evolucionPersonaVO);
			if (resultVehiculo.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultVehiculo.getMensaje());
			} else {
				if (resultVehiculo.getListaMensajes() != null && !resultVehiculo.getListaMensajes().isEmpty()) {
					resultado.addListaMensajes(resultVehiculo.getListaMensajes());
				}
				resultado.setVehiculo(resultVehiculo.getVehiculo());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar el vehiculo del tramite: " + numExpediente + " para guardar, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el vehiculo del tramite: " + numExpediente + " para guardar.");
		}
		return resultado;
	}

	@Override
	public ResultadoVehiculoBean guardarVehiculo(VehiculoVO vehiculoPantalla, BigDecimal numExpediente, String tipoTramite, Long idUsuario, Date fechaPresentacion, String conversionVehiculo,
			Boolean esPrever, Boolean guardarIntegrador, EvolucionPersonaVO evolucionIntegrador) {
		ResultadoVehiculoBean resultado = new ResultadoVehiculoBean(Boolean.FALSE);
		VehiculoVO vehiculoBBDD = null;
		EvolucionVehiculoVO evolucionVehiculo = new EvolucionVehiculoVO();
		EvolucionVehiculoPK id = new EvolucionVehiculoPK();
		try {
			id.setNumColegiado(vehiculoPantalla.getNumColegiado());
			evolucionVehiculo.setId(id);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario);
			evolucionVehiculo.setUsuario(usuario);
			evolucionVehiculo.setTipoTramite(tipoTramite);
			evolucionVehiculo.setNumExpediente(numExpediente);
			evolucionVehiculo.setOtros("");
			if ((tipoTramite.equals(TIPO_TRAMITE_MANTENIMIENTO) && !esPrever) || tipoTramite.equals(TIPO_TRAMITE_MATE_EITV) || tipoTramite.equals(MATRICULAR)) {
				vehiculoBBDD = servicioPersistenciaVehiculo.getVehiculoVO(vehiculoPantalla.getIdVehiculo(), vehiculoPantalla.getNumColegiado(), null, null, null, EstadoVehiculo.Activo);
				if (tipoTramite.equals(MATRICULAR)) {
					tipoTramite = TipoTramiteTrafico.Matriculacion.getValorEnum();
				}
			} else if (vehiculoPantalla.getBastidor() != null && !vehiculoPantalla.getBastidor().isEmpty()) {
				vehiculoBBDD = servicioPersistenciaVehiculo.getVehiculoVO(null, vehiculoPantalla.getNumColegiado(), null, vehiculoPantalla.getBastidor(), null, EstadoVehiculo.Activo);
			} else if (vehiculoPantalla.getMatricula() != null && !vehiculoPantalla.getMatricula().isEmpty()) {
				vehiculoBBDD = servicioPersistenciaVehiculo.getVehiculoVO(null, vehiculoPantalla.getNumColegiado(), vehiculoPantalla.getMatricula(), null, null, EstadoVehiculo.Activo);
			} else if (vehiculoPantalla.getNive() != null && !vehiculoPantalla.getNive().isEmpty()) {
				vehiculoBBDD = servicioPersistenciaVehiculo.getVehiculoVO(null, vehiculoPantalla.getNumColegiado(), null, null, vehiculoPantalla.getNive(), EstadoVehiculo.Activo);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Faltan datos obligatorios para realizar la búsqueda (Matrícula o bastidor)");
				return resultado;
			}

			if (vehiculoBBDD != null && vehiculoBBDD.getDireccion() != null && !esPrever) {
				vehiculoPantalla.getDireccion().setIdDireccion(vehiculoBBDD.getDireccion().getIdDireccion());
			}
			ResultadoDireccionBean resultDirVeh = guardarDireccion(vehiculoPantalla, tipoTramite);
			if (resultDirVeh.getError()) {
				resultado.addListaMensaje(resultDirVeh.getMensaje());
			}
			// Nueva
			if (vehiculoBBDD == null) {
				modificarDatos(vehiculoPantalla, tipoTramite, fechaPresentacion, evolucionVehiculo);
				evolucionVehiculo.setTipoActualizacion(TIPO_ACTUALIZACION_CRE);
				resultado.setVehiculo(servicioPersistenciaVehiculo.guardarVehiculoConEvolucionVO(vehiculoPantalla, evolucionVehiculo, resultDirVeh.getGuardarDir(), guardarIntegrador,
						evolucionIntegrador));
			} else {
				VehiculoVO vehiculoCompleto = (VehiculoVO) vehiculoBBDD.clone();
				if (conversionVehiculo != null) {
					conversor.transform(vehiculoPantalla, vehiculoCompleto, conversionVehiculo);
				} else {
					conversor.transform(vehiculoPantalla, vehiculoCompleto);
				}
				ResultadoValVehiculoBean resultadoValVehiculo = servicioValidacionVehiculo.esModificadoVehiculo(vehiculoCompleto, vehiculoBBDD);
				if (!resultadoValVehiculo.getError()) {
					gestionarEvolucionValidacion(resultadoValVehiculo, evolucionVehiculo);
					if (resultadoValVehiculo.getValorModificado() != 0) {
						modificarDatos(vehiculoCompleto, tipoTramite, fechaPresentacion, evolucionVehiculo);
						if (resultadoValVehiculo.getValorModificado() == 2 && servicioComunTramiteTrafico.getNumTramitePorVehiculo(numExpediente, vehiculoCompleto.getIdVehiculo()) > 0) {
							resultado.setVehiculo(servicioPersistenciaVehiculo.copiaVehiculoDesactivandoAnt(vehiculoCompleto, vehiculoBBDD, evolucionVehiculo, numExpediente, tipoTramite, idUsuario,
									resultDirVeh.getGuardarDir(), guardarIntegrador, evolucionIntegrador));
						} else {
							evolucionVehiculo.setTipoActualizacion(TIPO_ACTUALIZACION_MOD);
							servicioPersistenciaVehiculo.actualizarVehiculoConEvolucionVO(vehiculoCompleto, evolucionVehiculo, resultDirVeh.getGuardarDir(), guardarIntegrador, evolucionIntegrador);
							resultado.setVehiculo(vehiculoCompleto);
						}
					} else {
						if (resultDirVeh.getGuardarDir() != null && resultDirVeh.getGuardarDir()) {
							servicioComunDireccion.guardarActualizarDireccion(resultDirVeh.getDireccion());
						}
						resultado.setVehiculo(vehiculoBBDD);
					}
				} else {
					resultado.setMensaje(resultadoValVehiculo.getMensaje());
					resultado.setError(Boolean.TRUE);
				}
			}
		} catch (Throwable e) {
			resultado.setError(Boolean.TRUE);
			if (esPrever) {
				log.error("Ha sucedido un error a la hora de guardar el vehiculo prever del tramite: " + numExpediente + ", error: ", e);
				resultado.setMensaje("Ha sucedido un error a la hora de guardar el vehiculo prever del tramite: " + numExpediente);
			} else {
				log.error("Ha sucedido un error a la hora de guardar el vehiculo del tramite: " + numExpediente + ", error: ", e);
				resultado.setMensaje("Ha sucedido un error a la hora de guardar el vehiculo del tramite: " + numExpediente);
			}
		}
		return resultado;
	}

	private void gestionarEvolucionValidacion(ResultadoValVehiculoBean resultadoValVehiculo, EvolucionVehiculoVO evolucionVehiculo) {
		if (StringUtils.isNotBlank(resultadoValVehiculo.getTextoModificado())) {
			evolucionVehiculo.setOtros(evolucionVehiculo.getOtros() + resultadoValVehiculo.getTextoModificado());
		}
		if (StringUtils.isNotBlank(resultadoValVehiculo.getBastidorAnt())) {
			evolucionVehiculo.setBastidorAnt(resultadoValVehiculo.getBastidorAnt());
		}
		if (StringUtils.isNotBlank(resultadoValVehiculo.getBastidorNue())) {
			evolucionVehiculo.setBastidorNue(resultadoValVehiculo.getBastidorNue());
		}
		if (StringUtils.isNotBlank(resultadoValVehiculo.getCodigoItvAnt())) {
			evolucionVehiculo.setCodigoItvAnt(resultadoValVehiculo.getCodigoItvAnt());
		}
		if (StringUtils.isNotBlank(resultadoValVehiculo.getCodigoItvNue())) {
			evolucionVehiculo.setCodigoItvNue(resultadoValVehiculo.getCodigoItvNue());
		}
		if (StringUtils.isNotBlank(resultadoValVehiculo.getMatriculaAnt())) {
			evolucionVehiculo.setMatriculaAnt(resultadoValVehiculo.getMatriculaAnt());
		}
		if (StringUtils.isNotBlank(resultadoValVehiculo.getMatriculaNue())) {
			evolucionVehiculo.setMatriculaNue(resultadoValVehiculo.getMatriculaNue());
		}
		if (StringUtils.isNotBlank(resultadoValVehiculo.getTipoVehiculoAnt())) {
			evolucionVehiculo.setTipoVehiculoAnt(resultadoValVehiculo.getTipoVehiculoAnt());
		}
		if (StringUtils.isNotBlank(resultadoValVehiculo.getTipoVehiculoNue())) {
			evolucionVehiculo.setTipoVehiculoNue(resultadoValVehiculo.getTipoVehiculoNue());
		}
	}

	private void modificarDatos(VehiculoVO vehiculoVO, String tipoTramite, Date fechaPresentacion, EvolucionVehiculoVO evolucionVehiculo) {
		vehiculoVO.setFechaUltmModif(new Date());
		modificarDatosCombos(vehiculoVO);
		modificarDatosMayusculas(vehiculoVO);
		if (!TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
			procesarCodigoItvOriginal(vehiculoVO, evolucionVehiculo);
			if (vehiculoVO.getTipoVehiculo() != null && (vehiculoVO.getTipoVehiculo().startsWith("R") || vehiculoVO.getTipoVehiculo().startsWith("S"))) {
				if (vehiculoVO.getPlazas() == null || vehiculoVO.getPlazas().intValue() > 0) {
					vehiculoVO.setPlazas(new BigDecimal(0));
					if (evolucionVehiculo != null) {
						evolucionVehiculo.setOtros(evolucionVehiculo.getOtros() + "Plazas,");
					}
				}
			}
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite) || TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite) || TipoTramiteTrafico.TransmisionElectronica
					.getValorEnum().equals(tipoTramite)) {
				Date fechaItv = calcularFechaItv(vehiculoVO, tipoTramite, fechaPresentacion);
				if (fechaItv != null) {
					vehiculoVO.setFechaItv(fechaItv);
				}
			}
			String fabricante = obtenerFabricante(vehiculoVO.getFabricante());
			if (fabricante != null && !fabricante.isEmpty()) {
				vehiculoVO.setFabricante(fabricante);
			}
		}

		// Se añade al campo tipo industria la clasificacion itv
		if (vehiculoVO.getClasificacionItv() != null && !vehiculoVO.getClasificacionItv().isEmpty()) {
			vehiculoVO.setTipoIndustria(vehiculoVO.getClasificacionItv());
		}
	}

	public String obtenerFabricante(String fabricante) {
		try {
			if (fabricante != null && !fabricante.isEmpty()) {
				FabricanteVO fabricanteVO = servicioComunFabricante.getFabricante(fabricante);
				if (fabricanteVO != null) {
					return fabricanteVO.getFabricante();
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el fabricante", e);
		}
		return "";
	}

	private Date calcularFechaItv(VehiculoVO vehiculoVO, String tipoTramite, Date fechaPresentacion) {
		if (vehiculoVO.getTipoVehiculo() != null && !"SI".equals(vehiculoVO.getCheckFechaCaducidadItv()) && ((vehiculoVO.getIdTipoTarjetaItv() != null && !TipoTarjetaITV.A.getValorEnum().equals(
				vehiculoVO.getIdTipoTarjetaItv())) || vehiculoVO.getFechaItv() == null)) {
			return servicioPersistenciaVehiculo.calculoItvPQ(vehiculoVO, fechaPresentacion, tipoTramite);
		}
		return null;
	}

	private void procesarCodigoItvOriginal(VehiculoVO vehiculoVO, EvolucionVehiculoVO evolucionVehiculo) {
		String coditvOriginal = vehiculoVO.getCoditvOriginal();

		if (vehiculoVO.getCoditvOriginal() == null) {
			vehiculoVO.setCoditvOriginal(vehiculoVO.getCoditv());
		} else if (vehiculoVO.getCoditvOriginal() != null && !ConstantesTrafico.SIN_CODIG.equals(vehiculoVO.getCoditv())) {
			vehiculoVO.setCoditvOriginal(vehiculoVO.getCoditv());
		} else if (vehiculoVO.getCoditv() == null) {
			vehiculoVO.setCoditvOriginal(null);
		}

		if (evolucionVehiculo != null && !utiles.sonIgualesString(vehiculoVO.getCoditvOriginal(), coditvOriginal)) {
			evolucionVehiculo.setOtros(evolucionVehiculo.getOtros() + "Cod Itv Original,");
		}
	}

	private void modificarDatosMayusculas(VehiculoVO vehiculoVO) {
		if (vehiculoVO.getMatricula() != null) {
			vehiculoVO.setMatricula(vehiculoVO.getMatricula().toUpperCase());
			vehiculoVO.setMatricula(vehiculoVO.getMatricula().replace(" ", "").replace("-", "").replace("_", ""));
		}
		if (vehiculoVO.getBastidor() != null) {
			vehiculoVO.setBastidor(vehiculoVO.getBastidor().toUpperCase());
		}
		if (vehiculoVO.getModelo() != null) {
			vehiculoVO.setModelo(vehiculoVO.getModelo().toUpperCase());
		}
		if (vehiculoVO.getnHomologacion() != null) {
			vehiculoVO.setnHomologacion(vehiculoVO.getnHomologacion().toUpperCase());
		}
		if (vehiculoVO.getnHomologacionBase() != null) {
			vehiculoVO.setnHomologacionBase(vehiculoVO.getnHomologacionBase().toUpperCase());
		}
		if (vehiculoVO.getNifIntegrador() != null) {
			vehiculoVO.setNifIntegrador(vehiculoVO.getNifIntegrador().toUpperCase());
		}
		if (vehiculoVO.getNive() != null) {
			vehiculoVO.setNive(vehiculoVO.getNive().toUpperCase());
		}
		if (vehiculoVO.getMatriculaOrigen() != null) {
			vehiculoVO.setMatriculaOrigen(vehiculoVO.getMatriculaOrigen().toUpperCase());
		}
		if (vehiculoVO.getMatriculaOrigExtr() != null) {
			vehiculoVO.setMatriculaOrigExtr(vehiculoVO.getMatriculaOrigExtr().toUpperCase());
		}
		if (vehiculoVO.getNivelEmisiones() != null) {
			vehiculoVO.setNivelEmisiones(vehiculoVO.getNivelEmisiones().toUpperCase());
		}
	}

	private void modificarDatosCombos(VehiculoVO vehiculoVO) {
		vehiculoVO.setTipoVehiculo(utiles.convertirCombo(vehiculoVO.getTipoVehiculo()));
		vehiculoVO.setIdServicio(utiles.convertirCombo(vehiculoVO.getIdServicio()));
		vehiculoVO.setIdServicioAnterior(utiles.convertirCombo(vehiculoVO.getIdServicioAnterior()));
		vehiculoVO.setConceptoItv(utiles.convertirCombo(vehiculoVO.getConceptoItv()));
		vehiculoVO.setIdCriterioConstruccion(utiles.convertirCombo(vehiculoVO.getIdCriterioConstruccion()));
		vehiculoVO.setIdCriterioUtilizacion(utiles.convertirCombo(vehiculoVO.getIdCriterioUtilizacion()));
		vehiculoVO.setIdDirectivaCee(utiles.convertirCombo(vehiculoVO.getIdDirectivaCee()));
		vehiculoVO.setIdMotivoItv(utiles.convertirCombo(vehiculoVO.getIdMotivoItv()));
		vehiculoVO.setEstacionItv(utiles.convertirCombo(vehiculoVO.getEstacionItv()));
		vehiculoVO.setIdEpigrafe(utiles.convertirCombo(vehiculoVO.getIdEpigrafe()));
		vehiculoVO.setIdLugarMatriculacion(utiles.convertirCombo(vehiculoVO.getIdLugarMatriculacion()));
		vehiculoVO.setCarroceria(utiles.convertirCombo(vehiculoVO.getCarroceria()));
		vehiculoVO.setIdColor(utiles.convertirCombo(vehiculoVO.getIdColor()));
		vehiculoVO.setIdCarburante(utiles.convertirCombo(vehiculoVO.getIdCarburante()));
		vehiculoVO.setIdTipoTarjetaItv(utiles.convertirCombo(vehiculoVO.getIdTipoTarjetaItv()));
		vehiculoVO.setClasificacionItv(utiles.convertirCombo(vehiculoVO.getClasificacionItv()));
	}

	private ResultadoDireccionBean guardarDireccion(VehiculoVO vehiculoVO, String tipoTramite) throws OegamExcepcion {
		String conversion = "";
		ResultadoDireccionBean resultDireccion = new ResultadoDireccionBean(Boolean.FALSE);
		try {
			if (vehiculoVO.getDireccion() != null && utiles.convertirCombo(vehiculoVO.getDireccion().getIdProvincia()) != null) {
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
					conversion = ServicioComunDireccion.CONVERSION_DIRECCION_CORREOS;
				} else {
					conversion = ServicioComunDireccion.CONVERSION_DIRECCION_INE;
				}
				resultDireccion = servicioComunDireccion.tratarDirVehiculo(vehiculoVO.getDireccion(), vehiculoVO.getMatricula(), conversion);
				if (!resultDireccion.getError()) {
					vehiculoVO.setDireccion(resultDireccion.getDireccion());
				} else {
					vehiculoVO.setDireccion(null);
				}
			} else {
				vehiculoVO.setDireccion(null);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la dirección del vehiculo, error: ", e);
			resultDireccion.setError(Boolean.TRUE);
			resultDireccion.setMensaje("Ha sucedido un error a la hora de guardar la dirección del vehiculo.");
		}
		return resultDireccion;
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerTipoVehiculoDescripcion(String tipoVehiculo) {
		try {
			if (tipoVehiculo != null && !tipoVehiculo.isEmpty()) {
				TipoVehiculoVO tipoVehiculoVO = tipoVehiculoDao.getTipoVehiculo(tipoVehiculo);
				if (tipoVehiculoVO != null) {
					return tipoVehiculoVO.getDescripcion();
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de vehículo", e);
		}
		return "";
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerNombreMarca(String codigoMarca, boolean versionMatw) {
		try {
			if (codigoMarca != null && !codigoMarca.isEmpty()) {
				MarcaDgtVO marcaDgtVO = marcaDgtDao.getMarcaDgt(codigoMarca, null, versionMatw);
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
	public String obtenerServicioDescripcion(String idServicio) {
		try {
			if (idServicio != null && !idServicio.isEmpty()) {
				ServicioTraficoVO servicio = servicioTraficoDao.getServicioTrafico(idServicio);
				if (servicio != null) {
					return servicio.getDescripcion();
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el servicio trafico", e);
		}
		return "";
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerSinCodig(Long idVehiculo, BigDecimal numExpediente) {
		String sinCodig = "";
		try {
			EvolucionVehiculoVO evolucion = evolucionVehiculoDao.getEvolucionVehiculoSinCodig(idVehiculo, numExpediente);
			if (evolucion != null) {
				if (evolucion.getOtros() != null) {
					sinCodig += evolucion.getOtros();
				}
				if (evolucion.getMatriculaNue() != null) {
					sinCodig += "MATRICULA,";
				}
				if (evolucion.getBastidorNue() != null) {
					sinCodig += "BASTIDOR,";
				}
				if (evolucion.getTipoVehiculoNue() != null) {
					sinCodig += "TIPO VEHICULO,";
				}
			}
		} catch (Exception e) {
			log.error("Error el obtener el vehiculo");
		}
		return sinCodig;
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerDescripcionMotivoItv(String idMotivoItv) {
		try {
			if (idMotivoItv != null && !idMotivoItv.isEmpty()) {
				MotivoItvVO motivoItvVO = motivoItvDao.getMotivoItv(idMotivoItv);
				if (motivoItvVO != null) {
					return motivoItvVO.getDescripcion();
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar la descripcion del motivo itv", e);
		}
		return "";
	}
}
