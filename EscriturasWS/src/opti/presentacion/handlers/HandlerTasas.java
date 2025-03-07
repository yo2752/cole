package opti.presentacion.handlers;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.Handler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.codec.binary.Base64;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.transforms.Transforms;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Clase encargada de securizar los mensajes SOAP de petición realizados desde
 * un cliente.
 * 
 * Se tiene que iniciar con un mapa con la configuración del almacén de claves
 * 		keystoreLocation
 * 		keystoreType
 * 		keystorePassword
 *		keystoreCertAlias
 *		keystoreCertPassword 
 * 
 */
public class HandlerTasas implements Handler {
	private HandlerInfo handlerInfo;
	private static ILoggerOegam log = LoggerOegam.getLogger(HandlerTasas.class);
	private static final String ERROR_GENERICO = "ha ocurrido un error en al firmar el mensaje SOAP";
	static {
		org.apache.xml.security.Init.init();
	}

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

	private PrivateKey clavePrivada;
	private java.security.cert.X509Certificate certificado;

	/**
	 * Securiza, mediante el tag BinarySecurityToken y firma una petición SOAP
	 * no securizada.
	 * 
	 * @param soapEnvelopeRequest
	 *            Documento xml que representa la petición SOAP sin securizar.
	 * @return Un mensaje SOAP que contiene la petición SOAP de entrada
	 *         securizada mediante el tag BinarySecurityToken.
	 */
	private SOAPEnvelope createFirma(MessageContext msgContext) {

		try {
			generaCertificado();

			// Obtención del documento XML que representa la petición SOAP
			GestorPropiedades gestorPropiedades = (GestorPropiedades) ContextoSpring.getInstance().getBean("gestorPropiedades");
			Message msg = msgContext.getCurrentMessage();
			SOAPEnvelope soapEnvelope = (SOAPEnvelope) msg.getSOAPEnvelope();

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

			XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

			// Next, create a Reference to a same-document URI that is an Object
			// element and specify the SHA1 digest algorithm
			Reference ref = fac.newReference("#Body", fac.newDigestMethod(
					DigestMethod.SHA1, null), Collections.singletonList(fac
					.newTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS,
							new ExcC14NParameterSpec())), null, null);

			// Create the SignedInfo
			SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(
					CanonicalizationMethod.EXCLUSIVE,
					(C14NMethodParameterSpec) null), fac.newSignatureMethod(
					SignatureMethod.RSA_SHA1, null), Collections
					.singletonList(ref));

			// Create a KeyInfo
			KeyInfoFactory kif = fac.getKeyInfoFactory();
			X509IssuerSerial x509IssuerSerial = kif.newX509IssuerSerial(
					certificado.getIssuerX500Principal().getName(),
					certificado.getSerialNumber());

			List<Object> x509Content = new ArrayList<Object>();
			x509Content.add(x509IssuerSerial);
			x509Content.add(certificado);

			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kif.newX509Data(x509Content)));

			// Create the XMLSignature (but don't sign it yet)
			XMLSignature signature = fac.newXMLSignature(si, ki);

			Document doc = soapEnvelope.getAsDocument();
			DOMSignContext dsc = new DOMSignContext(clavePrivada, doc.getDocumentElement());

			signature.sign(dsc);

			// Mover elemento Signature a su lugar
			Element elementoFirma = (Element) ((Element) doc.getDocumentElement()).getElementsByTagNameNS("*", "Signature").item(0);
			elementoFirma.getParentNode().removeChild(elementoFirma);
			Element elementHeader = (Element) ((Element) doc.getDocumentElement()).getElementsByTagNameNS("*", "Header").item(0);
			elementHeader.appendChild(elementoFirma);

			// Crea el deserializador para la firma del documento XML
			Canonicalizer c14n = Canonicalizer.getInstance(new String(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS));
			byte[] mensajeCanonico = c14n.canonicalizeSubtree(doc);
			InputSource is = new InputSource(new java.io.ByteArrayInputStream(mensajeCanonico));
			String tipo = (msgContext.getPastPivot() ? Message.RESPONSE : Message.REQUEST);
			DeserializationContext dser = new DeserializationContext(is, msgContext, tipo, soapEnvelope);

			dser.parse();

			msgContext.getCurrentMessage().setMessageType(tipo);

			System.out.println(soapEnvelope);

			return soapEnvelope;

		} catch (IOException e) {
			log.error(ERROR_GENERICO, e);
		} catch (CertificateEncodingException | SOAPException | SAXException | InvalidCanonicalizerException
				| CanonicalizationException | NoSuchAlgorithmException | InvalidAlgorithmParameterException
				| MarshalException | XMLSignatureException e) {
			log.error(ERROR_GENERICO, e);
		} catch (Exception e) {
			log.error(ERROR_GENERICO, e);
		}
		return null;
	}

	/**
	 * Recupera el certificado
	 * @throws KeyStoreException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableKeyException
	 * 
	 * @throws Exception
	 */
	private void generaCertificado() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
		// Extrae la clave privada y el certificado del almacén de claves
		KeyStore almacen = KeyStore.getInstance((String) keystoreType);
		FileInputStream fis = new FileInputStream((String) keystoreLocation);
		almacen.load(fis, ((String) keystorePassword).toCharArray());
		clavePrivada = (PrivateKey) almacen.getKey((String) keystoreCertAlias, ((String) keystoreCertPassword).toCharArray());
		certificado = (java.security.cert.X509Certificate) almacen.getCertificate((String) keystoreCertAlias);
	}

	public void init(HandlerInfo arg0) {
		this.handlerInfo = arg0;
		keystoreLocation = (String) arg0.getHandlerConfig().get("keystoreLocation");
		keystoreType = (String) arg0.getHandlerConfig().get("keystoreType");
		keystorePassword = (String) arg0.getHandlerConfig().get("keystorePassword");
		keystoreCertAlias = (String) arg0.getHandlerConfig().get("keystoreCertAlias");
		keystoreCertPassword = (String) arg0.getHandlerConfig().get("keystoreCertPassword");
	}

	@Override
	public void destroy() {
	}

	@Override
	public QName[] getHeaders() {
		return handlerInfo.getHeaders();
	}

	@Override
	public boolean handleFault(javax.xml.rpc.handler.MessageContext arg0) {
		return true;
	}

	@Override
	public boolean handleRequest(javax.xml.rpc.handler.MessageContext arg0) {
		MessageContext msgContext = ((MessageContext) arg0);
		SOAPEnvelope signedSoapEnvelope = createFirma(msgContext);
		if (signedSoapEnvelope != null) {
			((SOAPPart) msgContext.getRequestMessage().getSOAPPart())
					.setCurrentMessage(signedSoapEnvelope, SOAPPart.FORM_SOAPENVELOPE);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean handleResponse(javax.xml.rpc.handler.MessageContext arg0) {
		return true;
	}
}