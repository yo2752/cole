package colas.procesos;

import java.io.IOException;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.comun.BeanDatosFiscales;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.gescogroup.blackbox.ProfessionalProofError;
import com.gescogroup.blackbox.ProfessionalProofResponse;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import colas.procesos.utiles.UtilesProcesos;
import trafico.beans.JustificanteProfesional;
import trafico.modelo.ModeloDGTWS;
import trafico.modelo.ModeloJustificanteProfesional;
import trafico.transmision.justificantesPro.DGTSolicitudProfessionalProof;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.RespuestaDesconocidaWS;
import utilidades.web.excepciones.SinDatosWSExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TrataMensajeExcepcion;

public class ProcesoIssueProfessionalProof extends ProcesoBase {
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoIssueProfessionalProof.class);
	private Integer hiloActivo;

	private ModeloDGTWS modeloDGTWS;
	private ModeloJustificanteProfesional modeloJustificanteProfesional;
	private UtilesProcesos utilesProcesos;
	
	private static final String JUSTIFICATES_DIAS_VALIDEZ  = "webservice.justificantesPro.diasValidez";
	private static final BigDecimal DIAS_VALIDEZ = new BigDecimal(60);
	
	@Autowired
	private ServicioMensajesProcesos servicioMensajesProcesos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		// Ya se hace en el padre ProcesoBase
		// Forzamos la inyección de dependencias de las clases anotadas como 'Autowired'
		//SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
				
		hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
		ColaBean solicitud = new ColaBean();
		try {
			log.info("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Buscando Solicitud");
			solicitud = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF, hiloActivo.toString());			
			BeanDatosFiscales datosFiscales = getModeloDGTWS().datosMateEitv(solicitud.getIdTramite()); 
