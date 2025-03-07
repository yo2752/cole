package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ws;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.Handler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.SOAPPart;
import org.apache.axis.message.SOAPEnvelope;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import viafirma.utilidades.UtilesViafirma;

public class CaycWSSecurityClientHandler implements Handler {

	public static final String ALIAS_KEY = "aliasKey";
	private static final String INICIO_TAG_CERTIFICADO = "<ds:X509Certificate>";
	private static final String FIN_TAG_CERTIFICADO = "</ds:X509Certificate>";
	private static final String SECURITY = "Security";
	private static final String ENCODING_TYPE = "EncodingType";
	private static final String URI = "http://www.w3.org/2000/09/xmldsig#";

	private static ILoggerOegam log = LoggerOegam.getLogger(CaycWSSecurityClientHandler.class);
	private static final String ERROR_GENERICO = "Ha ocurrido un error al firmar el mensaje SOAP";
	private String alias;
	private HandlerInfo handlerInfo;

	@Override
	public boolean handleRequest(MessageContext arg0) {
		org.apache.axis.MessageContext msgContext = (org.apache.axis.MessageContext) arg0;
		SOAPEnvelope signedSoapEnvelope = null;
		try {
			signedSoapEnvelope = createFirma2(msgContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (signedSoapEnvelope != null) {
			((SOAPPart) msgContext.getRequestMessage().getSOAPPart()).setCurrentMessage(signedSoapEnvelope,
					SOAPPart.FORM_SOAPENVELOPE);
			msgContext.removeProperty("jaxrpc.method.info");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean handleResponse(MessageContext arg0) {
		return true;
	}

	@Override
	public boolean handleFault(MessageContext arg0) {
		return true;
	}

	@Override
	public void init(HandlerInfo arg0) {
		this.handlerInfo = arg0;
		alias = (String) arg0.getHandlerConfig().get(ALIAS_KEY);
	}

	@Override
	public void destroy() {
	}

	@Override
	public QName[] getHeaders() {
		return handlerInfo.getHeaders();
	}

	private SOAPEnvelope createFirma2(org.apache.axis.MessageContext msgContext) {
		try {
			// Obtencion del documento XML que representa la peticionSOAP
			GestorPropiedades gestorPropiedades = (GestorPropiedades) ContextoSpring.getInstance().getBean("gestorPropiedades");
			Message msg = msgContext.getCurrentMessage();
			SOAPEnvelope soapEnvelope = (SOAPEnvelope) msg.getSOAPEnvelope();

			Message inMsg = msgContext.getRequestMessage();

			String contenido = inMsg.getSOAPBody().toString();

			int inicio = contenido.indexOf(INICIO_TAG_CERTIFICADO)+20;
			int fin = contenido.indexOf(FIN_TAG_CERTIFICADO);

			String certificate = contenido.substring(inicio,fin);

			SOAPHeaderElement soapHeader = soapEnvelope.getHeader().addHeaderElement(soapEnvelope.createName(
					SECURITY, "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
			soapHeader.setMustUnderstand(true);
			soapHeader.setActor(null);

			// BinarySecurityToken
			SOAPElement binarySecurityTokenElement = soapHeader.addChildElement("BinarySecurityToken", "wsse");
			binarySecurityTokenElement
					.setAttribute(
							ENCODING_TYPE,
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

			//System.out.println(soapEnvelope.getAsString());
			UtilesViafirma utilesViaFirma = new UtilesViafirma();
			if (utilesViaFirma.firmarPruebaCertificadoCaducidad(new String(soapEnvelope.getAsString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), alias) == null) {
				throw new Exception("No se ha podido comprobar la caducidad de la firma");
			}
			String xmlFirmado = utilesViaFirma.firmarHandlerEEFF(msgContext.getCurrentMessage().getSOAPEnvelope().getAsString(),alias);
			xmlFirmado = xmlFirmado.replace("&lt;?", "<![CDATA[<?");
			xmlFirmado = xmlFirmado.replace("&lt;", "<");
			xmlFirmado = xmlFirmado.replace("&gt;", ">");
			xmlFirmado = xmlFirmado.replace("</xsd:solicitud>", "]]></xsd:solicitud>");
			System.out.println(xmlFirmado);
			StringBuffer sb = new StringBuffer(xmlFirmado);
			int inicioSignature = sb.indexOf("<ds:Signature xmlns:")+61;
			int finSignature = sb.indexOf("</ds:Signature>");
			System.out.println(xmlFirmado);
			String signature =  sb.substring(inicioSignature, finSignature);

			SOAPElement SOAPsignature = soapEnvelope.getHeader().addChildElement(soapEnvelope.createName(
					"Signature", "ds", URI));

			SOAPsignature.setAttribute(ENCODING_TYPE, URI);
			SOAPsignature.addTextNode(new String(signature.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));

			return msgContext.getCurrentMessage().getSOAPEnvelope();

		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	private SOAPEnvelope createFirma(org.apache.axis.MessageContext msgContext) {
		try {
			// Obtención del documento XML que representa la peticionSOAP
			Message msg = msgContext.getCurrentMessage();
			SOAPEnvelope soapEnvelope = msg.getSOAPEnvelope();

			// Adding id "Body" to soapenv:body
			if (soapEnvelope.getBody().getAttribute("Id") == null) {
				soapEnvelope.getBody().addAttribute(soapEnvelope.createName("Id"), "Body");
			}

			String t = new String(soapEnvelope.getAsString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
			t = t.replaceAll(" xmlns=\"\"", "");
			String trozo = "xmlns=\"http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices\"";
			t = t.replaceAll(trozo, "");

			UtilesViafirma utilesViaFirma = new UtilesViafirma();
			if (utilesViaFirma.firmarPruebaCertificadoCaducidad(t, alias) == null) {
				throw new Exception("No se ha podido comprobar la caducidad de la firma");

			}
			byte[] xmlFirmado = utilesViaFirma
					.firmarCAYC(msgContext.getCurrentMessage().getSOAPEnvelope().getAsString().getBytes(), alias);

			actualizarCertificadoBinaryToken_OK(xmlFirmado, soapEnvelope);

			StringBuffer sb = new StringBuffer(new String(xmlFirmado, StandardCharsets.UTF_8));
			int inicioSignature = sb.indexOf("<ds:SignedInfo>");
			int finSignature = sb.indexOf("</ds:Signature>");
			System.out.println(sb.toString());
			String signature = sb.substring(inicioSignature, finSignature);

			SOAPElement SOAPsignature = soapEnvelope.getHeader()
					.addChildElement(soapEnvelope.createName("Signature", "ds", URI));

			// SOAPsignature.setAttribute(ENCODING_TYPE,
			// URI);

			t = new String(signature.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
			t = t.replaceAll("\n", "");
			SOAPsignature.addTextNode(t);
			//System.out.println(msgContext.getCurrentMessage().getSOAPEnvelope());
			return msgContext.getCurrentMessage().getSOAPEnvelope();
		} catch (AxisFault | SOAPException | UnsupportedEncodingException e) {
			log.error(ERROR_GENERICO, e);
		} catch (Exception e) {
			log.error(ERROR_GENERICO, e);
		}
		return null;
	}

	private void actualizarCertificadoBinaryToken_OK(byte[] firma, SOAPEnvelope soapEnvelope)
			throws SOAPException {
		GestorPropiedades gestorPropiedades = (GestorPropiedades) ContextoSpring.getInstance().getBean("gestorPropiedades");
		String cadena = new String(firma, StandardCharsets.UTF_8);
		String certificado = cadena.substring(cadena.indexOf(INICIO_TAG_CERTIFICADO) + INICIO_TAG_CERTIFICADO.length(),
				cadena.indexOf(FIN_TAG_CERTIFICADO));
		certificado = certificado.replaceAll("\n", "").replaceAll("\r", "");
		SOAPHeaderElement securityTag = soapEnvelope.getHeader().addHeaderElement(soapEnvelope.createName(SECURITY,
				"wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
		securityTag.setActor(null);
		securityTag.addAttribute(soapEnvelope.createName("Id"), SECURITY);

		// BinarySecurityToken
		SOAPElement binarySecurityTokenElement = securityTag.addChildElement("BinarySecurityToken", "wsse");
		binarySecurityTokenElement.setAttribute(ENCODING_TYPE,
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
		binarySecurityTokenElement.setAttribute("ValueType",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
		binarySecurityTokenElement.setAttribute("wsu:Id", gestorPropiedades.valorPropertie("securityToken.generico"));
		binarySecurityTokenElement.setAttribute("xmlns:wsu",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
		binarySecurityTokenElement.addTextNode(certificado);
	}

}