package es.iam.sbae.sbintopexterna.handler;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.xml.sax.InputSource;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import viafirma.utilidades.UtilesViafirma;

public class SecurityHandlerNew implements SOAPHandler<SOAPMessageContext> {

	private static final ILoggerOegam log = LoggerOegam.getLogger(SecurityHandlerNew.class);
	public static final String ALIAS_KEY = "aliasKey";
	public static final String PASSWORD_BINARY_TOKEN = "passwordBinaryToken";
	public static final String USER_BINARY_TOKEN = "userBinaryToken";

	private static final String CERTIFICADO_PROVISIONAL = "certificado_provisional";
	private static final String INICIO_TAG_CERTIFICADO = "<ds:X509Certificate>";
	private static final String FIN_TAG_CERTIFICADO = "</ds:X509Certificate>";
	private static final String ERROR_GENERICO = "Ha ocurrido un error al firmar el mensaje SOAP";
	private String alias;
	private String passwordBinaryToken;
	private String userBinaryToken;

	public void close(MessageContext arg0) {
	}

	public boolean handleFault(SOAPMessageContext arg0) {
		return true;
	}

	public boolean handleMessage(SOAPMessageContext arg0) {
		Boolean isRequest = (Boolean) arg0.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (isRequest) {
			if (arg0.containsKey("aliasKey")) {
				this.alias = (String) arg0.get("aliasKey");
			}
			if (arg0.containsKey("passwordBinaryToken")) {
				this.passwordBinaryToken = (String) arg0.get("passwordBinaryToken");
			}
			if (arg0.containsKey("userBinaryToken")) {
				this.userBinaryToken = (String) arg0.get("userBinaryToken");
			}

			SOAPEnvelope signedSoapEnvelope = null;
			signedSoapEnvelope = createFirma(arg0);

			if (signedSoapEnvelope != null) {
				return true;
			} else
				return false;
		}

		return true;
	}

	public Set<QName> getHeaders() {
		return null;
	}

	private SOAPEnvelope createFirma(SOAPMessageContext msgContext) {
		SOAPEnvelope soapEnvelope = null;
		try {
			soapEnvelope = msgContext.getMessage().getSOAPPart().getEnvelope();
			soapEnvelope.addHeader();

			// Binary Token
			createBinaryToken(soapEnvelope);

			// Adding id "Body" to soapenv:body
			if (soapEnvelope.getBody().getAttribute("Id") == null) {
				soapEnvelope.getBody().addAttribute(soapEnvelope.createName("Id"), "Body");
			} else {
				soapEnvelope.getBody().setAttribute("Id", "Body");
			}

			UtilesViafirma utilesViaFirma = new UtilesViafirma();

			final StringWriter envelopeStringWriter = new StringWriter();
			try {
				TransformerFactory.newInstance().newTransformer().transform(
						new DOMSource(msgContext.getMessage().getSOAPPart().getEnvelope()),
						new StreamResult(envelopeStringWriter));
			} catch (TransformerException e) {
				throw new RuntimeException(e);
			}

			//Escapamos caracteres que no valida el WS antes de firmar
			String envelopeFirmar = escapeCaracteresEnvelope(envelopeStringWriter.toString());

			byte[] firma = utilesViaFirma.firmarIvtm(envelopeFirmar.getBytes(StandardCharsets.UTF_8), alias);

			// Meter al alias en el binary token...
			firma = actualizarCertificadoBinaryToken(firma);

			// Actualizamos envelope firmado
			String envelopeFirmado = new String(firma, StandardCharsets.UTF_8);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		/*	dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");*/
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			DOMSource domSource = new DOMSource(db.parse(new InputSource(new StringReader(envelopeFirmado))));
			msgContext.getMessage().getSOAPPart().setContent(domSource);
			// msgContext.getMessage().writeTo(System.out);

			return soapEnvelope;

		} catch (SOAPException e) {
			log.error(ERROR_GENERICO, e);
		} catch (UnsupportedEncodingException e) {
			log.error(ERROR_GENERICO, e);
		} catch (Exception e) {
			log.error(ERROR_GENERICO, e);
		}
		return soapEnvelope;
	}

	private String escapeCaracteresEnvelope (String envelopeStringAux){
		envelopeStringAux =  envelopeStringAux.replaceAll("&lt;", "<");
		envelopeStringAux = envelopeStringAux.replaceAll("&gt;", ">");
		envelopeStringAux = envelopeStringAux.replaceAll("xsi:type="+"\""+ "xs:string" + "\"", "");
		return envelopeStringAux;
	}

	private byte[] actualizarCertificadoBinaryToken(byte[] firma) {
		String cadena = new String (firma, StandardCharsets.UTF_8);
		String certificado = cadena.substring(cadena.indexOf(INICIO_TAG_CERTIFICADO)+ INICIO_TAG_CERTIFICADO.length(), cadena.indexOf(FIN_TAG_CERTIFICADO));
		certificado = certificado.replace("\n", "").replace("\r", "");
		return cadena.replace(CERTIFICADO_PROVISIONAL, certificado).getBytes(StandardCharsets.UTF_8);
	}

	private void createBinaryToken(SOAPEnvelope soapEnvelope) throws SOAPException {
		GestorPropiedades gestorPropiedades = (GestorPropiedades) ContextoSpring.getInstance().getBean("gestorPropiedades");
		SOAPHeaderElement securityTag = soapEnvelope.getHeader().addHeaderElement(
				soapEnvelope.createName("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
		securityTag.addAttribute(soapEnvelope.createName("Id"), "Security");

		// BinarySecurityToken
		SOAPElement binarySecurityTokenElement = securityTag.addChildElement("BinarySecurityToken", "wsse");
		binarySecurityTokenElement.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
		binarySecurityTokenElement.setAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
		binarySecurityTokenElement.setAttribute("wsu:Id", gestorPropiedades.valorPropertie("securityToken.generico"));
		binarySecurityTokenElement.setAttribute("xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
		binarySecurityTokenElement.addTextNode(CERTIFICADO_PROVISIONAL);

		SOAPElement usernameToken =	securityTag.addChildElement("UsernameToken", "wsse");
		securityTag.removeNamespaceDeclaration("SOAP-ENV");
		// usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

		SOAPElement username = usernameToken.addChildElement("Username", "wsse");
		username.addTextNode(this.userBinaryToken);

		SOAPElement password = usernameToken.addChildElement("Password", gestorPropiedades.valorPropertie("securityToken.pass"));
		password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");

		password.addTextNode(this.passwordBinaryToken);
	}

	public ILoggerOegam getLogger() {
		return log;
	}

}