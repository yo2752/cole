package es.trafico.cliente.vehiculos.custodiaitv.webservice;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Properties;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeaderElement;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;

/**
 * Clase encargada de securizar los mensajes SOAP de petición realizados desde
 * un cliente.
 * 
 * 
 */
public class SecurityClientHandler extends BasicHandler {
	static {
		org.apache.xml.security.Init.init();
	}

	private static final long serialVersionUID = 1L;

	// Opciones de seguridad

	// Localización del keystore con certificado y clave privada de usuario
	private String keystoreLocation = null;

	// Tipo de keystore
	private String keystoreType = null;

	// Clave del keystore
	private String keystorePassword = null;

	// Alias del certificado usado para firmar el tag soapBody de la petición y
	// que será alojado en el token BinarySecurityToken
	private String keystoreCertAlias = null;

	// Password del certificado usado para firmar el tag soapBody de la petición
	// y que será alojado en el token BinarySecurityToken
	private String keystoreCertPassword = null;

	private KeyStore almacen;
	private PrivateKey clavePrivada;
	private java.security.cert.X509Certificate certificado;

	/**
	 * Constructor que inicializa el atributo securityOption
	 * 
	 * @param securityOption opción de seguridad.
	 * @throws Exception
	 */
	public SecurityClientHandler(Properties config) {
		if (config == null) {
			System.err.println("Fichero de configuracion de propiedades nulo");
		}
		try {
			keystoreLocation = config.getProperty("security.keystore.location");
			keystoreType = config.getProperty("security.keystore.type");
			keystorePassword = config.getProperty("security.keystore.password");
			keystoreCertAlias = config.getProperty("security.keystore.cert.alias");
			keystoreCertPassword = config.getProperty("security.keystore.cert.password");

		} catch (Exception e) {
			System.err.println("Error leyendo el fichero de configuración de securización");
		}
	}

	public void invoke(MessageContext msgContext) throws AxisFault {
		try {
			((SOAPPart) msgContext.getRequestMessage().getSOAPPart())
					.setCurrentMessage(
							this.createBinarySecurityToken(msgContext),
							SOAPPart.FORM_SOAPENVELOPE);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Securiza, mediante el tag BinarySecurityToken y firma una petición SOAP
	 * no securizada.
	 * 
	 * @param soapEnvelopeRequest
	 *            Documento xml que representa la petición SOAP sin securizar.
	 * @return Un mensaje SOAP que contiene la petición SOAP de entrada
	 *         securizada mediante el tag BinarySecurityToken.
	 */
	public SOAPEnvelope createBinarySecurityToken(MessageContext msgContext) {

		try {
			return createFirma(msgContext);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		}
	}

	// protected static String convertToPem(X509Certificate cert) throws
	// CertificateEncodingException {
	// String pemCertPre =
	// DatatypeConverter.printBase64Binary(cert.getEncoded());
	// return pemCertPre;
	// }

	private void GeneraCertificado() throws Exception {
		// Extrae la clave privada y el certificado del almacén de claves
		try {
			almacen = KeyStore.getInstance(keystoreType);
			FileInputStream fis = new FileInputStream(keystoreLocation);
			almacen.load(fis, (keystorePassword).toCharArray());
			clavePrivada = (PrivateKey) almacen.getKey(keystoreCertAlias, (keystoreCertPassword).toCharArray());
			certificado = (java.security.cert.X509Certificate) almacen.getCertificate(keystoreCertAlias);
		} catch (Exception e) {
			throw e;
		}
	}

	private SOAPEnvelope createFirma(MessageContext msgContext) {
		try {
			GeneraCertificado();
			GestorPropiedades gestorPropiedades = (GestorPropiedades) ContextoSpring.getInstance().getBean("gestorPropiedades");

			// Obtención del documento XML que representa la petición SOAP
			Message msg = msgContext.getCurrentMessage();
			SOAPEnvelope soapEnvelope = msg.getSOAPEnvelope();

			SOAPHeaderElement soapHeader = soapEnvelope.getHeader().addHeaderElement(soapEnvelope.createName(
					"Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
			soapHeader.setMustUnderstand(true);
			soapHeader.setActor(null);

			// BinarySecurityToken
			SOAPElement binarySecurityTokenElement = soapHeader.addChildElement("BinarySecurityToken", "wsse");
			binarySecurityTokenElement
					.setAttribute(
							"EncodingType",
							"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
			binarySecurityTokenElement
					.setAttribute(
							"ValueType",
							"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
			binarySecurityTokenElement.setAttribute("wsu:Id",gestorPropiedades.valorPropertie("securityToken.generico"));
			binarySecurityTokenElement
					.setAttribute(
							"xmlns:wsu",
							"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
			binarySecurityTokenElement.addTextNode(new String(Base64.encodeBase64(certificado.getEncoded())));

			// Adding id "Body" to soapenv:body
			if (soapEnvelope.getBody().getAttribute("Id") == null) {
				soapEnvelope.getBody().addAttribute(soapEnvelope.createName("Id"), "Body");
			}

			return soapEnvelope;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	private void verifySign(MessageContext msgContext) throws AxisFault {
	}

}