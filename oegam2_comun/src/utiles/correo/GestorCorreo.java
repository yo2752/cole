package utiles.correo;

import java.util.ArrayList;
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

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Component
public class GestorCorreo {
	public static final String SEPARATOR_TOKEN = "||";
	private static final String START_HTML = "<html><head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> </head><body style=\"background-color:white\">";
	private static final String SUBJECT = "MENSAJE PLATAFORMA OEGAM";
	private static final String END_HTML = "</body></html>";
	private static final String COMMA_TOKEN = ", ";

	private static final String PROPERTY_KEY_RUTA_IMAGEN = "mail.icogam.imagen.logo.correo";
	private static final String PROPERTY_KEY_TEXTO_PLANO = "mail.icogam.formato.textoPlano";
	private static final String PROPERTY_KEY_MAIL_USUARIO = "mail.usuario";

	private static ILoggerOegam log = LoggerOegam.getLogger(GestorCorreo.class);

	private Session session;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	/**
	 * 
	 * Constructor vacio
	 * 
	 * @throws OegamExcepcion
	 */
	public GestorCorreo() throws OegamExcepcion {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * 
	 * Metodo privado que toma la configuracion del GestorCorreo del properties gestorCorreo.properties
	 * 
	 * @throws OegamExcepcion
	 */
	private Session getSession() throws OegamExcepcion {

		if (session == null) {
			// Lectura del fichero de propiedades con la configuración del correo
			String mailSmtpUser = gestorPropiedades.valorPropertie(PROPERTY_KEY_MAIL_USUARIO);
	
			Properties props = new Properties();
			// Nombre del host de correo, es smtp.gmail.com
			props.setProperty("mail.smtp.host", gestorPropiedades.valorPropertie("mail.host"));
			// TLS si está disponible
			props.setProperty("mail.smtp.starttls.enable", Boolean.TRUE.toString());
			// Puerto de gmail para envio de correos
			props.setProperty("mail.smtp.port", gestorPropiedades.valorPropertie("mail.puerto"));
			// Nombre del usuario
			props.setProperty("mail.smtp.user", mailSmtpUser);
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", Boolean.TRUE.toString());
			// Propiedad que genera una SMTPAddressSucceededException por cada
			// dirección a la que se envia el email.
			// Tras la ejecución todas se añadirán en una lista a una
			// SendFailedException aunque todos
			// los envios resulten correctos:
			props.setProperty("mail.smtp.reportsuccess", Boolean.TRUE.toString());
	
	        String password = gestorPropiedades.valorPropertie("mail.password");
	
			SMTPAuthentication auth = new SMTPAuthentication(mailSmtpUser, password);
	
			session = Session.getDefaultInstance(props, auth);
			//session.setDebug(true);
		}
		return session;
	}

	private boolean isTextoPlano () {
		// Se comprueba si el formato va a ser en texto plano o no
		return "si".equalsIgnoreCase(gestorPropiedades.valorPropertie(PROPERTY_KEY_TEXTO_PLANO));
	}

	private String getFirma() {
		String icogamAddress = gestorPropiedades.valorPropertie("mail.icogam.address");
		String icogamMail = gestorPropiedades.valorPropertie("mail.icogam.mail");
		String icogamPhone = gestorPropiedades.valorPropertie("mail.icogam.phone");
		String icogamFax = gestorPropiedades.valorPropertie("mail.icogam.fax");

		icogamAddress = icogamAddress != null ? icogamAddress : "";
		icogamMail = icogamMail != null ? icogamMail : "";
		icogamPhone = icogamPhone != null ? icogamPhone : "";
		icogamFax = icogamFax != null ? icogamFax : "";

		return new StringBuffer(
				"<span style=\"color: rgb(153, 0, 0);margin-left:20px;\"><i>\"Este mensaje va dirigido, de manera exclusiva, a su destinatario y contiene información confidencial cuya divulgación no está permitida por la ley. En caso de haber recibido este mensaje por error,le rogamos que, de forma inmediata, nos lo comunique mediante correo electrónico y proceda a su eliminación,así como a la de cualquier documento adjunto al mismo.\"</i></span><br><br><img src=\"cid:logo\"><br><span style=\"color: rgb(153, 0, 0);font-size:12pt;font-family:Tunga\"><i>Dirección: ")
				.append(icogamAddress).append("<br>E-mail: ")
				.append(icogamMail).append("<br>Tfno: ")
				.append(icogamPhone).append("<br>Fax: ").append(icogamFax)
				.append("</i></span>").toString();
	}

	/**
	 * 
	 * Envia un correo corporativo con la cabecera "Notificación desde... OEGAM"
	 * 
	 * @param texto
	 * @param textoPlano
	 * @param subject
	 * @param recipient
	 * @param direccionesOcultas
	 * @param adjuntos
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean enviarNotificacion (String texto, Boolean textoPlano, String subject, String recipient, String direccionesOcultas, FicheroBean... adjuntos) throws OegamExcepcion {
		StringBuffer sb = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificaci&oacute;n desde la Oficina Electr&oacute;nica de Gesti&oacute;n Administrativa (OEGAM) <br>")
			.append(texto)
			.append("<br><br></span>");
		return enviarMail(sb.toString(), textoPlano, null, subject, recipient, direccionesOcultas, adjuntos);
	}
	
	/**
	 * 
	 * Envia un correo corporativo con la cabecera "Notificación desde... OEGAM"
	 * 
	 * @param texto
	 * @param textoPlano
	 * @param subject
	 * @param recipient
	 * @param copia
	 * @param direccionesOcultas
	 * @param adjuntos
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean enviarNotificacion (String texto, Boolean textoPlano, String subject, String recipient, String copia, String direccionesOcultas, FicheroBean... adjuntos) throws OegamExcepcion {
		StringBuffer sb = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificaci&oacute;n desde la Oficina Electr&oacute;nica de Gesti&oacute;n Administrativa (OEGAM) <br>")
			.append(texto)
			.append("<br><br></span>");
		return enviarMail(sb.toString(), textoPlano, null, subject, recipient, copia, direccionesOcultas, adjuntos);
	}

	/**
	 * Envia correo corporativo
	 * 
	 * @param propiedadesEmail
	 * @param adjuntos campo opcional, archivo/s a adjuntar en el correo 
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean enviarMail(PropiedadesEmail propiedadesEmail, FicheroBean... adjuntos) throws OegamExcepcion {
		return enviarMail(propiedadesEmail.getCuerpo(),
				propiedadesEmail.getTextoPlano(),
				propiedadesEmail.getRemitente(),
				propiedadesEmail.getAsunto(),
				propiedadesEmail.getPara(),
				propiedadesEmail.getCopia(),
				adjuntos);
	}

	/**
	 * 
	 * Envia un correo corporativo
	 * 
	 * @param texto texto a incluir en el correo, si es null lanza OegamException
	 * @param textoPlano Booleano que indica si el correo es textoplano (-true) o no lo es (lleva firma e imagen embebida -false)
	 * @param from remitente del correo, si es null toma el valor por defecto
	 * @param subject asunto del correo, si es null pone el asunto por defecto "MENSAJE PLATAFORMA OEGAM" 
	 * @param recipient destinatarios del correo, si es null lanza OegamException
	 * @param direccionesOcultas copia oculta
	 * @param adjuntos campo opcional, archivo/s a adjuntar en el correo 
	 * 
	 * @return ResultBean con el estado del envio del correcto. En su listaMensajes se informa (por orden)
	 * del numero de validSentAddresses, numero de validUnSentAddresses y numero de InValidAddresses 
	 * 
	 * @throws OegamExcepcion
	 */
	public ResultBean enviarMail(String texto, Boolean textoPlano, String from, String subject, String recipient, String direccionesOcultas, FicheroBean... adjuntos) throws OegamExcepcion {
		if(texto == null){
			throw new OegamExcepcion("No se ha recibido el texto del mensaje");
		}
		if(recipient == null){
			throw new OegamExcepcion("No se han configurado los destinatarios del mensaje");
		}
		
		// Si no se informa del booleano textoplano, se toma el de por defecto
		Boolean plano = textoPlano!=null?textoPlano:isTextoPlano();

		ResultBean resultBean = null;

		// COMIENZO Envío del fichero que se ha generado por correo electrónico
		try {

			MimeMessage mimeMessage = new MimeMessage(getSession());
			mimeMessage.setFrom(new InternetAddress(from != null ? from : gestorPropiedades.valorPropertie(PROPERTY_KEY_MAIL_USUARIO)));
			mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
			if (direccionesOcultas != null && !direccionesOcultas.isEmpty()) {
				mimeMessage.addRecipients(Message.RecipientType.BCC, direccionesOcultas);
			}
			mimeMessage.setSubject(subject != null ? subject : SUBJECT, Claves.ENCODING_UTF8);

			Multipart multipart = new MimeMultipart("related");

			// Texto del correo
			StringBuffer textoHtml = new StringBuffer(START_HTML).append(texto);
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
				DataSource fds = new FileDataSource(gestorPropiedades.valorPropertie(PROPERTY_KEY_RUTA_IMAGEN));
				bodyPart.setDataHandler(new DataHandler(fds));
				bodyPart.setHeader("Content-ID", "<logo>");
				multipart.addBodyPart(bodyPart);
			}

			// Adjuntos del correo
			if (adjuntos != null) {
				for (FicheroBean adjunto: adjuntos) {
					// Adjuntar fichero
					bodyPart = new MimeBodyPart();
					DataSource source = null;
					if (adjunto.getFichero() != null){
						source = new FileDataSource(adjunto.getFichero());
					}else{
						source = new ByteArrayDataSource(adjunto.getFicheroByte(), "text/plain");
					}
					bodyPart.setDataHandler(new DataHandler(source));
					bodyPart.setFileName(adjunto.getNombreYExtension());
					String contentType = getContentType(adjunto.getNombreDocumento());
					if (contentType != null ) {
						bodyPart.setHeader("ContentType", contentType);
					}
					if (adjunto.getTipoDocumento()!=null && ! adjunto.getTipoDocumento().isEmpty() && adjunto.getTipoDocumento().contains(SEPARATOR_TOKEN)) {
						String[] s = adjunto.getTipoDocumento().split(SEPARATOR_TOKEN);
						bodyPart.setHeader(s[0],  s[1]);
					}
					multipart.addBodyPart(bodyPart);
				}
			}

			mimeMessage.setContent(multipart);
			
			
			// Envia el mensaje:
			Transport.send(mimeMessage, mimeMessage.getAllRecipients());

			// La ejecución de Transport.send siempre lanzará
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

			// ¿Se ha enviado el correo con exito?
			if (validSentAddresses > 0) {
				// Si:
				resultBean.setError(false);
				if (validUnsentAddresses + invalidAddresses > 0) {
					// Alguna de las direcciones no ha recibido el mail
					resultBean.setMensaje("No se ha podido realizar el envío correctamente, alguna direccion de correo no es correcta");
				}
			} else {
				// No se ha realizado el envio de correo con exito
				resultBean.setError(true);
				if (validUnsentAddresses > 0) {
					// Hay direcciones de correo validas
					resultBean.setMensaje("No se ha podido realizar el envío a ninguna dirección de correo.");
				} else {
					// Ninguna direccion es valida
					resultBean.setMensaje("No se ha podido realizar el envío, ninguna direccion de correo es correcta");
				}
			}

			resultBean.setListaMensajes(new ArrayList<String>());
			resultBean.getListaMensajes().add(Integer.toString(validSentAddresses));
			resultBean.getListaMensajes().add(Integer.toString(validUnsentAddresses));
			resultBean.getListaMensajes().add(Integer.toString(invalidAddresses));

		} catch (MessagingException e) {
			log.error("Error en envio de mail", e);
			throw (new OegamExcepcion(e, EnumError.error_NoExisteMensajeEnFicheroErrores));
		}

		return resultBean;
	}
	/**
	 * 
	 * Envia un correo corporativo
	 * 
	 * @param texto texto a incluir en el correo, si es null lanza OegamException
	 * @param textoPlano Booleano que indica si el correo es textoplano (-true) o no lo es (lleva firma e imagen embebida -false)
	 * @param from remitente del correo, si es null toma el valor por defecto
	 * @param subject asunto del correo, si es null pone el asunto por defecto "MENSAJE PLATAFORMA OEGAM" 
	 * @param recipient destinatarios del correo, si es null lanza OegamException
	 * @param con copia a los siguientes direcciones
	 * @param direccionesOcultas copia oculta
	 * @param adjuntos campo opcional, archivo/s a adjuntar en el correo 
	 * 
	 * @return ResultBean con el estado del envio del correcto. En su listaMensajes se informa (por orden)
	 * del numero de validSentAddresses, numero de validUnSentAddresses y numero de InValidAddresses 
	 * 
	 * @throws OegamExcepcion
	 */
	public ResultBean enviarMail(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, FicheroBean... adjuntos) throws OegamExcepcion {
		if(texto == null){
			throw new OegamExcepcion("No se ha recibido el texto del mensaje");
		}
		if(recipient == null){
			throw new OegamExcepcion("No se han configurado los destinatarios del mensaje");
		}

		// Si no se informa del booleano textoplano, se toma el de por defecto
		Boolean plano = textoPlano!=null?textoPlano:isTextoPlano();

		ResultBean resultBean = null;

		// COMIENZO Envío del fichero que se ha generado por correo electrónico
		try {

			MimeMessage mimeMessage = new MimeMessage(getSession());
			mimeMessage.setFrom(new InternetAddress(from != null ? from : gestorPropiedades.valorPropertie(PROPERTY_KEY_MAIL_USUARIO)));
			mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
			if (copia != null && !copia.isEmpty()) {
				mimeMessage.addRecipients(Message.RecipientType.CC, copia);
			}
			if (direccionesOcultas != null && !direccionesOcultas.isEmpty()) {
				mimeMessage.addRecipients(Message.RecipientType.BCC, direccionesOcultas);
			}
			mimeMessage.setSubject(subject != null ? subject : SUBJECT, Claves.ENCODING_UTF8);

			Multipart multipart = new MimeMultipart("related");

			// Texto del correo
			StringBuffer textoHtml = new StringBuffer(START_HTML).append(texto);
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
				DataSource fds = new FileDataSource(gestorPropiedades.valorPropertie(PROPERTY_KEY_RUTA_IMAGEN));
				bodyPart.setDataHandler(new DataHandler(fds));
				bodyPart.setHeader("Content-ID", "<logo>");
				multipart.addBodyPart(bodyPart);
			}

			// Adjuntos del correo
			for (FicheroBean adjunto: adjuntos) {
				// Adjuntar fichero
				bodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(adjunto.getFichero());
				bodyPart.setDataHandler(new DataHandler(source));
				bodyPart.setFileName(adjunto.getNombreDocumento());
				String contentType = getContentType(adjunto.getNombreDocumento());
				if (contentType != null ) {
					bodyPart.setHeader("ContentType", contentType);
				}
				if (adjunto.getTipoDocumento()!=null && ! adjunto.getTipoDocumento().isEmpty() && adjunto.getTipoDocumento().contains(SEPARATOR_TOKEN)) {
					String[] s = adjunto.getTipoDocumento().split(SEPARATOR_TOKEN);
					bodyPart.setHeader(s[0],  s[1]);
				}
				multipart.addBodyPart(bodyPart);
			}

			mimeMessage.setContent(multipart);

			// Envia el mensaje:
			Transport.send(mimeMessage, mimeMessage.getAllRecipients());

			// La ejecución de Transport.send siempre lanzará
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

			// ¿Se ha enviado el correo con exito?
			if (validSentAddresses > 0) {
				// Si:
				resultBean.setError(false);
				if (validUnsentAddresses + invalidAddresses > 0) {
					// Alguna de las direcciones no ha recibido el mail
					resultBean.setMensaje("No se ha podido realizar el envío correctamente, alguna direccion de correo no es correcta");
				}
			} else {
				// No se ha realizado el envio de correo con exito
				resultBean.setError(true);
				if (validUnsentAddresses > 0) {
					// Hay direcciones de correo validas
					resultBean.setMensaje("No se ha podido realizar el envío a ninguna dirección de correo.");
				} else {
					// Ninguna direccion es valida
					resultBean.setMensaje("No se ha podido realizar el envío, ninguna direccion de correo es correcta");
				}
			}

			resultBean.setListaMensajes(new ArrayList<String>());
			resultBean.getListaMensajes().add(Integer.toString(validSentAddresses));
			resultBean.getListaMensajes().add(Integer.toString(validUnsentAddresses));
			resultBean.getListaMensajes().add(Integer.toString(invalidAddresses));

		} catch (MessagingException e) {
			log.error("Error en envio de mail", e);
			throw (new OegamExcepcion(e, EnumError.error_NoExisteMensajeEnFicheroErrores));
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
		// Direcciones validas a las que se ha enviado el correo
		if (log.isInfoEnabled()
				&& sendFailedException.getValidSentAddresses() != null
				&& sendFailedException.getValidSentAddresses().length > 0) {
			StringBuffer sb = new StringBuffer("Se ha enviado correctamente el mensaje a la/s dirección/es: ");
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
			StringBuffer sb = new StringBuffer("La siguiente dirección de correo no es válida: ");
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
			if (ConstantesGestorFicheros.EXTENSION_PDF.equalsIgnoreCase(sufijo)){
				result = "application/pdf";
			} else if (ConstantesGestorFicheros.EXTENSION_HTML.equalsIgnoreCase(sufijo)){
				result = "text/html; charset=UTF-8";
			} else if (ConstantesGestorFicheros.EXTENSION_XML.equalsIgnoreCase(sufijo)){
				result = "text/xml";
			} else if (ConstantesGestorFicheros.EXTENSION_XLS.equalsIgnoreCase(sufijo)){
				result = "application/xls";
			} else {
				result = "text/plain";
			}
		}else{
			return "text/plain";
		}
		return result;
	}

}
