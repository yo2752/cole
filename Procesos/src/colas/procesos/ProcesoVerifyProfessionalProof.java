package colas.procesos;

import java.io.IOException;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.gescogroup.blackbox.ProfessionalProofError;
import com.gescogroup.blackbox.ProfessionalProofVerificationResponse;

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
import utilidades.web.excepciones.CrearJustificanteExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;
import utilidades.web.excepciones.RespuestaDesconocidaWS;
import utilidades.web.excepciones.SinDatosWSExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TrataMensajeExcepcion;

public class ProcesoVerifyProfessionalProof extends ProcesoBase {
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoVerifyProfessionalProof.class);
	private Integer hiloActivo;

	private ModeloDGTWS modeloDGTWS;
	private ModeloJustificanteProfesional modeloJustificanteProfesional;
	private UtilesProcesos utilesProcesos;
	
	@Autowired
	private ServicioMensajesProcesos servicioMensajesProcesos;
	
	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		// Forzamos la inyección de dependencias de las clases anotadas como 'Autowired'
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
		ColaBean solicitud = new ColaBean();
		try {
			log.info("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " -- Buscando Solicitud");
			solicitud = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF, hiloActivo.toString());
			log.info("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " -- Solicitud " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " encontrada, llamando a WS");
			llamadaWS(jobExecutionContext, solicitud);
		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion){
			sinPeticiones(jobExecutionContext); 
			log.info(sinSolicitudesExcepcion.getMensajeError1(), sinSolicitudesExcepcion);
		} catch (SinDatosWSExcepcion sinDatosWSExcepcion){
			tratarRecuperable(jobExecutionContext, solicitud,ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_AL_WEBSERVICE);
			log.logarOegamExcepcion(sinDatosWSExcepcion.getMensajeError1(), sinDatosWSExcepcion);
		} //excepciones dentro del webservice
		catch (java.net.ConnectException eCon){
			log.error(ConstantesProcesos.TIMEOUT, eCon); 
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT); 
		} catch (java.net.SocketTimeoutException eCon){
			log.error(ConstantesProcesos.TIMEOUT, eCon); 
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT); 
		/*} catch (DescontarCreditosExcepcion descontarCreditosExcepcion){
			try {
				UtilesProcesos.finalizarTransaccionConError(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);
			} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
				marcarSolicitudConErrorServicio(solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1(), jobExecutionContext);
			} catch (BorrarSolicitudExcepcion e) {
				marcarSolicitudConErrorServicio(solicitud,e.getMensajeError1(),jobExecutionContext);
			}
		*/
		} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion){
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
			log.error("Excepcion VerifyPro", e); 
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + ":" + e.getMessage()); 
		} catch (Throwable e){
			log.error("Throwable VerifyPro", e); 
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ ":" + e.getMessage()); 
		}		
	}	
	
	private void llamadaWS(JobExecutionContext jobExecutionContext,	ColaBean solicitud) 
			throws CambiarEstadoTramiteTraficoExcepcion, OegamExcepcion,TrataMensajeExcepcion,Exception {
		try {
			//-----------------PASAMOS A INVOCAR AL WEBSERVICE-------------
			if (solicitud.getXmlEnviar()==null)
				throw new SinDatosWSExcepcion("No hay datos para enviar al WebService");
			log.info("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " -- Procesando petición");
			ProfessionalProofVerificationResponse respuestaWS = DGTSolicitudProfessionalProof.presentarDGTVerifyProfessionalProof(solicitud.getXmlEnviar());
			log.info("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " -- Peticion Procesada");
			boolean error = compruebaError(respuestaWS);

			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
			try{
				if(!error) {
					log.info("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " -- Respuesta recibida");
					solicitud.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, null);
					log.info("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " -- Proceso ejecutado correctamente");
				} else {
					String respuesta = null;
					if(respuestaWS != null && respuestaWS.getErrors() != null && respuestaWS.getErrors().length > 0){
						for(ProfessionalProofError pproofError : respuestaWS.getErrors()){
							respuesta = pproofError.getCode() + " - " + pproofError.getMessage() + "; ";
						}
						solicitud.setRespuesta(respuesta);
					} else {
						solicitud.setRespuesta(ConstantesProcesos.EJECUCION_NO_CORRECTA);
					}
					log.info("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " -- Error Recibido: " + solicitud.getRespuesta());
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
				}
			} catch(Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.VERIFYPROPROOF.getNombreEnum());
			}
			// FIN : Incidencia : 0000814

			//MANEJAMOS LA RESPUESTA DEL WS
			if(error) {
				respuestaError(jobExecutionContext, solicitud, respuestaWS);
			} else {
				respuestaOk(jobExecutionContext, solicitud, respuestaWS, solicitud.getXmlEnviar());
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
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF);
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error);
			} catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.VERIFYPROPROOF.getNombreEnum());
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
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF);
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error);
			} catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.VERIFYPROPROOF.getNombreEnum());
			}
			throw ex;
		}
		// FIN : Incidencia : 0000814
	}

	private boolean compruebaError(ProfessionalProofVerificationResponse respuestaWS) {
		ProfessionalProofError[] bbErrores = respuestaWS.getErrors();

		if (null == bbErrores || bbErrores.length == 0) {
			log.info("LOG_VERIFY_PROFESSIONAL: RESPUESTA SIN ERRORES");
			return false;
		} else if (bbErrores.length == 1) {			
			if("0".equals(respuestaWS.getErrors(0).getCode())){
				log.info("LOG_VERIFY_PROFESSIONAL: RESPUESTA SIN ERRORES");
				log.info("LOG_VERIFY_PROFESSIONAL: SÓLO INDICA QUE NO ES VÁLIDO EL CSV");
				return false;
			} else {
				log.error("respuesta con errores");
				return true;
			}
		} else {
			log.error("respuesta con errores");
			return true;
		}
	}

	private void respuestaOk(JobExecutionContext jobExecutionContext,ColaBean solicitud, ProfessionalProofVerificationResponse respuestaWS,
		String peticion) throws DescontarCreditosExcepcion, CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion, IOException, Exception, CrearJustificanteExcepcion {		
		finalizarTransaccionVerifyProfessionalProofOk(jobExecutionContext, solicitud, respuestaWS);
	}
	
	private void respuestaError(JobExecutionContext jobExecutionContext, ColaBean solicitud,
			ProfessionalProofVerificationResponse respuestaWS) throws IOException, Exception, CambiarEstadoTramiteTraficoExcepcion, TrataMensajeExcepcion, BorrarSolicitudExcepcion {
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
		log.info("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF);
	}

	private void cambiaEstadoAError(ColaBean solicitud, TrataMensajeExcepcion trataMensajeExcepcion, JobExecutionContext jobExecutionContext){
		try{
			finalizarTransaccionConErrorNoRecuperable(solicitud, trataMensajeExcepcion.getMessage(),jobExecutionContext);
		}catch( BorrarSolicitudExcepcion e){
			log.error("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " -- Error al cambiar de estado: " + e);	
		}catch(CambiarEstadoTramiteTraficoExcepcion ex){
			log.error("Proceso " + ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF + " -- Error al cambiar de estado: " + ex);	
		}
	}

	private void finalizarTransaccionVerifyProfessionalProofOk(
			JobExecutionContext jobExecutionContext, ColaBean solicitud, ProfessionalProofVerificationResponse respuestaWS) throws BorrarSolicitudExcepcion,  IOException, Exception, CrearJustificanteExcepcion {
		
		JustificanteProfesional justificanteProf = new JustificanteProfesional();
		justificanteProf.setVerificado(respuestaWS.isVerificationSuccessful());
		justificanteProf.setCodigoVerificacion(solicitud.getXmlEnviar());
		getModeloJustificanteProfesional().crear_validacion(justificanteProf); 
		
		peticionCorrecta(jobExecutionContext); 	
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