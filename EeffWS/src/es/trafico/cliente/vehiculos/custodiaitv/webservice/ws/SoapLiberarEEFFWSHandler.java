package es.trafico.cliente.vehiculos.custodiaitv.webservice.ws;

import java.math.BigDecimal;

import javax.xml.rpc.handler.HandlerInfo;

import org.w3c.dom.Document;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;

public class SoapLiberarEEFFWSHandler extends GenericWSHandler{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapLiberarEEFFWSHandler.class);
	
	public static final String PROPERTY_KEY_ID = "numExpediente";
	
	private final String PREF_NOMBRE_FICHERO = "LIBERAR_EEFF_WS_";
	
	private final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private final String SUF_NOMBRE_FICHERO_RESP = "_RESP";
	
	private final String TIPO_DOCUMENTO = ConstantesGestorFicheros.EEFF;
	private final String SUB_TIPO_DOCUMENTO = ConstantesGestorFicheros.EEFFLIBERACION;
	
	private BigDecimal numExpediente;
	
	public SoapLiberarEEFFWSHandler() {
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
		sb.append(numExpediente).append(SUF_NOMBRE_FICHERO_REQ);
		return sb.toString();
	}

	@Override
	public String getNombreFicheroResponse() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		sb.append(numExpediente).append(SUF_NOMBRE_FICHERO_RESP);
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