//			construirxmlSolicitudEitv(solicitud,datosCardMateBean);
			log.info("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Solicitud " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " encontrada, llamando a WS");
			llamadaWS(jobExecutionContext, solicitud,datosFiscales);
		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion){
			sinPeticiones(jobExecutionContext); 
			log.info(sinSolicitudesExcepcion.getMensajeError1());
		} catch (SinDatosWSExcepcion sinDatosWSExcepcion){
			tratarRecuperable(jobExecutionContext, solicitud,ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_AL_WEBSERVICE);
			log.logarOegamExcepcion(sinDatosWSExcepcion.getMensajeError1(), sinDatosWSExcepcion);
		} catch (java.io.FileNotFoundException fileNotFound) {
			log.error(ConstantesProcesos.XML_PARA_JUSTIFICANTE_PROFESIONAL_NO_ENCONTRADO, fileNotFound); 
			marcarSolicitudConErrorServicio(solicitud, fileNotFound.getMessage(), jobExecutionContext); 
		} //excepciones dentro del webservice
		catch (java.net.ConnectException eCon){
			log.error(ConstantesProcesos.TIMEOUT, eCon); 
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT); 
		} catch (java.net.SocketTimeoutException eCon){
			log.error(ConstantesProcesos.TIMEOUT, eCon); 
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT); 
		}		
		/*catch (DescontarCreditosExcepcion descontarCreditosExcepcion){
			try {
				UtilesProcesos.finalizarTransaccionConError(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);
			} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
				marcarSolicitudConErrorServicio(solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1(), jobExecutionContext);
			} catch (BorrarSolicitudExcepcion e) {
				marcarSolicitudConErrorServicio(solicitud,e.getMensajeError1(),jobExecutionContext);
			}
		}*/
		catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion){
			tratarRecuperable(jobExecutionContext, solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1()); 
		} catch (TrataMensajeExcepcion trataMensajeExcepcion) {
			tratarRecuperable(jobExecutionContext, solicitud, trataMensajeExcepcion.getMensajeError1());
			cambiaEstadoAError(solicitud, trataMensajeExcepcion, jobExecutionContext);
		} catch(RespuestaDesconocidaWS resWsEx) {
			tratarRecuperable(jobExecutionContext, solicitud, 
					ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR +ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
		} catch (OegamExcepcion oegamEx){
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + oegamEx.getMensajeError1()); 
			log.logarOegamExcepcion(oegamEx.getMensajeError1(), oegamEx);
		} catch (Exception e){
			log.error("Excepcion IssuePro", e); 
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + ":" + e.getMessage()); 
		} catch (Throwable e){
			log.error("Throwable IssuePro", e); 
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ ":" + e.getMessage()); 
		}	
	}	
	
	private void llamadaWS(JobExecutionContext jobExecutionContext,	ColaBean solicitud, BeanDatosFiscales datosFiscales) 
			throws CambiarEstadoTramiteTraficoExcepcion, OegamExcepcion,TrataMensajeExcepcion,Exception {

		//-----------------PASAMOS A INVOCAR AL WEBSERVICE-------------
		try {
			log.info("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Procesando petición");
			/** Gestor de Documentos **/
			String peticionXML = getUtilesProcesos().recogerXmlIssueProfessionalProof(solicitud);
			log.info("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Peticion Procesada");
			ProfessionalProofResponse respuestaWS = DGTSolicitudProfessionalProof.presentarDGTIssueProfessionalProof(peticionXML,datosFiscales,solicitud.getIdTramite());
			log.info("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Fin de llamada a WS");

			boolean error = compruebaError(respuestaWS);

			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
			try {
				if(error) {
					boolean errorExcepcion = false;
					String respuesta = null;
					if(respuestaWS.getErrors() != null && respuestaWS.getErrors().length > 0){
						for(ProfessionalProofError pproofError : respuestaWS.getErrors()){
							respuesta = pproofError.getCode() + " - " + pproofError.getMessage() + "; ";
							if("PROOFI00115".equals(pproofError.getCode()) || "PROOFI00A04".equals(pproofError.getCode())){
								errorExcepcion = true;
							}
						}
						solicitud.setRespuesta(respuesta);						
					} else {						
						solicitud.setRespuesta(ConstantesProcesos.EJECUCION_NO_CORRECTA);
					}	
					log.info("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Error Recibido: " + respuesta);
					
					//TODO Habria que averiguar cuales son exactamente los codigos de error que lanza el WS no contemplados
					//Mantis 0013025 
					if(solicitud.getRespuesta().contains("generico") || solicitud.getRespuesta().contains("timeout") || errorExcepcion){
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF);
						//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta());
					}else{
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF);
						//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					}
					//Fin Mantis 0013025
					
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
				} else {
					log.info("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Respuesta recibida");
					solicitud.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, null);
					log.info("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Proceso ejecutado correctamente");
				}
			} catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.ISSUEPROPROOF.getNombreEnum());
			}
			// FIN : Incidencia : 0000814

			//MANEJAMOS LA RESPUESTA DEL WS
			if(error) {
				respuestaError(jobExecutionContext, solicitud, respuestaWS);
			} else {
				respuestaOk(jobExecutionContext, solicitud, respuestaWS,peticionXML);
			}
			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
		} catch(Exception ex){
			try{
				String error = null;
				if(ex.getMessage() == null || ex.getMessage().equals("")){
					error = ex.toString();
				}else{
					error = ex.toString() + " " + ex.getMessage();
				}
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF);
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error);
			}catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.ISSUEPROPROOF.getNombreEnum());
			}
			throw ex;
		} catch(OegamExcepcion ex){
			try{
				String error = null;
				if((ex.getMessage() == null || ex.getMessage().equals("")) && (ex.getMensajeError1() == null || ex.getMensajeError1().equals(""))){
					error = ex.toString();
				}else{
					error = ex.toString() + " " + ex.getMessage() + " " + ex.getMensajeError1();
				}
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF);
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error);
			}catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.ISSUEPROPROOF.getNombreEnum());
			}
			throw ex;
		}
		// FIN : Incidencia : 0000814
	}

	private boolean compruebaError(ProfessionalProofResponse respuestaWS) {
		ProfessionalProofError[] bbErrores = respuestaWS.getErrors();

		if (null == bbErrores || bbErrores.length == 0) {
			log.info("LOG__ISSUE_PROF: RESPUESTA SIN ERRORES");
			return false;
		} else {
			log.error("respuesta con errores");
			return true;
		}
	}

	private void respuestaOk(JobExecutionContext jobExecutionContext,ColaBean solicitud, ProfessionalProofResponse respuestaWS,
		String peticionXML) throws IOException, Exception, OegamExcepcion {
		finalizarTransaccionIssueProfessionalProofOk(jobExecutionContext, solicitud,respuestaWS); 
	}

	private void respuestaError(JobExecutionContext jobExecutionContext, ColaBean solicitud,
			ProfessionalProofResponse respuestaWS) throws IOException, Exception, CambiarEstadoTramiteTraficoExcepcion, TrataMensajeExcepcion, BorrarSolicitudExcepcion {

		//Hay error, Evaluar error_tecnico o error_funcional 
		//mostrar lista de errores del webservice.
		ProfessionalProofError[] listadoErrores = respuestaWS.getErrors();
		String respuestaError = "";
		if(listadoErrores!=null){
			listarErroresEitv(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}
		
		boolean recuperable = compruebaErroresRecuperables(listadoErrores);
		if (!recuperable) {
			finalizarTransaccionConAccionNoRecuperable(solicitud, respuestaError,jobExecutionContext);
		}
		if (recuperable) {
			tratarRecuperable(jobExecutionContext, solicitud, respuestaError);
		}
	}

	private String  getMensajeError(ProfessionalProofError[] listadoErrores) {
		
		StringBuffer mensajeError = new StringBuffer(); 
		for (int i = 0; i < listadoErrores.length; i++) {
			if (listadoErrores[i].getMessage()!=null){
				mensajeError.append(listadoErrores[i].getCode());
				mensajeError.append(" - " );
				String error = listadoErrores[i].getMessage().replaceAll("'", "");
				error = error.replaceAll("\"", ""); 
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
		}
		return mensajeError.toString(); 
	}

	private boolean compruebaErroresRecuperables(ProfessionalProofError[] listadoErrores) throws TrataMensajeExcepcion {	
		boolean recuperable = true; 
		
		for(ProfessionalProofError error:listadoErrores) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getCode(), error.getMessage());
		}
		return recuperable;
	}

	private void listarErroresEitv(ProfessionalProofError[] listadoErrores){
		for (int i = 0; i < listadoErrores.length; i++){
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.CODIGO + listadoErrores[i].getCode()); 
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.DESCRIPCION +listadoErrores[i].getMessage());
		}
	}

	@Override
	public void cambioNumeroInstancias(int numero) { 
		log.info("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF);
	}

	private void cambiaEstadoAError(ColaBean solicitud, TrataMensajeExcepcion trataMensajeExcepcion, JobExecutionContext jobExecutionContext){
		try {
			finalizarTransaccionConErrorNoRecuperable(solicitud, trataMensajeExcepcion.getMessage(),jobExecutionContext);
		} catch( BorrarSolicitudExcepcion e) {
			log.error("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Error al cambiar de estado: " + e);
		} catch(CambiarEstadoTramiteTraficoExcepcion ex) {
			log.error("Proceso " + ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + " -- Error al cambiar de estado: " + ex);
		}
	}


	private void finalizarTransaccionIssueProfessionalProofOk(
			JobExecutionContext jobExecutionContext, ColaBean solicitud, ProfessionalProofResponse respuestaWS) throws IOException, Exception, OegamExcepcion {
		
		log.info("professioal proofpdf " + respuestaWS.getProfessionalProofPdf());
		log.info("proof number" + respuestaWS.getProofNumber()); 
		
		String diasValidez = gestorPropiedades.valorPropertie(JUSTIFICATES_DIAS_VALIDEZ);
		
		/**Cambio Gestor ficheros**/
		getUtilesProcesos().guardarPdfJustificante(solicitud, respuestaWS);
		
		JustificanteProfesional justificanteProf = new JustificanteProfesional();
		justificanteProf.setId_justificante(new BigDecimal(respuestaWS.getProofNumber()));
		justificanteProf.setNum_expediente(solicitud.getIdTramite());
		justificanteProf.setDias_validez(diasValidez!=null && !diasValidez.equals("")? new BigDecimal(diasValidez):DIAS_VALIDEZ);
		justificanteProf.setDocumentos("documentos");
		justificanteProf.setFecha_inicio(utilesFecha.getFechaActual());
		getModeloJustificanteProfesional().crear(justificanteProf, solicitud.getIdUsuario()); 
		
		peticionCorrecta(jobExecutionContext); 	
//		getModeloCreditosTrafico().descontarCreditosExcep(getModeloTrafico().obtenerIdContratoTramite(solicitud.getIdTramite()).toString(),Utiles.convertirIntegerABigDecimal(1), solicitud.getTipoTramite(),solicitud.getIdUsuario());
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(),ConstantesProcesos.OK);
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloJustificanteProfesional getModeloJustificanteProfesional() {
		if (modeloJustificanteProfesional == null) {
			modeloJustificanteProfesional = new ModeloJustificanteProfesional();
		}
		return modeloJustificanteProfesional;
	}

	public void setModeloJustificanteProfesional(
			ModeloJustificanteProfesional modeloJustificanteProfesional) {
		this.modeloJustificanteProfesional = modeloJustificanteProfesional;
	}

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
}