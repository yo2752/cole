package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.dao.CambioServPermitidoDao;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoProcedureDao;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.CambioServPermitidoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.DgtCodigoItvVO;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoTramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDgtCodigoItv;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioEvolucionVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioFabricante;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaFabricante;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.EvolucionVehiculoDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaFabricanteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoTarjetaITV;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioVehiculoImpl implements ServicioVehiculo {

	private static final long serialVersionUID = 8085338398268840137L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioVehiculoImpl.class);
	private static final String VEHICULO_NO_GUARDADO = "El vehiculo no se ha guardado";
	private static final String ERROR_AL_GUARDAR_DIRECCION_VEHICULO = "Error al guardar la dirección del vehículo";

	@Autowired
	VehiculoDao vehiculoDao;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioEvolucionVehiculo servicioEvolucionVehiculo;

	@Autowired
	ServicioMarcaDgt servicioMarcaDgt;

	@Autowired
	ServicioFabricante servicioFabricante;

	@Autowired
	ServicioTipoVehiculo servicioTipoVehiculo;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioDgtCodigoItv servicioDgtCodigoItv;

	@Autowired
	ServicioMarcaFabricante servicioMarcaFabricante;

	@Autowired
	ServicioVehiculoTramiteTrafico servicioVehiculoTramiteTrafico;

	@Autowired
	VehiculoProcedureDao vehiculoProcedureDaoImpl;

	@Autowired
	Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;
	
	@Autowired
	CambioServPermitidoDao cambioServPermitidoDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public VehiculoDto getVehiculoDto(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estadoVehiculo) {
		try {
			VehiculoVO vehiculo = getVehiculoVO(idVehiculo, numColegiado, matricula, bastidor, nive, estadoVehiculo);
			if (vehiculo != null) {
				return conversor.transform(vehiculo, VehiculoDto.class);
			}
		} catch (Exception e) {
			log.error("Error el obtener el vehiculo", e);
		}
		return null;
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
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultBean guardarVehiculoConPrever(VehiculoDto vehiculoDto, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario, Date fechaPresentacion, String tipoConversion,
			VehiculoDto vehiculoPreverDto, boolean admin) {
		ResultBean resultado = new ResultBean();
		try {
			if (vehiculoPreverDto != null) {
				// Guardar vehiculo prever
				vehiculoPreverDto.setNumColegiado(vehiculoDto.getNumColegiado());
				VehiculoVO vehiculoPreverVO = conversor.transform(vehiculoPreverDto, VehiculoVO.class);
				resultado = guardarVehiculo(vehiculoPreverVO, numExpediente, tipoTramite, usuario, fechaPresentacion, CONVERSION_VEHICULO_PREVER, true, admin);
				if (!resultado.getError()) {
					VehiculoVO vehiculo = (VehiculoVO) resultado.getAttachment(VEHICULO);
					vehiculoDto.setIdVehiculoPrever(new BigDecimal(vehiculo.getIdVehiculo()));
				}
			}

			VehiculoVO vehiculoVO = conversor.transform(vehiculoDto, VehiculoVO.class);

			// Guardar integrador
			if (vehiculoVO.getPersona() != null && vehiculoVO.getPersona().getId() != null && vehiculoVO.getPersona().getId().getNif() != null && !vehiculoVO.getPersona().getId().getNif().isEmpty()) {
				vehiculoVO.getPersona().getId().setNumColegiado(vehiculoDto.getNumColegiado());

				ResultBean resultPersona = servicioPersona.guardarActualizar(vehiculoVO.getPersona(), numExpediente, tipoTramite, usuario, ServicioPersona.CONVERSION_PERSONA_INTEGRADOR);
				if (resultPersona.getError()) {
					resultado.addListaMensajes(resultPersona.getListaMensajes());
				} else {
					PersonaVO integrador = (PersonaVO) resultPersona.getAttachment(ServicioPersona.PERSONA);
					vehiculoVO.setNifIntegrador(integrador.getId().getNif());
					vehiculoVO.setPersona(integrador);
				}
			}

			resultado = guardarVehiculo(vehiculoVO, numExpediente, tipoTramite, usuario, fechaPresentacion, tipoConversion, false, admin);

			if (!resultado.getError()) {
				resultado.addAttachment(ID_VEHICULO, ((VehiculoVO) resultado.getAttachment(VEHICULO)).getIdVehiculo());
			}
		} catch (Exception e) {
			log.error(VEHICULO_NO_GUARDADO, e, numExpediente.toString());
			resultado.setError(true);
			resultado.addMensajeALista(VEHICULO_NO_GUARDADO);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean actualizarMatricula(BigDecimal numExpediente, String matricula, Date fechaMatriculacion, UsuarioDto usuario) {
		ResultBean resultado = new ResultBean();
		EvolucionVehiculoDto evolucionDto = new EvolucionVehiculoDto();
		try {
			TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(numExpediente, true);
			if (tramite != null & tramite.getVehiculo() != null) {
				if (tramite.getEstado() != null && !EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramite.getEstado().toString())) {
					resultado.setMensaje("El estado debe ser Finalizado PDF");
					resultado.setError(true);
				} else if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramite.getTipoTramite())) {
					resultado.setMensaje("El tipo de trámite debe ser MATRICULACION");
					resultado.setError(true);
				} else if (tramite.getVehiculo().getMatricula() != null && !tramite.getVehiculo().getMatricula().isEmpty()) {
					resultado.setMensaje("El vehículo ya tiene matrícula");
					resultado.setError(true);
				} else if (StringUtils.isNotBlank(matricula) && matricula.length() > 12) {
					resultado.setMensaje("La matrícula tiene más de 12 carácteres.");
					resultado.setError(true);
				} else {
					tramite.getVehiculo().setMatricula(matricula);
					tramite.getVehiculo().setFechaMatriculacion(fechaMatriculacion);
					vehiculoDao.guardarOActualizar(tramite.getVehiculo());
					guardarEvolucionVehiculo(evolucionDto, new BigDecimal(tramite.getVehiculo().getIdVehiculo()), tramite.getVehiculo().getNumColegiado(), numExpediente,
							ServicioVehiculo.TIPO_TRAMITE_MANTENIMIENTO, usuario, ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_MOD);
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("No existe el trámite");
			}
		} catch (Throwable e) {
			log.error("Error al actualizar la matrícula del vehículo: " + e.getMessage(), e, numExpediente.toString());
			resultado.setError(true);
			resultado.setMensaje(VEHICULO_NO_GUARDADO);
		}
		return resultado;
	}

	//DVV
	@Transactional
	public void actualizarEsSiniestro(BigDecimal numExpediente, BigDecimal esSiniestro) {
		TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(numExpediente, true);
		if (tramite != null && tramite.getVehiculo() != null) {
			tramite.getVehiculo().setEsSiniestro(esSiniestro.equals(new BigDecimal(1)));
			vehiculoDao.guardarOActualizar(tramite.getVehiculo());
		}
	}
	
	@Transactional
	@Override
	public void actualizarVelocidadMaxima(BigDecimal numExpediente, BigDecimal velocidadMaxima) {
		TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(numExpediente, true);
		if (tramite != null && tramite.getVehiculo() != null) {
			tramite.getVehiculo().setVelocidadMaxima(velocidadMaxima);
			vehiculoDao.guardarOActualizar(tramite.getVehiculo());
		}
	}

//	@Transactional
//	public void actualizarTieneCargaFinanciera(BigDecimal numExpediente, BigDecimal tieneCargaFinanciera) {
//		TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(numExpediente, true);
//		if (tramite != null && tramite.getVehiculo() != null) {
//			tramite.getVehiculo().setTieneCargaFinanciera(tieneCargaFinanciera.equals(new BigDecimal(1)));
//			vehiculoDao.guardarOActualizar(tramite.getVehiculo());
//		}
//	}

	@Override
	@Transactional
	public ResultBean guardarVehiculoMatriculaManual(VehiculoDto vehiculoPantalla, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario, Date fechaPresentacion, String conversion,
			VehiculoDto vehiculoPreverDto, boolean admin) {
		ResultBean resultado = new ResultBean();
		int resultadoModificacion = 0;
		EvolucionVehiculoDto evolucionDto = new EvolucionVehiculoDto();
		VehiculoVO vehiculoBBDD = null;
		VehiculoVO vehiculoCompleto = null;
		Long idVehiculo = null;
		Long idVehiculoAntiguo = null;
		if ((vehiculoPreverDto != null) && vehiculoPreverDto.getIdVehiculoPrever() != null) {
			try {
				if (vehiculoPreverDto != null) {
					// Guardar vehiculo prever
					vehiculoPreverDto.setNumColegiado(vehiculoPantalla.getNumColegiado());
					VehiculoVO vehiculoPreverVO = conversor.transform(vehiculoPreverDto, VehiculoVO.class);
					resultado = guardarVehiculo(vehiculoPreverVO, numExpediente, tipoTramite, usuario, fechaPresentacion, CONVERSION_VEHICULO_PREVER, true, admin);
					if (!resultado.getError()) {
						VehiculoVO vehiculo = (VehiculoVO) resultado.getAttachment(VEHICULO);
						vehiculoPantalla.setIdVehiculoPrever(new BigDecimal(vehiculo.getIdVehiculo()));
					}
				}

				VehiculoVO vehiculoVO = conversor.transform(vehiculoPantalla, VehiculoVO.class);

				// Guardar integrador
				if (vehiculoVO.getPersona() != null && vehiculoVO.getPersona().getId() != null && vehiculoVO.getPersona().getId().getNif() != null && !vehiculoVO.getPersona().getId().getNif()
						.isEmpty()) {
					vehiculoVO.getPersona().getId().setNumColegiado(vehiculoPantalla.getNumColegiado());

					ResultBean resultPersona = servicioPersona.guardarActualizar(vehiculoVO.getPersona(), numExpediente, tipoTramite, usuario, ServicioPersona.CONVERSION_PERSONA_INTEGRADOR);
					if (resultPersona.getError()) {
						resultado.addListaMensajes(resultPersona.getListaMensajes());
					} else {
						PersonaVO integrador = (PersonaVO) resultPersona.getAttachment(ServicioPersona.PERSONA);
						vehiculoVO.setNifIntegrador(integrador.getId().getNif());
						vehiculoVO.setPersona(integrador);
					}
				}

				resultado = guardarVehiculo(vehiculoVO, numExpediente, tipoTramite, usuario, fechaPresentacion, conversion, false, admin);

				if (!resultado.getError()) {
					resultado.addAttachment(ID_VEHICULO, ((VehiculoVO) resultado.getAttachment(VEHICULO)).getIdVehiculo());
				}
			} catch (Exception e) {
				log.error(VEHICULO_NO_GUARDADO, e, numExpediente.toString());
				resultado.setError(true);
				resultado.addMensajeALista(VEHICULO_NO_GUARDADO);
			}

		} else {
			try {
				evolucionDto.setOtros("");
				if ((tipoTramite.equals(TIPO_TRAMITE_MANTENIMIENTO)) || tipoTramite.equals(TIPO_TRAMITE_MATE_EITV) || tipoTramite.equals(MATRICULAR)) {
					vehiculoBBDD = getVehiculoVO(vehiculoPantalla.getIdVehiculo().longValue(), vehiculoPantalla.getNumColegiado(), null, null, null, EstadoVehiculo.Activo);
					if (tipoTramite.equals(MATRICULAR)) {
						tipoTramite = TipoTramiteTrafico.Matriculacion.getValorEnum();
					}
				} else if (vehiculoPantalla.getMatricula() != null && !vehiculoPantalla.getMatricula().isEmpty()) {
					vehiculoBBDD = getVehiculoVO(null, vehiculoPantalla.getNumColegiado(), vehiculoPantalla.getMatricula(), null, null, EstadoVehiculo.Activo);
				} else if (vehiculoPantalla.getNive() != null && !vehiculoPantalla.getNive().isEmpty()) {
					vehiculoBBDD = getVehiculoVO(null, vehiculoPantalla.getNumColegiado(), null, null, vehiculoPantalla.getNive(), EstadoVehiculo.Activo);
				} else if (vehiculoPantalla.getBastidor() != null && !vehiculoPantalla.getBastidor().isEmpty()) {
					vehiculoBBDD = getVehiculoVO(null, vehiculoPantalla.getNumColegiado(), null, vehiculoPantalla.getBastidor(), null, EstadoVehiculo.Activo);
				} else {
					resultado.setError(true);
					resultado.addMensajeALista("Faltan datos obligatorios para realizar la búsqueda (Matrícula o bastidor)");
					return resultado;
				}

				if (vehiculoBBDD != null && vehiculoBBDD.getDireccion() != null) {
					vehiculoPantalla.getDireccion().setIdDireccion(new BigDecimal(vehiculoBBDD.getDireccion().getIdDireccion()));
				}
				resultado = guardarDireccion(conversor.transform(vehiculoPantalla, VehiculoVO.class), tipoTramite);

				validarDatosVehiculos(conversor.transform(vehiculoPantalla, VehiculoVO.class),numExpediente, resultado);

				// Nueva
				VehiculoVO vehiculoVO = conversor.transform(vehiculoPantalla, VehiculoVO.class);
				if (vehiculoBBDD == null) {
					modificarDatos(conversor.transform(vehiculoPantalla, VehiculoVO.class), tipoTramite, fechaPresentacion, null);
					vehiculoVO = crearVehiculoNuevoConEvolucion(conversor.transform(vehiculoPantalla, VehiculoVO.class), evolucionDto, numExpediente, tipoTramite, usuario,
							ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_CRE);
					resultado.addAttachment(VEHICULO, vehiculoPantalla);
				} else {
					vehiculoCompleto = (VehiculoVO) vehiculoBBDD.clone();
					if (conversion != null) {
						conversor.transform(vehiculoVO, vehiculoCompleto, conversion);
					} else {
						conversor.transform(vehiculoVO, vehiculoCompleto);
					}
					resultadoModificacion = esModificada(vehiculoCompleto, vehiculoBBDD, evolucionDto);
					if (resultadoModificacion != 0) {
						modificarDatos(vehiculoCompleto, tipoTramite, fechaPresentacion, evolucionDto);
						if (resultadoModificacion == 2 && servicioTramiteTrafico.getNumTramitePorVehiculo(numExpediente, vehiculoCompleto.getIdVehiculo()) > 0 && !admin) {
							idVehiculoAntiguo = vehiculoCompleto.getIdVehiculo();
							vehiculoDao.evict(vehiculoCompleto);
							idVehiculo = copiaVehiculo(vehiculoCompleto, idVehiculoAntiguo, numExpediente, tipoTramite, usuario);
							desactivarVehiculo(vehiculoBBDD, numExpediente, tipoTramite, idVehiculo, usuario, evolucionDto);
						} else {
							vehiculoDao.evict(vehiculoBBDD);
							vehiculoDao.guardarOActualizar(vehiculoCompleto);
							guardarEvolucionVehiculo(evolucionDto, new BigDecimal(vehiculoCompleto.getIdVehiculo()), vehiculoCompleto.getNumColegiado(), numExpediente, tipoTramite, usuario,
									ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_MOD);
						}
					} else {
						vehiculoDao.evict(vehiculoCompleto);
					}
					resultado.addAttachment(VEHICULO, vehiculoCompleto);
				}
			} catch (Exception e) {
				log.error("Error al guardar el vehículo: " + e.getMessage(), e, numExpediente.toString());
				resultado.setError(true);
				resultado.addMensajeALista(VEHICULO_NO_GUARDADO);
			} catch (OegamExcepcion e) {
				log.error("Error al guardar el vehículo: " + e.getMessage(), e, numExpediente.toString());
				resultado.setError(true);
				resultado.addMensajeALista("El vehiculo no se ha guardado - " + e.getMessage());
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarVehiculo(VehiculoVO vehiculoPantalla, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario, Date fechaPresentacion, String conversion, boolean prever,
			boolean admin) {
		ResultBean resultado = new ResultBean();
		int resultadoModificacion = 0;
		EvolucionVehiculoDto evolucionDto = new EvolucionVehiculoDto();
		VehiculoVO vehiculoBBDD = null;
		VehiculoVO vehiculoCompleto = null;
		Long idVehiculo = null;
		Long idVehiculoAntiguo = null;
		try {
			evolucionDto.setOtros("");
			if ((tipoTramite.equals(TIPO_TRAMITE_MANTENIMIENTO) && !prever) || tipoTramite.equals(TIPO_TRAMITE_MATE_EITV) || tipoTramite.equals(MATRICULAR)) {
				vehiculoBBDD = getVehiculoVO(vehiculoPantalla.getIdVehiculo(), vehiculoPantalla.getNumColegiado(), null, null, null, EstadoVehiculo.Activo);
				if (tipoTramite.equals(MATRICULAR)) {
					tipoTramite = TipoTramiteTrafico.Matriculacion.getValorEnum();
				}
			} else if (vehiculoPantalla.getBastidor() != null && !vehiculoPantalla.getBastidor().isEmpty()) {
				vehiculoBBDD = getVehiculoVO(null, vehiculoPantalla.getNumColegiado(), null, vehiculoPantalla.getBastidor(), null, EstadoVehiculo.Activo);
			} else if (vehiculoPantalla.getMatricula() != null && !vehiculoPantalla.getMatricula().isEmpty()) {
				vehiculoBBDD = getVehiculoVO(null, vehiculoPantalla.getNumColegiado(), vehiculoPantalla.getMatricula(), null, null, EstadoVehiculo.Activo);
			} else if (vehiculoPantalla.getNive() != null && !vehiculoPantalla.getNive().isEmpty()) {
				vehiculoBBDD = getVehiculoVO(null, vehiculoPantalla.getNumColegiado(), null, null, vehiculoPantalla.getNive(), EstadoVehiculo.Activo);
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Faltan datos obligatorios para realizar la búsqueda (Matrícula o bastidor)");
				return resultado;
			}

			if (vehiculoBBDD != null && vehiculoBBDD.getDireccion() != null && !prever) {
				vehiculoPantalla.getDireccion().setIdDireccion(vehiculoBBDD.getDireccion().getIdDireccion());
			}
			resultado = guardarDireccion(vehiculoPantalla, tipoTramite);

			validarDatosVehiculos(vehiculoPantalla,numExpediente ,resultado);

			// Nueva
			if (vehiculoBBDD == null) {
				modificarDatos(vehiculoPantalla, tipoTramite, fechaPresentacion, null);
				vehiculoPantalla = crearVehiculoNuevoConEvolucion(vehiculoPantalla, evolucionDto, numExpediente, tipoTramite, usuario, ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_CRE);
				resultado.addAttachment(VEHICULO, vehiculoPantalla);
			} else {
				vehiculoCompleto = (VehiculoVO) vehiculoBBDD.clone();
				if (conversion != null) {
					conversor.transform(vehiculoPantalla, vehiculoCompleto, conversion);
				} else {
					conversor.transform(vehiculoPantalla, vehiculoCompleto);
				}
				resultadoModificacion = esModificada(vehiculoCompleto, vehiculoBBDD, evolucionDto);
				if (resultadoModificacion != 0) {
					modificarDatos(vehiculoCompleto, tipoTramite, fechaPresentacion, evolucionDto);
					if (resultadoModificacion == 2 && servicioTramiteTrafico.getNumTramitePorVehiculo(numExpediente, vehiculoCompleto.getIdVehiculo()) > 0 && !admin) {
						idVehiculoAntiguo = vehiculoCompleto.getIdVehiculo();
						vehiculoDao.evict(vehiculoCompleto);
						idVehiculo = copiaVehiculo(vehiculoCompleto, idVehiculoAntiguo, numExpediente, tipoTramite, usuario);
						desactivarVehiculo(vehiculoBBDD, numExpediente, tipoTramite, idVehiculo, usuario, evolucionDto);
					} else {
						vehiculoDao.evict(vehiculoBBDD);
						vehiculoDao.guardarOActualizar(vehiculoCompleto);
						guardarEvolucionVehiculo(evolucionDto, new BigDecimal(vehiculoCompleto.getIdVehiculo()), vehiculoCompleto.getNumColegiado(), numExpediente, tipoTramite, usuario,
								ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_MOD);
					}
				} else {
					vehiculoDao.evict(vehiculoCompleto);
				}
				resultado.addAttachment(VEHICULO, vehiculoCompleto);
			}
		} catch (Exception e) {
			log.error("Error al guardar el vehículo: " + e.getMessage(), e, numExpediente.toString());
			resultado.setError(true);
			resultado.addMensajeALista(VEHICULO_NO_GUARDADO);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el vehículo: " + e.getMessage(), e, numExpediente.toString());
			resultado.setError(true);
			resultado.addMensajeALista("El vehículo no se ha guardado - " + e.getMessage());
		}
		return resultado;
	}

	private void validarDatosVehiculos(VehiculoVO vehiculo, BigDecimal numExpediente, ResultBean result) {
		String respuestaTramiteTrafico = "";
		if (numExpediente != null) {
			respuestaTramiteTrafico = servicioTramiteTrafico.getRespuestaTramiteTrafico(numExpediente);
		}
		if (!validarCodITV(vehiculo.getCoditv())) {
			result.addMensajeALista("El código ITV introducido es erróneo.");
		}
		String tipoInspeccionItvValidacion = validarTipoInspeccionItv(vehiculo);
		if ("error".equals(tipoInspeccionItvValidacion)) {
			result.addMensajeALista(ConstantesMensajes.MENSAJE_TIPO_INSPECCION_ITV_ERROR);
		} else if ("aviso".equals(tipoInspeccionItvValidacion)) {
			result.addMensajeALista(ConstantesMensajes.MENSAJE_TIPO_INSPECCION_ITV);
		}
		if ("SI".equals(vehiculo.getVehiUsado()) || "S".equals(vehiculo.getVehiUsado())) {
			if ("SI".equals(vehiculo.getImportado()) || "S".equals(vehiculo.getImportado())) {
				if (vehiculo.getProcedencia() == null || vehiculo.getProcedencia().isEmpty() || vehiculo.getProcedencia() == "" || vehiculo.getProcedencia() == "-1") {
					result.addMensajeALista("Para los vehículos importados es obligatorio indicar la procedencia.");
				}

				if (vehiculo.getMatriculaOrigExtr() == null || vehiculo.getMatriculaOrigExtr().isEmpty()) {
					result.addMensajeALista("Para los vehículos importados es obligatorio indicar la matrícula extranjera.");
				}
				if (vehiculo.getMatriculaOrigen() != null && !vehiculo.getMatriculaOrigen().isEmpty()) {
					result.addMensajeALista("Para los vehículos importados no se debe de indicar la matrícula origen.");
				}
			} else if (vehiculo.getMatriculaOrigen() == null || vehiculo.getMatriculaOrigen().isEmpty() && (respuestaTramiteTrafico != null && !"SIMA01014 - El bastidor/NIVE aparece bloqueado en EEFF. (Recibido de la DGT)".contains(respuestaTramiteTrafico))) {
				result.addMensajeALista("Para los vehículos usados es obligatorio indicar la matrícula origen.");
			}
			if (vehiculo.getFechaPrimMatri() == null) {
				result.addMensajeALista("Para los vehículos usados es obligatorio indicar la fecha de primera matriculación.");
			}
		}
	}

	private boolean validarCodITV(String codITV) {
		if (codITV != null) {
			String[] caracteresInvalidos = { "á", "à", "ä", "â", "ª", "Á", "À", "Â", "Ä", "é", "è", "ë", "ê", "É", "È", "Ê", "Ë", "í", "ì", "ï", "î", "Í", "Ì", "Ï", "Î", "ó", "ò", "ö", "ô", "Ó", "Ò",
					"Ö", "Ô", "ú", "ù", "ü", "û", "Ú", "Ù", "Û", "Ü", "ñ", "Ñ", "ç", "Ç" };
			for (int i = 0; i < codITV.length(); ++i) {
				char caracter = codITV.charAt(i);
				if (!Character.isLetterOrDigit(caracter)) {
					return false;
				} else {
					if (Character.isLetter(caracter)) {
						for (int j = 0; j < caracteresInvalidos.length; j++) {
							if (codITV.contains(caracteresInvalidos[j])) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	public String validarTipoInspeccionItv(VehiculoVO vehiculo) {
		String servicio = null;
		String tipoVehiculo = null;
		String motivo = null;
		String tipo = null;
		String validacion = null;
		boolean fechaCompleta = true;
		try {
			if (vehiculo.getIdServicio() != null && !vehiculo.getIdServicio().isEmpty()) {
				servicio = vehiculo.getIdServicio();
			}
			if (vehiculo.getTipoVehiculo() != null && !vehiculo.getTipoVehiculo().isEmpty()) {
				tipoVehiculo = vehiculo.getTipoVehiculo();
			}
			if (vehiculo.getIdMotivoItv() != null && !vehiculo.getIdMotivoItv().isEmpty()) {
				motivo = vehiculo.getIdMotivoItv();
			}
			if (tipoVehiculo != null && !tipoVehiculo.isEmpty()) {
				tipo = tipoVehiculo.substring(0, 1);
				// A09: PUBL-Obras, B09: PART-Obras, 7: Vehículo especial, E:
				// Exento ITV
				// No se ha seleccionado Exento ITV cuando corresponde
				if (("A09".equals(servicio) || "B09".equals(servicio)) && ("7".equals(tipo) && !"E".equals(motivo))) {
					validacion = "aviso";
				}
				// Aviso fecha para validación con exento y fecha ITV rellena
				else if (("A09".equals(servicio) || "B09".equals(servicio)) && ("7".equals(tipo) && "E".equals(motivo) && fechaCompleta)) {
					validacion = "avisoFecha";
				}
				// Se ha seleccionado Exento ITV cuando no corresponde
				else if ((!"A09".equals(servicio) || !"B09".equals(servicio)) && (!"7".equals(tipo) && ("E".equals(motivo)))) {
					validacion = "error";
				} else {
					validacion = "correcta";
				}
			}
		} catch (Throwable e) {
			log.error("Error al validar el Tipo Inspeccion ITV.");
		}
		return validacion;
	}

	@Override
	@Transactional
	public VehiculoVO crearVehiculoNuevoConEvolucion(VehiculoVO vehiculoVO, EvolucionVehiculoDto evolucionDto, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario,
			String tipoActualizacion) throws OegamExcepcion {
		vehiculoVO.setActivo(EstadoVehiculo.Activo.getValorEnum());
		vehiculoVO.setIdVehiculo((Long) vehiculoDao.guardar(vehiculoVO));
		guardarEvolucionVehiculo(evolucionDto, new BigDecimal(vehiculoVO.getIdVehiculo()), vehiculoVO.getNumColegiado(), numExpediente, tipoTramite, usuario, tipoActualizacion);
		return vehiculoVO;
	}

	private ResultBean guardarDireccion(VehiculoVO vehiculoVO, String tipoTramite) throws OegamExcepcion {
		String conversion = "";
		ResultBean resultDireccion = new ResultBean();
		try {
			if (vehiculoVO.getDireccion() != null && utiles.convertirCombo(vehiculoVO.getDireccion().getIdProvincia()) != null) {
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
					conversion = ServicioDireccion.CONVERSION_DIRECCION_CORREOS;
				} else {
					conversion = ServicioDireccion.CONVERSION_DIRECCION_INE;
				}
				resultDireccion = servicioDireccion.guardarActualizarVehiculo(vehiculoVO.getDireccion(), vehiculoVO.getMatricula(), conversion);
				if (!resultDireccion.getError()) {
					vehiculoVO.setDireccion((DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION));
				} else {
					vehiculoVO.setDireccion(null);
				}
			} else {
				vehiculoVO.setDireccion(null);
			}
		} catch (Exception e) {
			log.error(ERROR_AL_GUARDAR_DIRECCION_VEHICULO);
			throw new OegamExcepcion(ERROR_AL_GUARDAR_DIRECCION_VEHICULO);
		}
		return resultDireccion;
	}

	private void guardarEvolucionVehiculo(EvolucionVehiculoDto evolucionDto, BigDecimal idVehiculo, String numColegiado, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario,
			String tipoActualizacion) throws OegamExcepcion {
		try {
			evolucionDto.setFechaHora(utilesFecha.getFechaHoraActualLEG());
			evolucionDto.setNumColegiado(numColegiado);
			evolucionDto.setNumExpediente(numExpediente);
			evolucionDto.setTipoTramite(tipoTramite);
			evolucionDto.setTipoActualizacion(tipoActualizacion);
			evolucionDto.setIdVehiculo(idVehiculo);
			evolucionDto.setUsuario(usuario);

			servicioEvolucionVehiculo.guardarEvolucion(evolucionDto);
		} catch (Exception e) {
			log.error("Error al guardar la evolución del vehículo", e, numExpediente.toString());
			throw new OegamExcepcion(ERROR_AL_GUARDAR_DIRECCION_VEHICULO);
		}
	}

	@Override
	@Transactional
	public void desactivarVehiculo(VehiculoVO vehiculo, BigDecimal numExpediente, String tipoTramite, Long idVehiculoNuevo, UsuarioDto usuario, EvolucionVehiculoDto evolucionDto)
			throws OegamExcepcion {
		try {
			vehiculo.setActivo(EstadoVehiculo.Desactivo.getValorEnum());
			vehiculoDao.guardarOActualizar(vehiculo);
			String otros = evolucionDto.getOtros();
			evolucionDto.setOtros("Vehículo Desactivado. Id nuevo: " + idVehiculoNuevo);
			if (!"".equals(otros)) {
				evolucionDto.setOtros(evolucionDto.getOtros() + " -- " + otros);
			}
			guardarEvolucionVehiculo(evolucionDto, new BigDecimal(vehiculo.getIdVehiculo()), vehiculo.getNumColegiado(), numExpediente, tipoTramite, usuario,
					ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_MOD);
		} catch (Exception e) {
			log.error("Error al desactivar el vehículo", e, numExpediente.toString());
			throw new OegamExcepcion("Error al desactivar el vehículo");
		}
	}

	@Override
	@Transactional
	public Long copiaVehiculo(VehiculoVO vehiculo, Long idVehiculoAntiguo, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario) throws OegamExcepcion {
		Long idVehiculo = null;
		try {
			EvolucionVehiculoDto evolucionVehiculoDto = new EvolucionVehiculoDto();
			vehiculo.setIdVehiculo(null);
			vehiculo.setActivo(EstadoVehiculo.Activo.getValorEnum());
			idVehiculo = (Long) vehiculoDao.guardar(vehiculo);
			evolucionVehiculoDto.setIdVehiculoOrigen(new BigDecimal(idVehiculoAntiguo));
			guardarEvolucionVehiculo(evolucionVehiculoDto, new BigDecimal(idVehiculo), vehiculo.getNumColegiado(), numExpediente, tipoTramite, usuario,
					ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_COP);
		} catch (Exception e) {
			log.error("Error al copiar el vehículo", e, numExpediente.toString());
			throw new OegamExcepcion("Error al copiar el vehículo");
		}
		return idVehiculo;
	}

	@Override
	@Transactional
	public ResultBean consultaDatosItv(VehiculoDto vehiculoDto) {
		ResultBean result = new ResultBean();
		DgtCodigoItvVO dgtCodigoItvVO = servicioDgtCodigoItv.obtenerDatosItv(vehiculoDto.getCodItv());
		if (dgtCodigoItvVO != null) {
			result.setError(false);
			setDatosItv(dgtCodigoItvVO, vehiculoDto);
			result.addAttachment(VEHICULO, vehiculoDto);
		} else {
			result.setError(true);
		}
		return result;
	}

	private void setDatosItv(DgtCodigoItvVO dgtCodigoItvVO, VehiculoDto vehiculoDto) {
		if (vehiculoDto.getBastidor() != null || !"".equals(vehiculoDto.getBastidor())) {
			vehiculoDto.setBastidor(dgtCodigoItvVO.getBastidor());
		}

		if (dgtCodigoItvVO.getCo2() != null && !"".equals(dgtCodigoItvVO.getCo2())) {
			vehiculoDto.setCo2(new BigDecimal(dgtCodigoItvVO.getCo2()).toString());
		}
		vehiculoDto.setCarburante(dgtCodigoItvVO.getCarburante());

		if (dgtCodigoItvVO.getCilindrada() != null && !"".equals(dgtCodigoItvVO.getCilindrada())) {
			vehiculoDto.setCilindrada(new BigDecimal(dgtCodigoItvVO.getCilindrada()).toString());
		}

		MarcaDgtDto marca = getMarcaDgt(null, dgtCodigoItvVO.getMarca(), true);
		if (marca != null) {
			vehiculoDto.setCodigoMarca(marca.getCodigoMarca().toString());
		}

		if (dgtCodigoItvVO.getMma() != null && !"".equals(dgtCodigoItvVO.getMma())) {
			vehiculoDto.setPesoMma(new BigDecimal(dgtCodigoItvVO.getMma()).toString());
		}
		vehiculoDto.setModelo(dgtCodigoItvVO.getModelo());

		if (dgtCodigoItvVO.getPlazas() != null && !"".equals(dgtCodigoItvVO.getPlazas())) {
			vehiculoDto.setPlazas(new BigDecimal(dgtCodigoItvVO.getPlazas()));
		}
		vehiculoDto.setPotenciaFiscal(dgtCodigoItvVO.getPotenciaFiscal());
		vehiculoDto.setPotenciaNeta(dgtCodigoItvVO.getPotenciaReal());

		if (dgtCodigoItvVO.getPotenciaPeso() != null && !"".equals(dgtCodigoItvVO.getPotenciaPeso())) {
			vehiculoDto.setPotenciaPeso(new BigDecimal(dgtCodigoItvVO.getPotenciaPeso()));
		}

		if (dgtCodigoItvVO.getTara() != null && !"".equals(dgtCodigoItvVO.getTara())) {
			vehiculoDto.setTara(new BigDecimal(dgtCodigoItvVO.getTara()).toString());
		}

		if (dgtCodigoItvVO.getTipoVehiculoTrafico() != null) {
			vehiculoDto.setTipoVehiculo(dgtCodigoItvVO.getTipoVehiculoTrafico());
		}
		vehiculoDto.setTipoIndustria(dgtCodigoItvVO.getTipoVehiculoIndustria());

		if (dgtCodigoItvVO.getTipoVehiculoIndustria() != null) {
			vehiculoDto.setCriterioConstruccion(dgtCodigoItvVO.getTipoVehiculoIndustria().substring(0, 2));
			vehiculoDto.setCriterioUtilizacion(dgtCodigoItvVO.getTipoVehiculoIndustria().substring(2, dgtCodigoItvVO.getTipoVehiculoIndustria().length()));
		}

		if (dgtCodigoItvVO.getMma() != null && !"".equals(dgtCodigoItvVO.getMma())) {
			vehiculoDto.setMtmaItv(new BigDecimal(dgtCodigoItvVO.getMma()).toString());
		}
		vehiculoDto.setFabricante(dgtCodigoItvVO.getFabricante());
		vehiculoDto.setTipoItv(dgtCodigoItvVO.getTipo());
		vehiculoDto.setVariante(dgtCodigoItvVO.getVariante());
		vehiculoDto.setVersion(dgtCodigoItvVO.getVersion35());
		vehiculoDto.setIdDirectivaCee(dgtCodigoItvVO.getCatHomologacion());
		vehiculoDto.setConsumo(dgtCodigoItvVO.getConsumo());
		vehiculoDto.setMom(dgtCodigoItvVO.getMasaMom());
		vehiculoDto.setFabricacion(dgtCodigoItvVO.getProcedencia());
		vehiculoDto.setViaAnterior(dgtCodigoItvVO.getViaAnterior());
		vehiculoDto.setViaPosterior(dgtCodigoItvVO.getViaPosterior());
		vehiculoDto.setDistanciaEjes(dgtCodigoItvVO.getDistanciaEjes());
		vehiculoDto.setTipoAlimentacion(dgtCodigoItvVO.getTipoAlimentacion());
		vehiculoDto.setNumHomologacion(dgtCodigoItvVO.getContraseniaHomologacion());
		vehiculoDto.setNivelEmisiones(dgtCodigoItvVO.getNivelEmision());
		vehiculoDto.setEcoInnovacion(dgtCodigoItvVO.getEcoInnovacion());
		vehiculoDto.setReduccionEco(dgtCodigoItvVO.getReduccionEco());
		vehiculoDto.setCodigoEco(dgtCodigoItvVO.getCodigoEco());
		//vehiculoDto.setVelocidadMaxima();

		if (dgtCodigoItvVO.getPlazasPie() != null && !"".equals(dgtCodigoItvVO.getPlazasPie())) {
			vehiculoDto.setNumPlazasPie(new BigDecimal(dgtCodigoItvVO.getPlazasPie()));
		}

		vehiculoDto.setCarroceria(dgtCodigoItvVO.getCarroceria());
	}

	@Override
	public void modificarDatos(VehiculoVO vehiculoVO, String tipoTramite, Date fechaPresentacion, EvolucionVehiculoDto evolucionDto) {
		vehiculoVO.setFechaUltmModif(new Date());

		modificarDatosCombos(vehiculoVO);
		modificarDatosMayusculas(vehiculoVO);

		if (!TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
			procesarCodigoItvOriginal(vehiculoVO, evolucionDto);

			if (vehiculoVO.getTipoVehiculo() != null && (vehiculoVO.getTipoVehiculo().startsWith("R") || vehiculoVO.getTipoVehiculo().startsWith("S"))) {
				if (vehiculoVO.getPlazas() == null || vehiculoVO.getPlazas().intValue() > 0) {
					vehiculoVO.setPlazas(new BigDecimal(0));
					if (evolucionDto != null) {
						evolucionDto.setOtros(evolucionDto.getOtros() + "Plazas,");
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

		// Se añade al campo tipo industria la clasificación ITV
		if (vehiculoVO.getClasificacionItv() != null && !vehiculoVO.getClasificacionItv().isEmpty()) {
			vehiculoVO.setTipoIndustria(vehiculoVO.getClasificacionItv());
		}
	}

	private void procesarCodigoItvOriginal(VehiculoVO vehiculoVO, EvolucionVehiculoDto evolucionDto) {
		String coditvOriginal = vehiculoVO.getCoditvOriginal();

		if (vehiculoVO.getCoditvOriginal() == null) {
			vehiculoVO.setCoditvOriginal(vehiculoVO.getCoditv());
		} else if (vehiculoVO.getCoditvOriginal() != null && !ConstantesTrafico.SIN_CODIG.equals(vehiculoVO.getCoditv())) {
			vehiculoVO.setCoditvOriginal(vehiculoVO.getCoditv());
		} else if (vehiculoVO.getCoditv() == null) {
			vehiculoVO.setCoditvOriginal(null);
		}

		if (evolucionDto != null && !utiles.sonIgualesString(vehiculoVO.getCoditvOriginal(), coditvOriginal)) {
			evolucionDto.setOtros(evolucionDto.getOtros() + "Cod ITV Original,");
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

	private Date calcularFechaItv(VehiculoVO vehiculoVO, String tipoTramite, Date fechaPresentacion) {
		if (vehiculoVO.getTipoVehiculo() != null && !"SI".equals(vehiculoVO.getCheckFechaCaducidadItv()) && ((vehiculoVO.getIdTipoTarjetaItv() != null && !TipoTarjetaITV.A.getValorEnum().equals(
				vehiculoVO.getIdTipoTarjetaItv())) || vehiculoVO.getFechaItv() == null)) {
			return vehiculoProcedureDaoImpl.calculoItv(vehiculoVO, fechaPresentacion, tipoTramite);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public MarcaDgtDto getMarcaDgt(String codigoMarca, String marca, Boolean versionMatw) {
		try {
			MarcaDgtVO marcaDgtVO = servicioMarcaDgt.getMarcaDgt(codigoMarca, marca, versionMatw);
			if (marcaDgtVO != null) {
				return conversor.transform(marcaDgtVO, MarcaDgtDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la marca", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerNombreMarca(String codigoMarca, boolean versionMatw) {
		try {
			if (codigoMarca != null && !codigoMarca.isEmpty()) {
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
	public String obtenerFabricante(String fabricante) {
		try {
			if (fabricante != null && !fabricante.isEmpty()) {
				FabricanteVO fabricanteVO = servicioFabricante.getFabricante(fabricante);
				if (fabricanteVO != null) {
					return fabricanteVO.getFabricante();
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el fabricante", e);
		}
		return "";
	}

	@Override
	@Transactional(readOnly = true)
	public MarcaFabricanteDto obtenerMarcaFabricante(String fabricante, String codigoMarca) {
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

	@Override
	@Transactional(readOnly = true)
	public String obtenerTipoVehiculoDescripcion(String tipoVehiculo) {
		try {
			TipoVehiculoVO tipoVehiculoVO = servicioTipoVehiculo.getTipoVehiculo(tipoVehiculo);
			if (tipoVehiculoVO != null) {
				return tipoVehiculoVO.getDescripcion();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de vehículo", e);
		}
		return "";
	}

	/**
	 * return: 0 - No modificado; 1 - Modificado; 2 - Modificado y creación de copia
	 */
	@Override
	@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
	public int esModificada(VehiculoVO vehiculoVO, VehiculoVO vehiculoBBDD, EvolucionVehiculoDto evolucionDto) {
		int resultado = 0;
		try {
			evolucionDto.setIdVehiculo(new BigDecimal(vehiculoBBDD.getIdVehiculo()));

			if (!utiles.sonIgualesCheckBox(vehiculoVO.getFichaTecnicaRd750(), vehiculoBBDD.getFichaTecnicaRd750())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Ficha TecnicaRD 750,");
				if (vehiculoBBDD.getFichaTecnicaRd750() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}

			if (!utiles.sonIgualesCheckBox(vehiculoVO.getSubastado(), vehiculoBBDD.getSubastado())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Subastado,");
				if (vehiculoBBDD.getSubastado() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getVehiculoAgricola(), vehiculoBBDD.getVehiculoAgricola())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Vehículo Agrícola,");
				if (vehiculoBBDD.getVehiculoAgricola() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getVehiculoTransporte(), vehiculoBBDD.getVehiculoTransporte())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Vehículo Transporte,");
				if (vehiculoBBDD.getVehiculoTransporte() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getAnioFabrica(), vehiculoBBDD.getAnioFabrica())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Vehículo Transporte,");
				if (vehiculoBBDD.getAnioFabrica() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getAutonomiaElectrica(), vehiculoBBDD.getAutonomiaElectrica())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Año Fábrica,");
				if (vehiculoBBDD.getAutonomiaElectrica() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getBastidor() != null ? vehiculoVO.getBastidor().toUpperCase() : null, vehiculoBBDD.getBastidor() != null ? vehiculoBBDD.getBastidor().toUpperCase()
					: null)) {
				evolucionDto.setBastidorAnt(vehiculoBBDD.getBastidor());
				if (vehiculoBBDD.getBastidor() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
				evolucionDto.setBastidorNue(vehiculoVO.getBastidor());
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getBastidorMatriculado(), vehiculoBBDD.getBastidorMatriculado())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Bastidor Matriculado,");
				if (vehiculoBBDD.getBastidorMatriculado() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCaracteristicas(), vehiculoBBDD.getCaracteristicas())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Características,");
				if (vehiculoBBDD.getCaracteristicas() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdCarburante(), vehiculoBBDD.getIdCarburante())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Carburante,");
				if (vehiculoBBDD.getIdCarburante() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getCarroceria(), vehiculoBBDD.getCarroceria())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Carrocería,");
				if (vehiculoBBDD.getCarroceria() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCategoriaElectrica(), vehiculoBBDD.getCategoriaElectrica())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Categoría Eléctrica,");
				if (vehiculoBBDD.getCategoriaElectrica() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCdmarca(), vehiculoBBDD.getCdmarca())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Marca Vehículo,");
				if (vehiculoBBDD.getCdmarca() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCdmodveh(), vehiculoBBDD.getCdmodveh())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Modelo Vehículo,");
				if (vehiculoBBDD.getCdmodveh() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCheckFechaCaducidadItv(), vehiculoBBDD.getCheckFechaCaducidadItv())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Check Fecha Caducidad,");
				if (vehiculoBBDD.getCheckFechaCaducidadItv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCilindrada(), vehiculoBBDD.getCilindrada())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Cilindrada,");
				if (vehiculoBBDD.getCilindrada() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getClasificacionItv(), vehiculoBBDD.getClasificacionItv())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Clasificación ITV,");
				if (vehiculoBBDD.getClasificacionItv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCo2(), vehiculoBBDD.getCo2())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "CO2,");
				if (vehiculoBBDD.getCo2() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCodigoEco(), vehiculoBBDD.getCodigoEco())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Código Eco,");
				if (vehiculoBBDD.getCodigoEco() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCoditv(), vehiculoBBDD.getCoditv())) {
				evolucionDto.setCodigoItvAnt(vehiculoBBDD.getCoditv());
				if (vehiculoBBDD.getCoditv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
				evolucionDto.setCodigoItvNue(vehiculoVO.getCoditv());
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdColor(), vehiculoBBDD.getIdColor())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Color,");
				if (vehiculoBBDD.getIdColor() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getConceptoItv(), vehiculoBBDD.getConceptoItv())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Concepto ITV,");
				if (vehiculoBBDD.getConceptoItv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getConsumo(), vehiculoBBDD.getConsumo())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Consumo,");
				if (vehiculoBBDD.getConsumo() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdCriterioConstruccion(), vehiculoBBDD.getIdCriterioConstruccion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Criterio Construcción,");
				if (vehiculoBBDD.getIdCriterioConstruccion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdCriterioUtilizacion(), vehiculoBBDD.getIdCriterioUtilizacion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Criterio Utilización,");
				if (vehiculoBBDD.getIdCriterioUtilizacion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getDiplomatico(), vehiculoBBDD.getDiplomatico())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Diplomático,");
				if (vehiculoBBDD.getDiplomatico() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.diferenciaNulls(vehiculoVO.getDireccion(), vehiculoBBDD.getDireccion()) || (vehiculoVO.getDireccion() != null && vehiculoBBDD.getDireccion() != null && !utiles
					.sonIgualesObjetos(vehiculoVO.getDireccion().getIdDireccion(), vehiculoBBDD.getDireccion().getIdDireccion()))) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Dirección,");
				if (vehiculoBBDD.getDireccion() != null && vehiculoBBDD.getDireccion().getIdDireccion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdDirectivaCee(), vehiculoBBDD.getIdDirectivaCee())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Directiva CEE,");
				if (vehiculoBBDD.getIdDirectivaCee() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getDistanciaEjes(), vehiculoBBDD.getDistanciaEjes())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Distancia Ejes,");
				if (vehiculoBBDD.getDistanciaEjes() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getEcoInnovacion(), vehiculoBBDD.getEcoInnovacion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Eco Innovación,");
				if (vehiculoBBDD.getEcoInnovacion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdEpigrafe(), vehiculoBBDD.getIdEpigrafe())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Epígrafe,");
				if (vehiculoBBDD.getIdEpigrafe() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getEstacionItv(), vehiculoBBDD.getEstacionItv())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Estación ITV,");
				if (vehiculoBBDD.getEstacionItv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getExcesoPeso(), vehiculoBBDD.getExcesoPeso())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Exceso Peso,");
				if (vehiculoBBDD.getExcesoPeso() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getFabricacion(), vehiculoBBDD.getFabricacion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Fabricación,");
				if (vehiculoBBDD.getFabricacion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getFabricante(), vehiculoBBDD.getFabricante())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Fabricante,");
				if (vehiculoBBDD.getFabricante() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getFabricanteBase(), vehiculoBBDD.getFabricanteBase())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Fabricante Base,");
				if (vehiculoBBDD.getFabricanteBase() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFecdesde(), vehiculoBBDD.getFecdesde()) != 0) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Fecha desde,");
				if (vehiculoBBDD.getFecdesde() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFechaInspeccion(), vehiculoBBDD.getFechaInspeccion()) != 0) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Fecha Inspección,");
				if (vehiculoBBDD.getFechaInspeccion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFechaItv(), vehiculoBBDD.getFechaItv()) != 0) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Fecha ITV,");
				if (vehiculoBBDD.getFechaItv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFechaMatriculacion(), vehiculoBBDD.getFechaMatriculacion()) != 0) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Fecha Matriculación,");
				if (vehiculoBBDD.getFechaMatriculacion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getIdVehiculoPrever(), vehiculoBBDD.getIdVehiculoPrever())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Vehículo Prever,");
				if (vehiculoBBDD.getIdVehiculoPrever() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdLugarAdquisicion(), vehiculoBBDD.getIdLugarAdquisicion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Lugar Adquisición,");
				if (vehiculoBBDD.getIdLugarAdquisicion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCodigoMarca(), vehiculoBBDD.getCodigoMarca())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Marca,");
				if (vehiculoBBDD.getCodigoMarca() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCodigoMarcaBase(), vehiculoBBDD.getCodigoMarcaBase())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Marca Base,");
				if (vehiculoBBDD.getCodigoMarcaBase() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatricula() != null ? vehiculoVO.getMatricula().toUpperCase() : null, vehiculoBBDD.getMatricula() != null ? vehiculoBBDD.getMatricula()
					.toUpperCase() : null)) {
				evolucionDto.setMatriculaAnt(vehiculoBBDD.getMatricula() != null ? vehiculoBBDD.getMatricula().toUpperCase() : null);
				if (vehiculoBBDD.getMatricula() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
				evolucionDto.setMatriculaNue(vehiculoVO.getMatricula() != null ? vehiculoVO.getMatricula() : null);
			}
			if (!utiles.sonIgualesString(vehiculoVO.getModelo() != null ? vehiculoVO.getModelo().toUpperCase() : null, vehiculoBBDD.getModelo() != null ? vehiculoBBDD.getModelo().toUpperCase()
					: null)) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Modelo,");
				if (vehiculoBBDD.getModelo() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getMom(), vehiculoBBDD.getMom())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "MOM,");
				if (vehiculoBBDD.getMom() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getMomBase(), vehiculoBBDD.getMomBase())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "MOM Base,");
				if (vehiculoBBDD.getMomBase() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdMotivoItv(), vehiculoBBDD.getIdMotivoItv())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Motivo ITV,");
				if (vehiculoBBDD.getIdMotivoItv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMtmaItv(), vehiculoBBDD.getMtmaItv())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "MTMA ITV,");
				if (vehiculoBBDD.getMtmaItv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getnCilindros(), vehiculoBBDD.getnCilindros())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Número Cilindros,");
				if (vehiculoBBDD.getnCilindros() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getnHomologacion() != null ? vehiculoVO.getnHomologacion().toUpperCase() : null, vehiculoBBDD.getnHomologacion() != null ? vehiculoBBDD
					.getnHomologacion().toUpperCase() : null)) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Número Homologación,");
				if (vehiculoBBDD.getnHomologacion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getnHomologacionBase() != null ? vehiculoVO.getnHomologacionBase().toUpperCase() : null, vehiculoBBDD.getnHomologacionBase() != null ? vehiculoBBDD
					.getnHomologacionBase().toUpperCase() : null)) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Número Homologación Base,");
				if (vehiculoBBDD.getnHomologacionBase() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getNifIntegrador() != null ? vehiculoVO.getNifIntegrador().toUpperCase() : null, vehiculoBBDD.getNifIntegrador() != null ? vehiculoBBDD
					.getNifIntegrador().toUpperCase() : null)) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "NIF Integrador,");
				if (vehiculoBBDD.getNifIntegrador() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getNive(), vehiculoBBDD.getNive())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "NIVE,");
				if (vehiculoBBDD.getNive() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getNivelEmisiones(), vehiculoBBDD.getNivelEmisiones())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Nivel Emisiones,");
				if (vehiculoBBDD.getNivelEmisiones() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getnPlazasPie(), vehiculoBBDD.getnPlazasPie())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Num Plazas de Pie,");
				if (vehiculoBBDD.getnPlazasPie() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getnRuedas(), vehiculoBBDD.getnRuedas())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Número Ruedas,");
				if (vehiculoBBDD.getnRuedas() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getnSerie(), vehiculoBBDD.getnSerie())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Número serie,");
				if (vehiculoBBDD.getnSerie() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getPaisFabricacion(), vehiculoBBDD.getPaisFabricacion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "País Fabricación,");
				if (vehiculoBBDD.getPaisFabricacion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getPaisImportacion(), vehiculoBBDD.getPaisImportacion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "País Importación,");
				if (vehiculoBBDD.getPaisImportacion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getPesoMma(), vehiculoBBDD.getPesoMma())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Peso MMA,");
				if (vehiculoBBDD.getPesoMma() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getPlazas(), vehiculoBBDD.getPlazas())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Plazas,");
				if (vehiculoBBDD.getPlazas() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getPotenciaFiscal(), vehiculoBBDD.getPotenciaFiscal())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Potencia Fiscal,");
				if (vehiculoBBDD.getPotenciaFiscal() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getPotenciaNeta(), vehiculoBBDD.getPotenciaNeta())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Potencia Neta,");
				if (vehiculoBBDD.getPotenciaNeta() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getPotenciaPeso(), vehiculoBBDD.getPotenciaPeso())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Potencia Peso,");
				if (vehiculoBBDD.getPotenciaPeso() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getProcedencia(), vehiculoBBDD.getProcedencia())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Procedencia,");
				if (vehiculoBBDD.getProcedencia() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getProvinciaPrimeraMatricula(), vehiculoBBDD.getProvinciaPrimeraMatricula())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Provincia Primera Matrícula,");
				if (vehiculoBBDD.getProvinciaPrimeraMatricula() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getReduccionEco(), vehiculoBBDD.getReduccionEco())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Reducción Eco,");
				if (vehiculoBBDD.getReduccionEco() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdServicio(), vehiculoBBDD.getIdServicio())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Servicio Tráfico,");
				if (vehiculoBBDD.getIdServicio() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdServicioAnterior(), vehiculoBBDD.getIdServicioAnterior())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Servicio Tráfico Anterior,");
				if (vehiculoBBDD.getIdServicioAnterior() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTara(), vehiculoBBDD.getTara())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Tara,");
				if (vehiculoBBDD.getTara() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoAlimentacion(), vehiculoBBDD.getTipoAlimentacion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Tipo Alimentación,");
				if (vehiculoBBDD.getTipoAlimentacion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoBase(), vehiculoBBDD.getTipoBase())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Tipo Base,");
				if (vehiculoBBDD.getTipoBase() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoIndustria(), vehiculoBBDD.getTipoIndustria())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Tipo Industria,");
				if (vehiculoBBDD.getTipoIndustria() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoItv(), vehiculoBBDD.getTipoItv())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Tipo ITV,");
				if (vehiculoBBDD.getTipoItv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdTipoTarjetaItv(), vehiculoBBDD.getIdTipoTarjetaItv())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Tipo Tarjeta ITV,");
				if (vehiculoBBDD.getIdTipoTarjetaItv() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getTipoVehiculo(), vehiculoBBDD.getTipoVehiculo())) {
				evolucionDto.setTipoVehiculoAnt(utiles.convertirCombo(vehiculoBBDD.getTipoVehiculo()));
				if (vehiculoBBDD.getTipoVehiculo() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
				evolucionDto.setTipoVehiculoNue(utiles.convertirCombo(vehiculoVO.getTipoVehiculo()));
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipvehi(), vehiculoBBDD.getTipvehi())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Tipo Vehículo CAM,");
				if (vehiculoBBDD.getTipvehi() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVariante(), vehiculoBBDD.getVariante())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Variante,");
				if (vehiculoBBDD.getVariante() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVarianteBase(), vehiculoBBDD.getVarianteBase())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Variante Base,");
				if (vehiculoBBDD.getVarianteBase() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getVehiUsado(), vehiculoBBDD.getVehiUsado())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Vehículo Usado,");
				if (vehiculoBBDD.getVehiUsado() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatriAyuntamiento(), vehiculoBBDD.getMatriAyuntamiento())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Matriculación Ayuntamiento,");
				if (vehiculoBBDD.getMatriAyuntamiento() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getLimiteMatrTuris(), vehiculoBBDD.getLimiteMatrTuris()) != 0) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Límite Matriculación Turismo,");
				if (vehiculoBBDD.getLimiteMatrTuris() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (utilesFecha.compararFechaDate(vehiculoVO.getFechaPrimMatri(), vehiculoBBDD.getFechaPrimMatri()) != 0) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Fecha Primera Matriculación,");
				if (vehiculoBBDD.getFechaPrimMatri() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdLugarMatriculacion(), vehiculoBBDD.getIdLugarMatriculacion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Lugar Primera Matriculación,");
				if (vehiculoBBDD.getIdLugarMatriculacion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatriculaOrigen(), vehiculoBBDD.getMatriculaOrigen())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Matrícula Origen,");
				if (vehiculoBBDD.getMatriculaOrigen() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getKmUso(), vehiculoBBDD.getKmUso())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Km Uso,");
				if (vehiculoBBDD.getKmUso() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getHorasUso(), vehiculoBBDD.getHorasUso())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Horas uso,");
				if (vehiculoBBDD.getHorasUso() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesCheckBox(vehiculoVO.getImportado(), vehiculoBBDD.getImportado())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Importado,");
				if (vehiculoBBDD.getImportado() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdProcedencia(), vehiculoBBDD.getIdProcedencia())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Procedencia,");
				if (vehiculoBBDD.getIdProcedencia() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVersion(), vehiculoBBDD.getVersion())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Versión,");
				if (vehiculoBBDD.getVersion() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVersionBase(), vehiculoBBDD.getVersionBase())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Versión Base,");
				if (vehiculoBBDD.getVersionBase() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getViaAnterior(), vehiculoBBDD.getViaAnterior())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Vía Anterior,");
				if (vehiculoBBDD.getViaAnterior() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getViaPosterior(), vehiculoBBDD.getViaPosterior())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Vía Posterior,");
				if (vehiculoBBDD.getViaPosterior() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatriculaOrigExtr(), vehiculoBBDD.getMatriculaOrigExtr())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Matrícula Origen Extranjera,");
				if (vehiculoBBDD.getMatriculaOrigExtr() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}

			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getEsSiniestro(), vehiculoBBDD.getEsSiniestro())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Es siniestro,");
				if (vehiculoBBDD.getEsSiniestro() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getVelocidadMaxima(), vehiculoBBDD.getVelocidadMaxima())) {
				evolucionDto.setOtros(evolucionDto.getOtros() + "Velocidad máxima,");
				if (vehiculoBBDD.getVelocidadMaxima() != null) {
					resultado = 2;
				} else if (resultado != 2) {
					resultado = 1;
				}
			}
//			if (!utiles.sonIgualesObjetos(vehiculoVO.getTieneCargaFinanciera(), vehiculoBBDD.getTieneCargaFinanciera())) {
//				evolucionDto.setOtros(evolucionDto.getOtros() + "Tiene carga financiera,");
//				if (vehiculoBBDD.getTieneCargaFinanciera() != null) {
//					resultado = 2;
//				} else if (resultado != 2) {
//					resultado = 1;
//				}
//			}
		} catch (Exception e) {
			log.error("Error al comparar vehiculo de pantalla con el vehiculo de la bbdd", e);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	// Mantis 14937. David Sierra: Comprueba si un bastidor esta duplicado para
	// un mismo colegiado.
	// Si esta duplicado devuelve true en caso contrario false
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
	@Transactional
	public void eliminar(Long idVehiculo) {
		try {
			List<EvolucionVehiculoVO> lista = servicioEvolucionVehiculo.getEvolucionVehiculo(idVehiculo, null, null);
			for (EvolucionVehiculoVO evolucionVehiculo : lista) {
				servicioEvolucionVehiculo.eliminar(evolucionVehiculo);
			}

			VehiculoVO vehiculo = getVehiculoVO(idVehiculo, null, null, null, null, null);
			vehiculoDao.borrar(vehiculo);
		} catch (Exception e) {
			log.error("Error al eliminar un vehículo", e);
		}
	}

	@Override
	@Transactional
	public ResultBean liberarNive(Long idVehiculo, String numColegiado, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuarioDto) {
		ResultBean resultado = new ResultBean();
		try {
			VehiculoVO vehiculo = getVehiculoVO(idVehiculo, null, null, null, null, null);
			if (vehiculo != null && vehiculo.getNive() != null && !vehiculo.getNive().isEmpty()) {
				vehiculo.setNive(null);
				vehiculoDao.actualizar(vehiculo);
				guardarEvolucionVehiculo(new EvolucionVehiculoDto(), new BigDecimal(idVehiculo), numColegiado, numExpediente, tipoTramite, usuarioDto,
						ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_LIB_NIV);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(" El expediente " + numExpediente + " no se puede liberar porque no tiene asignado un Código Nive ");
			}
		} catch (Exception e) {
			log.error("Error al liberar el Nive", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(" El expediente " + numExpediente + " no se puede liberar porque no tiene asignado un Código Nive ");
		} catch (OegamExcepcion e) {
			log.error("Error al liberar el Nive", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(" El expediente " + numExpediente + " no se puede liberar porque no tiene asignado un Código Nive ");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerMatriculaPorBastidor(String bastidor) {
		String matricula = "";
		try {
			if (bastidor != null && !bastidor.isEmpty()) {
				matricula = vehiculoDao.obtenerMatriculaPorBastidor(bastidor);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la matrícula por bastidor", e.getMessage());
		}
		return matricula;
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoDto primeraMatricula(Date fechaActual) {
		try {
			TramiteTraficoVO tramite = servicioTramiteTrafico.primeraMatricula(fechaActual);
			if (tramite != null && tramite.getVehiculo() != null) {
				return conversor.transform(tramite.getVehiculo(), VehiculoDto.class);
			}

		} catch (Exception e) {
			log.error("Error al recuperar la primera matricula", e.getMessage());
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoDto ultimaMatricula(Date fechaActual) {
		try {
			TramiteTraficoVO tramite = servicioTramiteTrafico.ultimaMatricula(fechaActual);
			if (tramite != null && tramite.getVehiculo() != null) {
				return conversor.transform(tramite.getVehiculo(), VehiculoDto.class);
			}

		} catch (Exception e) {
			log.error("Error al recuperar la ultima matricula", e.getMessage());
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoTramiteTraficoVO getVehiculoTramite(BigDecimal numExpediente, Long idVehiculo) {
		try {
			VehiculoTramiteTraficoVO vehiculoTramiteTraficoVO = servicioVehiculoTramiteTrafico.getVehiculoTramite(numExpediente, idVehiculo);
			if (vehiculoTramiteTraficoVO != null) {
				return vehiculoTramiteTraficoVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el vehiculo tramite trafico", e.getMessage());
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Boolean esCambioServicioPermitido(String idServicio, String idServicioAnterior) {
		try {
			List<CambioServPermitidoVO> listaCambioServPermitidoVO = cambioServPermitidoDao.getCambioPermitido(idServicio, idServicioAnterior);
			if (listaCambioServPermitidoVO != null && !listaCambioServPermitidoVO.isEmpty()) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el cambio de servicio, error: ", e);
		}
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean esTipoVehiculoMotoOCiclomotor(String tipoVehiculo) {
		if (StringUtils.isNotBlank(tipoVehiculo) && ("90".equals(tipoVehiculo) || "91".equals(tipoVehiculo)
				|| "92".equals(tipoVehiculo) || "50".equals(tipoVehiculo) || "51".equals(tipoVehiculo)
				|| "53".equals(tipoVehiculo) || "54".equals(tipoVehiculo))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean esRemolqueOSemiRemlqOExpecial(String tipoVehiculo) {
		if (StringUtils.isNotBlank(tipoVehiculo) && ("S0".equals(tipoVehiculo) || "S1".equals(tipoVehiculo)
				|| "S2".equals(tipoVehiculo) || "S3".equals(tipoVehiculo) || "S4".equals(tipoVehiculo)
				|| "S5".equals(tipoVehiculo) || "S6".equals(tipoVehiculo) || "S7".equals(tipoVehiculo)
				|| "S8".equals(tipoVehiculo) || "S9".equals(tipoVehiculo) || "SA".equals(tipoVehiculo)
				|| "SB".equals(tipoVehiculo) || "SC".equals(tipoVehiculo) || "SD".equals(tipoVehiculo)
				|| "SE".equals(tipoVehiculo) || "SF".equals(tipoVehiculo) || "SH".equals(tipoVehiculo)
				|| "R0".equals(tipoVehiculo) || "R1".equals(tipoVehiculo) || "R2".equals(tipoVehiculo)
				|| "R3".equals(tipoVehiculo) || "R4".equals(tipoVehiculo) || "R5".equals(tipoVehiculo)
				|| "R6".equals(tipoVehiculo) || "R7".equals(tipoVehiculo) || "R8".equals(tipoVehiculo)
				|| "R9".equals(tipoVehiculo) || "RA".equals(tipoVehiculo) || "RB".equals(tipoVehiculo)
				|| "RC".equals(tipoVehiculo) || "RD".equals(tipoVehiculo) || "RE".equals(tipoVehiculo)
				|| "RF".equals(tipoVehiculo) || "RH".equals(tipoVehiculo) || "70".equals(tipoVehiculo)
				|| "71".equals(tipoVehiculo) || "72".equals(tipoVehiculo) || "73".equals(tipoVehiculo)
				|| "74".equals(tipoVehiculo) || "75".equals(tipoVehiculo) || "76".equals(tipoVehiculo)
				|| "77".equals(tipoVehiculo) || "78".equals(tipoVehiculo) || "79".equals(tipoVehiculo)
				|| "7A".equals(tipoVehiculo) || "7B".equals(tipoVehiculo) || "7C".equals(tipoVehiculo)
				|| "7D".equals(tipoVehiculo) || "7E".equals(tipoVehiculo) || "7F".equals(tipoVehiculo)
				|| "7G".equals(tipoVehiculo) || "7H".equals(tipoVehiculo) || "7I".equals(tipoVehiculo)
				|| "7J".equals(tipoVehiculo) || "7K".equals(tipoVehiculo))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}