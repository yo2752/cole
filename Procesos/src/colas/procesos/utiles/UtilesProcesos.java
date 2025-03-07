package colas.procesos.utiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;

import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.gescogroup.blackbox.CtitCheckError;
import com.gescogroup.blackbox.CtitFullError;
import com.gescogroup.blackbox.CtitFullImpediment;
import com.gescogroup.blackbox.CtitNotificationError;
import com.gescogroup.blackbox.CtitNotificationImpediment;
import com.gescogroup.blackbox.CtitTradeError;
import com.gescogroup.blackbox.CtitTradeImpediment;
import com.gescogroup.blackbox.ProfessionalProofResponse;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import hibernate.entities.general.Usuario;
import trafico.beans.AlimentacionBean;
import trafico.beans.CarroceriaBean;
import trafico.beans.ClasificacionCriterioConstruccionBean;
import trafico.beans.ClasificacionCriterioUtilizacionBean;
import trafico.beans.DatosCTITBean;
import trafico.beans.DirectivaCEEBean;
import trafico.beans.MarcaBean;
import trafico.beans.ServicioTraficoBean;
import trafico.beans.TipoTarjetaItvBean;
import trafico.beans.VehiculoBean;
import trafico.beans.schemas.generated.eitv.generated.xmlDataVehiculos.Vehiculo;
import trafico.beans.schemas.generated.transTelematica.SolicitudRegistroEntrada;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.XMLConsultaTarjetaEITVFactory;
import trafico.utiles.XmlCheckCTITFactory;
import trafico.utiles.XmlTransTelematicaFactory;
import trafico.utiles.enumerados.Combustible;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class UtilesProcesos {

	private static final String UTF_8 = "UTF-8";
	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesProcesos.class);
		
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private ServicioProcesos servicioProcesos;
	
	@Autowired
	private ServicioMarcaDgt servicioMarcaDgt;
	
	@Autowired
	Utiles utiles;

	public UtilesProcesos() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	private ModeloMatriculacion modeloMatriculacion;
	private ModeloTransmision modeloTransmision;
	private ModeloSolicitud modeloSolicitud;
	
	public void guardarPTC(ColaBean solicitud,String permisoTemporalCirculacion) throws Exception, IOException, OegamExcepcion {
		
		/**Cambios Gestor Ficheros**/ 
		if (null != permisoTemporalCirculacion
				&& !"".equals(permisoTemporalCirculacion)) {
			
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.CTIT);
			fichero.setSubTipo(ConstantesGestorFicheros.PTC);
			fichero.setSobreescribir(true);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
             
			if (solicitud.getIdTramite()!=null)
			{	 
				 fichero.setFecha(Utilidades.transformExpedienteFecha(solicitud.getIdTramite()));
				 fichero.setNombreDocumento( solicitud.getIdTramite().toString());
				
			}else{								
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NOMBRE_DOCUMENTO);		
			}
			
