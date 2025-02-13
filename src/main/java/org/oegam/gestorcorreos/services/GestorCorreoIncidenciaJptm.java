package org.oegam.gestorcorreos.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.oegam.gestorcorreos.beans.CorreoRequest;
import org.oegam.gestorcorreos.beans.FicheroAdjunto;
import org.oegam.gestorcorreos.beans.ResultBean;
import org.oegam.gestorcorreos.beans.SMTPAuthentication;

public class GestorCorreoIncidenciaJptm {
	
	private static final String START_HTML = "<html><head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> </head><body style=\"background-color:white\">";
	private static final String SUBJECT = "MENSAJE PLATAFORMA OEGAM";
	private static final String END_HTML = "</body></html>";
	private static final String COMMA_TOKEN = ", ";

	public static final String EXTENSION_ZIP = ".zip";
	public static final String EXTENSION_PDF = ".pdf";
	public static final String EXTENSION_XML = ".xml";
	public static final String EXTENSION_HTML = ".html";
	public static final String EXTENSION_XLS = ".xls";
	public static final String EXTENSION_DGT = ".dgt";
	public static final String EXTENSION_CSV = ".csv";
	public static final String EXTENSION_TXT = ".txt";
	public static final String EXTENSION_BM = ".bm";
	public static final String EXTENSION_DATA = ".data";
	
	public static HashMap<String,String> mapaPropiedades;

	public static final Logger log = Logger.getLogger(GestorCorreoIncidenciaJptm.class);
	
	private Session session;

	private static GestorCorreoIncidenciaJptm instance = null;
	
	protected GestorCorreoIncidenciaJptm() {
		super();
		if(mapaPropiedades==null){
			try {
				cargarPropiedades();
			} catch (IOException e) {
				log.error("Error al cargar las propiedades");
			}
		}
	}

	public static GestorCorreoIncidenciaJptm getInstance() {
		if(instance == null) {
	    	instance = new GestorCorreoIncidenciaJptm();
	    }
		return instance;
	}

	/**
	 * 
	 * Metodo privado que toma la configuracion del GestorCorreo del properties gestorCorreo.properties
	 * 
	 * @throws OegamExcepcion
	 */
	private Session getSession(){
		if (session == null) {
			// Lectura del fichero de propiedades con la configuracion del correo
			String mailSmtpUser =  mapaPropiedades.get("mail_usuario_incidencias_jptm");
	
			Properties props = new Properties();
			// Nombre del host de correo, es smtp.gmail.com
			props.setProperty("mail.smtp.host",  mapaPropiedades.get("mail_host"));
			// TLS si esta disponible
			props.setProperty("mail.smtp.starttls.enable", Boolean.TRUE.toString());
			// Puerto de gmail para envio de correos
			props.setProperty("mail.smtp.port",  mapaPropiedades.get("mail_puerto"));
			// Nombre del usuario
			props.setProperty("mail.smtp.user", mailSmtpUser);
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", Boolean.TRUE.toString());
			// Propiedad que genera una SMTPAddressSucceededException por cada
			// direccion a la que se envia el email.
			// Tras la ejecucion todas se anadiran en una lista a una
			// SendFailedException aunque todos
			// los envios resulten correctos:
			props.setProperty("mail.smtp.reportsuccess", Boolean.TRUE.toString());
		//	props.setProperty("mail.smtp.ssl.trust", "smtp.office365.com");
		//	props.setProperty("https.protocols", "TLSv1.2");
	
	        String password = mapaPropiedades.get("mail_password_incidencias_jptm");
			props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
	        props.setProperty("mail.smtp.ssl.trust", "in-v3.mailjet.com");
		
	        SMTPAuthentication auth = new SMTPAuthentication(mailSmtpUser, password);
	
			session = Session.getInstance(props, auth);
			//session.setDebug(true);
			log.info("* * * * * * * * * *");
			log.info("DATOS DE LA CONEXION:");
			log.info("usuario smtp:" + props.getProperty("mail.smtp.user"));
			log.info("Host smtp:" + props.getProperty("mail.smtp.host"));
			log.info("TLS:" + props.getProperty("mail.smtp.starttls.enable"));
			log.info("Puerto:" + props.getProperty("mail.smtp.port"));
			log.info("Autenticacion smtp:" + props.getProperty("mail.smtp.auth"));
			
			log.info("Report success:" + props.getProperty("mail.smtp.reportsuccess"));
			log.info("Protocolo:" + props.getProperty("https.protocols"));
			log.info("authorizationID:" + props.getProperty("mail.smtp.sasl.authorizationid"));
			log.info("* * * * * * * * * *");
		}
		return session;
	}

