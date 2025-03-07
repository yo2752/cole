package org.gestoresmadrid.oegam2comun.wscn.model.handler;

import java.io.UnsupportedEncodingException;

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
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.SOAPEnvelope;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.xml.sax.InputSource;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import viafirma.utilidades.UtilesViafirma;

public class NotificacionesSecurityClientHandler implements Handler {

	public static final String ALIAS_KEY = "aliasKey";
	private static final String CERTIFICADO_PROVISIONAL = "certificado_provisional";
	private static final String INICIO_TAG_CERTIFICADO = "<ds:X509Certificate>";
	private static final String FIN_TAG_CERTIFICADO = "</ds:X509Certificate>";
	
	private static ILoggerOegam log = LoggerOegam.getLogger(NotificacionesSecurityClientHandler.class);
	private static final String ERROR_GENERICO = "Ha ocurrido un error al firmar el mensaje SOAP";
	private String alias;
	private HandlerInfo handlerInfo;

	@Override
	public boolean handleRequest(MessageContext arg0) {
		org.apache.axis.MessageContext msgContext = (org.apache.axis.MessageContext) arg0;
		SOAPEnvelope signedSoapEnvelope = null;
		try {
			signedSoapEnvelope = createFirma(msgContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (signedSoapEnvelope != null) {
			((SOAPPart) msgContext.getRequestMessage().getSOAPPart()).setCurrentMessage(signedSoapEnvelope, SOAPPart.FORM_SOAPENVELOPE);
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
	public void destroy() {}

	@Override
	public QName[] getHeaders() {
		return handlerInfo.getHeaders();
	}

	private SOAPEnvelope createFirma(org.apache.axis.MessageContext msgContext) {
		try {

			SOAPEnvelope soapEnvelope = msgContext.getCurrentMessage().getSOAPEnvelope();

			// Binary Token
			createBinaryToken(soapEnvelope);

			// Adding id "Body" to soapenv:body
			if (soapEnvelope.getBody().getAttribute("Id") == null) {
				soapEnvelope.getBody().addAttribute(soapEnvelope.createName("Id"), "Body");
			} else {
				soapEnvelope.getBody().setAttribute("Id", "Body");
			}

			UtilesViafirma utilesViaFirma = new UtilesViafirma();
			
			if(utilesViaFirma.firmarPruebaCertificadoCaducidad(soapEnvelope.getAsString(), alias)==null){
				log.error(ERROR_GENERICO);
				return null;
				
			}
			byte[] firma = utilesViaFirma.firmarNotificacionesSS(soapEnvelope.getAsString().getBytes("UTF-8"), alias);
			
			// meter al alias en el binary token...
			firma = actualizarCertificadoBinaryToken(firma);
			
			// Actualizar peticion
			InputSource is = new InputSource(new java.io.ByteArrayInputStream(firma));
			String tipo = (msgContext.getPastPivot() ? Message.RESPONSE : Message.REQUEST);
			DeserializationContext dser = new DeserializationContext(is, msgContext, tipo, soapEnvelope);
			dser.parse();

			msgContext.getCurrentMessage().setMessageType(tipo);

			return soapEnvelope;
		} catch (AxisFault e) {
			log.error(ERROR_GENERICO, e);
		} catch (SOAPException e) {
			log.error(ERROR_GENERICO, e);
		} catch (UnsupportedEncodingException e) {
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
		securityTag.setActor(null);
		securityTag.addAttribute(soapEnvelope.createName("Id"), "Security");

		
		// BinarySecurityToken
		SOAPElement binarySecurityTokenElement = securityTag.addChildElement("BinarySecurityToken", "wsse");
		binarySecurityTokenElement.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
		binarySecurityTokenElement.setAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
		binarySecurityTokenElement.setAttribute("wsu:Id", gestorPropiedades.valorPropertie("securityToken.generico"));
		binarySecurityTokenElement.setAttribute("xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
		binarySecurityTokenElement.addTextNode(CERTIFICADO_PROVISIONAL);
	}

}