//			byte[] ptcBytes = UtilesRegistradores.doBase64Decode(permisoTemporalCirculacion, UTF_8);
			byte[] ptcBytes = utiles.doBase64Decode(permisoTemporalCirculacion, UTF_8);
			fichero.setFicheroByte(ptcBytes);
			gestorDocumentos.guardarByte(fichero);				
			log.debug(solicitud.getProceso() +": Tiene PTC");							
			log.info(" Tiene permiso temporal de Circulación");		
		}
		else log.info("no tiene ptc"); 
	}

	public boolean guardarInforme(ColaBean solicitud, String report) throws Exception,
			IOException, OegamExcepcion {
		
		boolean guardado=true; 
		
		/**Cambios Gestor Ficheros**/ 
		if (null != report && !"".equals(report)) {
			
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.CTIT);
			fichero.setSubTipo(ConstantesGestorFicheros.INFORMES);
			fichero.setSobreescribir(true);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
             
			if (solicitud.getIdTramite()!=null)
			{	 
				 fichero.setFecha(Utilidades.transformExpedienteFecha(solicitud.getIdTramite()));
				 fichero.setNombreDocumento( "i_"+solicitud.getIdTramite().toString());
				
			}else{								
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NOMBRE_DOCUMENTO);		
			}

			byte[] bytesReport = report.getBytes();
			if (Base64.isArrayByteBase64(bytesReport)) {
				bytesReport = Base64.decodeBase64(bytesReport);
			}
			fichero.setFicheroByte(bytesReport);
			gestorDocumentos.guardarFichero(fichero);

			log.info("ProcesoFullCTITT: Tiene informe");
			log.debug(solicitud.getProceso() +": Tiene informe");	
		}
		else {
				log.info("El expediente número "+solicitud.getIdTramite()+ "no tiene informe");
				guardado=false;
			}		
		return guardado;
	}
	
	/**
	 * Se guarda el justificante que se obtiene de la respuesta dek web service
	 * @param solicitud
	 * @param respuestaWS
	 * @throws Exception
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	public void guardarPdfJustificante(ColaBean solicitud, ProfessionalProofResponse respuestaWS) throws Exception, IOException, OegamExcepcion {
		
		String numJustificante ="";
		
		if (null != respuestaWS.getProfessionalProofPdf()
				&& !"".equals(respuestaWS.getProfessionalProofPdf())) {

			log.info(" Tiene Justificante Profesional");
						
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.JUSTIFICANTES);
			fichero.setSubTipo(ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA);
			fichero.setSobreescribir(true);
             
			if (solicitud.getIdTramite()!=null)
			{	 
				 fichero.setFecha(Utilidades.transformExpedienteFecha(solicitud.getIdTramite()));
				 numJustificante= respuestaWS!=null ? String.valueOf(Integer.valueOf(respuestaWS.getProofNumber())) : "";
				 fichero.setNombreDocumento( solicitud.getIdTramite().toString()+ "_" + utiles.rellenarCeros(numJustificante, BigDecimal.TEN.intValue()));				
		    }else{								
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NOMBRE_DOCUMENTO);		
			}
					
//			byte[] ptcBytes = UtilesRegistradores.doBase64Decode(new String(respuestaWS.getProfessionalProofPdf()), UTF_8);
			byte[] ptcBytes = utiles.doBase64Decode(new String(respuestaWS.getProfessionalProofPdf()), UTF_8);
			fichero.setFicheroByte(ptcBytes);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			gestorDocumentos.guardarByte(fichero);				
			log.debug(solicitud.getProceso() +": Tiene justificante");							
			log.info(" Tiene justificante");				
		}
		else log.info("No tiene Justificante Profesional (la respuesta del WS no trae PDF)"); 
	}

	/**
	 * Recupera los xml para el proceso FULLCTIT
	 * @param solicitud
	 * @return
	 * @throws FileNotFoundException
	 * @throws OegamExcepcion
	 */
	public String recogerXmlTransmision(ColaBean solicitud) throws FileNotFoundException, OegamExcepcion {
		Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(solicitud.getIdTramite());			
		File ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CTIT, ConstantesGestorFicheros.CTITENVIO, fechaBusqueda, solicitud.getXmlEnviar(), "").getFile();
		
		return new XmlTransTelematicaFactory().xmlFileToString(ficheroAenviar);
		}
		
	/**
	 * Recupera los xml para el proceso CheckCtit
	 * @param solicitud
	 * @return
	 * @throws FileNotFoundException
	 * @throws OegamExcepcion
	 */
	public String recogerXmlCheckCTIT(ColaBean solicitud) throws FileNotFoundException, OegamExcepcion {
		/** Cambios Gestor Documentos**/	   
		String nombreFichero = ConstantesGestorFicheros.NOMBRE_CHECKCTIT +  solicitud.getIdTramite();
		Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(solicitud.getIdTramite());
		File ficheroAenviar = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesProcesos.PROCESO_CHECKCTIT, ConstantesGestorFicheros.CTITENVIO, fechaBusqueda,nombreFichero).getFiles().get(0);
		return new XmlCheckCTITFactory().xmlFileToString(ficheroAenviar);
	}
	
	public String recogerXmlIssueProfessionalProof(ColaBean solicitud) throws FileNotFoundException, OegamExcepcion {
		String nombreFichero = ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + solicitud.getIdTramite();
		Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(solicitud.getIdTramite());
		File ficheroAenviar = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.ENVIO, fechaBusqueda,nombreFichero).getFiles().get(0);
	
		return new XMLConsultaTarjetaEITVFactory().xmlFileToString(ficheroAenviar);
	}
	
	public SolicitudRegistroEntrada rehacerSolicitudRegistroEntradaFullCTIT(
			ColaBean solicitud, DatosCTITBean datosCTITBean) throws MalformedURLException, JAXBException, OegamExcepcion {
		
			//recogemos y componemos el nuevo xml 
			return getModeloTransmision().rehacerSolicitudRegEntradaFullSegundoEnvio(solicitud.getXmlEnviar(),
				datosCTITBean.getConsentimiento());
	}

	public void finalizarGenerarXmlEitvOk(JobExecutionContext jobExecutionContext, ColaBean solicitud) throws BorrarSolicitudExcepcion, CrearSolicitudExcepcion {
		
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(),ConstantesProcesos.OK);
		getModeloMatriculacion().crearSolicitudEnColaEItv(solicitud.getIdTramite(), solicitud.getIdUsuario()); 
	}
	
	public void finalizarGenerarXmlEitvPrematriculadosOk(JobExecutionContext jobExecutionContext, ColaBean solicitud) throws BorrarSolicitudExcepcion, CrearSolicitudExcepcion {
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(),ConstantesProcesos.OK);
		getModeloMatriculacion().crearSolicitudEnColaEItvPrematriculados(solicitud.getIdTramite(), solicitud.getIdUsuario()); 
	}


	public String  getMensajeError(CtitTradeError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer(); 
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
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
		return mensajeError.toString(); 
	}
	
	public String getImpedimentosTrade(CtitTradeImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer(); 
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - " );
			String error = listadoImpedimentos[i].getMessage().replaceAll("'", "");
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
			impedimento.append(error);
		}
		return impedimento.toString(); 
	}
	
	public String getImpedimentosFull(CtitFullImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer(); 
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - " );
			String error = listadoImpedimentos[i].getMessage().replaceAll("'", "");
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
			impedimento.append(error);
		}
		return impedimento.toString(); 
	}
	
	public String  getImpedimentosNotification(CtitNotificationImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer(); 
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - " );
			String error = listadoImpedimentos[i].getMessage().replaceAll("'", "");
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
			impedimento.append(error);
		}
		return impedimento.toString(); 
	}
	
	public String getNombreProcesoOrdenado(int orden) throws OegamExcepcion {
		// Se busca y debería devolver uno solo.
		return servicioProcesos.getNombreProcesoOrdenado(Long.valueOf(orden));

	}

	public void creaNotificacion(ColaBean cola, String mensaje){
		NotificacionDto notificacion = new NotificacionDto();
		notificacion.setDescripcion(mensaje);
		notificacion.setIdTramite(cola.getIdTramite());
		notificacion.setEstadoAnt(new BigDecimal(0));
		notificacion.setEstadoNue(new BigDecimal(0));
		notificacion.setTipoTramite(cola.getTipoTramite());
		notificacion.setIdUsuario(cola.getIdUsuario().longValue());
		ServicioNotificacion servicioNotificacion = ContextoSpring.getInstance().getBean(ServicioNotificacion.class);
		servicioNotificacion.crearNotificacion(notificacion);
	}
	
	public void creaNotificacionColegiado(ColaBean cola, String mensaje, Usuario usuario) {
		NotificacionDto notificacion = new NotificacionDto();
		notificacion.setDescripcion(mensaje);
		notificacion.setIdTramite(cola.getIdTramite());
		notificacion.setEstadoAnt(new BigDecimal(0));
		notificacion.setEstadoNue(new BigDecimal(0));
		notificacion.setTipoTramite(cola.getTipoTramite());
		notificacion.setIdUsuario(usuario.getIdUsuario());
		ServicioNotificacion servicioNotificacion = ContextoSpring.getInstance().getBean(ServicioNotificacion.class);
		servicioNotificacion.crearNotificacion(notificacion);
	}
	
	/**
	 * 
	 * @param solicitud
	 * @param fichaTecnica
	 * @param tipo
	 * @param Subtipo
	 * @throws Exception
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	public void guardarFichaTecnica(ColaBean solicitud,String fichaTecnica, String tipo, String Subtipo) throws Exception, IOException, OegamExcepcion {
		
		/**Cambios Gestor Ficheros**/ 
		if (null != fichaTecnica
				&& !"".equals(fichaTecnica)) {

				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(tipo);
				fichero.setSubTipo(Subtipo);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
	             
				if (solicitud.getIdTramite()!=null)
				{	 
					 fichero.setFecha(Utilidades.transformExpedienteFecha(solicitud.getIdTramite()));
					 fichero.setNombreDocumento( solicitud.getIdTramite().toString());					
				}else{								
					throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NOMBRE_DOCUMENTO);		
				}				