	private boolean isTextoPlano () {
		return "si".equalsIgnoreCase(mapaPropiedades.get("mail.texto.plano"));
	}

	private String getFirma() {
		String icogamAddress = mapaPropiedades.get("mail.icogam.address");
		String icogamMail = mapaPropiedades.get("mail.icogam.mail");
		String icogamPhone = mapaPropiedades.get("mail.icogam.phone");
		String icogamFax = mapaPropiedades.get("mail.icogam.fax");
		
		icogamAddress = icogamAddress != null ? icogamAddress : "";
		icogamMail = icogamMail != null ? icogamMail : "";
		icogamPhone = icogamPhone != null ? icogamPhone : "";
		icogamFax = icogamFax != null ? icogamFax : "";

		return new StringBuffer(
				"<br><br><span style=\"color: rgb(153, 0, 0);margin-left:20px;\"><i>\"Este mensaje va dirigido, de manera exclusiva, a su destinatario y contiene informacion confidencial cuya divulgacion no esta permitida por la ley. En caso de haber recibido este mensaje por error,le rogamos que, de forma inmediata, nos lo comunique mediante correo electronico y proceda a su eliminacion,asi como a la de cualquier documento adjunto al mismo.\"</i></span><br><br><img src=\"cid:logo\"><br><span style=\"color: rgb(153, 0, 0);font-size:12pt;font-family:Tunga\"><i>Direccion: ")
				.append(icogamAddress).append("<br>E-mail: ")
				.append(icogamMail).append("<br>Tfno: ")
				.append(icogamPhone).append("<br>Fax: ").append(icogamFax)
				.append("</i></span>").toString();
	}

