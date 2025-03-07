package org.wscn.services;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.handler.Handler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeaderElement;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.codec.binary.Base64;
import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import viafirma.utilidades.UtilesViafirma;

public class ClientHandler2 implements Handler {

	static {
		org.apache.xml.security.Init.init();
	}

	private java.security.cert.X509Certificate certificado;
	private PrivateKey clavePrivada;
	private HandlerInfo handlerInfo;

	private SOAPEnvelope createFirma(MessageContext msgContext) {

		try {
			// Obtención del documento XML que representa la petición SOAP
			Message msg = msgContext.getCurrentMessage();
			SOAPEnvelope soapEnvelope = (SOAPEnvelope) msg.getSOAPEnvelope();

			SOAPHeaderElement soapHeader = soapEnvelope.getHeader().addHeaderElement(soapEnvelope.createName(
					"Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
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
			binarySecurityTokenElement.setAttribute("wsu:Id",
					"SecurityToken-60498088-e311-4f16-8a91-6c95447cd3e7");
			binarySecurityTokenElement
					.setAttribute(
							"xmlns:wsu",
							"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
			binarySecurityTokenElement.addTextNode(new String(Base64.encodeBase64(certificado.getEncoded())));

			// Adding id "Body" to soapenv:body
			if (soapEnvelope.getBody().getAttribute("Id") == null) {
				soapEnvelope.getBody().addAttribute(soapEnvelope.createName("Id"), "Body");
			}

			UtilesViafirma utilesViaFirma = new UtilesViafirma();
			String idFirma = utilesViaFirma.firmaSS(soapEnvelope.getAsString().getBytes(StandardCharsets.UTF_8), "2015C2056");

			String documento = utilesViaFirma.getDocumentoFirmado(idFirma);

//			XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
//
//			// Next, create a Reference to a same-document URI that is an Object
//			// element and specify the SHA1 digest algorithm
//			Reference ref = fac.newReference("#Body", fac.newDigestMethod(
//					DigestMethod.SHA1, null), Collections.singletonList(fac
//					.newTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS,
//							new ExcC14NParameterSpec())), null, null);
//
//			// Create the SignedInfo
//			SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(
//					CanonicalizationMethod.EXCLUSIVE,
//					(C14NMethodParameterSpec) null), fac.newSignatureMethod(
//					SignatureMethod.RSA_SHA1, null), Collections
//					.singletonList(ref));
//
//			// Create a KeyInfo
//			KeyInfoFactory kif = fac.getKeyInfoFactory();
//			X509IssuerSerial x509IssuerSerial = kif.newX509IssuerSerial(
//					certificado.getIssuerX500Principal().getName(),
//					certificado.getSerialNumber());
//
//			List<Object> x509Content = new ArrayList<Object>();
//			x509Content.add(x509IssuerSerial);
//			x509Content.add(certificado);
//
//			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kif.newX509Data(x509Content)));
//
//			// Create the XMLSignature (but don't sign it yet)
//			XMLSignature signature = fac.newXMLSignature(si, ki);
//
//			Document doc = soapEnvelope.getAsDocument();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			/*factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");*/

			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document documentoSoap = builder.parse(new ByteArrayInputStream(documento.getBytes()));
//			DOMSignContext dsc = new DOMSignContext(clavePrivada, doc.getDocumentElement());
//
//			signature.sign(dsc);
//
			// Mover elemento Signature a su lugar
			Element elementoFirma = (Element) ((Element) documentoSoap.getDocumentElement()).getElementsByTagNameNS("*", "Signature").item(0);
			elementoFirma.getParentNode().removeChild(elementoFirma);
			Element elementHeader = (Element) ((Element) documentoSoap.getDocumentElement()).getElementsByTagNameNS("*", "Security").item(0);
			elementHeader.appendChild(elementoFirma);
//
			// // Crea el deserializador para la firma del documento XML
			Canonicalizer c14n = Canonicalizer.getInstance(new String(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS));
			byte[] mensajeCanonico = c14n.canonicalizeSubtree(documentoSoap);
			InputSource is = new InputSource(new java.io.ByteArrayInputStream(mensajeCanonico));
			String tipo = (msgContext.getPastPivot() ? Message.RESPONSE : Message.REQUEST);
			DeserializationContext dser = new DeserializationContext(is, msgContext, tipo, soapEnvelope);

			dser.parse();

			msgContext.getCurrentMessage().setMessageType(tipo);
			return soapEnvelope;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String doBase64Encode(byte[] array) {
		return new String(Base64.encodeBase64(array));
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
		SOAPEnvelope signedSoapEnvelope = null;
		try {
			signedSoapEnvelope = createFirma(msgContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (signedSoapEnvelope != null) {
			((SOAPPart) msgContext.getRequestMessage().getSOAPPart()).setCurrentMessage(signedSoapEnvelope,
					SOAPPart.FORM_SOAPENVELOPE);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean handleResponse(javax.xml.rpc.handler.MessageContext arg0) {
		return true;
	}

	@Override
	public void init(HandlerInfo arg0) {
		this.handlerInfo = arg0;
		certificado = (X509Certificate) arg0.getHandlerConfig().get("certificado");
		clavePrivada = (PrivateKey) arg0.getHandlerConfig().get("clavePrivada");
	}

}