//				byte[] ptcBytes = UtilesRegistradores.doBase64Decode(fichaTecnica, UTF_8);
				byte[] ptcBytes = utiles.doBase64Decode(fichaTecnica, UTF_8);
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);				
				log.debug(solicitud.getProceso() +": Tiene Ficha técnica");							
				log.info("Tiene ficha técnica");		
			}
			else log.info("no tiene ficha técnica"); 
	
	}
	
	public void listarErroresTradeCTIT(CtitTradeError[] listadoErrores){
		log.error("Listando errores TradeCtit");
		for (int i = 0; i < listadoErrores.length; i++){
			log.error(ConstantesProcesos.PROCESO_TRADECTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO      + " " + listadoErrores[i].getKey()); 
			log.error(ConstantesProcesos.PROCESO_TRADECTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + listadoErrores[i].getMessage());
		}
	}
	
	public void listarErroresFullCTIT(CtitFullError[] listadoErrores){
		log.error("Listando errores FullCtit");
		for (int i = 0; i < listadoErrores.length; i++){
			log.error(ConstantesProcesos.PROCESO_FULLCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO      + " " + listadoErrores[i].getKey()); 
			log.error(ConstantesProcesos.PROCESO_FULLCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + listadoErrores[i].getMessage());
		}
	}
	
	public String getMensajeError(CtitFullError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer(); 
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - " );
			String error = listadoErrores[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", ""); 
			if(error.length()>80) {
				String resAux = "";
				for(int tam=0; tam<=Math.floor(error.length()/80); tam++) {
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
	
	public void listarErroresNotificationCTIT(CtitNotificationError[] listadoErrores){
		log.error("Listando errores NotificationCtit");
		for (int i = 0; i < listadoErrores.length; i++){
			log.error(ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO      + " " + listadoErrores[i].getKey()); 
			log.error(ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + listadoErrores[i].getMessage());
		}
	}
	
	public String getMensajeError(CtitNotificationError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer(); 
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - " );
			String error = listadoErrores[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", ""); 
			if(error.length()>80) {
				String resAux = "";
				for(int tam=0; tam<=Math.floor(error.length()/80); tam++) {
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
	
	public void listarErroresCheckCTIT(CtitCheckError[] listadoErrores){
		log.error("Listando errores CheckCtit");
		for (int i = 0; i < listadoErrores.length; i++){
			log.error(ConstantesProcesos.PROCESO_CHECKCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO      + " " + listadoErrores[i].getKey()); 
			log.error(ConstantesProcesos.PROCESO_CHECKCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + listadoErrores[i].getMessage());
		}
	}
	
	public String getMensajeError(CtitCheckError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer(); 
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - " );
			String error = listadoErrores[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", ""); 
			if(error.length()>80) {
				String resAux = "";
				for(int tam=0; tam<=Math.floor(error.length()/80); tam++) {
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
	
	public void chequeaDirectivaCEE(VehiculoBean vehiculo){
		/*
		 * Comprobacion del valor de homologacion UE antes de guardar
		 * Según la documentación recibida por DGT en Mayo de 2013 el campo categoría no es sensible a mayúsculas/minúsculas
		 * Debe seguir el siguiente patrón. Si no lo cumple se modifica para que así sea.
		 * M1, M2, M3, N1, N2, N3, O1, O2, O3, O4, L1e, L2e, L3e, L4e, L5e, L6e, L7e, T1, T2, T3, T4, quad, atv, M1G, M2G, M3G, N1G, N2G, N3G, OTRO
		 */

		if (vehiculo != null && vehiculo.getDirectivaCeeBean()!=null && vehiculo.getDirectivaCeeBean().getIdDirectivaCEE()!=null
				&& !vehiculo.getDirectivaCeeBean().getIdDirectivaCEE().equals("")){
			
			String directivaCEEaux = vehiculo.getDirectivaCeeBean().getIdDirectivaCEE().toUpperCase();
			if ("QUAD".equals(directivaCEEaux) || "ATV".equals(directivaCEEaux) ) {
				directivaCEEaux = directivaCEEaux.toLowerCase();
			}
			if (directivaCEEaux.startsWith("L")){
				directivaCEEaux = directivaCEEaux.replace("E", "e");
			}
			vehiculo.getDirectivaCeeBean().setIdDirectivaCEE(directivaCEEaux);	
		}
	}

	/**
	 * Convierte el vehículo recibido por el WS de consulta de tarjeta EITV, en un vehículo de pantalla
	 * @param vehiculoEitv
	 * @return vehiculoBean VehiculoBean
	 */
	public VehiculoBean vehiculoEITVToVehiculoBean(Vehiculo vehiculoEitv){
		
		// Creamos el objeto vehículo de pantalla
		VehiculoBean vehiculoBean = new VehiculoBean();
		
		// Iremos convirtiendo los datos de vehículo recibidos por el WS, siguiendo el orden fijado en pantalla (según estructuración de pantalla de MATW)
		
		// BLOQUE DATOS DEL VEHÍCULO
		eitvDatosVehiculoConversion(vehiculoEitv, vehiculoBean);
		
		// BLOQUE DATOS TÉCNICOS
		eitvDatosTecnicosConversion(vehiculoEitv, vehiculoBean);
		
		// BLOQUE CARACTERÍSTICAS
		eitvCaracteristicasConversion(vehiculoEitv, vehiculoBean);
				
		// BLOQUE DATOS DE LA INSPECCIÓN TÉCNICA (ITV)
		eitvDatosItvConversion(vehiculoEitv, vehiculoBean);
		
		return vehiculoBean;
		
	}
	
	/**
	 * Obtiene el bloque de datos del vehículo (según pantalla), desde el vehículo de la consulta del WS de EITV
	 * @param vehiculoEitv
	 * @param vehiculoBean
	 */
	private void eitvDatosVehiculoConversion(Vehiculo vehiculoEitv, VehiculoBean vehiculoBean){
		
		// Bastidor
		vehiculoBean.setBastidor(vehiculoEitv.getFabricante().getNumerovin());
	
		// Código ITV
		vehiculoBean.setCodItv(vehiculoEitv.getFabricante().getCodigoitv()!=null
				&& !vehiculoEitv.getFabricante().getCodigoitv().equals("") ? vehiculoEitv.getFabricante().getCodigoitv() : null);

		// Marca
		MarcaDgtDto marcaDGT = null;
		if(vehiculoEitv.getCaracteristicas().getMarca()!=null && !vehiculoEitv.getCaracteristicas().getMarca().equals("")){
			marcaDGT = servicioMarcaDgt.getMarcaDgtDto(null, vehiculoEitv.getCaracteristicas().getMarca(), null);
			if (marcaDGT != null) {
				MarcaBean marcaBean = new MarcaBean();
				marcaBean.setCodigoMarca(marcaDGT.getCodigoMarca());
				marcaBean.setMarca(marcaDGT.getMarca());
				marcaBean.setMarcaSinEditar(marcaDGT.getMarcaSinEditar());
				vehiculoBean.setMarcaBean(marcaBean);
			}
		}

		// Modelo
		vehiculoBean.setModelo(vehiculoEitv.getCaracteristicas().getDenominacion()!=null
				&& !vehiculoEitv.getCaracteristicas().getDenominacion().equals("") ? vehiculoEitv.getCaracteristicas().getDenominacion():null);
		
		// Tipo de vehículo
		
		// Matrícula (sólo si viene, porque el vehículo ya esté matriculado)
		vehiculoBean.setMatricula(vehiculoEitv.getTrafico().getMatricula()); 
		
	}
	
	/**
	 * Obtiene el bloque de datos técnicos (según pantalla), desde el vehículo de la consulta del WS de EITV
	 * @param vehiculoEitv
	 * @param vehiculoBean
	 */
	private void eitvDatosTecnicosConversion(Vehiculo vehiculoEitv, VehiculoBean vehiculoBean){
		
		String clasificacionITV = vehiculoEitv.getFabricante().getClasificacion()!=null
				&& !vehiculoEitv.getFabricante().getClasificacion().equals("") ? vehiculoEitv.getFabricante().getClasificacion() : null;
		String criterioConstruccion = clasificacionITV!=null ? clasificacionITV.substring(0, 2) : null;
		String criterioUtilizacion = clasificacionITV!=null ? clasificacionITV.substring(2) : null;
		
		// Criterio de Construcción
		ClasificacionCriterioConstruccionBean criterioConstruccionBean = new ClasificacionCriterioConstruccionBean();
		criterioConstruccionBean.setIdCriterioConstruccion(criterioConstruccion);
		vehiculoBean.setCriterioConstruccionBean(criterioConstruccionBean);
		
		// Criterio de Utilización
		ClasificacionCriterioUtilizacionBean criterioUtilizacionBean = new ClasificacionCriterioUtilizacionBean();
		criterioUtilizacionBean.setIdCriterioUtilizacion(criterioUtilizacion);
		vehiculoBean.setCriterioUtilizacionBean(criterioUtilizacionBean);
		
		// Clasificación ITV
		vehiculoBean.setClasificacionItv(clasificacionITV);
		
		// Color
		vehiculoBean.setColorBean(vehiculoEitv.getCaracteristicas().getColor()!=null
				&& !vehiculoEitv.getCaracteristicas().getColor().equals("") ? vehiculoEitv.getCaracteristicas().getColor() : null);
		
		// Epígrafe
		
		// Fabricante
		vehiculoBean.setFabricante(vehiculoEitv.getGeneral().getNomfabricante()!=null
				&& !vehiculoEitv.getGeneral().getNomfabricante().equals("") ? vehiculoEitv.getGeneral().getNomfabricante() : null);
		
		// Número de serie
		vehiculoBean.setNumSerie(vehiculoEitv.getGeneral().getSerieindustria()!=null
				&& !vehiculoEitv.getGeneral().getSerieindustria().equals("") ? vehiculoEitv.getGeneral().getSerieindustria() : null);
		
		// Número de homologación
		
		vehiculoBean.setNumHomologacion(vehiculoEitv.getCertificacion().getContraseña()!=null
				&& !vehiculoEitv.getCertificacion().getContraseña().equals("") ? vehiculoEitv.getCertificacion().getContraseña() : null);
		
		// País fabricación
		
		// Servicio
		ServicioTraficoBean servicioTrafico = new ServicioTraficoBean();
		servicioTrafico.setIdServicio(vehiculoEitv.getComunidad().getServicio()!=null
				&& !vehiculoEitv.getComunidad().getServicio().equals("") ? vehiculoEitv.getComunidad().getServicio() : null);
		vehiculoBean.setServicioTraficoBean(servicioTrafico);
		
		// Variante
		
		vehiculoBean.setVariante(vehiculoEitv.getCaracteristicas().getVariante()!=null
				&& !vehiculoEitv.getCaracteristicas().getVariante().equals("") ? vehiculoEitv.getCaracteristicas().getVariante() : null);
		
		// Versión
		
		vehiculoBean.setVersion(vehiculoEitv.getCaracteristicas().getVersion()!=null
				&& !vehiculoEitv.getCaracteristicas().getVersion().equals("") ? vehiculoEitv.getCaracteristicas().getVersion() : null);
		
	}
	
	/**
	 * Obtiene el bloque de características (según pantalla), desde el vehículo de la consulta del WS de EITV
	 * @param vehiculoEitv
	 * @param vehiculoBean
	 */
	private void eitvCaracteristicasConversion(Vehiculo vehiculoEitv, VehiculoBean vehiculoBean) {
		
		// Categoría / Homologación EU
		DirectivaCEEBean directivaCEE = new DirectivaCEEBean();
		directivaCEE.setIdDirectivaCEE(vehiculoEitv.getCaracteristicas().getCategoria()!=null
				&& !vehiculoEitv.getCaracteristicas().getCategoria().equals("") ? vehiculoEitv.getCaracteristicas().getCategoria() : null);
		vehiculoBean.setDirectivaCeeBean(directivaCEE);
		
		// Carrocería
		CarroceriaBean carroceria = new CarroceriaBean();
		carroceria.setIdCarroceria(vehiculoEitv.getCaracteristicas().getTipocarroc()!=null
				&& ! vehiculoEitv.getCaracteristicas().getTipocarroc().equals("") ? vehiculoEitv.getCaracteristicas().getTipocarroc().substring(0, 2) : null);
		carroceria.setDescripcion(vehiculoEitv.getCaracteristicas().getTipocarroc()!=null
				&& !vehiculoEitv.getCaracteristicas().getTipocarroc().equals("") ? vehiculoEitv.getCaracteristicas().getTipocarroc().substring(2) : null);
		vehiculoBean.setCarroceriaBean(carroceria);
	
		// Cilindrada
		vehiculoBean.setCilindrada(vehiculoEitv.getCaracteristicas().getCilindrada()!=null
				&& !vehiculoEitv.getCaracteristicas().getCilindrada().equals("") ? vehiculoEitv.getCaracteristicas().getCilindrada() : null);
		
		// CO2
		vehiculoBean.setCo2(vehiculoEitv.getCaracteristicas().getEco2Cmixto()!=null
				&& !vehiculoEitv.getCaracteristicas().getEco2Cmixto().equals("") ? vehiculoEitv.getCaracteristicas().getEco2Cmixto() : null);
		
		// Código ECO
		
		//vehiculoBean.setCodigoEco(codigoEco);
		
		// Consumo
		
		//vehiculoBean.setConsumo(consumo);
		
		// Distancia Entre Ejes
		vehiculoBean.setDistanciaEntreEjes(vehiculoEitv.getCaracteristicas().getDistanejes()!=null
				&& !vehiculoEitv.getCaracteristicas().getDistanejes().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getDistanejes()) : BigDecimal.ZERO);
		
		// ECO Innovacion
		//vehiculoBean.setEcoInnovacion(ecoInnovacion);
		
		// Potencia Fiscal
		vehiculoBean.setPotenciaFiscal(vehiculoEitv.getCaracteristicas().getPotencfiscal()!=null
				&& !vehiculoEitv.getCaracteristicas().getPotencfiscal().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getPotencfiscal()): BigDecimal.ZERO);
		
		// MOM
		vehiculoBean.setMom(vehiculoEitv.getCaracteristicas().getMasamarcha()!=null
				&& !vehiculoEitv.getCaracteristicas().getMasamarcha().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getMasamarcha()) : null);
		
		// MMA
		vehiculoBean.setPesoMma(vehiculoEitv.getCaracteristicas().getMma()!=null
				&& !vehiculoEitv.getCaracteristicas().getMma().equals("") ? vehiculoEitv.getCaracteristicas().getMma() : null);
		
		// MTA / MMA
		vehiculoBean.setMtmaItv(vehiculoEitv.getCaracteristicas().getMmta()!=null
				&& !vehiculoEitv.getCaracteristicas().getMmta().equals("") ? vehiculoEitv.getCaracteristicas().getMmta() : null);
		
		// Nivel de emisiones
		if (vehiculoEitv.getCaracteristicas().getNemisioneseuro()!=null && vehiculoEitv.getCaracteristicas().getNemisioneseuro().equals("")) {
			if (vehiculoEitv.getCaracteristicas().getNemisioneseuro().length() > 15) {
				vehiculoBean.setNivelEmisiones(vehiculoEitv.getCaracteristicas().getNemisioneseuro().substring(0, 14));
			} else {
				vehiculoBean.setNivelEmisiones(vehiculoEitv.getCaracteristicas().getNemisioneseuro());
			}
		} else {
			vehiculoBean.setNivelEmisiones(null);
		}
		
		// Número de ruedas
		vehiculoBean.setNumRuedas(vehiculoEitv.getCaracteristicas().getNumruedas()!=null
				&& !vehiculoEitv.getCaracteristicas().getNumruedas().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getNumruedas()) : null);
		
		// Número máximo de plazas
		vehiculoBean.setPlazas(vehiculoEitv.getCaracteristicas().getNumasientos()!=null
				&& !vehiculoEitv.getCaracteristicas().getNumasientos().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getNumasientos()) : null);
		
		// Número de plazas pie
		vehiculoBean.setNumPlazasPie(vehiculoEitv.getCaracteristicas().getNumplazaspie()!=null
				&& !vehiculoEitv.getCaracteristicas().getNumplazaspie().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getNumplazaspie()) : null);
		
		// Potencia Neta
		if (vehiculoEitv.getCaracteristicas().getPotenciamax()!=null && !vehiculoEitv.getCaracteristicas().getPotenciamax().equals("")) {
			if (vehiculoEitv.getCaracteristicas().getPotenciamax().contains("/")) {
				String potenciaMax[] = vehiculoEitv.getCaracteristicas().getPotenciamax().split("/");
				vehiculoBean.setPotenciaNeta(new BigDecimal(potenciaMax[0]));
			} else {
				vehiculoBean.setPotenciaNeta(new BigDecimal(vehiculoEitv.getCaracteristicas().getPotenciamax()));
			}
		} else {
			vehiculoBean.setPotenciaNeta(null);
		}
		
		// Potencia / Peso
		BigDecimal potenciaPeso = vehiculoEitv.getCaracteristicas().getPotenciapeso()!=null
				&& !vehiculoEitv.getCaracteristicas().getPotenciapeso().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getPotenciapeso()) : BigDecimal.ZERO;
		vehiculoBean.setPotenciaPeso(potenciaPeso);
		
		// Reducción ECO
		//vehiculoBean.setReduccionEco(reduccionEco);
		
		// Tara
		if (vehiculoEitv.getCaracteristicas().getTara() != null && vehiculoEitv.getCaracteristicas().getTara().length() > 7) {
			String tara = vehiculoEitv.getCaracteristicas().getTara().substring(vehiculoEitv.getCaracteristicas().getTara().length() - 7);
			vehiculoBean.setTara(tara);
		} else {
			vehiculoBean.setTara(vehiculoEitv.getCaracteristicas().getTara() != null && !vehiculoEitv.getCaracteristicas().getTara().equals("") ? vehiculoEitv.getCaracteristicas().getTara() : null);
		}
		
		// Tipo Alimentación
		AlimentacionBean alimentacion = new AlimentacionBean();
		if (vehiculoEitv.getCaracteristicas().getMonobiflex()!=null && !vehiculoEitv.getCaracteristicas().getMonobiflex().equals("")) {
			if ("MONOCOMBUSTIBLE".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase())  || "MONO COMBUSTIBLE".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase()) || "MONOCARBURANT".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase())) {
				alimentacion.setIdAlimentacion("M");
			} else  if ("BICOMBUSTIBLE".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase()) || "BI COMBUSTIBLE".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase())) {
				alimentacion.setIdAlimentacion("B");
			} else {
				alimentacion.setIdAlimentacion(vehiculoEitv.getCaracteristicas().getMonobiflex());
			} 
		}
		vehiculoBean.setAlimentacionBean(alimentacion);
		
		// Tipo Carburante
		vehiculoBean.setCarburanteBean(vehiculoEitv.getCaracteristicas().getCarburante()!=null
				&& !vehiculoEitv.getCaracteristicas().getCarburante().equals("") ? Combustible.convertirCarburanteBean(vehiculoEitv.getCaracteristicas().getCarburante()) : null);
		
		// Vía Anterior
		vehiculoBean.setViaAnterior(vehiculoEitv.getCaracteristicas().getViaeje1()!=null
				&& !vehiculoEitv.getCaracteristicas().getViaeje1().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getViaeje1()) : null);
			
		// Vía Posterior
		vehiculoBean.setViaPosterior(vehiculoEitv.getCaracteristicas().getViaeje2()!=null
				&& !vehiculoEitv.getCaracteristicas().getViaeje2().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getViaeje2()) : null);
		
	}
	
	/**
	 * Obtiene el bloque de datos ITV (según pantalla), desde el vehículo de la consulta del WS de EITV
	 * @param vehiculoEitv
	 * @param vehiculoBean
	 */
	private void eitvDatosItvConversion(Vehiculo vehiculoEitv, VehiculoBean vehiculoBean) {
		
		// Tipo ITV
		vehiculoBean.setTipoItv(vehiculoEitv.getCaracteristicas().getTipo()!=null &&
				!vehiculoEitv.getCaracteristicas().getTipo().equals("") ? vehiculoEitv.getCaracteristicas().getTipo() : null);
		
		// Tipo Tarjeta ITV
		TipoTarjetaItvBean tipoTarjetaItv = new TipoTarjetaItvBean();
		tipoTarjetaItv.setIdTipoTarjetaItv(vehiculoEitv.getGeneral().getTipotarjeta()!=null
				&& ! vehiculoEitv.getGeneral().getTipotarjeta().equals("") ? vehiculoEitv.getGeneral().getTipotarjeta() : null);
		vehiculoBean.setTipoTarjetaItvBean(tipoTarjetaItv);
		
	}
	
	public void finalizarTransaccionEEFFF(
			JobExecutionContext jobExecutionContext, ColaBean solicitud,
			ResultBean result) {
		
		log.debug("Borrando transacción de EEFF");
		if(result.getMensaje()!=null || result.getListaMensajes().size() > 0){
			getModeloSolicitud().borrarSolicitud(solicitud.getIdEnvio(),result.getMensaje());
		}
		
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
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

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

}