	/**
	 * 
	 * Envia un correo corporativo
	 * 
	 * @param correo
	 * @param textoPlano Booleano que indica si el correo es textoplano (-true) o no lo es (lleva firma e imagen embebida -false)
	 * @param from remitente del correo, si es null toma el valor por defecto
	 * @param subject asunto del correo, si es null pone el asunto por defecto "MENSAJE PLATAFORMA OEGAM" 
	 * @param recipient destinatarios del correo, si es null lanza OegamException
	 * @param con copia a los siguientes direcciones
	 * @param direccionesOcultas copia oculta
	 * @param adjuntos campo opcional, archivo/s a adjuntar en el correo 
	 * @param origen 
	 * @return ResultBean con el estado del envio del correcto. En su listaMensajes se informa (por orden)
	 * del numero de validSentAddresses, numero de validUnSentAddresses y numero de InValidAddresses 
	 * 
	 * @throws OegamExcepcion
	 */
	public ResultBean enviarMail(CorreoRequest correo){
		
		// Si no se informa del booleano textoplano, se toma el de por defecto
		Boolean plano = correo.getTextoPlano()!=null?correo.getTextoPlano():isTextoPlano();

		ResultBean resultBean = null;

		// COMIENZO Envio del fichero que se ha generado por correo electronico
		try {

			MimeMessage mimeMessage = new MimeMessage(getSession());
			mimeMessage.setFrom(new InternetAddress((correo.getFrom() != null && !correo.getFrom().trim().isEmpty())? correo.getFrom() :  mapaPropiedades.get("from_incJptm")));
			mimeMessage.addRecipients(Message.RecipientType.TO, correo.getRecipient());
			if (correo.getCopia() != null && !correo.getCopia().isEmpty()) {
				mimeMessage.addRecipients(Message.RecipientType.CC, correo.getCopia());
			}
			if (correo.getDireccionesOcultas() != null && !correo.getDireccionesOcultas().isEmpty()) {
				mimeMessage.addRecipients(Message.RecipientType.BCC, correo.getDireccionesOcultas());
			}
			mimeMessage.setSubject(correo.getSubject() != null ? correo.getSubject() : SUBJECT, "UTF-8");

			Multipart multipart = new MimeMultipart("related");

			// Texto del correo
			StringBuffer textoHtml = new StringBuffer(START_HTML).append(correo.getTexto());
			if (!plano) {
				textoHtml.append(getFirma());
			}
			textoHtml.append(END_HTML);
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(textoHtml.toString(), "text/html; charset=UTF-8");
			multipart.addBodyPart(bodyPart);

			if (!plano) {
				// Imagen embebida de la firma
				bodyPart = new MimeBodyPart();
				DataSource fds = new FileDataSource(mapaPropiedades.get("mail.icogam.imagen.logo.correo"));
				bodyPart.setDataHandler(new DataHandler(fds));
				bodyPart.setHeader("Content-ID", "<logo>");
				multipart.addBodyPart(bodyPart);
			}

			// Adjuntos del correo
			if(correo.getAdjuntos()!=null){
				for (FicheroAdjunto adjunto: correo.getAdjuntos()) {
					// Adjuntar fichero
					bodyPart = new MimeBodyPart();
					//DataSource source = new FileDataSource(adjunto.getFichero());
					DataSource source = new ByteArrayDataSource(adjunto.getFicheroByte(), "text/plain");
					bodyPart.setDataHandler(new DataHandler(source));
					bodyPart.setFileName(adjunto.getNombreDocumento());
					String contentType = getContentType(adjunto.getNombreDocumento());
					if (contentType != null ) {
						bodyPart.setHeader("ContentType", contentType);
					}
//					if (adjunto.getTipoDocumento()!=null && ! adjunto.getTipoDocumento().isEmpty() && adjunto.getTipoDocumento().contains(SEPARATOR_TOKEN)) {
//						String[] s = adjunto.getTipoDocumento().split(SEPARATOR_TOKEN);
//						bodyPart.setHeader(s[0],  s[1]);
//					}
					multipart.addBodyPart(bodyPart);
				}
			}

			mimeMessage.setContent(multipart);

			// Envia el mensaje:
			Transport.send(mimeMessage, mimeMessage.getAllRecipients());

			// La ejecucion de Transport.send siempre lanzara
			// SendFailedException
			// por el establecimiento a true de la propiedad:
			// 'mail.smtp.reportsuccess'

		} catch (SendFailedException sendFailedException) {

			// Resumen del envio del correo
			logResumen(sendFailedException);

			// Comprobar si se han enviado todos los correos correctamente
			resultBean = new ResultBean();

			// Comprobar exito o no
			int validSentAddresses = sendFailedException.getValidSentAddresses() != null ? sendFailedException.getValidSentAddresses().length : 0;
			int validUnsentAddresses = sendFailedException.getValidUnsentAddresses() != null ? sendFailedException.getValidUnsentAddresses().length : 0;
			int invalidAddresses = sendFailedException.getInvalidAddresses() != null ? sendFailedException.getInvalidAddresses().length : 0;

			// Se ha enviado el correo con exito
			if (validSentAddresses > 0) {
				// Si:
				resultBean.setError(false);
				if (validUnsentAddresses + invalidAddresses > 0) {
					// Alguna de las direcciones no ha recibido el mail
					resultBean.setMensaje("No se ha podido realizar el envio correctamente, alguna direccion de correo no es correcta");
				}
			} else {
				// No se ha realizado el envio de correo con exito
				resultBean.setError(true);
				if (validUnsentAddresses > 0) {
					// Hay direcciones de correo validas
					resultBean.setMensaje("No se ha podido realizar el envio a ninguna direccion de correo.");
				} else {
					// Ninguna direccion es valida
					resultBean.setMensaje("No se ha podido realizar el envio, ninguna direccion de correo es correcta");
				}
			}

			resultBean.setListaMensajes(new ArrayList<String>());
			resultBean.getListaMensajes().add(Integer.toString(validSentAddresses));
			resultBean.getListaMensajes().add(Integer.toString(validUnsentAddresses));
			resultBean.getListaMensajes().add(Integer.toString(invalidAddresses));

		} catch (MessagingException e) {
			log.error("Error en envio de mail", e);
			resultBean = new ResultBean();
			resultBean.setError(true);
			resultBean.setMensaje(e.getMessage());
			//throw (new OegamExcepcion(e, EnumError.error_NoExisteMensajeEnFicheroErrores));
		}

		return resultBean;
	}

