package opti.presentacion.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.codec.binary.Base64;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ClientHandler2 extends BasicHandler {

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
	private PublicKey clavePublica;
	private java.security.cert.X509Certificate certificado;

	@Autowired
	GestorPropiedades gestorPropiedades;

	/**
	 * Constructor que inicializa el atributo securityOption
	 *
	 * @param securityOption
	 *            opción de seguridad.
	 * @throws Exception
	 */
	public ClientHandler2() {
		try {
			keystoreLocation = gestorPropiedades.valorPropertie("security.keystore.location");
			keystoreType = gestorPropiedades.valorPropertie("security.keystore.type");
			keystorePassword = gestorPropiedades.valorPropertie("security.keystore.password");
			keystoreCertAlias = gestorPropiedades.valorPropertie("security.keystore.cert.alias");
			keystoreCertPassword = gestorPropiedades.valorPropertie("security.keystore.cert.password");
		} catch (Exception e) {
			System.err.println("Error leyendo el fichero de configuración de securización");
		}
	}

	public void invoke(MessageContext msgContext) throws AxisFault {
		SOAPMessage secMsg = null;
		try {
			((SOAPPart) msgContext.getRequestMessage().getSOAPPart()).setCurrentMessage(this.createBinarySecurityToken(msgContext),
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
			GeneraCertificado();
			return createBinarySecurityTokenFirma(msgContext);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		}
	}

	protected static String convertToPem(X509Certificate cert) throws CertificateEncodingException {
		return DatatypeConverter.printBase64Binary(cert.getEncoded());
	}

	public void GeneraCertificado() throws Exception {
		// Extrae la clave privada y el certificado del almacén de claves
		try {
			almacen = KeyStore.getInstance((String) keystoreType);
			FileInputStream fis = new FileInputStream((String) keystoreLocation);
			almacen.load(fis, ((String) keystorePassword).toCharArray());
			clavePrivada = (PrivateKey) almacen.getKey((String) keystoreCertAlias,
					((String) keystoreCertPassword).toCharArray());
			certificado = (java.security.cert.X509Certificate)almacen.getCertificate((String) keystoreCertAlias);
			clavePublica = certificado.getPublicKey();
		} catch (Exception e) {
			throw e;
		}
	}

	public void GeneraCertificadoColegiado(){
	}

	private Message getAxisMessage(String unsignedEnvelope, MessageContext msgContext) {
		InputStream inStream = new ByteArrayInputStream(unsignedEnvelope.getBytes());
		Message axisMessage = new Message(inStream);
		axisMessage.setMessageContext(msgContext);
		return axisMessage;
	}

	private Properties initializateCryptoProperties() {
		Properties res = new Properties();
		res.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
		res.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", this.keystoreType);
		res.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", this.keystorePassword);
		res.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias", this.keystoreCertAlias);
		res.setProperty("org.apache.ws.security.crypto.merlin.alias.password", this.keystoreCertPassword);
		res.setProperty("org.apache.ws.security.crypto.merlin.file", this.keystoreLocation);
		return res;
	}

	private SOAPEnvelope createBinarySecurityTokenFirma(MessageContext msgContext) throws WSSecurityException {
		ByteArrayOutputStream baos;
		Crypto crypto;
		Document secSOAPReqDoc;
		Document soapEnvelopeRequest;
		DOMSource source;
		Element element;
		StreamResult streamResult;
		String secSOAPReq;
		WSSecSignature wsSecSignature;
		WSSecHeader wsSecHeader;
		SOAPMessage msg;

		try {
			// Obtención del documento XML que representa la petición SOAP
			msg = msgContext.getCurrentMessage();
			soapEnvelopeRequest = ((org.apache.axis.message.SOAPEnvelope) msg.getSOAPPart().getEnvelope())
					.getAsDocument();

			// Inserción del tag wsse:Security y BinarySecurityToken
			wsSecHeader = new WSSecHeader(null, false);
			wsSecSignature = new WSSecSignature();
			crypto = CryptoFactory.getInstance(this.initializateCryptoProperties());
			// Indicación para que inserte el tag BinarySecurityToken
			wsSecSignature.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
			wsSecSignature.setUserInfo(this.keystoreCertAlias, this.keystoreCertPassword);
			wsSecHeader.insertSecurityHeader(soapEnvelopeRequest);
			wsSecSignature.prepare(soapEnvelopeRequest, crypto, wsSecHeader);
			// Modificación y firma de la petición
			secSOAPReqDoc = wsSecSignature.build(soapEnvelopeRequest, crypto, wsSecHeader);
			element = secSOAPReqDoc.getDocumentElement();
			// Transformación del elemento DOM a String
			source = new DOMSource(element);
			baos = new ByteArrayOutputStream();
			streamResult = new StreamResult(baos);
			TransformerFactory.newInstance().newTransformer().transform(source, streamResult);
			secSOAPReq = new String(baos.toByteArray());

			// Creación de un nuevo mensaje SOAP a partir del mensaje SOAP
			// securizado formado
			Message axisMessage = getAxisMessage(secSOAPReq, msgContext);
			System.out.println(axisMessage.getSOAPEnvelope());
			return axisMessage.getSOAPEnvelope();
		} catch (WSSecurityException e) {
			System.out.println(e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
			return null;
		}
	}

	public static String doBase64Encode(byte[] array) {
		return new String(Base64.encodeBase64(array));
	}

}