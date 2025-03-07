package colas.procesos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.dgt.DGTTransmision;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCTITDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.gescogroup.blackbox.CtitFullAdvise;
import com.gescogroup.blackbox.CtitFullError;
import com.gescogroup.blackbox.CtitFullImpediment;
import com.gescogroup.blackbox.CtitsoapFullResponse;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import colas.procesos.utiles.UtilesProcesos;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.beans.DatosCTITBean;
import trafico.beans.schemas.generated.transTelematica.SolicitudRegistroEntrada;
import trafico.beans.schemas.generated.transTelematica.TipoConsentimiento;
import trafico.modelo.ModeloDGTWS;
import trafico.modelo.ModeloTransmision;
import trafico.transmision.ctit.DGTSolicitudTransmision;
import trafico.utiles.XmlTransTelematicaFactory;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.enumerados.NPasos;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;
import utilidades.web.excepciones.RespuestaDesconocidaWS;
import utilidades.web.excepciones.SinDatosWSExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TrataMensajeExcepcion;

public class ProcesoFullCTIT extends AbstractProcesoCTIT {
	private static final String SEGUNDO_ENVIO = "segundoEnvio";
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoFullCTIT.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;

	private Integer hiloActivo;

	private ModeloDGTWS modeloDGTWS;
	private ModeloTransmision modeloTransmision;
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
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		Boolean esLaborable = true;
		Boolean forzarEjecucion = false;
		// Comprueba si el dia es laborable y no es festivo nacional
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

