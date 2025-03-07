package colas.procesos;

import java.io.IOException;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.dgt.DGTTransmision;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCTITDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.gescogroup.blackbox.CtitNotificationAdvise;
import com.gescogroup.blackbox.CtitNotificationError;
import com.gescogroup.blackbox.CtitNotificationImpediment;
import com.gescogroup.blackbox.CtitsoapNotificationResponse;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import colas.procesos.utiles.UtilesProcesos;
import escrituras.beans.ResultBean;
import oegam.constantes.ConstantesPQ;
import trafico.beans.DatosCTITBean;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.modelo.ModeloDGTWS;
import trafico.transmision.ctit.DGTSolicitudTransmision;
import trafico.utiles.constantes.ConstantesMensajes;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;
import utilidades.web.excepciones.RespuestaDesconocidaWS;
import utilidades.web.excepciones.RespuestaWSDesconocida;
import utilidades.web.excepciones.SinDatosWSExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TrataMensajeExcepcion;

public class ProcesoNotificationCTIT extends AbstractProcesoCTIT {	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoNotificationCTIT.class);
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
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		Boolean esLaborable = true;
		Boolean forzarEjecucion = false;
		//comprueba si el dia es laborable y no es festivo nacional
		if("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))){
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ",e1);
			}
		}
		
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.FORZAR_EJECUCION_TRANSMISION))) {
			forzarEjecucion = true;
		}
		
		if(esLaborable || forzarEjecucion){
			// Ya se hace en el padre ProcesoBase
			// Forzamos la inyección de dependencias de las clases anotadas como 'Autowired'
			//SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

					
			hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
			ColaBean solicitud = new ColaBean(); 
			DatosCTITBean datosCTITBean = new DatosCTITBean();
			DatosCTITDto datosCTITDto = new DatosCTITDto();
			
			try {
				log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Buscando Solicitud");
				solicitud = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_NOTIFICATIONCTIT, hiloActivo.toString());
				
				String deshabilitarPQ = gestorPropiedades.valorPropertie("deshabilitar.pq.dgtws");
				
				if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
					datosCTITDto = servicioTramiteTraficoTransmision.datosCTIT(solicitud.getIdTramite());
				} else {
					datosCTITBean = getModeloDGTWS().datosCTIT(solicitud.getIdTramite());
				}
				
				String peticionXML = getUtilesProcesos().recogerXmlTransmision(solicitud);
				log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Solicitud " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " encontrada, llamando a WS");
				llamadaWS(jobExecutionContext, solicitud, datosCTITBean,datosCTITDto,peticionXML,deshabilitarPQ);
			} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion){
				sinPeticiones(jobExecutionContext); 
				log.info(sinSolicitudesExcepcion.getMensajeError1());
			} catch (SinDatosWSExcepcion sinDatosWSExcepcion){
				tratarRecuperable(jobExecutionContext, solicitud,ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_AL_WEBSERVICE);
				log.logarOegamExcepcion(sinDatosWSExcepcion.getMensajeError1(), sinDatosWSExcepcion);
			}//excepciones dentro del webservice		
			catch (java.io.FileNotFoundException fileNotFound) {
				log.error(ConstantesProcesos.XML_PARA_JUSTIFICANTE_PROFESIONAL_NO_ENCONTRADO, fileNotFound); 
				marcarSolicitudConErrorServicio(solicitud, ConstantesProcesos.XML_PARA_JUSTIFICANTE_PROFESIONAL_NO_ENCONTRADO, jobExecutionContext); 
			} catch (java.net.ConnectException eCon){
				log.error(ConstantesProcesos.TIMEOUT, eCon); 
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT); 
			} catch (java.net.SocketTimeoutException eCon){
				log.error(ConstantesProcesos.TIMEOUT, eCon); 
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT); 
			} catch (DescontarCreditosExcepcion descontarCreditosExcepcion){
				try {
					finalizarTransaccionConError(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);
				} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
					marcarSolicitudConErrorServicio(solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1(), jobExecutionContext);
				} catch (BorrarSolicitudExcepcion e) {
						marcarSolicitudConErrorServicio(solicitud,e.getMensajeError1(),jobExecutionContext);
				}
			} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion){
				tratarRecuperable(jobExecutionContext, solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1()); 
			} catch (TrataMensajeExcepcion trataMensajeExcepcion) {
				tratarRecuperable(jobExecutionContext, solicitud, trataMensajeExcepcion.getMensajeError1());
				cambiaEstadoAError(solicitud, trataMensajeExcepcion, jobExecutionContext);
			} catch(RespuestaDesconocidaWS resWsEx) {
				tratarRecuperable(jobExecutionContext, solicitud, 
						ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR +ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
			} catch (OegamExcepcion oegamEx){
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR); 
				log.logarOegamExcepcion(oegamEx.getMensajeError1(), oegamEx);
			} catch (Exception e){
				log.error("Excepcion CTIT", e); 
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ e.getMessage()); 
			} catch (Throwable e){
				log.error("Throwable CTIT", e); 
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ e.getMessage()); 
			}
		}else{
			peticionCorrecta(jobExecutionContext);
		}
		//Solicitud recuperada correctamente
	}//fin del procedimiento si no hay error en la toma de solicitudes

	private void llamadaWS(JobExecutionContext jobExecutionContext,	ColaBean solicitud, DatosCTITBean datosCTITBean, DatosCTITDto datosCTITDto, String peticionXML, String deshabilitarPQ) 
			throws IOException, Exception, OegamExcepcion {
		//-----------------PASAMOS A INVOCAR AL WEBSERVICE-------------
		CtitsoapNotificationResponse respuestaWS = new CtitsoapNotificationResponse();
		try {
			log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Procesando petición");
			
			if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
				respuestaWS = dgtTransmision.notificationCTITTransmision(peticionXML, datosCTITDto);
			} else {
				respuestaWS = getDgtSolicitudTransmision().notificationCTITTransmision(peticionXML,datosCTITBean);
			}
			
			log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Peticion Procesada");
			
			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
			try {
				if(respuestaWS != null && respuestaWS.getResult() != null && respuestaWS.getResult().equals(ConstantesProcesos.OK)) {
					log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Respuesta recibida con resultado OK");
					solicitud.setRespuesta(ConstantesProcesos.OK);
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, null);
					log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Proceso ejecutado correctamente");
				} else if(respuestaWS != null) {
					String respuesta = null;
					if(respuestaWS.getErrorCodes() != null && respuestaWS.getErrorCodes().length > 0){
						for(CtitNotificationError error : respuestaWS.getErrorCodes()){
							respuesta = error.getKey() + " - " + error.getMessage() + "; ";
						}
						solicitud.setRespuesta(respuesta);
					}else if(respuestaWS.getResult() != null){
						solicitud.setRespuesta(respuestaWS.getResult());
					}else{
						solicitud.setRespuesta(ConstantesProcesos.ERROR);
					}
					log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + "Respuesta recibida con resultado NO OK: ->" + solicitud.getRespuesta());
					//TODO Habria que averiguar cuales son exactamente los codigos de error que lanza el WS no contemplados
					//Mantis 0013025 
					if(solicitud.getRespuesta() != null && (solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE"))){
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
						log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- se ha producido una excepción ");
						//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
						
					}else{
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
						log.error("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- El Proceso Devolvió error ");
						//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
						
					}
					//Fin Mantis 0013025
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
				}
			} catch(Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.NOTIFICATIONCTIT.getNombreEnum(), e);
			}
			// FIN : Incidencia : 0000814

			// Se guarda el informe si viene
			boolean existeInforme = false;
			if (respuestaWS!=null && respuestaWS.getReport()!=null  && !respuestaWS.getReport().isEmpty()) {
				try {
					existeInforme = getUtilesProcesos().guardarInforme(solicitud, respuestaWS.getReport());
				}catch (OegamExcepcion e) {
					log.error("No se pudo guardar el informe, no se cumplen requisitos", e);
				}catch (Exception e) {
					log.error("No se pudo guardar el informe, error generico", e);
				}
			}

			//MANEJAMOS LA RESPUESTA DEL WS
			if (respuestaWS.getResult()!=null ||
					(respuestaWS.getResult()==null && respuestaWS.getErrorCodes() != null && respuestaWS.getErrorCodes().length > 0)) {
				log.info("resultadoWS:" + respuestaWS.getResult());

				if (respuestaWS.getResult()!=null){
					if(respuestaWS.getResult().equals(ConstantesProcesos.OK)) {
						respuestaOk(jobExecutionContext, solicitud, respuestaWS);
					} else if(respuestaWS.getResult().equals(ConstantesProcesos.TRAMITABLE_CON_INCIDENCIAS)){
						log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT+ " -- "
								+ "Respuesta recibida con resultado TRAMITABLE CON INCIDENCIAS");
						respuestaTramitableConIncidencias(jobExecutionContext,solicitud, respuestaWS);
					} else if(respuestaWS.getResult().equals(ConstantesProcesos.NO_TRAMITABLE)){
						log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT+ " -- "
								+ "Respuesta recibida con resultado NO TRAMITABLE");
						respuestaNoTramitable(jobExecutionContext, solicitud,respuestaWS, existeInforme);
					} else if(respuestaWS.getResult().equals(ConstantesProcesos.ERROR)){
						log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT+ " -- "
								+ "Respuesta recibida con resultado ERROR");
						respuestaError(jobExecutionContext, solicitud, respuestaWS);
					}
				}else{
					respuestaError(jobExecutionContext, solicitud, respuestaWS);
				}
			}
			//SI ENTRA EN ESTE ELSE, EL WEBSERVICE HA DADO UNA RESPUESTA DESCONOCIDA 
			else throw new RespuestaWSDesconocida("NotificacionCTIT da una respuesta desconocida:" + respuestaWS.getResult()); 

			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
		} catch(Exception ex) {
			try {
				String error = null;
				if(ex.getMessage() == null || ex.getMessage().equals("")){
					error = ex.toString();
				}else{
					error = ex.toString() + " " + ex.getMessage();
				}
				if (solicitud.getRespuesta() == null) {
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error != null ? error:"sin mensaje",ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
				} else if(solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE")){
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
				}else if(!ConstantesProcesos.OK.equals(solicitud.getRespuesta())) {
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					log.error("Proceso NotificationCTIT: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);			
				}
			} catch(Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.NOTIFICATIONCTIT.getNombreEnum(), e);
			}
			throw ex;
		} catch(OegamExcepcion ex) {
			try{
				String error = null;
				if((ex.getMessage() == null || ex.getMessage().equals("")) && (ex.getMensajeError1() == null || ex.getMensajeError1().equals(""))){
					error = ex.toString();
				}else{
					error = ex.toString() + " " + ex.getMessage() + " " + ex.getMensajeError1();
				}
				if (solicitud.getRespuesta() == null) {
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error != null ? error:"sin mensaje",ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
				} else if(solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE")){
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
				}else if(!ConstantesProcesos.OK.equals(solicitud.getRespuesta())) {					
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					log.error("Proceso NotificationCTIT: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);								
				}
			}catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.NOTIFICATIONCTIT.getNombreEnum(), e);
			}
			throw ex;
		}
		// FIN : Incidencia : 0000814
	}
	
	private void respuestaNoTramitable(JobExecutionContext jobExecutionContext, ColaBean solicitud, CtitsoapNotificationResponse respuestaWS, boolean existeInforme)
			throws Exception, IOException, CambiarEstadoTramiteTraficoExcepcion, DescontarCreditosExcepcion, BorrarSolicitudExcepcion {
		
		//Cambiamos a Finalizado con error
		CtitNotificationError[] listadoErrores = respuestaWS.getErrorCodes();
		CtitNotificationImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		String respuestaError = "";
		if(listadoErrores!=null){
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}
		
		if(listadoImpedimentos!=null) {
			respuestaError += getImpedimentos(listadoImpedimentos);
		}
		
		try {
			getUtilesProcesos().guardarPTC(solicitud,  respuestaWS.getLicense());
		} catch (OegamExcepcion e) {
					log.error("No se pudo guardar el permiso temporal de circulación: " + e.getMessage());
		}
		try {
			if (existeInforme){
				finalizarTransaccionCorrectoTransmision(jobExecutionContext, solicitud, respuestaError); 
			} else {	
				finalizarTramiteNoTramitable(jobExecutionContext, solicitud, respuestaError);
			}
		} catch (OegamExcepcion e) {
			 	log.error("No se pudo guardar el informe: " + e.getMessage());
		}
	}

	private void respuestaTramitableConIncidencias (JobExecutionContext jobExecutionContext, ColaBean solicitud, CtitsoapNotificationResponse respuestaWS) 
	 		throws Exception, IOException, CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
	
		try {
			getUtilesProcesos().guardarPTC(solicitud,  respuestaWS.getLicense());
		}catch (OegamExcepcion e) {
			log.error("No se pudo guardar el permiso temporal de circulación: " + e.getMessage());
		}
		//Cambiamos a Finalizado con error
		CtitNotificationError[] listadoErrores = respuestaWS.getErrorCodes();
		CtitNotificationImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		CtitNotificationAdvise[] listadoAvisos = respuestaWS.getAdviseCodes();
		String respuestaError = "";
		
		if(listadoErrores!=null){
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}
		
		if(listadoImpedimentos!=null){
			respuestaError += getImpedimentos(listadoImpedimentos);
		}
		
		if(listadoAvisos!=null){
			respuestaError += getAvisos(listadoAvisos);
		}
		finalizarTransaccionConIncidencias(jobExecutionContext, solicitud, respuestaError); 
	}

	private void respuestaOk(JobExecutionContext jobExecutionContext, ColaBean solicitud, CtitsoapNotificationResponse respuestaWS)
			throws Exception, IOException, CambiarEstadoTramiteTraficoExcepcion, DescontarCreditosExcepcion, BorrarSolicitudExcepcion{
		
		try {
			getUtilesProcesos().guardarPTC(solicitud,  respuestaWS.getLicense());
		} catch (OegamExcepcion e2) {
			log.error("No se pudo guardar el permiso temporal de circulación: " + e2.getMessage());
		}
		
		//Cambiamos el estado del trámite a Finalizado_telemáticamente
		//@author: Carlos García
		//Incidencia: 0001828
		// Para los trámites del que van bien, se coloca en el mensaje establecido por SEA en el ticket TPY-233-69562
		
		String mensaje = ConstantesMensajes.MENSAJE_CTIT_RESULTADO_OK;
		finalizarTransaccionCorrectoTransmision(jobExecutionContext, solicitud, mensaje);
		//Fin Incidencia: 0001828
		
	}
	
	private void respuestaError(JobExecutionContext jobExecutionContext, ColaBean solicitud,
			CtitsoapNotificationResponse respuestaWS) throws IOException, Exception, CambiarEstadoTramiteTraficoExcepcion, TrataMensajeExcepcion, BorrarSolicitudExcepcion {

		try {
			getUtilesProcesos().guardarPTC(solicitud,  respuestaWS.getLicense());
		} catch (OegamExcepcion e) {
			log.error("No se pudo guardar el permiso temporal de circulación: " + e.getMessage());
		}
		//Hay error, Evaluar error_tecnico o error_funcional 
		//mostrar lista de errores del webservice.
		CtitNotificationError[] listadoErrores = respuestaWS.getErrorCodes();
		CtitNotificationImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		String respuestaError = "";
		if(listadoErrores!=null){
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}
		
		if(listadoImpedimentos!=null){
			respuestaError += getImpedimentos(listadoImpedimentos);
		}
		
		boolean recuperable = compruebaErroresRecuperables(listadoErrores);
		if (!recuperable) {
			finalizarTransaccionConErrorNoRecuperable(solicitud, respuestaError,jobExecutionContext);
		} if (recuperable) {
			tratarRecuperable(jobExecutionContext, solicitud, respuestaError);
		}
	}
	
	private boolean compruebaErroresRecuperables(CtitNotificationError[] listadoErrores) throws TrataMensajeExcepcion {		
		boolean recuperable = true; 
		
		for(CtitNotificationError error:listadoErrores) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
			if (!recuperable) {
				break;
			}
		}
		return recuperable;
	}

	private void listarErroresCTIT(CtitNotificationError[] listadoErrores) {
		for (int i = 0; i < listadoErrores.length; i++){
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.CODIGO + listadoErrores[i].getKey()); 
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.DESCRIPCION +listadoErrores[i].getMessage());
		}
	}
	
	private void finalizarTramiteNoTramitable(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuesta) {
		//Hacer la actualizacion de créditos incrementales
		log.info("finalizar tramite no tramitable");
			BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
			beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(solicitud.getIdTramite());
			beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
			beanPQCambiarEstadoTramite.setP_RESPUESTA(respuesta); 

			ResultBean resultBeanCambiareEstado  = (ResultBean) getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite).get(ConstantesPQ.RESULTBEAN);
			log.info(resultBeanCambiareEstado.getMensaje()); 
			if (!resultBeanCambiareEstado.getError()){
				peticionCorrecta(jobExecutionContext); 	
				getModeloSolicitud().borrarSolicitud(solicitud.getIdEnvio(),ConstantesProcesos.OK);
			}
		//}
	}

	private String  getMensajeError(CtitNotificationError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer(); 
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - " );
			String error = utiles.quitarCaracteresExtranios(listadoErrores[i].getMessage());
			if(error.length()>80){
				String resAux = "";
				for(int tam=0; tam<=Math.floor(error.length()/80); tam++){
					if(tam!=Math.floor(error.length()/80)){
						resAux += error.substring(80*tam, 80*tam+80)+" - ";
					} else {
						resAux += error.substring(80*tam)+" - ";
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		return mensajeError.toString(); 
	}
	
	private String getAvisos(CtitNotificationAdvise[] listadoAvisos) {
		StringBuffer aviso = new StringBuffer(); 
		for (int i = 0; i < listadoAvisos.length; i++) {
			aviso.append(listadoAvisos[i].getKey());
			aviso.append(" - " );
			String error = utiles.quitarCaracteresExtranios(listadoAvisos[i].getMessage());
			if(error.length()>80){
				String resAux = "";
				for(int tam=0; tam<=Math.floor(error.length()/80); tam++){
					if(tam!=Math.floor(error.length()/80)){
						resAux += error.substring(80*tam, 80*tam+80)+" - ";
					} else {
						resAux += error.substring(80*tam)+" - ";
					}
				}
				error = resAux;
			}
			aviso.append(error);
		}
		return aviso.toString(); 
	}
	
	private String  getImpedimentos(CtitNotificationImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer(); 
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - " );
			String error = utiles.quitarCaracteresExtranios(listadoImpedimentos[i].getMessage());
			if(error.length()>80){
				String resAux = "";
				for(int tam=0; tam<=Math.floor(error.length()/80); tam++){
					if(tam!=Math.floor(error.length()/80)){
						resAux += error.substring(80*tam, 80*tam+80)+" - ";
					} else {
						resAux += error.substring(80*tam)+" - ";
					}
				}
				error = resAux;
			}
			impedimento.append(error);
		}
		return impedimento.toString(); 
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
	}

	private void cambiaEstadoAError(ColaBean solicitud, TrataMensajeExcepcion trataMensajeExcepcion, JobExecutionContext jobExecutionContext){
		try{
			finalizarTransaccionConErrorNoRecuperable(solicitud, trataMensajeExcepcion.getMessage(),jobExecutionContext);
		}catch( BorrarSolicitudExcepcion e){
			log.error("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Error al cambiar de estado: " + e);
		}catch(CambiarEstadoTramiteTraficoExcepcion ex){
			log.error("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Error al cambiar de estado: " + ex);
		}
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