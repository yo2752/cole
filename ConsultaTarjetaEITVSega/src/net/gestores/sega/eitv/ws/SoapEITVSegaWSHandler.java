package net.gestores.sega.eitv.ws;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;



public class SoapEITVSegaWSHandler extends GenericWSHandler{
	
private static final ILoggerOegam log = LoggerOegam.getLogger(SoapEITVSegaWSHandler.class);
	
	public static final String PROPERTY_KEY_ID = "numExpediente";
	
	private final String PREF_NOMBRE_FICHERO = "MATE_";
	private final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private final String SUF_NOMBRE_FICHERO_RESP = "_RESP";
	
	private final String TIPO_DOCUMENTO = ConstantesGestorFicheros.MATE;
	private final String SUB_TIPO_DOCUMENTO = ConstantesGestorFicheros.CONSULTAEITV_RESQ_RESP;
	
	private BigDecimal numExpediente;
	private String fecha;

	@Autowired
	private UtilesFecha utilesFecha;

	@Override
	public void init(HandlerInfo arg0) {
		super.init(arg0);
		if(arg0.getHandlerConfig() != null){
			if(arg0.getHandlerConfig().get(PROPERTY_KEY_ID) != null){
				numExpediente = (BigDecimal) arg0.getHandlerConfig().get(PROPERTY_KEY_ID);
			}
			fecha = utilesFecha.formatoFecha("yyyyMMdd HHmmss", new Date()).replace(" ", "");
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
		return SUB_TIPO_DOCUMENTO;
	}

	@Override
	public String getSubtipoDocumentoResponse() {
		return SUB_TIPO_DOCUMENTO;
	}

	@Override
	public String getNombreFicheroRequest() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		sb.append(numExpediente).append("_"+fecha).append(SUF_NOMBRE_FICHERO_REQ);
		return sb.toString();
	}

	@Override
	public String getNombreFicheroResponse() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		sb.append(numExpediente).append("_"+fecha).append(SUF_NOMBRE_FICHERO_RESP);
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
