package colas.procesos;

import java.io.IOException;
import java.math.BigDecimal;

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

import com.gescogroup.blackbox.CtitTradeAdvise;
import com.gescogroup.blackbox.CtitTradeError;
import com.gescogroup.blackbox.CtitTradeImpediment;
import com.gescogroup.blackbox.CtitsoapTradeResponse;

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
import utilidades.web.excepciones.SinDatosWSExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TrataMensajeExcepcion;

public class ProcesoTradeCTIT extends AbstractProcesoCTIT {	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoTradeCTIT.class);
//	private int indice=1; 
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

			
			ColaBean solicitud = new ColaBean();		
			hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
			DatosCTITBean datosCTITBean = new DatosCTITBean();
			DatosCTITDto datosCTITDto = new DatosCTITDto();
			
			try	{
				log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- Buscando Solicitud");
				solicitud = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_TRADECTIT, hiloActivo.toString());	
				
				String deshabilitarPQ = gestorPropiedades.valorPropertie("deshabilitar.pq.dgtws");
				
				if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
					datosCTITDto = servicioTramiteTraficoTransmision.datosCTIT(solicitud.getIdTramite());
				} else {
					datosCTITBean = getModeloDGTWS().datosCTIT(solicitud.getIdTramite());
				}
				
				/**Cambios Gestor de Documentos*/
				String peticionXML = getUtilesProcesos().recogerXmlTransmision(solicitud);
				log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- Solicitud " + ConstantesProcesos.PROCESO_TRADECTIT + " encontrada, llamando a WS");
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
				marcarSolicitudConErrorServicio(solicitud, fileNotFound.getMessage(), jobExecutionContext); 
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
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR +ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
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
	}
	
	private void llamadaWS(JobExecutionContext jobExecutionContext,	ColaBean solicitud, DatosCTITBean datosCTITBean,  DatosCTITDto datosCTITDto, String peticionXML, String deshabilitarPQ)
			throws Exception, OegamExcepcion {
		String deshabilitarEjecucionDao = gestorPropiedades.valorPropertie("deshabilitar.ejecucion.dao");
		try{
			//-----------------PASAMOS A INVOCAR AL WEBSERVICE-------------
			CtitsoapTradeResponse respuestaWS = new CtitsoapTradeResponse();
			log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- Procesando petición");
			
			if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
				respuestaWS = dgtTransmision.tradeCTITTransmision(peticionXML, datosCTITDto);
			} else {
				respuestaWS = getDgtSolicitudTransmision().tradeCTITTransmision(peticionXML,datosCTITBean);
			}
			
			log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- Peticion Procesada");

			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
			try {
				if(respuestaWS != null && respuestaWS.getResult() != null && respuestaWS.getResult().equals(ConstantesProcesos.OK)) {
					log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- Respuesta recibida con resultado OK");
					solicitud.setRespuesta(ConstantesProcesos.OK);
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_TRADECTIT);	
					}
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, null);
					log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- Proceso ejecutado correctamente");
				} else if(respuestaWS != null) {
					String respuesta = null;
					if(respuestaWS.getErrorCodes() != null && respuestaWS.getErrorCodes().length > 0) {
						for(CtitTradeError error : respuestaWS.getErrorCodes()){
							respuesta = error.getKey() + " - " + error.getMessage() + "; ";
						}
						solicitud.setRespuesta(respuesta);				
					} else if(respuestaWS.getResult() != null) {
						// Incidencia 0003815: TRADE CTIT - Entrega compraventa (problemas en cambio de estado) atc
						if (respuestaWS.getResult().equals(ConstantesProcesos.NO_TRAMITABLE)) {
							log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- "
									+ "Respuesta recibida con resultado NO TRAMITABLE");
								solicitud.setRespuesta(ConstantesProcesos.FINALIZADO_CON_ERROR);}
						else {
							solicitud.setRespuesta(respuestaWS.getResult());
						}
						// fin incidencia 0003815
					} else {
						solicitud.setRespuesta(ConstantesProcesos.ERROR);
					}
					//TODO Habria que averiguar cuales son exactamente los codigos de error que lanza el WS no contemplados
					//Mantis 0013025 
					if(solicitud.getRespuesta() != null && (solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE"))){
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_TRADECTIT);	
						}
						
						//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
						log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- se ha producido una excepción ");
					}else{
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_TRADECTIT);	
						}
						
						log.error("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- El Proceso Devolvió error ");	
						//	ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
						
					}
					//Fin Mantis 0013025
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- Respuesta recibida con resultado NO OK: -> " + solicitud.getRespuesta());
				}
			} catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.TRADECTIT.getNombreEnum(), e);
			}
			// FIN : Incidencia : 0000814

			// Se guarda el informe si viene
			if (respuestaWS!=null && respuestaWS.getReport()!=null  && !respuestaWS.getReport().isEmpty()) {
				try {
					getUtilesProcesos().guardarInforme(solicitud, respuestaWS.getReport());
				}catch (OegamExcepcion e) {
					log.error("No se pudo guardar el informe, no se cumplen requisitos", e);
				}catch (Exception e) {
					log.error("No se pudo guardar el informe, error generico", e);
				}
			}

			//MANEJAMOS LA RESPUESTA DEL WS
			if (respuestaWS.getResult()!=null) {
				log.info(ConstantesProcesos.RESULTADO_WS + respuestaWS.getResult());
				if(respuestaWS.getResult().equals(ConstantesProcesos.OK)){
					respuestaOk(jobExecutionContext, solicitud, respuestaWS);
				} else if(respuestaWS.getResult().equals(ConstantesProcesos.TRAMITABLE_CON_INCIDENCIAS)){
					log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT+ " -- "
							+ "Respuesta recibida con resultado TRAMITABLE CON INCIDENCIAS");
					respuestaTramitableIncidencias(jobExecutionContext,	solicitud, respuestaWS);
				} else if(respuestaWS.getResult().equals(ConstantesProcesos.NO_TRAMITABLE)){
					log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- "
							+ "Respuesta recibida con resultado NO TRAMITABLE");
					respuestaNoTramitable(jobExecutionContext, solicitud,respuestaWS);
				}else if(respuestaWS.getResult().equals(ConstantesProcesos.FINALIZADO_CON_ERROR)){
					log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- "
							+ "Respuesta recibida con resultado FINALIZADO CON ERROR");
					respuestaError(jobExecutionContext, solicitud,respuestaWS);	
				} else if(respuestaWS.getResult().equals(ConstantesProcesos.ERROR)){
					respuestaError(jobExecutionContext, solicitud, respuestaWS);
				} else {
					if (respuestaWS.getErrorCodes()!=null){
						respuestaError(jobExecutionContext, solicitud, respuestaWS);
					}else{
						throw new RespuestaDesconocidaWS(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA + respuestaWS.getResult());
					}
				}
			}//end if !=null
			else {						
				if (respuestaWS.getErrorCodes()!=null){
					respuestaError(jobExecutionContext, solicitud, respuestaWS);
				}else{
					throw new RespuestaDesconocidaWS(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA + respuestaWS.getResult());
				}
			}
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
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error != null ? error:"sin mensaje",ConstantesProcesos.PROCESO_TRADECTIT);	
					}
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
				} else if(solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE")){
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_TRADECTIT);	
					}
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
					
				}else if(!ConstantesProcesos.OK.equals(solicitud.getRespuesta())) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_TRADECTIT);	
					}
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					log.error("Proceso TradeCTIT: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);											
				}
			} catch(Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.TRADECTIT.getNombreEnum(), e);
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
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error != null ? error:"sin mensaje",ConstantesProcesos.PROCESO_TRADECTIT);	
					}
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
				} else  if(solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE")){
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_TRADECTIT);	
					}
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
				}else if(!ConstantesProcesos.OK.equals(solicitud.getRespuesta())) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_TRADECTIT);	
					}
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					log.error("Proceso TradeCTIT: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);			
				}
			}catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.TRADECTIT.getNombreEnum(), e);
			}
			throw ex;
		}
		// FIN : Incidencia : 0000814
	}

	private void respuestaError(JobExecutionContext jobExecutionContext,
			ColaBean solicitud, CtitsoapTradeResponse respuestaWS) throws IOException, Exception, OegamExcepcion {
		//Hay error, Evaluar error_tecnico o error_funcional 
		//mostrar lista de errores del webservice.
	
		String permisoTemporalCirculacion = respuestaWS.getLicense();
		/**Cambios Gestor de Documentos*/
		getUtilesProcesos().guardarPTC(solicitud, permisoTemporalCirculacion);
		
		CtitTradeError[] listadoErrores = respuestaWS.getErrorCodes();
		CtitTradeImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		String respuestaError = "";
		if(listadoErrores!=null) {
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}
		
		if(listadoImpedimentos!=null) {
			respuestaError += getImpedimentos(listadoImpedimentos);
		}		
		
		boolean recuperable = compruebaErroresRecuperables(listadoErrores);		
		if (!recuperable) {
			finalizarTransaccionConErrorNoRecuperable(solicitud, respuestaError,jobExecutionContext);
		} if (recuperable) {
			tratarRecuperable(jobExecutionContext, solicitud, respuestaError);
		}
	}
	
	private void respuestaNoTramitable(JobExecutionContext jobExecutionContext,
			ColaBean solicitud, CtitsoapTradeResponse respuestaWS) throws IOException, Exception, OegamExcepcion {
		//Cambiamos a Finalizado con error
		CtitTradeError[] listadoErrores = respuestaWS.getErrorCodes();
		CtitTradeImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		String permisoTemporalCirculacion = respuestaWS.getLicense();
		getUtilesProcesos().guardarPTC(solicitud, permisoTemporalCirculacion);
		String respuestaError = "";
		
		if(listadoErrores!=null) {
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}
		
		if(listadoImpedimentos!=null) {
			respuestaError += getImpedimentos(listadoImpedimentos);
		}
		
		// Incidencia 3815 Entrega compraventa (problemas en cambio de estado)	
		// Si es un TRADEctit  y es finalizado con error la respuesta , cambiar el estado del tramite a FINALIZADO CON ERROR.
		if (solicitud.getRespuesta().equals(ConstantesProcesos.FINALIZADO_CON_ERROR)) {
			finalizarTransaccionConError(jobExecutionContext, solicitud, respuestaError); 
		} else {
			finalizarTransaccionNoTramitable(jobExecutionContext, solicitud, respuestaError);
		}
	}

	private void respuestaTramitableIncidencias(JobExecutionContext jobExecutionContext, ColaBean solicitud,
			CtitsoapTradeResponse respuestaWS) throws IOException, Exception, OegamExcepcion {
		//Cambiamos a Finalizado con error
		CtitTradeError[] listadoErrores = respuestaWS.getErrorCodes();
		CtitTradeImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		CtitTradeAdvise[] listadoAvisos = respuestaWS.getAdviseCodes();
		
		String permisoTemporalCirculacion = respuestaWS.getLicense();
		getUtilesProcesos().guardarPTC(solicitud, permisoTemporalCirculacion);
		
		String respuestaError = "";
		
		if(listadoErrores!=null) {
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}
		
		if(listadoImpedimentos!=null){
			respuestaError += getImpedimentos(listadoImpedimentos);
		}
		
		if(listadoAvisos!=null){
			respuestaError += getAvisos(listadoAvisos);
		}
		finalizarTramiteConIncidencias(jobExecutionContext, solicitud, respuestaError);
	}
	
	private void respuestaOk(JobExecutionContext jobExecutionContext,
			ColaBean solicitud, CtitsoapTradeResponse respuestaWS) throws Exception,
						IOException, CambiarEstadoTramiteTraficoExcepcion, DescontarCreditosExcepcion, BorrarSolicitudExcepcion {		
		//Obtenemos el Permiso Temporal de Circulación si lo hubiera
		// Comentado. Trade no tiene PTC.
		//String permisoTemporalCirculacion = respuestaWS.getLicense();
		//getUtilesProcesos().guardarPTC(solicitud, permisoTemporalCirculacion);
		//Cambiamos el estado del trámite a Finalizado_telemáticamente
		
		//@author: Carlos García
		//Incidencia: 0001828
		// Para los trámites del que van bien, se coloca en el mensaje establecido por SEA en el ticket TPY-233-69562
		
		String mensaje = ConstantesMensajes.MENSAJE_CTIT_RESULTADO_OK;
		
		finalizarTransaccionCorrectoTransmision(jobExecutionContext, solicitud, mensaje);
		//Fin Incidencia: 0001828		
	}
	
	private boolean compruebaErroresRecuperables(CtitTradeError[] listadoErrores) throws TrataMensajeExcepcion {		
		boolean recuperable = true; 		
		for(CtitTradeError error:listadoErrores) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
			if (!recuperable) {
				break;
			}
		}
		return recuperable;
	}
	
	private void listarErroresCTIT(CtitTradeError[] listadoErrores){
		for (int i = 0; i < listadoErrores.length; i++){
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE +  ConstantesProcesos.CODIGO + listadoErrores[i].getKey()); 
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE +  ConstantesProcesos.DESCRIPCION +listadoErrores[i].getMessage());
		}
	}

	private void finalizarTramiteConIncidencias(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuesta) {
		log.info("finalizar tramite con incidencias");
		//Hacer la actualizacion de créditos incrementales
			BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
			beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(solicitud.getIdTramite());
			beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
			beanPQCambiarEstadoTramite.setP_RESPUESTA(respuesta); 

			ResultBean resultBeanCambiareEstado  = (ResultBean) getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite).get(ConstantesPQ.RESULTBEAN);
			log.info(resultBeanCambiareEstado.getMensaje()); 
			if (!resultBeanCambiareEstado.getError()){
				peticionCorrecta(jobExecutionContext); 	
				getModeloSolicitud().borrarSolicitud(solicitud.getIdEnvio(),respuesta);
			}
	}
	
	private String  getMensajeError(CtitTradeError[] listadoErrores) {
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
	
	private String getAvisos(CtitTradeAdvise[] listadoAvisos) {
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
	
	private String  getImpedimentos(CtitTradeImpediment[] listadoImpedimentos) {
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
		log.info("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT);
	}

	private void cambiaEstadoAError(ColaBean solicitud, TrataMensajeExcepcion trataMensajeExcepcion, JobExecutionContext jobExecutionContext){
		try{
			finalizarTransaccionConErrorNoRecuperable(solicitud, trataMensajeExcepcion.getMessage(),jobExecutionContext);
		}catch( BorrarSolicitudExcepcion e){
			log.error("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- Error al cambiar de estado: " + e);
		}catch(CambiarEstadoTramiteTraficoExcepcion ex){
			log.error("Proceso " + ConstantesProcesos.PROCESO_TRADECTIT + " -- Error al cambiar de estado: " + ex);
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

	public void setDgtSolicitudTransmision(
			DGTSolicitudTransmision dgtSolicitudTransmision) {
		this.dgtSolicitudTransmision = dgtSolicitudTransmision;
	}

}