		if (esLaborable || forzarEjecucion) {
			// Forzamos la inyección de dependencias de las clases anotadas como 'Autowired'
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

			hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
			log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Buscando Solicitud");
			ColaBean solicitud = new ColaBean();
			DatosCTITBean datosCTITBean = new DatosCTITBean();
			DatosCTITDto datosCTITDto = new DatosCTITDto();
			try {
				solicitud = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_FULLCTIT, hiloActivo.toString());

				String deshabilitarPQ = gestorPropiedades.valorPropertie("deshabilitar.pq.dgtws");

				if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
					datosCTITDto = servicioTramiteTraficoTransmision.datosCTIT(solicitud.getIdTramite());
				} else {
					datosCTITBean = getModeloDGTWS().datosCTIT(solicitud.getIdTramite());
				}
				log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Solicitud " + ConstantesProcesos.PROCESO_FULLCTIT + " encontrada, llamando a WS");
				llamadaWS(jobExecutionContext, solicitud,datosCTITBean, datosCTITDto, deshabilitarPQ);
			} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion){
				sinPeticiones(jobExecutionContext);
				log.info(sinSolicitudesExcepcion.getMensajeError1());
			} catch (SinDatosWSExcepcion sinDatosWSExcepcion) {
				tratarRecuperable(jobExecutionContext, solicitud,ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_AL_WEBSERVICE);
				log.logarOegamExcepcion(sinDatosWSExcepcion.getMensajeError1(), sinDatosWSExcepcion);
			} catch (java.io.FileNotFoundException fileNotFound) {
				log.error(ConstantesProcesos.XML_PARA_JUSTIFICANTE_PROFESIONAL_NO_ENCONTRADO, fileNotFound);
				marcarSolicitudConErrorServicio(solicitud, fileNotFound.getMessage(), jobExecutionContext);
			} // Excepciones dentro del webservice
			catch (java.net.ConnectException eCon) {
				log.error(ConstantesProcesos.TIMEOUT, eCon);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT);
			} catch (java.net.SocketTimeoutException eCon) {
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
			} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
				tratarRecuperable(jobExecutionContext, solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1());
			} catch (TrataMensajeExcepcion trataMensajeExcepcion) {
				tratarRecuperable(jobExecutionContext, solicitud, trataMensajeExcepcion.getMensajeError1());
				cambiaEstadoAError(solicitud, trataMensajeExcepcion, jobExecutionContext);
			} catch(RespuestaDesconocidaWS resWsEx) {
				tratarRecuperable(jobExecutionContext, solicitud,
						ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR +ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
			} catch (OegamExcepcion oegamEx) {
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR);
				log.logarOegamExcepcion(oegamEx.getMensajeError1(), oegamEx);
			} catch (Exception e) {
				log.error("Excepcion CTIT", e);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ e.getMessage());
			} catch (Throwable e) {
				log.error("Throwable CTIT", e);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ e.getMessage());
			}
		}else{
			peticionCorrecta(jobExecutionContext);
		}
	}

	private void llamadaWS(JobExecutionContext jobExecutionContext,	ColaBean solicitud, DatosCTITBean datosCTITBean, DatosCTITDto datosCTITDto, String deshabilitarPQ)
			throws IOException, Exception, CambiarEstadoTramiteTraficoExcepcion, OegamExcepcion, TrataMensajeExcepcion, FileNotFoundException {
		CtitsoapFullResponse respuestaWS = new CtitsoapFullResponse();
		String deshabilitarEjecucionDao = gestorPropiedades.valorPropertie("deshabilitar.ejecucion.dao");
		//-----------------PASAMOS A INVOCAR AL WEBSERVICE-------------
		try {
			String peticionXML = getUtilesProcesos().recogerXmlTransmision(solicitud);

			log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Procesando petición");
			/**Cambio Gestor de Documentos**/

			if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
				respuestaWS = dgtTransmision.fullCTITTransmision(peticionXML, datosCTITDto);
			} else {
				respuestaWS = getDgtSolicitudTransmision().fullCTITTransmision(peticionXML, datosCTITBean);
			}

			log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Peticion Procesada");

			// 01-10-2012. Ricardo Rodríguez. Incidencia : 0000814
			try {
				if(respuestaWS != null && respuestaWS.getResult() != null && respuestaWS.getResult().equals(ConstantesProcesos.OK)) {
					log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Respuesta recibida con resultado OK");
					solicitud.setRespuesta(respuestaWS.getResult());

					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_FULLCTIT);
					}
					log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Proceso ejecutado correctamente");
				} else {
					log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Respuesta recibida con resultado NO OK. Iniciando tratamiento respuesta");
					String respuesta = null;
					if (respuestaWS != null && respuestaWS.getErrorCodes() != null && respuestaWS.getErrorCodes().length > 0) {
						for(CtitFullError ctitFullError : respuestaWS.getErrorCodes()){
							respuesta = ctitFullError.getKey() + " - " + ctitFullError.getMessage() + "; ";
						}
						solicitud.setRespuesta(respuesta);
						log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Respuesta recibida con resultado NO OK: ->" + solicitud.getRespuesta());
					} else if(respuestaWS != null){
						solicitud.setRespuesta(respuestaWS.getResult());
						log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Respuesta recibida con resultado NO OK y respuestaWS no vacia: ->" + solicitud.getRespuesta());
					} else {
						log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Respuesta no controlada");
					}

					//Mantis 0013025 
					if (solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE")) {
						log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Actualizo a Excepcion ultima ejecucion");
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud,
									ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta(),
									ConstantesProcesos.PROCESO_FULLCTIT);
						}
						log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- se ha producido una excepción ");
					} else {
						log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Actualizo a No Correcta ultima ejecucion");
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud,
									ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta(),
									ConstantesProcesos.PROCESO_FULLCTIT);
						}
						log.error("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- El Proceso Devolvió error ");
					}
					//Fin Mantis 0013025
				}
			}catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.FULLCTIT.getNombreEnum());
			}
			// FIN : Incidencia : 0000814

			// Se guarda el informe si viene
			boolean existeInforme = false;
			if (respuestaWS != null && respuestaWS.getReport() != null && !respuestaWS.getReport().isEmpty()) {
				try {
					existeInforme = getUtilesProcesos().guardarInforme(solicitud, respuestaWS.getReport());
				} catch (OegamExcepcion e) {
					log.error("No se pudo guardar el informe, no se cumplen requisitos", e);
				} catch (Exception e) {
					log.error("No se pudo guardar el informe, error generico", e);
				}
			}

			//MANEJAMOS LA RESPUESTA DEL WS
			if (respuestaWS.getResult() != null) {
				log.info("RESPUESTA DEL WS :" + respuestaWS.getResult());
			}
			if (ConstantesProcesos.OK.equals(respuestaWS.getResult())) {
				respuestaOk(jobExecutionContext, solicitud, respuestaWS,datosCTITBean,datosCTITDto, deshabilitarPQ);
			} else if (ConstantesProcesos.TRAMITABLE_CON_INCIDENCIAS.equals(respuestaWS.getResult())) {
				log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT+ " -- "
						+ "Respuesta recibida con resultado TRAMITABLE CON INCIDENCIAS");
				respuestaIncidencias(jobExecutionContext, solicitud,respuestaWS);
			} else if (ConstantesProcesos.NO_TRAMITABLE.equals(respuestaWS.getResult())) {
				log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- "
						+ "Respuesta recibida con resultado NO TRAMITABLE");
				respuestaNoTramitable(jobExecutionContext, solicitud,respuestaWS,existeInforme);
			} else if (ConstantesProcesos.ERROR.equals(respuestaWS.getResult())) {
				log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- "
						+ "Respuesta recibida con resultado ERROR");
				respuestaError(jobExecutionContext, solicitud, respuestaWS);
			} else {
				if (respuestaWS.getErrorCodes()!=null){
					log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- "
							+ "ERROR - Respuesta con codigos de error");
					respuestaError(jobExecutionContext, solicitud, respuestaWS);
				} else {
					log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- "
							+ "ERROR - Respuesta sin codigos de error");
					throw new RespuestaDesconocidaWS(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA + respuestaWS.getResult());
				}
			}
			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
		}catch(Exception ex){
			try{
				log.error("Ocurrio un error no controlado Exception en el proceso" + ConstantesProcesos.PROCESO_FULLCTIT, ex);
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
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud,
								ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error : "sin mensaje",
								ConstantesProcesos.PROCESO_FULLCTIT);
					}
				} else if (solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE")) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta(),ConstantesProcesos.PROCESO_FULLCTIT);
					}
				} else if (!ConstantesProcesos.OK.equals(solicitud.getRespuesta())) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_FULLCTIT);	
					}
					log.error("Proceso FullCTIT: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);
				}
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.FULLCTIT.getNombreEnum(), e);
			}
			throw ex;
		} catch (OegamExcepcion ex) {
			try {
				log.error("Ocurrio un error no controlado OegamExcepcionen en el proceso" + ConstantesProcesos.PROCESO_FULLCTIT, ex);
				String error = null;
				if ((ex.getMessage() == null || ex.getMessage().equals(""))
						&& (ex.getMensajeError1() == null || ex.getMensajeError1().equals(""))) {
					error = ex.toString();
				} else {
					error = ex.toString() + " " + ex.getMessage() + " " + ex.getMensajeError1();
				}

				if (solicitud.getRespuesta() == null) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud,
								ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error : "sin mensaje",
								ConstantesProcesos.PROCESO_FULLCTIT);
					}

				} else if (solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || solicitud.getRespuesta().contains("CTITE")) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud,
								ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta(),
								ConstantesProcesos.PROCESO_FULLCTIT);
					}

				} else if (!ConstantesProcesos.OK.equals(solicitud.getRespuesta())) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud,
								ConstantesProcesos.EJECUCION_NO_CORRECTA, solicitud.getRespuesta(),
								ConstantesProcesos.PROCESO_FULLCTIT);
					}

					log.error("Proceso FullCTIT: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);
				}
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.FULLCTIT.getNombreEnum(), e);
			}
			throw ex;
		}
		// FIN : Incidencia : 0000814
	}

	private void respuestaOk(JobExecutionContext jobExecutionContext, ColaBean solicitud, CtitsoapFullResponse respuestaWS,
			DatosCTITBean datosCTITBean, DatosCTITDto datosCTITDto, String deshabilitarPQ) throws Exception,IOException, OegamExcepcion{

		String pasos = "";
		TipoConsentimiento consentimiento = null;
		if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
			pasos = datosCTITDto.getPasos();
			consentimiento = datosCTITDto.getConsentimiento();
		} else {
			pasos = datosCTITBean.getPasos();
			consentimiento = datosCTITBean.getConsentimiento();
		}

		// Si npasos es 1, se recupera la informacion y fin.
		if (NPasos.Uno.getValorEnum().equals(pasos)) {
			try {
				getUtilesProcesos().guardarPTC(solicitud, respuestaWS.getLicense());
			} catch (OegamExcepcion e1) {
				log.error("No se pudo guardar el permiso temporal de circulación: " + e1.getMessage());
			}

			//@author: Carlos García
			//Incidencia: 0001828
			// Para los trámites del que van bien, se coloca en el mensaje establecido por SEA en el ticket TPY-233-69562
			String mensaje ="";
			mensaje = ConstantesMensajes.MENSAJE_CTIT_RESULTADO_OK;

			finalizarTransaccionCorrectoTransmision(jobExecutionContext, solicitud, mensaje);
			//Fin Incidencia: 0001828
		}
		// Si n pasos es 2, enviamos de nuevo el trámite cambiándole la tasa e incluyendole el consentimiento original.
		if (NPasos.Dos.getValorEnum().equals(pasos)) {
			// Si no hay consentimiento, dejamos el trámite finalizado con incidencias e incluimos en la respuesta
			if (TipoConsentimiento.NO.equals(consentimiento)) {
				finalizarTransaccionConIncidenciasFullCTIT(jobExecutionContext, solicitud, "Es necesario el consentimiento");
			} else {
				segundoEnvio(jobExecutionContext, solicitud, respuestaWS, datosCTITBean, datosCTITDto, deshabilitarPQ);
			}
		}
	}

	private void respuestaError(JobExecutionContext jobExecutionContext, ColaBean solicitud,
			CtitsoapFullResponse respuestaWS) throws IOException, Exception, TrataMensajeExcepcion,
			CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		//Hay error, Evaluar error_tecnico o error_funcional
		//mostrar lista de errores del webservice.
		String respuestaError = "";

		CtitFullError[] listadoErrores = respuestaWS.getErrorCodes();
		if (listadoErrores != null) {
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}

		CtitFullImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		if (listadoImpedimentos != null) {
			if (!"".equals(respuestaError))
				respuestaError += " - ";
			respuestaError += getImpedimentos(listadoImpedimentos);
		}

		boolean recuperable = compruebaErroresRecuperables(listadoErrores);
		if (!recuperable) {
			try {
				/**Cambios Gestor Ficheros**/
				getUtilesProcesos().guardarPTC(solicitud, respuestaWS.getLicense());
			} catch (OegamExcepcion e) {
				log.error("No se pudo guardar el permiso temporal de circulación: " + e.getMessage());
			}
			finalizarTransaccionConErrorNoRecuperable(solicitud, respuestaError,jobExecutionContext);
		}
		if (recuperable) {
			tratarRecuperable(jobExecutionContext, solicitud, respuestaError);
		}
	}

	private void respuestaNoTramitable(JobExecutionContext jobExecutionContext,	ColaBean solicitud, CtitsoapFullResponse respuestaWS,
			boolean existeInforme) throws IOException, Exception, OegamExcepcion {
		String respuestaError = "";

		CtitFullError[] listadoErrores = respuestaWS.getErrorCodes();
		if (listadoErrores != null) { 
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}

		CtitFullImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		if(listadoImpedimentos!=null) { 
			if (!"".equals(respuestaError)) respuestaError += " - ";
			respuestaError += getImpedimentos(listadoImpedimentos);
		}

		CtitFullAdvise[] listadoAvisos = respuestaWS.getAdviseCodes();
		if (listadoAvisos!=null) {
			if (!"".equals(respuestaError)) respuestaError += Claves.BR;
			respuestaError += getAvisos(listadoAvisos);
		}
		getUtilesProcesos().guardarPTC(solicitud, respuestaWS.getLicense());
		if (existeInforme) {
			finalizarTransaccionInformeTelematico(jobExecutionContext, solicitud, respuestaError);
		} else {
			finalizarTransaccionConError(jobExecutionContext, solicitud, respuestaError);
		}
	}

	private void respuestaIncidencias(JobExecutionContext jobExecutionContext, ColaBean solicitud,
			CtitsoapFullResponse respuestaWS)
			throws Exception, IOException, CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		//Cambiamos a Finalizado con error
		String respuestaError = "";

		CtitFullError[] listadoErrores = respuestaWS.getErrorCodes();
		if (listadoErrores != null) {
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}

		CtitFullImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		if (listadoImpedimentos != null) {
			if (!"".equals(respuestaError)) respuestaError += " - ";
			respuestaError += getImpedimentos(listadoImpedimentos);
		}

		CtitFullAdvise[] listadoAvisos = respuestaWS.getAdviseCodes();
		if (listadoAvisos != null) {
			if (!"".equals(respuestaError)) respuestaError += Claves.BR;
			respuestaError += getAvisos(listadoAvisos);
		}

		try {
			getUtilesProcesos().guardarPTC(solicitud, respuestaWS.getLicense());
		} catch (OegamExcepcion e) {
			log.error("No se pudo guardar el permiso temporal de circulación: " + e.getMessage());
		}
		finalizarTransaccionConIncidenciasFullCTIT(jobExecutionContext, solicitud, respuestaError);
	}

	private void segundoEnvio(JobExecutionContext jobExecutionContext, ColaBean solicitud, CtitsoapFullResponse respuestaWS,
			DatosCTITBean datosCTITBean, DatosCTITDto datosCTITDto, String deshabilitarPQ) throws Exception, IOException, OegamExcepcion {
		// En teoría si el envío va en dos pasos, lo primero que hay en este paso 2, es guardar el PTC.
		//En el paso 1 se guardaría el informe.
		/**Cambios Gestor de Documentos*/
		getUtilesProcesos().guardarPTC(solicitud, respuestaWS.getLicense());

		// Recogemos el XML y lo pasamos a clases java para borrar las firmas
		SolicitudRegistroEntrada solicitudRegistroEntrada = getUtilesProcesos().rehacerSolicitudRegistroEntradaFullCTIT(solicitud, datosCTITBean);

		String numColegiado = "";
		if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
			numColegiado = datosCTITDto.getNumColegiado();
		} else {
			numColegiado = datosCTITBean.getNumColegiado();
		}

		ResultBean resultadoFirmasBean =
			getModeloTransmision().anhadirFirmasTransTelematicaHSM(solicitudRegistroEntrada, numColegiado);

		if (!resultadoFirmasBean.getError()) {
			/** Cambio en Gestor de Documento**/
			String tipo = ConstantesGestorFicheros.CTIT;
			String subTipo = ConstantesGestorFicheros.ENVIO;
			BigDecimal numExpediente = solicitud.getIdTramite();
			String extension = ConstantesGestorFicheros.EXTENSION_XML;
			byte[] fichero = resultadoFirmasBean.getMensaje().getBytes();
			String nombreFichero = ConstantesGestorFicheros.NOMBRE_CTIT + String.valueOf(numExpediente);
			Fecha fechaTramite = Utilidades.transformExpedienteFecha(numExpediente);
			File ficheroFisico = gestorDocumentos.guardarFichero(tipo, subTipo, fechaTramite, nombreFichero, extension, fichero);
			String nombreFich = ficheroFisico.getPath();

			getUtilesProcesos().recogerXmlTransmision(solicitud);
			// Cambiamos el nombre al fichero para el segundo envío.
			solicitudRegistroEntrada = (SolicitudRegistroEntrada) resultadoFirmasBean.getAttachment(Claves.SOLICITUD_REGISTRO_ENTRADA);
			new XmlTransTelematicaFactory().grabarEnFicheroUTF8(solicitudRegistroEntrada, true, nombreFich, Claves.ENCODING_UTF8);
//			validarConXsdTransmision(rutaFicheroSegundoEnvio);
			solicitud.setXmlEnviar(SEGUNDO_ENVIO+solicitud.getXmlEnviar()); // Cambiamos el nombre a la solicitud para buscar el fichero.
		}
		// Cambiamos el número de pasos para que el siguiente envío no genere ninguno más.

		if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
			datosCTITDto.setPasos(NPasos.Uno.getValorEnum());
		} else {
			datosCTITBean.setPasos(NPasos.Uno.getValorEnum());
		}

		// Ejecutamos de nuevo el método general de comunicación con el ws con los nuevos datos pero sin leer de la cola.
		llamadaWS(jobExecutionContext, solicitud, datosCTITBean, datosCTITDto, deshabilitarPQ);
	}

	private boolean compruebaErroresRecuperables(CtitFullError[] listadoErrores) throws TrataMensajeExcepcion {
		boolean recuperable = true;
		if (listadoErrores != null && listadoErrores.length > 0) {
			for (CtitFullError error : listadoErrores) {
				recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
				if (!recuperable) {
					break;
				}
			}
		} else {
			return false;
		}
		return recuperable;
	}

	private void listarErroresCTIT(CtitFullError[] listadoErrores) {
		for (int i = 0; i < listadoErrores.length; i++) {
			log.error(" Error de webservice codigo: " + listadoErrores[i].getKey());
			log.error(" Error de webservice descripcion: " +listadoErrores[i].getMessage());
		}
	}

	private void finalizarTransaccionConIncidenciasFullCTIT(JobExecutionContext jobExecutionContext, ColaBean cola,
			String respuesta) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		//Hacer la actualizacion de créditos incrementales
		log.info("finalizar tramite con incidencias");
		getModeloTrafico().cambiarEstadoTramite(respuesta, EstadoTramiteTrafico.Finalizado_Con_Error, cola.getIdTramite(), cola.getIdUsuario());
		peticionCorrecta(jobExecutionContext);
		getModeloSolicitud().borrarSolicitudExcep(cola.getIdEnvio(),respuesta);
	}

	private String getMensajeError(CtitFullError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			if (i > 0)
				mensajeError.append(" - ");
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - " );
			String error = utiles.quitarCaracteresExtranios(listadoErrores[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length()/80); tam++) {
					if (tam != Math.floor(error.length()/80)) {
						resAux += error.substring(80*tam, 80*tam+80)+Claves.BR;
					} else {
						resAux += error.substring(80*tam)+Claves.BR;
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		return mensajeError.toString();
	}
	
	private String getAvisos(CtitFullAdvise[] listadoAvisos) {
		StringBuffer aviso = new StringBuffer();
		for (int i = 0; i < listadoAvisos.length; i++) {
			if (i > 0)
				aviso.append(" - ");
			aviso.append(listadoAvisos[i].getKey());
			aviso.append(" - " );
			String error = utiles.quitarCaracteresExtranios(listadoAvisos[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length()/80); tam++) {
					if (tam != Math.floor(error.length()/80)){
						resAux += error.substring(80*tam, 80*tam+80)+Claves.BR;
					} else {
						resAux += error.substring(80*tam)+Claves.BR;
					}
				}
				error = resAux;
			}
			aviso.append(error);
		}
		return aviso.toString();
	}

	private String getImpedimentos(CtitFullImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer();
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			if (i > 0)
				impedimento.append(" - ");
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - " );
			String error = utiles.quitarCaracteresExtranios(listadoImpedimentos[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80*tam, 80*tam+80)+Claves.BR;
					} else {
						resAux += error.substring(80*tam)+Claves.BR;
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
		log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT);
	}

	private void cambiaEstadoAError(ColaBean solicitud, TrataMensajeExcepcion trataMensajeExcepcion, JobExecutionContext jobExecutionContext) {
		try {
			finalizarTransaccionConErrorNoRecuperable(solicitud, trataMensajeExcepcion.getMessage(),jobExecutionContext);
		} catch (BorrarSolicitudExcepcion e) {
			log.error("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Error al cambiar de estado: " + e);
		} catch (CambiarEstadoTramiteTraficoExcepcion ex) {
			log.error("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Error al cambiar de estado: " + ex);
		}
	}

	private void finalizarTransaccionInformeTelematico(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuesta)
	throws DescontarCreditosExcepcion, CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		//Hacer la actualizacion de créditos incrementales
		log.info("finalizar Transaccion informe telematico");

		getModeloCreditosTrafico().descontarCreditosExcep(getModeloTrafico().obtenerIdContratoTramite(solicitud.getIdTramite()),
				utiles.convertirIntegerABigDecimal(1),TipoTramiteTrafico.Transmision.getValorEnum(),solicitud.getIdUsuario());

		getModeloTrafico().cambiarEstadoTramite(respuesta, EstadoTramiteTrafico.Informe_Telematico, solicitud.getIdTramite(), solicitud.getIdUsuario());
		peticionCorrecta(jobExecutionContext);
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), respuesta);

		//Anoto gasto.
		try {
			ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);
			if (servicioCreditoFacturado != null) {
				ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(solicitud.getIdUsuario().toString());
				servicioCreditoFacturado.anotarGasto(Integer.valueOf(1), ConceptoCreditoFacturado.INF,
						contrato.getId().getIdContrato(), TipoTramiteTrafico.Solicitud.getValorEnum(),
						solicitud.getIdTramite().toString());
			}
		} catch (Exception e) {
			log.error("Error anotando el gasto (historico)");
		}
	}

	/* *********************************************************** */
	/* MODELOS *************************************************** */
	/* *********************************************************** */

	public ModeloDGTWS getModeloDGTWS() {
		if (modeloDGTWS == null) {
			modeloDGTWS = new ModeloDGTWS();
		}
		return modeloDGTWS;
	}

	public void setModeloDGTWS(ModeloDGTWS modeloDGTWS) {
		this.modeloDGTWS = modeloDGTWS;
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