	/**
	 * 
	 * Traza en el log el resumen del envio del correo, indicando direcciones de
	 * correo validas/invalidas y exito o no del envio
	 * 
	 * @param sendFailedException
	 */
	private void logResumen(SendFailedException sendFailedException) {
		if (sendFailedException == null) {
			return;
		}
		log.info(sendFailedException);
		// Direcciones validas a las que se ha enviado el correo
		if (log.isInfoEnabled()
				&& sendFailedException.getValidSentAddresses() != null
				&& sendFailedException.getValidSentAddresses().length > 0) {
			StringBuffer sb = new StringBuffer("Se ha enviado correctamente el mensaje a la/s direccion/es: ");
			boolean flagFirst = true;
			for (Address address : sendFailedException.getValidSentAddresses()) {
				if (flagFirst) {
					flagFirst = false;
				} else {
					sb.append(COMMA_TOKEN);
				}
				sb.append(address.toString());
			}
			log.info(sb.toString());
		}
		// Direcciones validas a las que no se ha enviado el correo
		if (sendFailedException.getValidUnsentAddresses() != null && sendFailedException.getValidUnsentAddresses().length > 0) {
			StringBuffer sb = new StringBuffer("No se ha podido enviar el mensaje a la/s siguiente/s direccion/es: ");
			boolean flagFirst = true;
			for (Address address : sendFailedException.getValidUnsentAddresses()) {
				if (flagFirst) {
					flagFirst = false;
				} else {
					sb.append(COMMA_TOKEN);
				}
				sb.append(address.toString());
			}
			log.error(sb.toString());
		}
		// Direcciones invalidas
		if (sendFailedException.getInvalidAddresses() != null && sendFailedException.getInvalidAddresses().length > 0) {
			StringBuffer sb = new StringBuffer("La siguiente direccion de correo no es valida: ");
			boolean flagFirst = true;
			for (Address address : sendFailedException.getInvalidAddresses()) {
				if (flagFirst) {
					flagFirst = false;
				} else {
					sb.append(COMMA_TOKEN);
				}
				sb.append(address.toString());
			}
			log.error(sb.toString());
		}
	}

	/**
	 * Obtener el tipo de contenido que se envia
	 * 
	 * @param name
	 * @return
	 */
	private String getContentType(String name) {
		String result = null;
		if (name != null && name.contains(".")) {
			String sufijo = name.substring(name.lastIndexOf("."));
			if (EXTENSION_PDF.equalsIgnoreCase(sufijo)){
				result = "application/pdf";
			} else if (EXTENSION_HTML.equalsIgnoreCase(sufijo)){
				result = "text/html; charset=UTF-8";
			} else if (EXTENSION_XML.equalsIgnoreCase(sufijo)){
				result = "text/xml";
			} else if (EXTENSION_XLS.equalsIgnoreCase(sufijo)){
				result = "application/xls";
			} else {
				result = "text/plain";
			}
		}else{
			return "text/plain";
		}
		return result;
	}

	private void cargarPropiedades() throws IOException{
		Properties oegamProperties = new Properties();
		oegamProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("oegamCorreo.properties"));
		@SuppressWarnings("unchecked")
		Enumeration<String> propiedadesFichero = (Enumeration<String>) oegamProperties.propertyNames();
		mapaPropiedades = new HashMap<>();
		while(propiedadesFichero.hasMoreElements()){
			String propiedadFichero = (String)propiedadesFichero.nextElement();
			mapaPropiedades.put(propiedadFichero, oegamProperties.getProperty(propiedadFichero)); 
		}
	}

}
