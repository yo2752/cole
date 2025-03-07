package es.trafico.servicios.vehiculos.atem;

import java.io.ByteArrayOutputStream;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.Handler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class SoapLoggingHandler implements Handler {

	private HandlerInfo handlerInfo;
	private static ILoggerOegam logger = LoggerOegam.getLogger("ProcesoAtem");

	public boolean handleRequest(MessageContext arg0) {
		SOAPMessageContext messageContext = (SOAPMessageContext) arg0;
		try {
			logger.info("Request que se envía: " + getXmlMessage(messageContext.getMessage()));
		} catch (Exception e) {
			logger.error("No se pudo recuperar el XML del mensaje que se envía", e);
		}
		return true;
	}

	public boolean handleResponse(MessageContext arg0) {
		SOAPMessageContext messageContext = (SOAPMessageContext) arg0;
		try {
			logger.info("Response que se recibe: " + getXmlMessage(messageContext.getMessage()));
		} catch (Exception e) {
			logger.error("No se pudo recuperar el XML del mensaje que se recibe", e);
		}
		return true;
	}

	public boolean handleFault(MessageContext arg0) {
		SOAPMessageContext messageContext = (SOAPMessageContext) arg0;
		try {
			logger.error("Soap Fault: " + getXmlMessage(messageContext.getMessage()));
		} catch (Exception e) {
			logger.error("No se pudo recuperar el XML del fault", e);
		}
		return true;
	}

	public static String getXmlMessage(SOAPMessage message) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		message.writeTo(os);
		String encoding = (String) message.getProperty(SOAPMessage.CHARACTER_SET_ENCODING);
		if (encoding == null) {
			return new String(os.toByteArray());
		} else {
			return new String(os.toByteArray(), encoding);
		}
	} 

	public void init(HandlerInfo arg0) {
		this.handlerInfo = arg0;
	}

	public void destroy() {
	}

	public QName[] getHeaders() {
		return handlerInfo.getHeaders();
	}
}
