package es.trafico.cliente.vehiculos.custodiaitv.webservice;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.Handler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.axis.message.SOAPEnvelope;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;

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
public class SecurityClientHandlerEEFF implements Handler {
	private static final ILoggerOegam log = LoggerOegam.getLogger(SecurityClientHandlerEEFF.class);

	public static final String ALIAS_KEY = "aliasKey";
	private String alias;
	private HandlerInfo handlerInfo;

	@Override
	public void init(HandlerInfo arg0) {
		this.handlerInfo = arg0;
		alias = (String) arg0.getHandlerConfig().get(ALIAS_KEY);
	}

	private SOAPEnvelope createFirma(MessageContext msgContext) {
		try {
			// Obtencion del documento XML que representa la peticionSOAP
			GestorPropiedades gestorPropiedades = (GestorPropiedades) ContextoSpring.getInstance().getBean("gestorPropiedades");
			Message msg = msgContext.getCurrentMessage();
			SOAPEnvelope soapEnvelope = (SOAPEnvelope) msg.getSOAPEnvelope();

			Message inMsg = msgContext.getRequestMessage();

			String contenido =inMsg.getSOAPBody().toString();

			int inicio =contenido.indexOf("<ds:X509Certificate>")+20;
			int fin = contenido.indexOf("</ds:X509Certificate>");

			String certificate =contenido.substring(inicio,fin);

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
			binarySecurityTokenElement.addTextNode(certificate);

			// Adding id "Body" to soapenv:body
			if (soapEnvelope.getBody().getAttribute("Id") == null) {
				soapEnvelope.getBody().addAttribute(soapEnvelope.createName("Id"), "Body");
			}
			System.out.println(soapEnvelope.getAsString());
			UtilesViafirma utilesViaFirma = new UtilesViafirma();
			if (utilesViaFirma.firmarPruebaCertificadoCaducidad(new String(soapEnvelope.getAsString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), alias) == null) {
				throw new Exception("No se ha podido comprobar la caducidad de la firma");
			}
			String xmlFirmado = utilesViaFirma.firmarHandlerEEFF(msgContext.getCurrentMessage().getSOAPEnvelope().getAsString(),alias);
			xmlFirmado = xmlFirmado.replace("&lt;?", "<![CDATA[<?");
			xmlFirmado = xmlFirmado.replace("&lt;", "<");
			xmlFirmado = xmlFirmado.replace("&gt;", ">");
			xmlFirmado = xmlFirmado.replace("</xsd:solicitud>", "]]></xsd:solicitud>");
			StringBuffer sb = new StringBuffer(xmlFirmado);
			int inicioSignature = sb.indexOf("<ds:Signature xmlns:")+61;
			int finSignature = sb.indexOf("</ds:Signature>");

			String signature =  sb.substring(inicioSignature, finSignature);

			SOAPElement SOAPsignature = soapEnvelope.getHeader().addChildElement(soapEnvelope.createName(
					"Signature", "ds", "http://www.w3.org/2000/09/xmldsig#"));

			SOAPsignature.setAttribute("EncodingType", "http://www.w3.org/2000/09/xmldsig#");
			SOAPsignature.addTextNode(new String(signature.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
			System.out.println(msgContext.getCurrentMessage().getSOAPEnvelope().getAsString());
			return msgContext.getCurrentMessage().getSOAPEnvelope();
		} catch (Exception e) {
			log.error(e);
			return null;
		}
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
		try {
			((SOAPPart) ((MessageContext) arg0).getRequestMessage().getSOAPPart())
			.setCurrentMessage(createFirma((MessageContext) arg0),
							SOAPPart.FORM_SOAPENVELOPE);
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}

		SOAPMessageContext messageContext = (SOAPMessageContext) arg0;
		try {
			log.info(getXmlMessage(messageContext.getMessage()));
		} catch (Exception e) {
			log.error(e);
		}
		return true;
	}

	@Override
	public boolean handleResponse(javax.xml.rpc.handler.MessageContext arg0) {
		return true;
	}

	private static String getXmlMessage(SOAPMessage message) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		message.writeTo(os);
		String encoding = (String) message.getProperty(SOAPMessage.CHARACTER_SET_ENCODING);
		if (encoding == null) {
			return new String(os.toByteArray());
		} else {
			return new String(os.toByteArray(), encoding);
		}
	}

}