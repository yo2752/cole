package org.gestoresmadrid.opti.presentacion.ws;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.rpc.handler.HandlerInfo;

import org.w3c.dom.Document;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;

public class SoapModelo600601WSHandler extends GenericWSHandler{

	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapModelo600601WSHandler.class);
	
	private static final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private static final String SUF_NOMBRE_FICHERO_RESP = "_RESP";
	private static final String PREF_NOMBRE_FICHERO_RESP = "FORMULARIOCAM_";
	
	public static final String PROPERTY_KEY_ID = "numExpediente";
	
	private Date fechaFichero;
	private BigDecimal numExpediente;
	
	public SoapModelo600601WSHandler() {
		fechaFichero = new Date();
	}
	
	@Override
	public void init(HandlerInfo arg0) {
		super.init(arg0);
		if(arg0.getHandlerConfig() != null){
			if(arg0.getHandlerConfig().get(PROPERTY_KEY_ID) != null){
				numExpediente = (BigDecimal) arg0.getHandlerConfig().get(PROPERTY_KEY_ID);
			}
		}
		
	}

	@Override
	public String getTipoDocumentoRequest() {
		return ConstantesGestorFicheros.FORMULARIOCAM;
	}

	@Override
	public String getTipoDocumentoResponse() {
		return ConstantesGestorFicheros.FORMULARIOCAM;
	}

	@Override
	public String getSubtipoDocumentoRequest() {
		return ConstantesGestorFicheros.ENVIO;
	}

	@Override
	public String getSubtipoDocumentoResponse() {
		return ConstantesGestorFicheros.ENVIO;
	}

	@Override
	public String getNombreFicheroRequest() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO_RESP);
		sb.append(numExpediente).append("_");;
		sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(fechaFichero.getTime())).append(SUF_NOMBRE_FICHERO_REQ);
		return sb.toString();
	}

	@Override
	public String getNombreFicheroResponse() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO_RESP);
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

	@Override
	protected boolean formatearRequest() {
		return false;
	}

	@Override
	protected boolean formatearResponse() {
		return false;
	}
	
}
