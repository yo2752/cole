package trafico.utiles.importacion.importadores;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.axis.message.SOAPHeaderElement;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.anuntis.model.webservice.handler.SoapAnuntisFilesHandler;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import WebServices.MotorESP.AnuntisSGM.ReportRequestData;
import WebServices.MotorESP.AnuntisSGM.ReportsLocator;
import WebServices.MotorESP.AnuntisSGM.ReportsSoap;
import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import escrituras.beans.ResultBean;
import hibernate.dao.general.UsuarioDAOImpl;
import hibernate.dao.trafico.ColegiadoAnuntisDAO;
import hibernate.dao.trafico.TramiteAnuntisDAO;
import hibernate.entities.general.Contrato;
import hibernate.entities.general.Usuario;
import hibernate.entities.trafico.ColegiadoAnuntis;
import hibernate.entities.trafico.TramiteAnuntis;
import hibernate.entities.trafico.filters.ColegiadoAnuntisFilter;
import hibernate.entities.trafico.filters.TramiteAnuntisFilter;
import trafico.utiles.enumerados.EstadoTramiteAnuntis;
import trafico.utiles.enumerados.TipoProceso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImportadorAVPOAnuntis {
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ImportadorAVPOAnuntis.class);

	public static final String AVPO_PDF_PATH = "avpo.files.pdf.path";
	public static final String CONTRATO_POR_DEFECTO = "anuntis.contrato.null";
	private static final String NOTIFICACION_CONFIG_TO = "anuntis.notification.mail.error1.to";
	private static final String NOTIFICACION_CONFIG_BCC = "anuntis.notification.mail.error1.bcc";
	private static final String NOTIFICACION_CONFIG_SUBJECT = "Configuración del proceso Anuntis erronea";
	private static final String NOTIFICACION_SUMMARY_TO = "anuntis.notification.mail.summary.to";
	private static final String NOTIFICACION_SUMMARY_BCC = "anuntis.notification.mail.summary.bcc";
	private static final String NOTIFICACION_SUMMARY_SUBJECT = "Solicitudes Anuntis";
	private static final String MODE_DEBUG = "anuntis.mode.debug";
	public static final String USER_ID = "anuntis.identicacion.userid";
	public static final String API = "anuntis.identificacion.api";

	private static final String ERROR_CODE_0 = "Trámite creado.";
	private static final String ERROR_CODE_1 = "No se creó el trámite.";
	private static final String ERROR_CODE_2 = "No se creó el trámite, solicitud ya existente.";
	private static final String ERROR_CODE_3 = "Solicitud nula.";

	private static final String LOG_ERROR_0 = "No hay contrato configurado, se interrumpe el proceso";
	private static final String LOG_ERROR_1 = "Error de conexión con el webservice de Anuntis / No hay respuesta de Anuntis";
	private static final String LOG_ERROR_2 = "Error en la persistencia de las solicitudes de Anuntis";
	private static final String LOG_ERROR_3 = "Ha habido un error al importar las solicitudes AVPO de anuntis";

	private TramiteAnuntisDAO tramiteAnuntisDAO;
	private ColegiadoAnuntisDAO colegiadoAnuntisDAO;

	private Map<Integer, String> messages;
	

	private ServicioCorreo servicioCorreo;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	EjecucionesProcesosDAO ejecucionesProcesosDAO;

	public ServicioCorreo getServicioCorreo() {
		if(servicioCorreo == null){
			servicioCorreo = ContextoSpring.getInstance().getBean(ServicioCorreo.class);
		}
		return servicioCorreo;
	}
	
	public ImportadorAVPOAnuntis() throws OegamExcepcion {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		this.tramiteAnuntisDAO = new TramiteAnuntisDAO();
		this.colegiadoAnuntisDAO = new ColegiadoAnuntisDAO();
		this.messages = new HashMap<Integer, String>();
	}

	public void execute(TipoProceso tipoProceso) {
		boolean ok = true;
		String message = ConstantesProcesos.EJECUCION_NO_CORRECTA;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			Calendar calendar = Calendar.getInstance();

			log.info("Process started at " + sdf.format(calendar.getTime()));

			// Obtener contrato configurado para este periodo
			Contrato contrato = obtenerContrato(calendar.getTime());
			if (contrato == null) {
				throw new OegamExcepcion(LOG_ERROR_0);
			}

			// Conectar/Recuperar listado de WS de Anuntis
			ReportRequestData[] solicitudes = null;
			solicitudes = doRequestAnuntis();
			if ( solicitudes == null ){
				throw new OegamExcepcion(LOG_ERROR_1);
			}

			// Montar trámites y persistirlos en BBDD
			List<TramiteAnuntis> tramites = persistTramite(contrato, solicitudes);
			if ( tramites == null ){
				throw new OegamExcepcion(LOG_ERROR_2);
			}

			// Notificar vía mail
			notificarRecepcion(tramites, solicitudes, contrato, tipoProceso);

		}catch (OegamExcepcion oe){
			// Errores "controlados", se notifica vía email
			ok = false;
			message = oe.getMessage();
			log.error(oe.getMessage(), oe);
			notificarError(oe.getMessage());
		}catch (Throwable t) {
			log.error(LOG_ERROR_3, t);
		} finally {
			log.info("process end " + sdf.format(Calendar.getInstance().getTime()));
		}
		if(ok){
			try {
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(tipoProceso.getValorEnum(),
						 ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO, ConstantesProcesos.EJECUCION_CORRECTA);
			} catch (Exception e) {
				log.error("Error al Guardar Envio Data para " + tipoProceso.getNombreEnum());
				log.error("Detalle: " + e.getMessage());
			}
		}
		else{
			try{
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(tipoProceso.getValorEnum(),
						message, ConstantesProcesos.EJECUCION_NO_CORRECTA);
			}catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + tipoProceso.getNombreEnum());
			}
		}
	}

	/**
	 * Monta los tramites y los guarda en BBDD
	 * @param contrato - Contrato al que se asocian los trámites.
	 * @param solicitudes - Solicitudes que se convierten en trámites.
	 * @return
	 */
	private List<TramiteAnuntis> persistTramite(Contrato contrato, ReportRequestData[] solicitudes) {
		Calendar calendar = Calendar.getInstance();
		List<TramiteAnuntis> entityList = null;
		Usuario usuario = new UsuarioDAOImpl().getUsuario(contrato.getColegiado().getUsuario().getIdUsuario());
		try{
			entityList = new ArrayList<TramiteAnuntis>();
			for (ReportRequestData data : solicitudes) {
				if(data == null ){
					continue;
				}
				if(data.getCarPlate()==null || data.getCarPlate().isEmpty() ||
						data.getMail()==null || data.getMail().isEmpty() ||
						data.getNif()==null || data.getNif().isEmpty() ||
						data.getRequestDate()==null ){
					continue;
				}
				// Se comprueba que esa solicitud no exista ya en BBDD (por requestId)
				TramiteAnuntisFilter filtro = new TramiteAnuntisFilter();
				filtro.setIdRequest(data.getReportRequestId());
				List<TramiteAnuntis> list = tramiteAnuntisDAO.list(filtro);
				if( list == null || list.size() == 0 ){
					data.getReportRequestId();
					// Se formatean correctamente los campos recibidos
					data.setCarPlate(formatAnuntisText(data.getCarPlate()));
					data.setNif(formatAnuntisText(data.getNif()));
					try{
						TramiteAnuntis tramite = new TramiteAnuntis();
						// El número de expediente se genera en el DAO
						tramite.setNumExpediente(null);
						tramite.setNumColegiado(contrato.getColegiado().getNumColegiado());
						tramite.setEstado(BigDecimal.valueOf(Long.parseLong(EstadoTramiteAnuntis.Iniciado.getValorEnum())));
						tramite.setMatricula( data.getCarPlate());
						tramite.setNif(data.getNif());
						tramite.setEmail(data.getMail().trim());
						tramite.setContrato(contrato);
						tramite.setUsuario(usuario);
						tramite.setFechaAlta(calendar.getTime());
						tramite.setFechaUltModif(calendar.getTime());
						tramite.setFechaRequest(data.getRequestDate()!=null?data.getRequestDate().getTime():null);
						tramite.setIdRequest(data.getReportRequestId());
				
						// Persistir
						tramiteAnuntisDAO.create(tramite);
						entityList.add(tramite);
					}catch(Exception e){
						log.error("Error al persistir solicitud " + data.getReportRequestId(), e);
					}
				} else {
					// La solicitud ya fué recuperada anteriormente
					messages.put(data.getReportRequestId(), ERROR_CODE_2);
				}
			}
		}catch (Exception e){
			log.error("", e);
		}
		return entityList;
	}

	/**
	 * Mediante el cliente WS realiza la petición a Anuntis para obtener el
	 * listado de solicitudes
	 * 
	 * @return
	 * @throws OegamExcepcion 
	 * @throws Throwable
	 */
	public ReportRequestData[] doRequestAnuntis() {
		ReportRequestData[] reportsRequestData = null;

		try {
			ReportsLocator locator = new ReportsLocator();
			ReportsSoap wsReport = locator.getReportsSoap();

			String userId = gestorPropiedades.valorPropertie(USER_ID);
			String api = gestorPropiedades.valorPropertie(API);

			// Se añade una línea con el secureheader al soap:header
			SOAPHeaderElement header = new SOAPHeaderElement("AnuntisSGM.MotorESP.WebServices", "SecureHeaderAPI ");
			header.setAttribute("UserId", userId);
			header.setAttribute("API", api);
			header.setActor(null);
			((org.apache.axis.client.Stub) wsReport).setHeader(header);
			
			//Añadimos el Handler del log
			QName portName = new QName(locator.getReportsSoapAddress(), locator.getReportsSoapWSDDServiceName());
			List<HandlerInfo> list = locator.getHandlerRegistry().getHandlerChain(portName);
			list.add(getFilesHandler());

			reportsRequestData = wsReport.getReportsRequests();
			log.info("Recovered " + reportsRequestData.length + " ReportRequestData");
			
		} catch (RemoteException e) {
			log.error("Error retrieving reports", e);
		} catch (Throwable t){
			log.error("", t);
		}

		return reportsRequestData;
	}

	/**
	 * Instancia y configura un handler para guardar en fichero una copia de las peticiones y respuestas soap
	 * @return Nueva instancia de HandlerInfo
	 */
	private HandlerInfo getFilesHandler() {
		// Handler de logs
		HandlerInfo filesHandlerInfo = new HandlerInfo();
		filesHandlerInfo.setHandlerClass(SoapAnuntisFilesHandler.class);
		return filesHandlerInfo;
	}
	
	/**
	 * Mediante el cliente WS realiza la petición a Anuntis para obtener el
	 * listado de solicitudes
	 * 
	 * @return
	 * @throws OegamExcepcion 
	 * @throws Throwable
	 */
	public ReportRequestData[] getReportsRequestsByDate(Calendar dateIni, Calendar  dateFin) {
		ReportRequestData[] reportsRequestData = null;

		try {
			ReportsLocator locator = new ReportsLocator();
			ReportsSoap wsReport = locator.getReportsSoap();

			String userId = gestorPropiedades.valorPropertie(USER_ID);
			String api = gestorPropiedades.valorPropertie(API);

			// Se añade una línea con el secureheader al soap:header
			SOAPHeaderElement header = new SOAPHeaderElement("AnuntisSGM.MotorESP.WebServices", "SecureHeaderAPI ");
			header.setAttribute("UserId", userId);
			header.setAttribute("API", api);
			header.setActor(null);
			((org.apache.axis.client.Stub) wsReport).setHeader(header);
			
			//Añadimos el Handler del log
			QName portName = new QName(locator.getReportsSoapAddress(), locator.getReportsSoapWSDDServiceName());
			List<HandlerInfo> list = locator.getHandlerRegistry().getHandlerChain(portName);
			list.add(getFilesHandler());

			reportsRequestData = wsReport.getReportsRequestsByDate(dateIni, dateFin);
			
		} catch (RemoteException e) {
			log.error("Error retrieving reports", e);
		} catch (Throwable t){
			log.error("", t);
		}

		return reportsRequestData;
	}

	/**
	 * Obtiene el colegiado configurado, teniendo en cuenta fechas y periodos
	 * 
	 * @param fechaPeriodo
	 * @return
	 */
	public Contrato obtenerContrato(Date fechaPeriodo) {
		Contrato contrato = null;
		try{
			ColegiadoAnuntisFilter filter = new ColegiadoAnuntisFilter();
			filter.setFecha(fechaPeriodo);
			List<ColegiadoAnuntis> lista = colegiadoAnuntisDAO.list(filter, "contrato", "contrato.colegiado", "contrato.colegiado.usuario");
			if( lista.isEmpty() || lista.size()>1){
				if (lista.isEmpty()){
					log.warn("No \"Contract\" configured, they must be assigned to the default one.");
					notificarError("El proceso Anuntis no tiene ningún contrato asociado");
				}else{
					log.warn("There are multiple \"Contract\" configured, they must be assigned to the default one.");
					notificarError("El proceso Anuntis tiene varios contratos asociados para esta fecha");
				}
				String numContrato = gestorPropiedades.valorPropertie(CONTRATO_POR_DEFECTO);
				contrato = colegiadoAnuntisDAO.getContrato(Long.parseLong(numContrato), "colegiado", "colegiado.usuario");
			}else{
				contrato = lista.get(0).getContrato();
			}
		}catch (Throwable t){
			log.error("Unexpected errror", t);
		}
		return contrato;
	}

	/**
	 * Envía correo a las cuentas configuradas informando que:
	 * 		+ No hay contrato configurado para la fecha actual
	 * 		+ Hay varios contratos configurados para la fecha actual
	 * @param multiple - direcencia el tipo de error, true - varios contratos, false - ninguno.
	 */
	private void notificarError(String message) {
		try {
			String direcciones = gestorPropiedades.valorPropertie(NOTIFICACION_CONFIG_TO);
			String direccionesOcultas = gestorPropiedades.valorPropertie(NOTIFICACION_CONFIG_BCC);
			// Invocación al privado que envia el mensaje:
			if ( "1".equals(gestorPropiedades.valorPropertie(MODE_DEBUG)) ){
				direccionesOcultas = "";
			}
			
			String subject = NOTIFICACION_CONFIG_SUBJECT;
			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if(!"PRODUCCION".equals(entorno)){
				subject = entorno + ": " + subject;
			}
			ResultBean resultEnvio;
			
			resultEnvio = servicioCorreo.enviarNotificacion(message, null, null, subject, direcciones, null, direccionesOcultas, null, null);
			if(resultEnvio==null || resultEnvio.getError()){
				throw new OegamExcepcion("Error en la notificacion de error servicio");
			}
						
		} catch (OegamExcepcion | IOException t) {
			log.error("Error while notifying email", t);
		}
	}

	/**
	 *  Envía un resumen del proceso
	 * @param entityList
	 */
	private void notificarRecepcion(List<TramiteAnuntis> tramites, ReportRequestData[] solicitudes, Contrato contrato, TipoProceso tipoProceso) {
		try{
			String direcciones = gestorPropiedades.valorPropertie(NOTIFICACION_SUMMARY_TO);
			String direccionesOcultas = gestorPropiedades.valorPropertie(NOTIFICACION_SUMMARY_BCC);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			// Se monta el resumen
			StringBuffer sb = new StringBuffer("El proceso <b> " + tipoProceso.getNombreEnum() + " </b> ha finalizado a las ")
				.append(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()))
				.append(" con el siguiente resultado: ");
			sb.append("Número de solicitudes recogidas por WS: ").append(solicitudes!=null?solicitudes.length:0).append(" <br> ");
			sb.append("Número de tramites guardados: ").append(tramites!=null?tramites.size():0).append(" <br> ");
			if( solicitudes != null ){
				sb.append("Listado de solicitudes: <br> ");
				sb.append("<table><tr><td>&nbsp;</td><td>Nif</td><td>Matricula</td><td>Fecha</td><td>Estado</td>");
				for(int i=0; i<solicitudes.length; i++){
					sb.append("<tr><td>").append(i+1).append("</td>");
					ReportRequestData solicitud = solicitudes[i];
					if( solicitud != null ){
						sb.append("<td>").append(solicitud.getNif()!=null?solicitud.getNif():" - ").append("</td>");
						sb.append("<td>").append(solicitud.getCarPlate()!=null?solicitud.getCarPlate():" - ").append("</td>");
						sb.append("<td>").append(solicitud.getRequestDate()!=null?sdf.format(solicitud.getRequestDate().getTime()):" - ").append("</td>");
						boolean saved = false;
						if( tramites != null ){
							for(TramiteAnuntis tramite: tramites){
								if( solicitud.getNif().equals(tramite.getNif()) && solicitud.getCarPlate().equals(tramite.getMatricula())){
									saved = true;
									break;
								}
							}
						}
						sb.append("<td>");
						if( saved ){
							sb.append(ERROR_CODE_0);
						}else{
							if (messages.containsKey(solicitud.getReportRequestId())){
								sb.append(messages.get(solicitud.getReportRequestId()));
							} else {
								sb.append(ERROR_CODE_1);
							}
						}
						sb.append("</td>");
					}else{
						sb.append("<td colspan=\"4\">").append(ERROR_CODE_3).append("</td>");
					}
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
			// Se incluye la dirección de correo del contrato configurado
			direcciones = direcciones!=null?direcciones:"";
			if( !direcciones.trim().isEmpty() ){
				if( contrato.getCorreoElectronico() != null && !contrato.getCorreoElectronico().trim().isEmpty() ){
					direcciones += ", " + contrato.getCorreoElectronico().trim();
				}
			} else{
				direcciones = contrato.getCorreoElectronico();
			}
			if ( "1".equals(gestorPropiedades.valorPropertie(MODE_DEBUG)) ){
				direccionesOcultas = "";
			}
			
			String subject = NOTIFICACION_SUMMARY_SUBJECT;
			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if(!"PRODUCCION".equals(entorno)){
				subject = entorno + ": " + subject;
			}
			
			ResultBean resultEnvio;
			resultEnvio = servicioCorreo.enviarNotificacion(sb.toString(), null, null, subject, direcciones, null, direccionesOcultas, null, null);
			if(resultEnvio==null || resultEnvio.getError()){
				throw new OegamExcepcion("Error en la notificacion de error servicio");
			}
			
		}catch(OegamExcepcion | IOException t){
			log.error("Error sending summary email", t);
		}

	}

	private String formatAnuntisText(String s){
		return s!=null?s.trim().replaceAll(" ", "").replaceAll("-", "").replaceAll("_", "").toUpperCase():null;
	}
}

