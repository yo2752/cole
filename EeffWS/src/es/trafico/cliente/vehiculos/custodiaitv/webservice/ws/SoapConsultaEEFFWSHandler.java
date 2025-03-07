package es.trafico.cliente.vehiculos.custodiaitv.webservice.ws;

import java.math.BigDecimal;

import javax.xml.rpc.handler.HandlerInfo;

import org.w3c.dom.Document;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;

public class SoapConsultaEEFFWSHandler extends GenericWSHandler{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapConsultaEEFFWSHandler.class);
	
	public static final String PROPERTY_KEY_ID = "numExpediente";
	
	private final String PREF_NOMBRE_FICHERO = "RESPUESTA_WS_";
	
	private final String TIPO_DOCUMENTO = ConstantesGestorFicheros.EEFF;
	private final String SUB_TIPO_DOCUMENTO_RESP = ConstantesGestorFicheros.EEFFCONSULTA;
	
	private BigDecimal numExpediente;
	
	public SoapConsultaEEFFWSHandler() {
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
		return null;
	}

	@Override
	public String getTipoDocumentoResponse() {
		return TIPO_DOCUMENTO;
	}

	@Override
	public String getSubtipoDocumentoRequest() {
		return null;
	}

	@Override
	public String getSubtipoDocumentoResponse() {
		return SUB_TIPO_DOCUMENTO_RESP;
	}

	@Override
	public String getNombreFicheroRequest() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		sb.append(numExpediente);
		return sb.toString();
	}

	@Override
	public String getNombreFicheroResponse() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		sb.append(numExpediente);
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
