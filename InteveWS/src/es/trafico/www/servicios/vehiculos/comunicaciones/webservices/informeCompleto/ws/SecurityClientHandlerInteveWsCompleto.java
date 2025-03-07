package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.ws;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.util.Set;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
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

import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.xml.sax.InputSource;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import viafirma.utilidades.UtilesViafirma;

/**
 * Clase encargada de securizar los mensajes SOAP de petición realizados desde
 * un cliente.
 * 
 * Se tiene que iniciar con un mapa con la configuración del almacen de claves
 * 		keystoreLocation
 * 		keystoreType
 * 		keystorePassword
 *		keystoreCertAlias
 *		keystoreCertPassword 
 * 
 */
public class SecurityClientHandlerInteveWsCompleto implements SOAPHandler<SOAPMessageContext> {
	private static ILoggerOegam log = LoggerOegam.getLogger(SecurityClientHandlerInteveWsCompleto.class);
	private static final String ERROR_GENERICO = "ha ocurrido un error en al firmar el mensaje SOAP";
	private static final String CERTIFICADO_PROVISIONAL = "certificado_provisional";
	private static final String INICIO_TAG_CERTIFICADO = "<ds:X509Certificate>";
	private static final String FIN_TAG_CERTIFICADO = "</ds:X509Certificate>";
	private String alias;
	public static final String ALIAS_KEY = "aliasKey";
	public static final String PASSWORD_BINARY_TOKEN = "passwordBinaryToken";
	public static final String USER_BINARY_TOKEN = "userBinaryToken";
	
	/**
	 * Securiza, mediante el tag BinarySecurityToken y firma una petición SOAP
	 * no securizada.
	 * 
	 * @param soapEnvelopeRequest
	 *            Documento xml que representa la petición SOAP sin securizar.
	 * @return Un mensaje SOAP que contiene la petición SOAP de entrada
	 *         securizada mediante el tag BinarySecurityToken.
	 */
	private SOAPEnvelope createFirma(SOAPMessageContext msgContext) {
		SOAPEnvelope soapEnvelope = null;
		try {
			soapEnvelope = msgContext.getMessage().getSOAPPart().getEnvelope();
			soapEnvelope.addHeader();
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
			System.out.println(envelopeStringWriter.toString());
			if(utilesViaFirma.firmarPruebaCertificadoCaducidad(new String(envelopeStringWriter.toString().getBytes("UTF-8"),"UTF-8"), alias)==null){
				throw new Exception("No se ha podido comprobar la caducidad de la firma");
				
			}
			byte[] firma = utilesViaFirma.firmarInteveCompleto(envelopeStringWriter.toString().getBytes("UTF-8"), alias);
			
			// meter al alias en el binary token...
			firma = actualizarCertificadoBinaryToken(firma);
						
			// Actualizar peticion
			String envelopeFirmado = new String(firma, "UTF-8");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			/*dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");*/
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			DOMSource domSource = new DOMSource(db.parse(new InputSource(new StringReader(envelopeFirmado))));
			msgContext.getMessage().getSOAPPart().setContent(domSource);
						
			return soapEnvelope;


		} catch (IOException e) {
			log.error(ERROR_GENERICO, e);
		} catch (CertificateEncodingException e) {
			log.error(ERROR_GENERICO, e);
		} catch (SOAPException e) {
			log.error(ERROR_GENERICO, e);
		/*} catch (SAXException e) {
			log.error(ERROR_GENERICO, e);*/
		} catch (InvalidCanonicalizerException e) {
			log.error(ERROR_GENERICO, e);
		} catch (CanonicalizationException e) {
			log.error(ERROR_GENERICO, e);
		} catch (NoSuchAlgorithmException e) {
			log.error(ERROR_GENERICO, e);
		} catch (InvalidAlgorithmParameterException e) {
			log.error(ERROR_GENERICO, e);
		} catch (MarshalException e) {
			log.error(ERROR_GENERICO, e);
		} catch (XMLSignatureException e) {
			log.error(ERROR_GENERICO, e);
		} catch (Exception e) {
			log.error(ERROR_GENERICO, e);
		}
		return null;
	}
	
	private byte[] actualizarCertificadoBinaryToken(byte[] firma) throws UnsupportedEncodingException {
		String cadena = new String (firma, "UTF-8");
		String certificado = cadena.substring(cadena.indexOf(INICIO_TAG_CERTIFICADO)+ INICIO_TAG_CERTIFICADO.length(), cadena.indexOf(FIN_TAG_CERTIFICADO));
		certificado = certificado.replace("\n", "").replace("\r", "");
		return cadena.replace(CERTIFICADO_PROVISIONAL, certificado).getBytes("UTF-8");
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
		binarySecurityTokenElement.setAttribute("wsu:Id", gestorPropiedades.valorPropertie("securityToken.id.generico"));
		binarySecurityTokenElement.setAttribute("xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
		binarySecurityTokenElement.addTextNode(CERTIFICADO_PROVISIONAL);
			        
	}
	
	@Override
	public Set<QName> getHeaders() {
		return null;
	}
	
	@Override
	public boolean handleMessage(SOAPMessageContext arg0) {
		Boolean isRequest = (Boolean) arg0.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (isRequest) {
			if (arg0.containsKey("aliasKey")) {
				this.alias = (String) arg0.get("aliasKey");
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





	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}


	@Override
	public void close(javax.xml.ws.handler.MessageContext context) {
	}
}
