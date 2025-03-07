package colas.procesos;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.dgt.DGTTransmision;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCTITDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.gescogroup.blackbox.CtitCheckError;
import com.gescogroup.blackbox.CtitsoapResponse;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import colas.procesos.utiles.UtilesProcesos;
import trafico.beans.DatosCTITBean;
import trafico.modelo.ModeloDGTWS;
import trafico.transmision.ctit.DGTSolicitudTransmision;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;
import utilidades.web.excepciones.RespuestaDesconocidaWS;
import utilidades.web.excepciones.SinDatosWSExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TrataMensajeExcepcion;

public class ProcesoCheckCTIT extends AbstractProcesoCTIT {
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoCheckCTIT.class);
	private static final int TAMANIO_RESPUESTA_COLA = 300;
	private Integer hiloActivo;

	private ModeloDGTWS modeloDGTWS;
	private UtilesProcesos utilesProcesos;
	private DGTSolicitudTransmision dgtSolicitudTransmision;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private ServicioMensajesProcesos servicioMensajesProcesos;

	@Autowired
	private DGTTransmision dgtTransmision;
	
	@Autowired
	private ServicioProcesos servicioProcesos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		Boolean esLaborable = true;
		Boolean forzarEjecucion = false;
		// comprueba si el dia es laborable y no es festivo nacional
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}
		
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.FORZAR_EJECUCION_TRANSMISION))) {
			forzarEjecucion = true;
		}
		
		if(esLaborable || forzarEjecucion){
			// Forzamos la inyección de dependencias de las clases anotadas como 'Autowired'

			hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
			ColaBean solicitud = new ColaBean();
			DatosCTITBean datosCTITBean = new DatosCTITBean();
			DatosCTITDto datosCTITDto = new DatosCTITDto();

			try {
				log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Buscando Solicitud");
				solicitud = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_CHECKCTIT, hiloActivo.toString());

				String deshabilitarPQ = gestorPropiedades.valorPropertie("deshabilitar.pq.dgtws");

				if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
					datosCTITDto = servicioTramiteTraficoTransmision.datosCTIT(solicitud.getIdTramite());
				} else {
					datosCTITBean = getModeloDGTWS().datosCTIT(solicitud.getIdTramite());
				}

				String peticionXML = getUtilesProcesos().recogerXmlCheckCTIT(solicitud);
				// podemos recoger todo lo necesario para el webservice.
				// -----------------PASAMOS A INVOCAR AL WEBSERVICE-------------
				log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Solicitud " + ConstantesProcesos.PROCESO_CHECKCTIT + " encontrada, llamando a WS");
				
				llamadaWS(jobExecutionContext, solicitud, datosCTITBean, datosCTITDto, peticionXML, deshabilitarPQ);
			} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion) {
				sinPeticiones(jobExecutionContext);
				log.info(sinSolicitudesExcepcion.getMensajeError1());
			} catch (SinDatosWSExcepcion sinDatosWSExcepcion) {
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_AL_WEBSERVICE);
				log.logarOegamExcepcion(sinDatosWSExcepcion.getMensajeError1(), sinDatosWSExcepcion);
			}// excepciones dentro del webservice
			catch (java.io.FileNotFoundException fileNotFound) {
				log.error(ConstantesProcesos.XML_PARA_CHECK_CTIT_NO_ENCONTRADO, fileNotFound);
				marcarSolicitudConErrorServicio(solicitud, ConstantesProcesos.XML_PARA_CHECK_CTIT_NO_ENCONTRADO, jobExecutionContext);
			} catch (java.net.ConnectException eCon) {
				log.error(ConstantesProcesos.TIMEOUT, eCon);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT);
			} catch (java.net.SocketTimeoutException eCon) {
				log.error(ConstantesProcesos.TIMEOUT, eCon);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT);
			} catch (DescontarCreditosExcepcion descontarCreditosExcepcion) {
				try {
					finalizarTransaccionConError(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);
				} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
					marcarSolicitudConErrorServicio(solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1(), jobExecutionContext);
				} catch (BorrarSolicitudExcepcion e) {
					marcarSolicitudConErrorServicio(solicitud, e.getMensajeError1(), jobExecutionContext);
				}
			} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
				tratarRecuperable(jobExecutionContext, solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1());
			} catch (TrataMensajeExcepcion trataMensajeExcepcion) {
				tratarRecuperable(jobExecutionContext, solicitud, trataMensajeExcepcion.getMensajeError1());
				cambiaEstadoAError(solicitud, trataMensajeExcepcion, jobExecutionContext);
			} catch (RespuestaDesconocidaWS resWsEx) {
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
			} catch (OegamExcepcion oegamEx) {
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR);
				log.logarOegamExcepcion(oegamEx.getMensajeError1(), oegamEx);
			} catch (Exception e) {
				log.error("Excepcion CheckCTIT", e);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + ":" + e.getMessage());
			} catch (Throwable e) {
				log.error("Excepcion CheckCTIT", e);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + ":" + e.getMessage());
			}// fin del procedimiento si no hay error en la toma de solicitudes
		} else {
			peticionCorrecta(jobExecutionContext);
		}
	}

	private void llamadaWS(JobExecutionContext jobExecutionContext, ColaBean solicitud, DatosCTITBean datosCTITBean, DatosCTITDto datosCTITDto, String peticionXML, String deshabilitarPQ)
			throws Exception, OegamExcepcion {
		CtitsoapResponse respuestaWS = new CtitsoapResponse();
		String deshabilitarEjecucionDao = gestorPropiedades.valorPropertie("deshabilitar.ejecucion.dao");
		try {
			log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Procesando petición para el trámite:" + solicitud.getIdTramite());

			if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
				respuestaWS = dgtTransmision.checkCTITTransmision(peticionXML, datosCTITDto);
			} else {
				respuestaWS = getDgtSolicitudTransmision().checkCTITTransmision(peticionXML, datosCTITBean);
			}

			log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Peticion Procesada");
			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
			try {
				if (respuestaWS != null && respuestaWS.getResult() != null && respuestaWS.getResult().equals(ConstantesProcesos.ERROR)) {
					// ModeloTrafico.guardarEnvioData(jobExecutionContext.getFireTime(), solicitud.getProceso(),
					// solicitud.getIdTramite(), respuestaWS.getResult(), ConstantesProcesos.EJECUCION_NO_CORRECTA);
					String respuesta = null;
					if (respuestaWS.getErrorCodes() != null && respuestaWS.getErrorCodes().length > 0) {
						for (CtitCheckError ctitCheckError : respuestaWS.getErrorCodes()) {
							respuesta = ctitCheckError.getKey() + " - " + ctitCheckError.getMessage() + "; ";
						}
						solicitud.setRespuesta(respuesta);
					} else {
						solicitud.setRespuesta(respuestaWS.getResult());
					}
					log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Error Recibido: " + solicitud.getRespuesta());

					// Mantis 0013025
					if (solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE")) {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud,ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_CHECKCTIT);	
						}
						
						//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
						log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- se ha producido una excepción ");
					} else {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_CHECKCTIT);	
						}
						
						log.error("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- El Proceso Devolvió error ");
						//	ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					}
					// Fin Mantis 0013025

				} else if (respuestaWS != null && respuestaWS.getResult() != null) {
					log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Respuesta recibida");
					// ModeloTrafico.guardarEnvioData(jobExecutionContext.getFireTime(), solicitud.getProceso(),
					// solicitud.getIdTramite(), respuestaWS.getResult(), ConstantesProcesos.EJECUCION_CORRECTA);
					solicitud.setRespuesta(respuestaWS.getResult());
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_CHECKCTIT);	
					}
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, null);
					log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Proceso ejecutado correctamente");
				}
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.CHECKCTIT.getNombreEnum());
			}
			// FIN : Incidencia : 0000814

			// MANEJAMOS LA RESPUESTA DEL WS
			if (respuestaWS.getResult() != null)
				log.info("resultado checkCTIT para " + solicitud.getIdTramite() + ": " + respuestaWS.getResult());

			if (respuestaWS.getResult().equals(ConstantesProcesos.TRAMITABLE)) {
				respuestaTramitable(jobExecutionContext, solicitud, respuestaWS);
			} else if (respuestaWS.getResult().equals(ConstantesProcesos.INFORME_REQUERIDO_PARA_TRAMITACION) || respuestaWS.getResult().equals("INFORME_REQUERIDO")) {
				respuestaInformeRequerido(jobExecutionContext, solicitud, respuestaWS);
			} else if (respuestaWS.getResult().equals(ConstantesProcesos.NO_TRAMITABLE)) {
				respuestaNoTramitable(jobExecutionContext, solicitud, respuestaWS);
			} else if (respuestaWS.getResult().equals(ConstantesProcesos.JEFATURA)) {
				respuestaJefatura(jobExecutionContext, solicitud, respuestaWS);
			} else if (respuestaWS.getResult().equals(ConstantesProcesos.ERROR)) {
				respuestaError(jobExecutionContext, solicitud, respuestaWS);
			} // SI ENTRA EN ESTE ELSE, EL WEBSERVICE HA DADO UNA RESPUESTA DESCONOCIDA
			else {
				throw new RespuestaDesconocidaWS(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
			}

			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
		} catch (Exception ex) {
			// ModeloTrafico.guardarEnvioData(jobExecutionContext.getFireTime(), solicitud.getProceso(),
			// solicitud.getIdTramite(), ConstantesProcesos.EXCEPCION_INVOCACION_WS + ex.toString(),
			// ConstantesProcesos.EJECUCION_EXCEPCION);
			try {
				String error = null;
				if (ex.getMessage() == null || ex.getMessage().equals("")) {
					error = ex.toString();
				} else {
					error = ex.toString() + " " + ex.getMessage();
				}

				if (solicitud.getRespuesta() == null) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error : "sin mensaje");
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error != null ? error : "sin mensaje",ConstantesProcesos.PROCESO_CHECKCTIT);	
					}
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error : "sin mensaje");
				} else if (solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE")) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_CHECKCTIT);	
					}
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
				} else if (!ConstantesProcesos.OK.equals(solicitud.getRespuesta()) && !ConstantesProcesos.TRAMITABLE.equals(solicitud.getRespuesta())) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_CHECKCTIT);	
					}
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					log.error("Proceso CheckCTIT: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);
				}
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.CHECKCTIT.getNombreEnum(), e);
			}
			throw ex;
		} catch (OegamExcepcion ex) {
			// ModeloTrafico.guardarEnvioData(jobExecutionContext.getFireTime(), solicitud.getProceso(),
			// solicitud.getIdTramite(), ConstantesProcesos.EXCEPCION_INVOCACION_WS + ex.toString(),
			// ConstantesProcesos.EJECUCION_EXCEPCION);
			try {
				String error = null;
				if ((ex.getMessage() == null || ex.getMessage().equals("")) && (ex.getMensajeError1() == null || ex.getMensajeError1().equals(""))) {
					error = ex.toString();
				} else {
					error = ex.toString() + " " + ex.getMessage() + " " + ex.getMensajeError1();
				}

				if (solicitud.getRespuesta() == null) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error : "sin mensaje");
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error != null ? error : "sin mensaje",ConstantesProcesos.PROCESO_CHECKCTIT);	
					}
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error : "sin mensaje");
				} else if (solicitud.getRespuesta() != null
						&& (solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE"))) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_CHECKCTIT);	
					}
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
				} else if (!ConstantesProcesos.OK.equals(solicitud.getRespuesta()) && !ConstantesProcesos.TRAMITABLE.equals(solicitud.getRespuesta())) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_CHECKCTIT);	
					}
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					log.error("Proceso CheckCTIT: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);
				}
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.CHECKCTIT.getNombreEnum(), e);
			}
			throw ex;
		}
		// FIN : Incidencia : 0000814
	}

	private void respuestaError(JobExecutionContext jobExecutionContext, ColaBean solicitud, CtitsoapResponse respuestaWS) throws TrataMensajeExcepcion, CambiarEstadoTramiteTraficoExcepcion,
			BorrarSolicitudExcepcion {
		// Hay error, Evaluar error_tecnico o error_funcional
		// mostrar lista de errores del webservice.
		CtitCheckError[] listadoErrores = respuestaWS.getErrorCodes();
		String respuestaError = "";
		if (listadoErrores != null) {
			listarErroresCTIT(listadoErrores);
			respuestaError = getMensajeError(listadoErrores);
		}
		// reencolar la peticion dependiendo del tipo de error

		if (!compruebaErroresRecuperables(listadoErrores)) {
			finalizarTransaccionNoTramitable(jobExecutionContext, solicitud, !"".equals(respuestaError) ? respuestaError : "NO TRAMITABLE");
		} else {
			tratarRecuperable(jobExecutionContext, solicitud, respuestaError);
		}
	}

	private boolean compruebaErroresRecuperables(CtitCheckError[] listadoErrores) throws TrataMensajeExcepcion {
		boolean recuperable = true;
		if (listadoErrores != null) {
			for (CtitCheckError error : listadoErrores) {
				recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
				if (!recuperable) {
					break;
				}
			}
		}
		return recuperable;
	}

	private void respuestaJefatura(JobExecutionContext jobExecutionContext, ColaBean cola, CtitsoapResponse respuestaWS) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		CtitCheckError[] listadoErrores = respuestaWS.getErrorCodes();
		String respuestaError = "";
		if (listadoErrores != null) {
			listarErroresCTIT(listadoErrores);
			respuestaError = getMensajeError(listadoErrores);
		}
		finalizarTransaccionEnJefatura(jobExecutionContext, cola, !"".equals(respuestaError) ? respuestaError : "TRAMITABLE EN JEFATURA");
	}

	private void respuestaNoTramitable(JobExecutionContext jobExecutionContext, ColaBean cola, CtitsoapResponse respuestaWS) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		CtitCheckError[] listadoErrores = respuestaWS.getErrorCodes();
		String respuestaError = "";
		if (listadoErrores != null) {
			listarErroresCTIT(listadoErrores);
			respuestaError = getMensajeError(listadoErrores);
		}
		finalizarTransaccionNoTramitable(jobExecutionContext, cola, !"".equals(respuestaError) ? respuestaError : "NO TRAMITABLE");
	}

	private void respuestaInformeRequerido(JobExecutionContext jobExecutionContext, ColaBean cola, CtitsoapResponse respuestaWS) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		CtitCheckError[] listadoErrores = respuestaWS.getErrorCodes();
		String respuestaError = "";
		if (listadoErrores != null) {
			listarErroresCTIT(listadoErrores);
			respuestaError = getMensajeError(listadoErrores);
		}
		finalizarTransaccionConIncidencias(jobExecutionContext, cola, !"".equals(respuestaError) ? respuestaError : "EMBARGO O PRECINTO ANOTADO. SE REQUIERE INFORME.");
	}

	private void respuestaTramitable(JobExecutionContext jobExecutionContext, ColaBean cola, CtitsoapResponse respuestaWS) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		CtitCheckError[] listadoErrores = respuestaWS.getErrorCodes();
		String respuestaError = "";
		if (listadoErrores != null) {
			listarErroresCTIT(listadoErrores);
			respuestaError = getMensajeError(listadoErrores);
		}
		finalizarTransaccionTramitable(jobExecutionContext, cola, !"".equals(respuestaError) ? respuestaError : ConstantesProcesos.TRAMITABLE);
	}

	private void listarErroresCTIT(CtitCheckError[] listadoErrores) {
		for (int i = 0; i < listadoErrores.length; i++) {
			log.error("error de webservice codigo:" + listadoErrores[i].getKey());
			log.error("error de webservice descripcion:" + listadoErrores[i].getMessage());
		}
	}
	
	private String getMensajeError(CtitCheckError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - ");
			String error = utiles.quitarCaracteresExtranios(listadoErrores[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		String mensaje = mensajeError.toString();
		if (mensaje != null && mensaje.length() > TAMANIO_RESPUESTA_COLA) {
			log.error("El mensaje es mayor de " + TAMANIO_RESPUESTA_COLA + ": " + mensaje);
			mensaje = mensaje.substring(0, TAMANIO_RESPUESTA_COLA - 1);
		}
		return mensaje;
	}
	
	@Override
	public void cambioNumeroInstancias(int numero) {
		log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT);
	}

	private void cambiaEstadoAError(ColaBean solicitud, TrataMensajeExcepcion trataMensajeExcepcion, JobExecutionContext jobExecutionContext) {
		try {
			finalizarTransaccionConErrorNoRecuperable(solicitud, trataMensajeExcepcion.getMessage(), jobExecutionContext);
		} catch (BorrarSolicitudExcepcion e) {
			log.error("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Error al cambiar de estado: " + e);
		} catch (CambiarEstadoTramiteTraficoExcepcion ex) {
			log.error("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Error al cambiar de estado: " + ex);
		}
	}

	private void finalizarTransaccionEnJefatura(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuestaError) throws CambiarEstadoTramiteTraficoExcepcion,
			BorrarSolicitudExcepcion {
		log.info("finalizar tramite en Jefatura");
		getModeloTrafico().cambiarEstadoTramite(respuestaError, EstadoTramiteTrafico.Tramitable_Jefatura, solicitud.getIdTramite(), solicitud.getIdUsuario());
		peticionCorrecta(jobExecutionContext);
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), respuestaError);
	}

	private void finalizarTransaccionTramitable(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuesta) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		log.info("finalizar tramite tramitable");
		getModeloTrafico().cambiarEstadoTramite(respuesta, EstadoTramiteTrafico.Tramitable, solicitud.getIdTramite(), solicitud.getIdUsuario());
		peticionCorrecta(jobExecutionContext);
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), respuesta);
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloDGTWS getModeloDGTWS() {
		if (modeloDGTWS == null) {
			modeloDGTWS = new ModeloDGTWS();
		}
		return modeloDGTWS;
	}

	public void setModeloDGTWS(ModeloDGTWS modeloDGTWS) {
		this.modeloDGTWS = modeloDGTWS;
	}

	public UtilesProcesos getUtilesProcesos() {
		if (utilesProcesos == null) {
			utilesProcesos = new UtilesProcesos();
		}
		return utilesProcesos;
	}

	public void setUtilesProcesos(UtilesProcesos utilesProcesos) {
		this.utilesProcesos = utilesProcesos;
	}

	public DGTSolicitudTransmision getDgtSolicitudTransmision() {
		if (dgtSolicitudTransmision == null) {
			dgtSolicitudTransmision = new DGTSolicitudTransmision();
		}
		return dgtSolicitudTransmision;
	}

	public void setDgtSolicitudTransmision(DGTSolicitudTransmision dgtSolicitudTransmision) {
		this.dgtSolicitudTransmision = dgtSolicitudTransmision;
	}

}