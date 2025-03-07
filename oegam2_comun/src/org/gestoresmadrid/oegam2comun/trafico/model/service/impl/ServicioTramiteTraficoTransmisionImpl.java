package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.AcreditacionTrafico;
import org.gestoresmadrid.core.model.enumerados.Estado620;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.ModoAdjudicacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoProcedureDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoTransDao;
import org.gestoresmadrid.core.trafico.model.procedure.bean.ValidacionTramite;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoTramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioCheckCTIT;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.tramitar.build.BuildCheckCtitSega;
import org.gestoresmadrid.oegam2comun.trafico.tramitar.build.BuildCtitSega;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCTITDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.EvolucionTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioCriterioConstruccion;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioEvolucionVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.am.service.ServicioWebServiceAm;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioPersistenciaTramiteTrafico;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.utiles.UtilidadesNIFValidator;
import org.gestoresmadrid.oegamComun.vehiculo.service.impl.ServicioValidacionVehiculo;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import oegam.constantes.ConstantesPQ;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarTransmision;
import trafico.beans.jaxb.matriculacion.TipoServicio;
import trafico.beans.transmision.jaxb.ReduccionType;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.enumerados.Paso;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioTramiteTraficoTransmisionImpl implements ServicioTramiteTraficoTransmision {

	private static final long serialVersionUID = 7920646144736949182L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoTransmisionImpl.class);

	private static final String EL_TIPO_DE_TRANSFERENCIA_NO_ES_CORRECTO = "El tipo de transferencia no es correcto";
	private static final String ESTADO_VALIDAR = "estadoValidar";
	private static final String SI = "SI";
	private static final String NO = "NO";
	private static final String ND = "ND"; 

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ServicioEvolucionVehiculo servicioEvolucionVehiculo;

	@Autowired
	ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	ServicioCheckCTIT servicioCheckCTIT;

	@Autowired
	ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	TramiteTraficoTransDao tramiteTraficoTransDao;
	
	@Autowired
	ServicioValidacionVehiculo servicioValidacionVehiculo;
	
	@Autowired
	ServicioCriterioConstruccion servicioCriterioConstruccion;

	@Autowired
	Conversor conversor;

	private ModeloTransmision modeloTransmision;

	@Autowired
	ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	BuildCheckCtitSega buildCheckCtitSega;

	@Autowired
	BuildCtitSega buildCtitSega;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	TramiteTraficoProcedureDao validacionTramiteDao;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;
	
	@Autowired
	UtilidadesNIFValidator utilidadesNif;

	@Autowired
	ServicioWebServiceAm servicioWebServiceAm;
	
	@Autowired
	ServicioPersistenciaTramiteTrafico servicioPersistenciaTramiteTrafico;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Autowired
	GestorDocumentos gestorDocumentos;

	@Override
	@Transactional(readOnly = true)
	public String obtenerTipoTransferenciaTramite(BigDecimal numExpediente) {
		try {
			TramiteTrafTranVO tramite = servicioTramiteTrafico.getTramiteTransmision(numExpediente, Boolean.FALSE);
			if (tramite != null) {
				return tramite.getTipoTransferencia();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo transferencia del trámite: " + numExpediente
					+ ", error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafTranDto getTramiteTransmision(BigDecimal numExpediente, boolean tramiteCompleto) {
		log.debug("Recuperar el trámite transmisión: " + numExpediente);
		TramiteTrafTranDto result = null;
		try {
			TramiteTrafTranVO tramite = servicioTramiteTrafico.getTramiteTransmision(numExpediente, tramiteCompleto);

			if (tramite != null) {
				result = conversor.transform(tramite, TramiteTrafTranDto.class);
				if (tramite.getTasa2() != null) {
					result.setCodigoTasaCamser(tramite.getTasa2().getCodigoTasa());
				}
				convertirIntervinientesVoToDto(tramite, result);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de transmisión: " + numExpediente, e, numExpediente.toString());
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public String getTipoTransferencia(BigDecimal numExpediente) {
		try {
			TramiteTrafTranVO tramite = servicioTramiteTrafico.getTramiteTransmision(numExpediente, Boolean.FALSE);
			if (tramite != null) {
				return tramite.getTipoTransferencia();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de transferencia: " + numExpediente, e, numExpediente.toString());
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean validarTramite(TramiteTrafTranDto tramite, Long idUsuario) {
		ResultBean respuesta = new ResultBean(Boolean.FALSE);
		String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
		if ("2631".equals(tramite.getNumColegiado()) && SI.equals(gestionarConAM)) {
			ResultadoBean resultVal = servicioWebServiceAm.validarCtit(tramite.getNumExpediente(),
					tramite.getUsuarioDto().getIdUsuario().longValue());
			if (!resultVal.getError()) {
				if (StringUtils.isNotBlank(resultVal.getEstado())) {
					respuesta.addAttachment(ESTADO_VALIDAR, Long.parseLong(resultVal.getEstado()));
					if (StringUtils.isNotBlank(resultVal.getMensaje())) {
						respuesta.addMensajeALista(resultVal.getMensaje());
					}
				} else {
					respuesta.setError(Boolean.TRUE);
					respuesta.addMensajeALista(
							"No se ha podido recuperar el estado del trámite: " + tramite.getNumExpediente());
				}
			} else {
				respuesta.setError(Boolean.TRUE);
				respuesta.addMensajeALista(resultVal.getMensaje());
			}
		} else {
			respuesta = validacionesBloqueantes(tramite);
			if (respuesta != null && !respuesta.getError()) {
				try {
					if (tramite.getEstado().equalsIgnoreCase(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())) {
						respuesta.addError("No es posible validar un trámite que ya se encuentra finalizado.");
						respuesta.setError(true);
						log.error("No es posible validar un trámite que ya se encuentra finalizado.");
					} else {
						if (tramite.getFechaUltModif() != null) {
							tramite.setFechaUltModif(utilesFecha.getFechaHoraActual());
						}
						TramiteTrafTranVO trafTranVO = conversor.transform(tramite, TramiteTrafTranVO.class);
						ValidacionTramite vTramite = validacionTramiteDao.validarTramiteCTIT(trafTranVO);
						if (vTramite != null && vTramite.getSqlerrm().equals("Correcto")) {
							log.debug("Trámite CTit validado: " + tramite.getNumExpediente());
							respuesta.setError(false);
							if (vTramite.getInformacion() != null) {
								respuesta.addMensajeALista(vTramite.getInformacion());
							}
							if(comprobarNuevosTramites(tramite)) {
								if(EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum().equalsIgnoreCase(trafTranVO.getEstado().toString())) {
									if("SI".equalsIgnoreCase(trafTranVO.getAcreditacionPago())
											&& !TipoTransferencia.tipo5.getValorEnum().equalsIgnoreCase(trafTranVO.getTipoTransferencia())) {
										respuesta.addAttachment(ESTADO_VALIDAR,Long.parseLong(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()));
									}else if("HERENCIA".equalsIgnoreCase(trafTranVO.getAcreditaHerenciaDonacion()) ||
											"DONACION".equalsIgnoreCase(trafTranVO.getAcreditaHerenciaDonacion())) {
										respuesta.addAttachment(ESTADO_VALIDAR, Long.parseLong(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()));
									}else if("SI".equalsIgnoreCase(trafTranVO.getAcreditacionPago())
										&& TipoTransferencia.tipo5.getValorEnum().equalsIgnoreCase(trafTranVO.getTipoTransferencia())) {
										respuesta.addAttachment(ESTADO_VALIDAR, Long.parseLong(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()));
									}else if(utiles.esUsuarioExentoCtr(trafTranVO.getNumColegiado()) && esExentoCtr(trafTranVO)) {
										trafTranVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()));
										trafTranVO.setExentoCtr("SI");
										respuesta.addAttachment(ESTADO_VALIDAR, Long.parseLong(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()));
									}
									List<EvolucionTramiteTraficoVO> evolucion = servicioEvolucionTramite.getTramiteFinalizadoErrorAutorizado(trafTranVO.getNumExpediente());
									if (evolucion != null && !evolucion.isEmpty()) {
										respuesta.addAttachment(ESTADO_VALIDAR, Long.parseLong(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()));
									}
								}
							}else {
								respuesta.addAttachment(ESTADO_VALIDAR, vTramite.getEstado());
							}
						} else {
							respuesta.setError(true);
							if (vTramite != null) {
								respuesta.addMensajeALista(vTramite.getSqlerrm());
							} else {
								respuesta.addMensajeALista("Error al validar");
							}
						}
					}
				} catch (Exception e) {
					log.error("Error al validar el trámite de ctit: " + tramite.getNumExpediente(), e,
							tramite.getNumExpediente().toString());
					respuesta.addMensajeALista("Error al validar el trámite de CTit");
					respuesta.setError(true);
				}
			}
		}
		return respuesta;
	}

	private boolean esExentoCtr(TramiteTrafTranVO trafTranVO) {
		if(esTransporteBasura(trafTranVO)) {
			return Boolean.TRUE;
		}else if(esTransporteDinero(trafTranVO)) {
			return Boolean.TRUE;
		}else if(esVelMaxAutorizada(trafTranVO)) {
			return Boolean.TRUE;
		}else if(esVehUnidoMaquina(trafTranVO)) {
			return Boolean.TRUE;
		}else if(esEmpresaASC(trafTranVO)) {
			return Boolean.TRUE;
		}else if(esMinistCCEntLocal(trafTranVO)) {
			return Boolean.TRUE;
		}else if(esAutoescuela(trafTranVO)) {
			return Boolean.TRUE;
		}else if(esCompraventaVeh(trafTranVO)) {
			return Boolean.TRUE;
		}else if(esVivienda(trafTranVO)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public boolean esTransporteBasura(TramiteTrafTranVO trafTranVO) {
		return (TipoServicio.A_11.value().equalsIgnoreCase(trafTranVO.getVehiculo().getIdServicio()) && "55".equalsIgnoreCase(trafTranVO.getVehiculo().getIdCriterioUtilizacion()));
	}
	
	public boolean esTransporteDinero(TramiteTrafTranVO trafTranVO) {
		return ("22".equalsIgnoreCase(trafTranVO.getVehiculo().getIdCriterioUtilizacion()));
	}
	
	public boolean esVelMaxAutorizada(TramiteTrafTranVO trafTranVO) {
		int velMax = 40;
		return ("70".equalsIgnoreCase(trafTranVO.getVehiculo().getTipoVehiculo()) && trafTranVO.getVehiculo().getVelocidadMaxima().intValue() <= velMax);
	}
	

	public boolean esVehUnidoMaquina(TramiteTrafTranVO trafTranVO) {
		return ("54".equalsIgnoreCase(trafTranVO.getVehiculo().getIdCriterioUtilizacion()) || "60".equalsIgnoreCase(trafTranVO.getVehiculo().getIdCriterioUtilizacion())
				|| "61".equalsIgnoreCase(trafTranVO.getVehiculo().getIdCriterioUtilizacion()) 
				|| "62".equalsIgnoreCase(trafTranVO.getVehiculo().getIdCriterioUtilizacion())
				|| "63".equalsIgnoreCase(trafTranVO.getVehiculo().getIdCriterioUtilizacion()));
	}
	
	public boolean esEmpresaASC(TramiteTrafTranVO trafTranVO) {
		for (IntervinienteTraficoVO intervinienteTraficoVO : trafTranVO.getIntervinienteTraficosAsList()) {
			if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente()) && TipoPersona.Juridica.getValorEnum().equalsIgnoreCase(intervinienteTraficoVO.getPersona().getTipoPersona())){
				return(intervinienteTraficoVO.getAutonomo() != null && ("SI".equals(intervinienteTraficoVO.getAutonomo()) || "S".equals(intervinienteTraficoVO.getAutonomo()) || "true".equals(intervinienteTraficoVO.getAutonomo()))
						&& "8541".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae()));
			}
		}
		return false;
	}
	
	public boolean esMinistCCEntLocal(TramiteTrafTranVO trafTranVO) {
		for (IntervinienteTraficoVO intervinienteTraficoVO : trafTranVO.getIntervinienteTraficosAsList()) {
			if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
				return (intervinienteTraficoVO.getId().getNif().toUpperCase().startsWith("P") || intervinienteTraficoVO.getId().getNif().toUpperCase().startsWith("Q") 
						|| intervinienteTraficoVO.getId().getNif().toUpperCase().startsWith("S"));
			}
		}
		return false;
		 
	}
	
	public boolean esAutoescuela(TramiteTrafTranVO trafTranVO) {
		for (IntervinienteTraficoVO intervinienteTraficoVO : trafTranVO.getIntervinienteTraficosAsList()) {
			if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
				return(intervinienteTraficoVO.getAutonomo() != null && ("SI".equals(intervinienteTraficoVO.getAutonomo()) || "S".equals(intervinienteTraficoVO.getAutonomo()) || "true".equals(intervinienteTraficoVO.getAutonomo()))
						&& "9331".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae()));
			}
		}
		return false;
	}
	
	public boolean esCompraventaVeh(TramiteTrafTranVO trafTranVO) {
		for (IntervinienteTraficoVO intervinienteTraficoVO : trafTranVO.getIntervinienteTraficosAsList()) {
			if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente()) && TipoPersona.Juridica.getValorEnum().equalsIgnoreCase(intervinienteTraficoVO.getPersona().getTipoPersona())){
				return(intervinienteTraficoVO.getAutonomo() != null && ("SI".equals(intervinienteTraficoVO.getAutonomo()) || "S".equals(intervinienteTraficoVO.getAutonomo()) || "true".equals(intervinienteTraficoVO.getAutonomo()))
						&& ("6151".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae()) || "6541".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae())));
			}
		}
		return false;
	}

	public boolean esVivienda(TramiteTrafTranVO trafTranVO) {
		return (TipoServicio.B_17.value().equalsIgnoreCase(trafTranVO.getVehiculo().getIdServicio()) && "48".equalsIgnoreCase(trafTranVO.getVehiculo().getIdCriterioUtilizacion()));
	}

	public Boolean comprobarNuevosTramites(TramiteTrafTranDto tramite) {
		if("SI".equalsIgnoreCase(tramite.getAcreditacionPago()) && utiles.esUsuarioIvtm(tramite.getNumColegiado())) {
			return Boolean.TRUE;
		}
		/*else if(AcreditacionTrafico.Herencia.getValorEnum().equalsIgnoreCase(tramite.getAcreditaHerenciaDonacion()) || 
				AcreditacionTrafico.Donacion.getValorEnum().equalsIgnoreCase(tramite.getAcreditaHerenciaDonacion())) {
			return Boolean.TRUE;
		}else if("SI".equalsIgnoreCase(tramite.getActaSubasta()) || "SI".equalsIgnoreCase(tramite.getSentenciaJudicial())) {
			return Boolean.TRUE;
		}*/
		return Boolean.FALSE;
	}

	private ResultBean validacionesBloqueantes(TramiteTrafTranDto tramite) {
		ResultBean resultBean = new ResultBean();

		if (!validarTerritorialidad(tramite)) {
			String mensajeTerritorialidad = gestorPropiedades
					.valorPropertie("territorialidad.ctit.exclusiones.mensaje");
			resultBean.setError(true);
			resultBean.setMensaje(mensajeTerritorialidad);
			return resultBean;
		}

		if (tramite.getVehiculoDto() != null) {
			VehiculoTramiteTraficoVO vehiculoTramite = servicioVehiculo.getVehiculoTramite(tramite.getNumExpediente(),
					tramite.getVehiculoDto().getIdVehiculo().longValue());
			if (gestorPropiedades.valorPropertie("nuevas.espcificaciones.ctit.5").equalsIgnoreCase(SI)) {
				if (vehiculoTramite != null) {
					// if(!TipoPersona.Juridica.getValorEnum().equals(tramite.getTransmitente().getPersona().getTipoPersona()))
					// {
					if (vehiculoTramite.getKilometros() != null
							&& (tramite.getVehiculoDto().getFechaLecturaKm() == null
								|| StringUtils.isBlank(tramite.getVehiculoDto().getDoiResponsableKm()))) {
						resultBean.setError(true);
						resultBean.addMensajeALista(
								"Si rellena el kilometraje del vehículo, debe rellenar la fecha de lectura y el NIF del responsable de la lectura");
					}
					/*
					 * }else {
					 * if(TipoPersona.Juridica.getValorEnum().equals(tramite.getTransmitente().
					 * getPersona().getTipoPersona()) && vehiculoTramite.getKilometros() == null) {
					 * resultBean.setError(true); resultBean.
					 * addMensajeALista("Para personas juridicas es obligatorío indicar el kilometraje."
					 * ); }else
					 * if(TipoPersona.Juridica.getValorEnum().equals(tramite.getTransmitente().
					 * getPersona().getTipoPersona()) && vehiculoTramite.getKilometros() != null) {
					 * if (tramite.getVehiculoDto().getFechaLecturaKm() == null ||
					 * StringUtils.isBlank(tramite.getVehiculoDto().getDoiResponsableKm())) {
					 * resultBean.setError(true); resultBean.
					 * addMensajeALista("Si rellena el kilometraje del vehículo, debe rellenar la fecha de lectura y el NIF del responsable de la lectura"
					 * ); } } }
					 */
					return resultBean;
				}
			} else {
				if (vehiculoTramite != null && vehiculoTramite.getKilometros() != null
						&& (tramite.getVehiculoDto().getFechaLecturaKm() == null
							|| StringUtils.isBlank(tramite.getVehiculoDto().getDoiResponsableKm()))) {
					resultBean.setError(true);
					resultBean.addMensajeALista(
							"Si rellena el kilometraje del vehículo, debe rellenar la fecha de lectura y el NIF del responsable de la lectura");
					return resultBean;
				}
			}

			if (SI.equals(tramite.getConsentimientocambio())) {
				List<String> resultValidaDirVehiculo = validarDireccionVehiculo(tramite.getVehiculoDto());
				if (!resultValidaDirVehiculo.isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("LOCALIZACION DEL VEHICULO: ");
					resultBean.addListaMensajes(resultValidaDirVehiculo);
					return resultBean;
				}
			}

		}

		try {
			ResultBean resultValCadNif = comprobarCaducidadNifs(tramite);
			if (resultValCadNif.getError()) {
				return resultValCadNif;
			} else if (!resultValCadNif.getError() && resultValCadNif.getListaMensajes() != null
					&& !resultValCadNif.getListaMensajes().isEmpty()) {
				resultBean.setListaMensajes(resultValCadNif.getListaMensajes());
			}
		} catch (ParseException e) {
			log.error("Al comprobar la fecha de caducidad de un NIF se ha producido el siguiente error: "
					+ e.getMessage());
		}

		if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramite.getTipoTramite())) {
			ResultBean resultadoValidaFecha = validarFechaFacturacionContrato(tramite);
			if (resultadoValidaFecha.getListaMensajes() != null && !resultadoValidaFecha.getListaMensajes().isEmpty()) {
				for (String mensaje : resultadoValidaFecha.getListaMensajes()) {
					resultBean.setMensaje(mensaje);
					resultBean.setError(true);
				}
				return resultBean;
			}
		}
		return resultBean;
	}

	private List<String> validarDireccionVehiculo(VehiculoDto vehiculo) {
		List<String> listaMensajes = new ArrayList<>();

		if (vehiculo.getDireccion() != null) {
			DireccionDto direccionVehiculo = vehiculo.getDireccion();
			if (StringUtils.isBlank(direccionVehiculo.getIdProvincia())) {
				listaMensajes.add("La provincia del vehículo es obligatoria");
			}

			if (StringUtils.isBlank(direccionVehiculo.getIdMunicipio())) {
				listaMensajes.add("El municipio del vehículo es obligatorio");
			}

			if (StringUtils.isBlank(direccionVehiculo.getIdTipoVia())) {
				listaMensajes.add("El tipo de vía del vehículo es obligatorio");
			}

			if (StringUtils.isBlank(direccionVehiculo.getNombreVia())) {
				listaMensajes.add("El número de vía del vehículo es obligatorio");
			} else if (direccionVehiculo.getNombreVia().length() > 50) {
				listaMensajes.add("El nombre de vía del vehículo no puede tener más de 50 caracteres");
			}

			if (StringUtils.isBlank(direccionVehiculo.getNumero())) {
				listaMensajes.add("El número de vía del vehículo es obligatorio");
			}

			if (StringUtils.isBlank(direccionVehiculo.getCodPostal())) {
				listaMensajes.add("El código postal del vehículo es obligatorio");
			} else if (direccionVehiculo.getCodPostal().length() < 5) {
				listaMensajes.add("El código postal del vehículo debe tener 5 caracteres");
			}

			if (direccionVehiculo.getPortal() != null && direccionVehiculo.getPortal().length() > 1) {
				listaMensajes.add("El portal de la dirección del vehículo no puede tener más de 1 caracter");
			}

			if (direccionVehiculo.getEscalera() != null && direccionVehiculo.getEscalera().length() > 2) {
				listaMensajes.add("La escalera de la dirección del vehículo no puede tener más de 2 caracteres");
			}

			if (direccionVehiculo.getPlanta() != null && direccionVehiculo.getPlanta().length() > 3) {
				listaMensajes.add("El piso de la dirección del vehículo no puede tener más de 3 caracteres");
			}

			if (direccionVehiculo.getPuerta() != null && direccionVehiculo.getPuerta().length() > 2) {
				listaMensajes.add("La puerta de la dirección del vehículo no puede tener más de 2 caracteres");
			}
		} else {
			listaMensajes.add("La dirección del vehículo es obligatoria.");
		}

		return listaMensajes;
	}

	private ResultBean validarFechaFacturacionContrato(TramiteTrafTranDto tramite) {
		ResultBean resultado = new ResultBean();
		ArrayList<String> errores = new ArrayList<>();
		resultado.setError(Boolean.FALSE);
		if (tramite.getTipoTransferencia() != null
				&& !TipoTransferencia.tipo4.getValorEnum().equals(tramite.getTipoTransferencia())
				&& !TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia())
				&& (!ModoAdjudicacion.tipo3.getValorEnum().equals(tramite.getModoAdjudicacion()))) {
			if (tramite.getFechaContrato() == null && tramite.getFechaFactura() == null) {
				errores.add("Falta fecha de factura o contrato");
			}
			if (tramite.getFechaContrato() != null && tramite.getFechaFactura() != null) {
				errores.add("No se pueden presentar las fechas de factura y contrato a la vez");
				errores.add("Solamente es válida una de las dos en función del documento aportado");
			}
		}
		resultado.addListaMensajes(errores);
		return resultado;
	}

	private ResultBean comprobarCaducidadNifs(TramiteTrafTranDto tramite) throws ParseException {
		ResultBean result = new ResultBean(false);
		Fecha fechaActual = utilesFecha.getFechaActual();
		Date fechaAdvertencia = utilesFecha.getDate((utilesFecha.sumaDias(fechaActual, "-30")));
		List<TipoInterviniente> listaIntervinientes = new ArrayList<>();
		listaIntervinientes.add(TipoInterviniente.Adquiriente);
		listaIntervinientes.add(TipoInterviniente.RepresentanteAdquiriente);
		listaIntervinientes.add(TipoInterviniente.ConductorHabitual);
		listaIntervinientes.add(TipoInterviniente.Transmitente);
		listaIntervinientes.add(TipoInterviniente.RepresentanteTransmitente);
		listaIntervinientes.add(TipoInterviniente.CotitularTransmision);
		listaIntervinientes.add(TipoInterviniente.Compraventa);
		listaIntervinientes.add(TipoInterviniente.RepresentanteCompraventa);
		listaIntervinientes.add(TipoInterviniente.Arrendatario);
		listaIntervinientes.add(TipoInterviniente.RepresentanteArrendatario);
		IntervinienteTraficoDto interviniente = null;

		for (TipoInterviniente tipo : listaIntervinientes) {
			switch (tipo) {
			case Adquiriente:
				IntervinienteTraficoDto adquiriente = tramite.getAdquiriente();
				if (adquiriente != null && adquiriente.getPersona() != null) {
					interviniente = adquiriente;
				}
				break;
			case RepresentanteAdquiriente:
				IntervinienteTraficoDto repreAdquiriente = tramite.getRepresentanteAdquiriente();
				if (repreAdquiriente != null && repreAdquiriente.getPersona() != null) {
					interviniente = repreAdquiriente;
				}
				break;
			case ConductorHabitual:
				IntervinienteTraficoDto conductor = tramite.getConductorHabitual();
				if (conductor != null && conductor.getPersona() != null) {
					interviniente = conductor;
				}
				break;
			case TransmitenteTrafico:
				IntervinienteTraficoDto transmitente = tramite.getTransmitente();
				if (transmitente != null && transmitente.getPersona() != null) {
					interviniente = transmitente;
				}
				break;
			case RepresentanteTransmitente:
				IntervinienteTraficoDto repreTransmitente = tramite.getRepresentanteTransmitente();
				if (repreTransmitente != null && repreTransmitente.getPersona() != null) {
					interviniente = repreTransmitente;
				}
				break;
			case CotitularTransmision:
				IntervinienteTraficoDto cotitular = tramite.getPrimerCotitularTransmision();
				if (cotitular != null && cotitular.getPersona() != null) {
					interviniente = cotitular;
				}
				break;
			case Compraventa:
				IntervinienteTraficoDto compraventa = tramite.getCompraVenta();
				if (compraventa != null && compraventa.getPersona() != null) {
					interviniente = compraventa;
				}
				break;
			case RepresentanteCompraventa:
				IntervinienteTraficoDto repreCompraventa = tramite.getRepresentanteCompraventa();
				if (repreCompraventa != null && repreCompraventa.getPersona() != null) {
					interviniente = repreCompraventa;
				}
				break;
			case Arrendatario:
				IntervinienteTraficoDto arrendatario = tramite.getArrendatario();
				if (arrendatario != null && arrendatario.getPersona() != null) {
					interviniente = arrendatario;
				}
				break;
			case RepresentanteArrendatario:
				IntervinienteTraficoDto repreArrendatario = tramite.getRepresentanteArrendatario();
				if (repreArrendatario != null && repreArrendatario.getPersona() != null) {
					interviniente = repreArrendatario;
				}
				break;
			default:
				interviniente = null;
				break;
			}

			if (interviniente != null) {
				ResultBean resultValNif = validarCaducidadNif(interviniente, tramite, fechaActual, fechaAdvertencia);
				if (resultValNif != null && resultValNif.getError()) {
					result.setError(true);
				}
				if (resultValNif.getListaMensajes() != null && !resultValNif.getListaMensajes().isEmpty()) {
					for (String mensaje : resultValNif.getListaMensajes()) {
						result.addMensajeALista(mensaje);
					}
				}
			}

		}
		return result;
	}

	private ResultBean validarCaducidadNif(IntervinienteTraficoDto interviniente, TramiteTrafTranDto tramite,
			Fecha fechaActual, Date fechaAdvertencia) {
		ResultBean resultado = new ResultBean(false);
		boolean sinFechaFactura = false;
		boolean sinFechaContrato = false;
		try {
			if (interviniente != null) {
				PersonaDto persona = interviniente.getPersona();
				if (persona != null && persona.getFechaCaducidadNif() != null) {
					fechaAdvertencia = utilesFecha.sumaDias(persona.getFechaCaducidadNif(), "-30");
					// Si la fecha de caducidad es después de la fecha actual, pero anterior a la
					// fecha de advertencia (está en el rango), mensaje de próximo a caducar
					if (persona.getFechaCaducidadNif().getDate().before(fechaActual.getDate())) {
						// Fecha de Factura prevalece sobre fecha de caducidad de los NIFs
						if (tramite.getFechaFactura() != null) {
							if (persona.getFechaCaducidadNif().getDate()
									.getTime() < (tramite.getFechaFactura().getTime())) {
								if (!ModoAdjudicacion.tipo3.getValorEnum().equals(tramite.getModoAdjudicacion())) {
									resultado.addMensajeALista("NIF del "
											+ TipoInterviniente.convertirTexto(interviniente.getTipoInterviniente())
											+ " caducado");
									resultado.setError(true);
								} else {
									resultado.setError(false);
								}
							}
						} else {
							sinFechaFactura = true;
						}
						if (tramite.getFechaContrato() != null) {
							if (persona.getFechaCaducidadNif().getDate()
									.getTime() < (tramite.getFechaContrato().getTime())) {
								if (!ModoAdjudicacion.tipo3.getValorEnum().equals(tramite.getModoAdjudicacion())) {
									resultado.addMensajeALista("NIF del "
											+ TipoInterviniente.convertirTexto(interviniente.getTipoInterviniente())
											+ " caducado");
									resultado.setError(true);
								} else {
									resultado.setError(false);
								}
							}
						} else {
							sinFechaContrato = true;
						}
						if (sinFechaFactura && sinFechaContrato) {
							if (!ModoAdjudicacion.tipo3.getValorEnum().equals(tramite.getModoAdjudicacion())) {
								resultado.addMensajeALista("NIF del "
										+ TipoInterviniente.convertirTexto(interviniente.getTipoInterviniente())
										+ " caducado");
								resultado.setError(true);
							} else {
								resultado.setError(false);
							}
						}
					}
					// Comprobamos que tenga documento alternativo, para que en el caso que el DNI esté
					// caducado no falle
					if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()
							&& persona.getFechaCaducidadAlternativo() != null
							&& StringUtils.isNotBlank(persona.getTipoDocumentoAlternativo())) {
						resultado.setError(false);
					}
				}
			}
		} catch (ParseException e) {
			log.error("Ha sucedido un error a la hora de validar la caducidad del NIF, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar la caducidad del NIF del "
					+ TipoInterviniente.convertirTexto(interviniente.getTipoInterviniente()));
		}
		return resultado;
	}

	private boolean validarTerritorialidad(TramiteTrafTranDto tramite) {
		boolean valido = true;
		boolean adquirienteValido = true;
		boolean transmitenteValido = true;
		boolean vehiculoValido = true;
		try {
			IntervinienteTraficoDto adquiriente = null;
			IntervinienteTraficoDto transmitente = null;
			IntervinienteTraficoDto compraventa = null;
			VehiculoDto vehiculo = null;

			String provinciaAdquiriente = null;
			String provinciaTransmitente = null;
			String provinciaVehiculo = null;

			String[] arrayExcluidas = gestorPropiedades.valorPropertie("territorialidad.ctit.exclusiones.provincias")
					.split(",");
			if (tramite != null) {
				adquiriente = tramite.getAdquiriente();
				transmitente = tramite.getTransmitente();
				compraventa = tramite.getCompraVenta();
				vehiculo = tramite.getVehiculoDto();
			}

			// Comparar tipo de transferencia para tomar datos del adquiriente o Compraventa
			if (!TipoTransferencia.tipo5.equals(tramite.getTipoTransferencia()) && adquiriente != null
					&& adquiriente.getDireccion() != null) {
				provinciaAdquiriente = servicioDireccion
						.obtenerNombreProvincia(adquiriente.getDireccion().getIdProvincia());
			} else if (compraventa != null && compraventa.getDireccion() != null) {
				provinciaAdquiriente = servicioDireccion
						.obtenerNombreProvincia(compraventa.getDireccion().getIdProvincia());
			}

			if (transmitente != null && transmitente.getDireccion() != null) {
				provinciaTransmitente = servicioDireccion
						.obtenerNombreProvincia(transmitente.getDireccion().getIdProvincia());
			}

			if (vehiculo != null && vehiculo.getDireccion() != null) {
				provinciaVehiculo = servicioDireccion.obtenerNombreProvincia(vehiculo.getDireccion().getIdProvincia());
				if (provinciaVehiculo == null) {
					provinciaVehiculo = provinciaTransmitente;
				}
			}

			for (String provinciaExcluida : arrayExcluidas) {
				if (provinciaExcluida.equals(provinciaAdquiriente)) {
					adquirienteValido = false;
				}
				if (provinciaExcluida.equals(provinciaTransmitente)) {
					transmitenteValido = false;
				}
				if (NO.equals(tramite.getConsentimientocambio()) || provinciaExcluida.equals(provinciaVehiculo)) {
					vehiculoValido = false;
				}
			}
			if (!adquirienteValido && !transmitenteValido && !vehiculoValido) {
				valido = false;
			}
		} catch (Throwable e) {
			valido = true;
			log.error("Error general validando la territorialidad del trámite " + tramite.getNumExpediente()
					+ ". Se da como válida para permitir la ejecución normal de la validación.");
		}

		return valido;
	}

	private void convertirIntervinientesVoToDto(TramiteTrafTranVO tramiteTrafTranVO,
			TramiteTrafTranDto tramiteTrafTranDto) {
		if (tramiteTrafTranVO.getIntervinienteTraficos() != null) {
			for (IntervinienteTraficoVO interviniente : tramiteTrafTranVO.getIntervinienteTraficosAsList()) {
				if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Adquiriente.getValorEnum())) {
					tramiteTrafTranDto
							.setAdquiriente(conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.TransmitenteTrafico.getValorEnum())) {
					tramiteTrafTranDto
							.setTransmitente(conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.Presentador.getValorEnum())) {
					tramiteTrafTranDto
							.setPresentador(conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.RepresentanteAdquiriente.getValorEnum())) {
					tramiteTrafTranDto.setRepresentanteAdquiriente(
							conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.RepresentanteTransmitente.getValorEnum())) {
					tramiteTrafTranDto.setRepresentanteTransmitente(
							conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.ConductorHabitual.getValorEnum())) {
					tramiteTrafTranDto
							.setConductorHabitual(conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.RepresentanteCompraventa.getValorEnum())) {
					tramiteTrafTranDto.setRepresentanteCompraventa(
							conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.Arrendatario.getValorEnum())) {
					tramiteTrafTranDto
							.setArrendatario(conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.RepresentanteArrendatario.getValorEnum())) {
					tramiteTrafTranDto.setRepresentanteArrendatario(
							conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.CotitularTransmision.getValorEnum())
						&& interviniente.getNumInterviniente() == 1) {
					tramiteTrafTranDto.setPrimerCotitularTransmision(
							conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.CotitularTransmision.getValorEnum())
						&& interviniente.getNumInterviniente() == 2) {
					tramiteTrafTranDto.setSegundoCotitularTransmision(
							conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.Compraventa.getValorEnum())) {
					tramiteTrafTranDto
							.setCompraVenta(conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.RepresentantePrimerCotitularTransmision.getValorEnum())) {
					tramiteTrafTranDto.setRepresentantePrimerCotitularTransmision(
							conversor.transform(interviniente, IntervinienteTraficoDto.class));
				} else if (interviniente.getId().getTipoInterviniente()
						.equals(TipoInterviniente.RepresentanteSegundoCotitularTransmision.getValorEnum())) {
					tramiteTrafTranDto.setRepresentanteSegundoCotitularTransmision(
							conversor.transform(interviniente, IntervinienteTraficoDto.class));
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultBean guardarTramite(TramiteTrafTranDto tramiteDto, BigDecimal idUsuario, boolean desdeValidar,
			boolean desdeJustificante, boolean admin) {
		ResultBean respuesta = new ResultBean();
		TramiteTrafTranVO tramiteTrans = new TramiteTrafTranVO();
		try {
			tramiteDto = cargaNumColegiado(tramiteDto);
			if (tramiteDto.getJefaturaTraficoDto() == null
					|| tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial() == null
					|| "-1".equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (tramiteDto.getIdContrato() != null) {
					ContratoDto contratoDto = servicioContrato.getContratoDto(tramiteDto.getIdContrato());
					tramiteDto.setJefaturaTraficoDto(contratoDto.getJefaturaTraficoDto());
				}
			}

			if (!respuesta.getError()) {
				UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
				tramiteDto.setUsuarioDto(usuario);

				// Datos del VO
				tramiteTrans = convertirDTOVO(tramiteDto);

				// Guardamos vehículo
				ResultBean respuestaVehiculo = null;
				if (desdeJustificante) {
					respuestaVehiculo = guardarVehiculo(tramiteDto, tramiteTrans, usuario,
							ServicioVehiculo.CONVERSION_VEHICULO_JUSTIFICANTE, admin);
				} else {
					respuestaVehiculo = guardarVehiculo(tramiteDto, tramiteTrans, usuario, "", admin);
				}
				respuesta.addListaMensajes(respuestaVehiculo.getListaMensajes());

				ResultBean respuestaTramite = guardarTramite(tramiteDto, tramiteTrans, desdeValidar);
				if (respuestaTramite != null && !respuestaTramite.getError()) {
					respuesta.addAttachment(NUMEXPEDIENTE, tramiteTrans.getNumExpediente());

					// Guardamos interviniente
					ResultBean respuestaInterviniente = guardarIntervinientes(tramiteDto, tramiteTrans, usuario,
							desdeJustificante);
					respuesta.addListaMensajes(respuestaInterviniente.getListaMensajes());
				} else {
					log.error("Error al guardar el trámite de transmisión: " + tramiteTrans.getNumExpediente()
							+ ". Mensaje: " + respuestaTramite.getMensaje());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					respuesta.setError(true);
					respuesta.addMensajeALista(respuestaTramite.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de transmisión: " + tramiteTrans.getNumExpediente() + ". Mensaje: "
					+ e.getMessage());
			respuesta.setError(true);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.addMensajeALista(e.getMessage());
		}
		return respuesta;
	}
	@Override
	public void actualizarTramiteCtit(TramiteTrafTranVO trafTranVOBBDD, TramiteTrafTranDto tramiteTrafTranDto, Long estado,BigDecimal idUsuario) {
		TramiteTrafTranVO tramiteTrafTranVO = conversor.transform(tramiteTrafTranDto, TramiteTrafTranVO.class);
		try {
			if (estado != null) {
				tramiteTrafTranVO.setEstado(BigDecimal.valueOf(estado));
			}
			if (tramiteTrafTranVO.getTasa1() != null && tramiteTrafTranVO.getTasa1().getCodigoTasa() != null && !tramiteTrafTranVO.getTasa1().getCodigoTasa().isEmpty()) {
				tramiteTrafTranVO.setTasa1(trafTranVOBBDD.getTasa1());
			}else {
				tramiteTrafTranVO.setTasa1(null);
			}
			if (tramiteTrafTranVO.getTasa2() != null && tramiteTrafTranVO.getTasa2().getCodigoTasa() != null && !tramiteTrafTranVO.getTasa2().getCodigoTasa().isEmpty()) {
				tramiteTrafTranVO.setTasa2(trafTranVOBBDD.getTasa2());
			}else {
				tramiteTrafTranVO.setTasa2(null);
			}
			String estadoAntiguo = tramiteTrafTranDto.getEstado().toString();
			servicioPersistenciaTramiteTrafico.guardarOActualizar(tramiteTrafTranVO);
			guardarEvolucionTramite(tramiteTrafTranVO,estadoAntiguo,idUsuario);
		} catch (Exception e) {
			log.error("Error al guardar al actualizar la tasa que hay en base de datos para el expediente: " + tramiteTrafTranVO.getNumExpediente() + ". Mensaje: "
					+ e.getMessage());
		}
		
	}
	
	private void guardarEvolucionTramite(TramiteTrafTranVO tramiteTrafTranVO, String estadoAnterior, BigDecimal idUsuario) {
		if(!tramiteTrafTranVO.getEstado().equals(new BigDecimal(estadoAnterior))) {
			EvolucionTramiteTraficoDto evolucion = new EvolucionTramiteTraficoDto();
			if (estadoAnterior != null && !estadoAnterior.isEmpty()) {
				evolucion.setEstadoAnterior(new BigDecimal(estadoAnterior));
			} else {
				evolucion.setEstadoAnterior(BigDecimal.ZERO);
			}
			evolucion.setEstadoNuevo(tramiteTrafTranVO.getEstado());
			UsuarioDto usuarioDto = new UsuarioDto();
			usuarioDto.setIdUsuario(idUsuario);
			evolucion.setUsuarioDto(usuarioDto);
			evolucion.setNumExpediente(tramiteTrafTranVO.getNumExpediente());
			servicioEvolucionTramite.guardar(evolucion);	
		}
		
	}

	@Override
	public TramiteTrafTranVO getTramite(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			TramiteTrafTranVO trafTranVO = servicioTramiteTrafico.getTramiteTransmision(numExpediente, Boolean.FALSE);
			if(trafTranVO != null) {
				return trafTranVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tramite de base de datos: " + numExpediente, e, numExpediente.toString());
		}
		return null;
	}

	@Override
	@Transactional
	public void guardarOActualizar(TramiteTrafTranVO tramiteTrafTranVO) throws OegamExcepcion {
		try {
			tramiteTraficoTransDao.guardarOActualizar(tramiteTrafTranVO);
		} catch (Exception e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}
	}

	private ResultBean guardarVehiculo(TramiteTrafTranDto tramiteDto, TramiteTrafTranVO tramiteTran, UsuarioDto usuario,
			String conversion, boolean admin) throws ParseException {
		ResultBean respuesta = new ResultBean();
		// Guardado Vehículo
		if (tramiteDto.getVehiculoDto().getMatricula() != null
				&& !tramiteDto.getVehiculoDto().getMatricula().isEmpty()) {
			VehiculoVO vehiculoPantalla = conversor.transform(tramiteDto.getVehiculoDto(), VehiculoVO.class);

			Date fechaPresentacion = null;
			if (tramiteDto.getFechaPresentacion() != null) {
				fechaPresentacion = tramiteDto.getFechaPresentacion().getDate();
			}

			ResultBean respuestaVehiculo = servicioVehiculo.guardarVehiculo(vehiculoPantalla,
					tramiteDto.getNumExpediente(), tramiteDto.getTipoTramite(), usuario, fechaPresentacion, conversion,
					false, admin);

			Object attachment = respuestaVehiculo.getAttachment(ServicioVehiculo.VEHICULO);
			if (attachment != null && attachment instanceof VehiculoVO) {
				tramiteTran.setVehiculo((VehiculoVO) attachment);
			}
			if (respuestaVehiculo.getListaMensajes() != null && !respuestaVehiculo.getListaMensajes().isEmpty()) {
				for (String mensaje : respuestaVehiculo.getListaMensajes()) {
					respuesta.addMensajeALista(mensaje);
				}
			}
		} else {
			tramiteTran.setVehiculo(null);
		}
		return respuesta;
	}

	private ResultBean guardarTramite(TramiteTrafTranDto tramiteDto, TramiteTrafTranVO tramiteTransmision,
			boolean desdeValidar) {
		ResultBean result = new ResultBean();
		try {
			if (tramiteTransmision.getNumExpediente() == null) {
				BigDecimal numExpediente = servicioTramiteTrafico
						.generarNumExpediente(tramiteTransmision.getNumColegiado());
				log.debug("Creación trámite de transmisión: " + numExpediente);
				tramiteTransmision.setNumExpediente(numExpediente);
				tramiteDto.setNumExpediente(numExpediente);
				if (tramiteTransmision.getEstado() == null || (tramiteTransmision.getEstado() != null
						&& !EstadoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTransmision.getEstado()))) {
					tramiteTransmision.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
				}
				if (tramiteTransmision.getEstado620() == null) {
					tramiteTransmision.setEstado620(new BigDecimal(Estado620.Iniciado.getValorEnum()));
				}
				// Actualizar la evolución vehículo
				if (tramiteTransmision.getVehiculo() != null) {
					servicioEvolucionVehiculo.actualizarEvolucionVehiculoCreacionOCopia(
							tramiteTransmision.getVehiculo().getIdVehiculo(), numExpediente);
				}

				Fecha fecha = utilesFecha.getFechaHoraActualLEG();
				tramiteTransmision.setFechaUltModif(fecha.getFechaHora());
				tramiteTransmision.setFechaAlta(fecha.getFechaHora());
				tramiteTraficoTransDao.guardar(tramiteTransmision);
				guardarEvolucionTramite(tramiteDto, EstadoTramiteTrafico.Iniciado.getValorEnum());
			} else {
				log.debug("Actualización trámite de transmisión: " + tramiteTransmision.getNumExpediente());
				tramiteTransmision.setFechaUltModif(utilesFecha.getFechaHoraActualLEG().getFechaHora());
				if (!desdeValidar) {
					try {
						tramiteTransmision.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						guardarEvolucionTramite(tramiteDto, EstadoTramiteTrafico.Iniciado.getValorEnum());
					} catch (Exception e) {
						log.error("Error al guardar la evolución del trámite de transmisión: "
								+ tramiteTransmision.getNumExpediente() + ". Mensaje: " + e.getMessage());
					}
				}
				tramiteDto.setNumExpediente(tramiteTransmision.getNumExpediente());
				tramiteTransmision = tramiteTraficoTransDao.actualizar(tramiteTransmision);
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de transmisión: " + tramiteTransmision.getNumExpediente()
					+ ". Mensaje: " + e.getMessage());
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		return result;
	}

	private ResultBean guardarIntervinientes(TramiteTrafTranDto tramiteDto, TramiteTrafTranVO tramiteTransmision,
			UsuarioDto usuario, boolean desdeJustificante) {
		ResultBean respuesta = new ResultBean();
		boolean direccionNueva = false;
		// Guardado de Intervinientes
		if (tramiteTransmision.getIntervinienteTraficos() != null
				&& !tramiteTransmision.getIntervinienteTraficos().isEmpty()) {
			for (IntervinienteTraficoVO interviniente : tramiteTransmision.getIntervinienteTraficos()) {
				if (interviniente.getPersona() != null && interviniente.getPersona().getId() != null) {
					if (interviniente.getPersona().getId().getNif() != null
							&& !interviniente.getPersona().getId().getNif().isEmpty()) {
						interviniente.getId().setNif(interviniente.getPersona().getId().getNif().toUpperCase());
						interviniente.getId().setNumExpediente(tramiteTransmision.getNumExpediente());
						interviniente.getPersona().getId().setNumColegiado(tramiteTransmision.getNumColegiado());
						// Estado para saber que está activo
						interviniente.getPersona().setEstado(Long.valueOf(1));
						// Se crea el anagrama
						String anagrama = Anagrama.obtenerAnagramaFiscal(
								interviniente.getPersona().getApellido1RazonSocial(),
								interviniente.getPersona().getId().getNif());
						interviniente.getPersona().setAnagrama(anagrama);
						// Guardar la persona antes de guardar el interviniente
						String conversion = null;
						if (desdeJustificante) {
							conversion = ServicioPersona.CONVERSION_PERSONA_JUSTIFICANTE;
						} else if (interviniente.getId().getTipoInterviniente()
								.equals(TipoInterviniente.RepresentanteTitular.getValorEnum())
								|| interviniente.getId().getTipoInterviniente()
										.equals(TipoInterviniente.RepresentanteAdquiriente.getValorEnum())) {
							conversion = ServicioPersona.CONVERSION_PERSONA_REPRESENTANTE;
						} else {
							conversion = ServicioPersona.CONVERSION_PERSONA_BAJA;
						}

						// Guardar persona
						ResultBean resultPersona = servicioPersona.guardarActualizar(interviniente.getPersona(),
								tramiteTransmision.getNumExpediente(), tramiteTransmision.getTipoTramite(), usuario,
								conversion);

						if (!resultPersona.getError()) {
							// Guardar dirección
							if (interviniente.getDireccion() != null
									&& utiles.convertirCombo(interviniente.getDireccion().getIdProvincia()) != null) {
								ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(
										interviniente.getDireccion(), interviniente.getPersona().getId().getNif(),
										interviniente.getPersona().getId().getNumColegiado(),
										tramiteDto.getTipoTramite(), ServicioDireccion.CONVERSION_DIRECCION_INE);
								if (resultDireccion.getError()) {
									respuesta.addMensajeALista("- "
											+ TipoInterviniente
													.convertirTexto(interviniente.getId().getTipoInterviniente())
											+ ": " + resultDireccion.getMensaje());
									interviniente.setIdDireccion(null);
								} else {
									DireccionVO direccion = (DireccionVO) resultDireccion
											.getAttachment(ServicioDireccion.DIRECCION);
									direccionNueva = (Boolean) resultDireccion
											.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
									if (direccion != null) {
										interviniente.setIdDireccion(direccion.getIdDireccion());
									}
									servicioEvolucionPersona.guardarEvolucionPersonaDireccion(
											interviniente.getPersona().getId().getNif(),
											tramiteTransmision.getNumExpediente(), tramiteTransmision.getTipoTramite(),
											interviniente.getPersona().getId().getNumColegiado(), usuario,
											direccionNueva);
								}
							} else {
								interviniente.setIdDireccion(null);
							}

							// Guardar interviniente
							ResultBean result = servicioIntervinienteTrafico.guardarActualizar(interviniente, null);
							for (String mensaje : result.getListaMensajes()) {
								respuesta.addMensajeALista(mensaje);
							}
						} else {
							respuesta.addMensajeALista(resultPersona.getMensaje());
						}
					} else {
						servicioIntervinienteTrafico.eliminar(interviniente);
					}
				}
			}
		} else {
			respuesta.addMensajeALista("No se ha guardado ningún interviniente");
		}
		return respuesta;
	}

	private void guardarEvolucionTramite(TramiteTrafTranDto tramiteDto, String estadoNuevo) {
		if (!estadoNuevo.equals(tramiteDto.getEstado())) {
			EvolucionTramiteTraficoDto evolucion = new EvolucionTramiteTraficoDto();
			if (tramiteDto.getEstado() != null && !tramiteDto.getEstado().isEmpty()) {
				evolucion.setEstadoAnterior(new BigDecimal(tramiteDto.getEstado()));
			} else {
				evolucion.setEstadoAnterior(BigDecimal.ZERO);
			}
			evolucion.setEstadoNuevo(new BigDecimal(estadoNuevo));
			evolucion.setUsuarioDto(tramiteDto.getUsuarioDto());
			evolucion.setNumExpediente(tramiteDto.getNumExpediente());
			servicioEvolucionTramite.guardar(evolucion);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean sitexComprobarCTITPrevio(String matricula) {
		return tramiteTraficoTransDao.sitexComprobarCTITPrevio(matricula);
	}

	@Override
	public ResultBean comprobarTramitabilidadTramiteTransmisionSega(TramiteTraficoTransmisionBean tramite,
			BigDecimal idUsuario, BigDecimal idContrato) {
		log.info("Inicio de comprobar tramitabilidad Sega");
		// Comprueba datos obligatorios
		ResultBean resultado = validarDatosObligatorios(tramite);
		if (!resultado.getError()) {
			resultado = servicioCheckCTIT.crearSolicitud(tramite.getTramiteTraficoBean().getNumExpediente(), null,
					idUsuario, idContrato);
		}
		return resultado;
	}

	@Override
	public ResultBean comprobarTramitabilidadTramiteTransmision(TramiteTraficoTransmisionBean tramite,
			BigDecimal idUsuarioSession, BigDecimal idContrato) {
		log.info("Inicio de comprobar tramitabilidad");

		// Comprueba datos obligatorios
		ResultBean resultadoComprobar = validarDatosObligatorios(tramite);

		if (!resultadoComprobar.getError()) {
			BigDecimal numExpediente = tramite.getTramiteTraficoBean().getNumExpediente();
			try {
				ResultBean resultSolicitud = servicioCheckCTIT.crearSolicitud(numExpediente, null, idUsuarioSession,
						idContrato);

				// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
				if (resultadoComprobar != null && resultadoComprobar.getListaMensajes() != null) {
					resultSolicitud.getListaMensajes().addAll(resultadoComprobar.getListaMensajes());
				}
				// FIN 15-10-2012. Ricardo Rodríguez. Incidencia : 0002589
				return resultSolicitud;

			} catch (TransactionalException e) {
				resultadoComprobar.setError(true);
				resultadoComprobar.setMensaje(e.getMessage());
			}
		}
		return resultadoComprobar;
	}

	/**
	 * Valida que el trámite tiene los datos obligatorios informados para poder
	 * comprobar su tramitabilidad
	 * 
	 * @param tramite
	 * @return
	 */
	private ResultBean validarDatosObligatorios(TramiteTraficoTransmisionBean tramite) {
		ResultBean resultadoComprobar = new ResultBean();

		if (tramite == null || tramite.getTramiteTraficoBean() == null
				|| tramite.getTramiteTraficoBean().getNumColegiado() == null
				|| tramite.getTramiteTraficoBean().getNumColegiado().isEmpty()
				|| tramite.getTramiteTraficoBean().getNumExpediente() == null) {
			resultadoComprobar.setError(true);
			resultadoComprobar.addMensajeALista("No se ha podido recuperar el trámite");
		} else if (!EstadoTramiteTrafico.Iniciado.equals(tramite.getTramiteTraficoBean().getEstado())) {
			resultadoComprobar.setError(true);
			resultadoComprobar.addMensajeALista("El trámite debe estar en estado iniciado");
		} else {
			if (tramite.getTramiteTraficoBean().getVehiculo() == null
					|| tramite.getTramiteTraficoBean().getVehiculo().getMatricula() == null
					|| tramite.getTramiteTraficoBean().getVehiculo().getMatricula().isEmpty()) {
				resultadoComprobar.setError(true);
				resultadoComprobar.addMensajeALista("Falta la matrícula del vehículo");
			}

			if (tramite.getTramiteTraficoBean().getVehiculo() != null
					&& tramite.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean() != null
					&& tramite.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean()
							.getKilometros() != null) {
				if (tramite.getTramiteTraficoBean().getVehiculo().getFechaLecturaKm() == null
						|| StringUtils.isBlank(tramite.getTramiteTraficoBean().getVehiculo().getDoiResponsableKm())) {
					resultadoComprobar.setError(true);
					resultadoComprobar.addMensajeALista(
							"Si rellena el kilometraje del vehículo, debe rellenar la fecha de lectura y el NIF del responsable de la lectura");
				}
			}

			if (tramite.getTransmitenteBean() == null || tramite.getTransmitenteBean().getPersona() == null
					|| tramite.getTransmitenteBean().getPersona().getNif() == null
					|| tramite.getTransmitenteBean().getPersona().getNif().isEmpty()) {
				resultadoComprobar.setError(true);
				resultadoComprobar.addMensajeALista("Falta el NIF del transmitente");
			}

			if (tramite.getTipoTransferencia() == null) {
				resultadoComprobar.setError(true);
				resultadoComprobar.addMensajeALista(EL_TIPO_DE_TRANSFERENCIA_NO_ES_CORRECTO);

			} else if (!TipoTransferencia.tipo5.equals(tramite.getTipoTransferencia())
					&& (tramite.getAdquirienteBean() == null || tramite.getAdquirienteBean().getPersona() == null
							|| tramite.getAdquirienteBean().getPersona().getNif() == null
							|| tramite.getAdquirienteBean().getPersona().getNif().isEmpty())) {
				resultadoComprobar.setError(true);
				resultadoComprobar.addMensajeALista("Es necesario el NIF del adquiriente");
			} else if (TipoTransferencia.tipo5.equals(tramite.getTipoTransferencia())
					&& (tramite.getPoseedorBean() == null || tramite.getPoseedorBean().getPersona() == null
							|| tramite.getPoseedorBean().getPersona().getNif() == null
							|| tramite.getPoseedorBean().getPersona().getNif().isEmpty())) {
				resultadoComprobar.setError(true);
				resultadoComprobar.addMensajeALista("Falta el NIF del poseedor");
			}

			if (tramite.getTramiteTraficoBean().getTasa() == null
					|| tramite.getTramiteTraficoBean().getTasa().getCodigoTasa() == null
					|| tramite.getTramiteTraficoBean().getTasa().getCodigoTasa().isEmpty()) {
				resultadoComprobar.setError(true);
				resultadoComprobar.addMensajeALista("No tiene tasa asociada");
			}
		}
		return resultadoComprobar;
	}

	private TramiteTrafTranDto cargaNumColegiado(TramiteTrafTranDto tramiteDto) {
		if (tramiteDto.getNumColegiado() == null || tramiteDto.getNumColegiado().isEmpty()) {
			tramiteDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		if (tramiteDto.getVehiculoDto() != null && (tramiteDto.getVehiculoDto().getNumColegiado() == null
				|| tramiteDto.getVehiculoDto().getNumColegiado().isEmpty())) {
			tramiteDto.getVehiculoDto().setNumColegiado(tramiteDto.getNumColegiado());
		}
		return tramiteDto;
	}

	private TramiteTrafTranVO convertirDTOVO(TramiteTrafTranDto tramiteDto) {
		TramiteTrafTranVO tramiteTran = conversor.transform(tramiteDto, TramiteTrafTranVO.class);

		if (tramiteDto.getContrato() == null) {
			ContratoVO contrato = new ContratoVO();
			contrato.setIdContrato(tramiteDto.getIdContrato().longValue());
			tramiteTran.setContrato(contrato);
		}

		if (tramiteDto.getAdquiriente() != null && tramiteDto.getAdquiriente().getPersona() != null
				&& tramiteDto.getAdquiriente().getPersona().getNif() != null
				&& !tramiteDto.getAdquiriente().getPersona().getNif().isEmpty()
				&& (tramiteDto.getAdquiriente().getNifInterviniente() == null
						|| tramiteDto.getAdquiriente().getNifInterviniente().isEmpty())) {
			tramiteDto.getAdquiriente().setNifInterviniente(tramiteDto.getAdquiriente().getPersona().getNif());
		}
		if (tramiteDto.getAdquiriente() != null && tramiteDto.getAdquiriente().getTipoInterviniente() != null
				&& tramiteDto.getAdquiriente().getNifInterviniente() != null
				&& !tramiteDto.getAdquiriente().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getAdquiriente().getNifInterviniente().isEmpty()) {
			if (tramiteTran.getIntervinienteTraficos() == null) {
				tramiteTran.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteDto.getAdquiriente().setNumColegiado(tramiteDto.getNumColegiado());
			tramiteTran.getIntervinienteTraficos()
					.add(conversor.transform(tramiteDto.getAdquiriente(), IntervinienteTraficoVO.class));
		}

		if (tramiteDto.getPresentador() != null && tramiteDto.getPresentador().getPersona() != null
				&& tramiteDto.getPresentador().getPersona().getNif() != null
				&& !tramiteDto.getPresentador().getPersona().getNif().isEmpty()
				&& (tramiteDto.getPresentador().getNifInterviniente() == null
						|| tramiteDto.getPresentador().getNifInterviniente().isEmpty())) {
			tramiteDto.getPresentador().setNifInterviniente(tramiteDto.getPresentador().getPersona().getNif());
		}
		if (tramiteDto.getPresentador() != null && tramiteDto.getPresentador().getTipoInterviniente() != null
				&& tramiteDto.getPresentador().getNifInterviniente() != null
				&& !tramiteDto.getPresentador().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getPresentador().getNifInterviniente().isEmpty()) {
			if (tramiteTran.getIntervinienteTraficos() == null) {
				tramiteTran.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteDto.getPresentador().setNumColegiado(tramiteDto.getNumColegiado());
			tramiteTran.getIntervinienteTraficos()
					.add(conversor.transform(tramiteDto.getPresentador(), IntervinienteTraficoVO.class));
		}
		return tramiteTran;
	}

	@Override
	@Transactional
	public ResultBean comprobarCheckCTIT(TramiteTrafTranDto tramiteTrafTranDto, BigDecimal idUsuario) {
		ResultBean resultado = null;
		try {
			Map<String, Object> tramiteDetalle = getModeloTransmision()
					.obtenerDetalleConDescripciones(tramiteTrafTranDto.getNumExpediente());
			// Recogemos los valores del modelo
			ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
			// Comprobamos que se ha recuperado bien
			if (resultadoDetalle.getError()) {
				resultado = new ResultBean(true, "Error al obtener el trámite " + tramiteTrafTranDto.getNumExpediente()
						+ ": " + resultadoDetalle.getMensaje());
				log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite "
						+ tramiteTrafTranDto.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar el CheckCTIT, error: ", e,
					tramiteTrafTranDto.getNumExpediente().toString());
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de realizar el CheckCTIT");
		}
		return resultado;
	}

	@Override
	@Transactional
	public DatosCTITDto datosCTIT(BigDecimal numExpediente) {
		log.debug("Datos CTIT trámite transmisión: " + numExpediente);
		DatosCTITDto datosCTIT = null;
		try {
			TramiteTrafTranVO tramiteVO = tramiteTraficoTransDao.datosCTIT(numExpediente);
			if (tramiteVO != null) {
				datosCTIT = conversor.transform(tramiteVO, DatosCTITDto.class);

				datosCTIT.setCustomDossierNumber(tramiteVO.getNumExpediente().toString());

				if (tramiteVO.getIntervinienteTraficos() != null) {
					for (IntervinienteTraficoVO interviniente : tramiteVO.getIntervinienteTraficosAsList()) {
						if (TipoInterviniente.TransmitenteTrafico.getValorEnum()
								.equals(interviniente.getId().getTipoInterviniente())) {
							if (tramiteVO.getTipoTransferencia() != null && !TipoTransferencia.tipo2.getValorEnum()
									.equals(tramiteVO.getTipoTransferencia())) {
								if (interviniente.getDireccion() != null
										&& interviniente.getDireccion().getIdProvincia() != null
										&& interviniente.getDireccion().getIdProvincia().isEmpty()
										&& "-1".equals(interviniente.getDireccion().getIdProvincia())) {
									datosCTIT.setSellerINECode(interviniente.getDireccion().getIdProvincia());
								} else {
									datosCTIT.setSellerINECode(ND);
								}
							} else {
								datosCTIT.setSellerINECode(ND);
							}
						}
					}
				}

				if (tramiteVO.getConsentimiento() != null) {
					datosCTIT.setConsentimiento(tramiteVO.getConsentimiento());
				} else {
					datosCTIT.setConsentimiento(NO);
				}

				if (tramiteVO.getVehiculo().getProvinciaPrimeraMatricula() != null
						&& new BigDecimal(-1).equals(tramiteVO.getVehiculo().getProvinciaPrimeraMatricula())) {
					int codigoProvinciaPrimeraMatricula = tramiteVO.getVehiculo().getProvinciaPrimeraMatricula()
							.intValue();
					if (codigoProvinciaPrimeraMatricula < 10 && codigoProvinciaPrimeraMatricula > 0) {
						datosCTIT.setFirstMatriculationINECode(
								"0" + tramiteVO.getVehiculo().getProvinciaPrimeraMatricula().toString());
					} else {
						datosCTIT.setFirstMatriculationINECode(
								tramiteVO.getVehiculo().getProvinciaPrimeraMatricula().toString());
					}
				} else {
					datosCTIT.setFirstMatriculationINECode(ND);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar Datos CTIT trámite transmisión: " + numExpediente, e,
					numExpediente.toString());
		}
		return datosCTIT;
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafTranDto getTramiteTransmisionPorTasaCamser(String codigoTasa, Boolean tramiteCompleto) {
		TramiteTrafTranDto tramiteTrafTranDto = null;
		try {
			if (codigoTasa != null && !codigoTasa.isEmpty()) {
				TramiteTrafTranVO tramiteTrafTranVO = tramiteTraficoTransDao.getTramitePorTasaCamSer(codigoTasa,
						tramiteCompleto);
				if (tramiteTrafTranVO != null) {
					tramiteTrafTranDto = conversor.transform(tramiteTrafTranVO, TramiteTrafTranDto.class);
					if (tramiteTrafTranVO.getTasa2() != null) {
						tramiteTrafTranDto.setCodigoTasaCamser(tramiteTrafTranVO.getTasa2().getCodigoTasa());
					}
					convertirIntervinientesVoToDto(tramiteTrafTranVO, tramiteTrafTranDto);
				}
			}
		} catch (Exception e) {
			log.error(
					"Ha sucedido un error a la hora de obtener el trámite de transmisión por la tasa de Camser, error: "
							+ e);
		}
		return tramiteTrafTranDto;
	}

	@Override
	@Transactional
	public ResultBean generarJustificante(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		IntervinienteTraficoDto titular = null;
		try {
			TramiteTrafDto tramiteTrafDto = servicioTramiteTrafico.getTramiteDto(numExpediente, true);
			if (tramiteTrafDto == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del trámite para el expediente : " + numExpediente);
			} else if (!TipoTramiteTrafico.TransmisionElectronica.getValorEnum()
					.equals(tramiteTrafDto.getTipoTramite())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Del expediente: " + tramiteTrafDto.getNumExpediente()
						+ " no se puede generar un justificante profesional porque no es un trámite de Transmisión Electrónica.");
			} else if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafDto.getEstado())
					|| EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()
							.equals(tramiteTrafDto.getEstado())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El trámite de transmisión no debe estar Finalizado Telemáticamente");
			} else if (tramiteTrafDto.getVehiculoDto() != null && tramiteTrafDto.getVehiculoDto().getMatricula() != null
					&& tramiteTrafDto.getVehiculoDto().getMatricula().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del vehículo del trámite para el expediente : " + numExpediente);
			} else {
				titular = servicioIntervinienteTrafico.getIntervinienteTraficoDto(tramiteTrafDto.getNumExpediente(),
						TipoInterviniente.Adquiriente.getValorEnum(), null, tramiteTrafDto.getNumColegiado());
				if (titular == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(
							"No existen datos del titular del trámite para el expediente : " + numExpediente);
				}
			}
			if (!resultado.getError()) {
				JustificanteProfDto justificanteProfDto = new JustificanteProfDto();
				justificanteProfDto.setTramiteTrafico(tramiteTrafDto);
				justificanteProfDto.setNumExpediente(tramiteTrafDto.getNumExpediente()); // TODO: posible NullPointerException
				justificanteProfDto.setEstado(new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()));
				ResultBean resultValidar = servicioJustificanteProfesional.validarGenerarJP(justificanteProfDto,
						titular, new Date());
				if (!resultValidar.getError()) {
					resultado = servicioJustificanteProfesional.guardarJustificanteConsultaTramite(justificanteProfDto,
							idUsuario);
				} else {
					resultado = resultValidar;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el justificante, error: ", e,
					numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el justificante.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}

	// Mantis 25415
	@Override
	@Transactional
	public void actualizarValorRealTransmision(BigDecimal numExpediente, BigDecimal valorReal) {
		// TODO: DUDA Este trámite está completo? Argumento 2 getTramiteTransmision
		TramiteTrafTranVO tramiteTransmision = tramiteTraficoTransDao.getTramiteTransmision(numExpediente, true);
		tramiteTransmision.setValorReal(valorReal);
		tramiteTraficoTransDao.guardarOActualizar(tramiteTransmision);
	}

	// DVV
//	@Override
//	@Transactional
//	public void actualizarCambioSocietario(BigDecimal numExpediente, BigDecimal cambioSocietario) {
//		TramiteTrafTranVO tramiteTransmision = tramiteTraficoTransDao.getTramiteTransmision(numExpediente, true);
//		tramiteTransmision.setCambioSocietario(cambioSocietario.equals(new BigDecimal(1)));
//		tramiteTraficoTransDao.guardarOActualizar(tramiteTransmision);
//	}

	// DVV
//	@Override
//	@Transactional
//	public void actualizarModificacionNoConstante(BigDecimal numExpediente, BigDecimal modificacionNoConstante) {
//		TramiteTrafTranVO tramiteTransmision = tramiteTraficoTransDao.getTramiteTransmision(numExpediente, true);
//		tramiteTransmision.setModificacionNoConstante(modificacionNoConstante.equals(new BigDecimal(1)));
//		tramiteTraficoTransDao.guardarOActualizar(tramiteTransmision);
//	}

	@Override
	@Transactional
	public TramiteTrafTranDto actualizarAcreditacionPago(BigDecimal numExpediente) {
		TramiteTrafTranVO tramiteTransmision = tramiteTraficoTransDao.getTramiteTransmision(numExpediente, true);
		if (tramiteTransmision.getAcreditacionPago() != null) {
			tramiteTransmision.setAcreditacionPago(tramiteTransmision.getAcreditacionPago());
		} else {
			tramiteTransmision.setAcreditacionPago(null);
		}
	
		tramiteTraficoTransDao.guardarOActualizar(tramiteTransmision);
		TramiteTrafTranDto tramiteTranTrafDto = conversor.transform(tramiteTransmision, TramiteTrafTranDto.class);
		return tramiteTranTrafDto;
	}

	@Override
	@Transactional
	public void actualizarDatos620(BigDecimal numExpediente,
			BeanPQTramiteTraficoGuardarTransmision beanGuardarTransmision) {
		TramiteTrafTranVO tramiteTransmision = tramiteTraficoTransDao.getTramiteTransmision(numExpediente, true);
		BigDecimal porcentajeReduccion = calculaReduccion(beanGuardarTransmision.getP_TIPO_REDUCCION());
		HashMap<String, BigDecimal> calculo = utiles.calculoCuotaTributaria(beanGuardarTransmision.getP_VALOR_DECLARADO(),
				porcentajeReduccion);
		tramiteTransmision.setBaseImponible(calculo.get("baseImponible"));
		tramiteTransmision.setReduccionImporte(calculo.get("importeReduccion"));
		tramiteTransmision.setCuotaTributaria(calculo.get("cuotaTributaria"));
		tramiteTransmision.setReduccionPorcentaje(porcentajeReduccion);
		tramiteTransmision.setOficinaLiquidadora(beanGuardarTransmision.getP_OFICINA_LIQUIDADORA());
		tramiteTransmision.setTipoReduccion(beanGuardarTransmision.getP_TIPO_REDUCCION());
		tramiteTransmision.setTipoMotor(beanGuardarTransmision.getP_TIPO_MOTOR());
		tramiteTransmision.setProcedencia(beanGuardarTransmision.getP_PROCEDENCIA_620());
		tramiteTransmision.setSubasta(beanGuardarTransmision.getP_SUBASTA());
		tramiteTraficoTransDao.guardarOActualizar(tramiteTransmision);
	}

	private BigDecimal calculaReduccion(String reduccionCodigo) {
		// Calcula la reducción para un código dado
		BigDecimal resultado = BigDecimal.ZERO;
		if (StringUtils.isNotBlank(reduccionCodigo)
				&& (reduccionCodigo.equalsIgnoreCase(ReduccionType.VALUE_5.value()) || reduccionCodigo.equalsIgnoreCase(ReduccionType.VALUE_6.value()))) {
			resultado = BigDecimal.valueOf(0.04);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoCtitBean transmisionTelematica(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean,
			String nPasos, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			Paso paso = Paso.convertirNpaso(nPasos);
			resultado = buildCtitSega.generarXmlTransmisionTelematica(tramiteTraficoTransmisionBean, paso);
			if (!resultado.getError()) {
				if (TipoTransferencia.tipo1.getValorEnum()
						.equals(tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum())
						|| TipoTransferencia.tipo2.getValorEnum()
								.equals(tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum())
						|| TipoTransferencia.tipo3.getValorEnum()
								.equals(tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum())) {
					resultado = crearSolicitudTransTelematica(resultado.getNombreXml(),
							tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(),
							ProcesosEnum.FULL_CTIT_SEGA, idUsuario, idContrato,
							tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
				} else if (TipoTransferencia.tipo4.getValorEnum()
						.equals(tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum())) {
					resultado = crearSolicitudTransTelematica(resultado.getNombreXml(),
							tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(),
							ProcesosEnum.NOTIFICACION_CTIT_SEGA, idUsuario, idContrato,
							tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
				} else if (TipoTransferencia.tipo5.getValorEnum()
						.equals(tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum())) {
					resultado = crearSolicitudTransTelematica(resultado.getNombreXml(),
							tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(),
							ProcesosEnum.TRADE_CTIT_SEGA, idUsuario, idContrato,
							tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la tramitación telemática, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de realizar la tramitación telemática.");
		} catch (OegamExcepcion e) {
			log.error(e.getMensajeError1());
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista(e.getMensajeError1());
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoCtitBean crearSolicitudTransTelematica(String nombreXml, BigDecimal numExpediente,
			ProcesosEnum proceso, BigDecimal idUsuario, BigDecimal idContrato, String estadoAnt) throws OegamExcepcion {
		ResultadoCtitBean resultadoCtitBean = new ResultadoCtitBean(Boolean.FALSE);
		try {
			ResultBean resultBean = servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente,
					EstadoTramiteTrafico.convertir(estadoAnt), EstadoTramiteTrafico.Pendiente_Envio, false, null,
					idUsuario);
			if (!resultBean.getError()) {
				resultBean = servicioCola.crearSolicitud(proceso.getNombreEnum(), nombreXml,
						gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
						TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), numExpediente.toString(), idUsuario,
						null, idContrato);
			}
			if (resultBean.getError()) {
				resultadoCtitBean.setError(Boolean.TRUE);
				resultadoCtitBean.addMensajeLista(resultBean.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de finalizar la tramitación telemática, error: ", e,
					numExpediente.toString());
			resultadoCtitBean.setError(Boolean.TRUE);
			resultadoCtitBean.addMensajeLista("Ha sucedido un error a la hora de finalizar la tramitación telemática.");
		}
		return resultadoCtitBean;
	}

	@Override
	@Transactional
	public void actualizarTramiteTransmision(TramiteTrafTranDto tramiteTrafTranDto) {
		try {
			if (tramiteTrafTranDto != null && tramiteTrafTranDto.getNumExpediente() != null) {
				TramiteTrafTranVO tramiteTrafTranVO = conversor.transform(tramiteTrafTranDto, TramiteTrafTranVO.class);
				if (tramiteTrafTranVO != null) {
					tramiteTraficoTransDao.actualizar(tramiteTrafTranVO);
				}
			} else {
				log.error("No se puede actualizar el trámite porque es nulo o no tiene número de expediente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el trámite de transmisión, error: ", e);
		}
	}

	@Override
	public ResultBean generarJustitificanteProf(TramiteTrafTranDto tramiteTrafTranDto, BigDecimal idUsuario,
			String motivo, String documento, String diasValidez) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			IntervinienteTraficoDto titular = servicioIntervinienteTrafico.getIntervinienteTraficoDto(
					tramiteTrafTranDto.getNumExpediente(), TipoInterviniente.Adquiriente.getValorEnum(), null,
					tramiteTrafTranDto.getNumColegiado());
			if (titular != null) {
				Date fecha = new Date();
				JustificanteProfDto justificanteProfDto = new JustificanteProfDto();
				justificanteProfDto.setTramiteTrafico(tramiteTrafTranDto);
				justificanteProfDto.setNumExpediente(tramiteTrafTranDto.getNumExpediente());
				justificanteProfDto.setEstado(new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()));
				justificanteProfDto.setDocumentos(documento);
				justificanteProfDto.setMotivo(motivo);
				justificanteProfDto.setDiasValidez(new BigDecimal(diasValidez));
				justificanteProfDto.setFechaInicio(fecha);
				justificanteProfDto.setFechaAlta(fecha);
				resultado = servicioJustificanteProfesional
						.validarGenracionJPDesdeTransmisionODuplicado(justificanteProfDto, titular, Boolean.FALSE);
				if (!resultado.getError()) {
					Boolean esForzar = (Boolean) resultado.getAttachment(ServicioJustificanteProfesional.ES_JP_FORZAR);
					Boolean esErrorFecha = (Boolean) resultado
							.getAttachment(ServicioJustificanteProfesional.ES_JP_ERROR_FECHA);
					resultado = servicioJustificanteProfesional.crearJustificanteDesdeTransmisionODuplicado(
							justificanteProfDto, titular, esForzar, idUsuario);
					if (!resultado.getError() && esForzar && esErrorFecha) {
						resultado.setMensaje("Tramite " + justificanteProfDto.getNumExpediente()
								+ "; El Justificante Profesional fue emitido hace menos de 30 días para este vehículo. "
								+ "Si quiere volver a emitir un Justificante Profesional póngase en contacto con el Colegio de Gestores Administrativos de Madrid.");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del " + TipoInterviniente.Adquiriente.getNombreEnum()
						+ " del expediente: " + tramiteTrafTranDto.getNumExpediente());
			}
		} catch (Exception e) {
			log.error(
					"Ha sucedido un error a la hora de generar el justificante desde el trámite de transmisión, error: ",
					e, tramiteTrafTranDto.getNumExpediente().toString());
			resultado.setMensaje(
					"Ha sucedido un error a la hora de generar el justificante desde el trámite de transmisión.");
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoPermisoDistintivoItvBean comprobarCheckImpresionPermiso(BigDecimal numExpediente) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			TramiteTrafTranVO tramiteTrafTranVO = tramiteTraficoTransDao.getTramiteTransmision(numExpediente,
					Boolean.FALSE);
			if (tramiteTrafTranVO != null) {
				if (tramiteTrafTranVO.getImprPermisoCircu() == null || tramiteTrafTranVO.getImprPermisoCircu().isEmpty()
						|| !SI.equals(tramiteTrafTranVO.getImprPermisoCircu())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede imprimir el permiso del expediente: " + numExpediente
							+ " porque no se encuentra marcado el check de impresión.");
				}
				tramiteTraficoTransDao.evict(tramiteTrafTranVO);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentran datos para el expediente: " + numExpediente);
			}
		} catch (Exception e) {
			log.error(
					"Ha sucedido un error a la hora de comprobar el check de impresión de permiso para el expediente: "
							+ numExpediente + ", error: ",
					e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de comprobar el check de impresión de permiso para el expediente: "
							+ numExpediente);
		}
		return resultado;
	}

	@Override
	public ResultBean cambiarEstadoTramite(TramiteTraficoTransmisionBean tramite) {
		return servicioTramiteTrafico.cambiarEstadoConEvolucion(tramite.getTramiteTraficoBean().getNumExpediente(),
				tramite.getTramiteTraficoBean().getEstado(), EstadoTramiteTrafico.Pendiente_Envio, false, null,
				tramite.getTramiteTraficoBean().getIdUsuario());
	}

	// Estadísticas CTIT

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerTotalesTramitesCTIT(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta,
			String idProvincia) throws ParseException {
		return tramiteTraficoTransDao.obtenerTotalesTramitesCTIT(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), idProvincia);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerDetalleTramitesCTIT(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta,
			String idProvincia) throws ParseException {
		return tramiteTraficoTransDao.obtenerDetalleTramitesCTIT(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), idProvincia);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListadoNumerosExpedienteCTIT(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta) throws ParseException {
		return tramiteTraficoTransDao.obtenerListadoNumerosExpedienteCTIT(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate());
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListadoNumerosExpedienteCTITAnotacionesGest(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerListadoNumerosExpedienteCTITAnotacionesGest(esTelematico,
				fechaDesde.getDate(), fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerDetalleTramitesCTITCarpetaFusion(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerDetalleTramitesCTITCarpetaFusion(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListaExpedientesCTITCarpetaFusion(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerListaExpedientesCTITCarpetaFusion(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerDetalleTramitesCTITCarpetaI(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta,
			List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerDetalleTramitesCTITCarpetaI(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListaExpedientesCTITCarpetaI(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerListaExpedientesCTITCarpetaI(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListaExpedientesCTITCarpetaB(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerListaExpedientesCTITCarpetaB(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerDetalleTramitesCTITVehiculosAgricolas(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerDetalleTramitesCTITVehiculosAgricolas(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListaExpedientesCTITVehiculosAgricolas(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerListaExpedientesCTITVehiculosAgricolas(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerDetalleTramitesCTITJudicialSubasta(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerDetalleTramitesCTITJudicialSubasta(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListaExpedientesCTITSinCETNiFactura(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerListaExpedientesCTITSinCETNiFactura(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListaExpedientesCTITEmbargoPrecinto(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerListaExpedientesCTITEmbargoPrecinto(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerDetalleTramitesCTITErrorJefatura(Fecha fechaDesde, Fecha fechaHasta,
			List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerDetalleTramitesCTITErrorJefatura(fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListaExpedientesCTITErrorJefatura(Fecha fechaDesde, Fecha fechaHasta,
			List<BigDecimal> listaPosibles) throws ParseException {
		return tramiteTraficoTransDao.obtenerListaExpedientesCTITErrorJefatura(fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> obtenerListaExpedientesCTITEvolucion(boolean esTelematico, Fecha fechaDesde,
			Fecha fechaHasta, List<BigDecimal> listaPosibles, List<EstadoTramiteTrafico> transicionEstados)
			throws ParseException {
		return tramiteTraficoTransDao.obtenerListaExpedientesCTITEvolucion(esTelematico, fechaDesde.getDate(),
				fechaHasta.getDate(), listaPosibles, transicionEstados);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerListaTramitesGestorSobreExp(List<BigDecimal> listaPosibles) {
		return tramiteTraficoTransDao.obtenerListaTramitesGestorSobreExp(listaPosibles);
	}
	
	@Override
	public ArrayList<FicheroInfo> recuperarDocumentos(BigDecimal numExpediente) {
		ArrayList<FicheroInfo> ficherosSubidos = new ArrayList<FicheroInfo>();
		if (numExpediente != null) {
			try {
				String carpeta = ConstantesGestorFicheros.CTIT;
				String subcarpeta = ConstantesGestorFicheros.DOCUMENTOS_SUBIDOS;
				TramiteTrafTranVO tramiteTrafTranVO = servicioTramiteTrafico.getTramiteTransmision(numExpediente, true);
				if (tramiteTrafTranVO.getVehiculo() != null && StringUtils.isNotBlank(tramiteTrafTranVO.getVehiculo().getMatricula())) {
					FileResultBean ficheros = gestorDocumentos.buscarFicheroPorNumExpTipo(carpeta, subcarpeta, Utilidades.transformExpedienteFecha(numExpediente), tramiteTrafTranVO.getNumColegiado() + "_" + tramiteTrafTranVO.getVehiculo().getMatricula());
					if (ficheros.getFiles() != null && !ficheros.getFiles().isEmpty()) {
						ficherosSubidos = new ArrayList<FicheroInfo>();
						for (File fichero : ficheros.getFiles()) {
							FicheroInfo ficheroInfo = new FicheroInfo(fichero, 0);
							ficherosSubidos.add(ficheroInfo);
						}
					}
				}
			} catch (OegamExcepcion e) {
				log.error("Error al recuperar los documentos " + e.getMessage(), e, numExpediente.toString());
			}
		}
		return ficherosSubidos;
	}
	
	@Override
	@Transactional
	public void actualizarFicheroSubido(BigDecimal numExpediente) {
		try {
			if (numExpediente != null) {
				TramiteTrafTranVO tramiteVO = tramiteTraficoTransDao.getTramiteTransmision(numExpediente,Boolean.FALSE);
				tramiteVO.setFicheroSubido("si");
			}
		} catch (Exception e) {
			log.error("Error al actualizar fichero subido a si: " + numExpediente, e, numExpediente.toString());
		}
		
	}
}