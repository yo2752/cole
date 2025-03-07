package com.gescogroup.blackbox.ws;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.rpc.handler.HandlerInfo;

import org.w3c.dom.Document;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class SoapTramitarBtvWSHandler extends GenericWSHandler{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapTramitarBtvWSHandler.class);
	
	public static final String PROPERTY_KEY_ID = "numExpediente";
	
	private final String PREF_NOMBRE_FICHERO = "TRAMITAR_BTV_";
	private final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private final String SUF_NOMBRE_FICHERO_RESP = "_RESP";
	
	private final String TIPO_DOCUMENTO = ConstantesGestorFicheros.TRAMITAR_BTV;
	private final String SUB_TIPO_DOCUMENTO_REQ = ConstantesGestorFicheros.ENVIO;
	private final String SUB_TIPO_DOCUMENTO_RESP = ConstantesGestorFicheros.RESPUESTA;
	
	private Date fechaFichero;
	private String numExpediente;
	
	public SoapTramitarBtvWSHandler() {
		this.fechaFichero = new Date();
	}
	
	@Override
	public void init(HandlerInfo arg0) {
		super.init(arg0);
		if(arg0.getHandlerConfig() != null){
			if(arg0.getHandlerConfig().get(PROPERTY_KEY_ID) != null){
				numExpediente = (String) arg0.getHandlerConfig().get(PROPERTY_KEY_ID).toString();
			}
		}
		
	}

	@Override
	public String getTipoDocumentoRequest() {
		return TIPO_DOCUMENTO;
	}

	@Override
	public String getTipoDocumentoResponse() {
		return TIPO_DOCUMENTO;
	}

	@Override
	public String getSubtipoDocumentoRequest() {
		return SUB_TIPO_DOCUMENTO_REQ;
	}

	@Override
	public String getSubtipoDocumentoResponse() {
		return SUB_TIPO_DOCUMENTO_RESP;
	}

	@Override
	public String getNombreFicheroRequest() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		sb.append(numExpediente).append("_");;
		sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(fechaFichero.getTime())).append(SUF_NOMBRE_FICHERO_REQ);
		return sb.toString();
	}

	@Override
	public String getNombreFicheroResponse() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		sb.append(numExpediente).append("_");;
		sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(fechaFichero.getTime())).append(SUF_NOMBRE_FICHERO_RESP);
		return sb.toString();
	}

	@Override
	public void setDOMRequest(Document doc) {
		if (log.isDebugEnabled()) {
			log.debug("tratando peticion " + doc.getTextContent());
		}
	}

	@Override
	public void setDOMResponse(Document doc) {
		if (log.isDebugEnabled()) {
			log.debug("tratando respuesta " + doc.getTextContent());
		}
	}

	@Override
	public ILoggerOegam getLogger() {
		return log;
	}
}
