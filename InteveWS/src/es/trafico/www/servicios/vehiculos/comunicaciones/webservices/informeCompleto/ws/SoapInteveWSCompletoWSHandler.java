package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.ws;

import java.text.SimpleDateFormat;

import org.w3c.dom.Document;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandlerCXF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class SoapInteveWSCompletoWSHandler extends GenericWSHandlerCXF{

	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapInteveWSCompletoWSHandler.class);

	public static final String PROPERTY_KEY_ID = "numExpediente";

	private final String PREF_NOMBRE_FICHERO = "INTEVE_WS_COMPLETO_";
	private final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private final String SUF_NOMBRE_FICHERO_RESP = "_RESP";

	private final String TIPO_DOCUMENTO = ConstantesGestorFicheros.INTEVE_WS_COMPLETO;
	private final String SUB_TIPO_DOCUMENTO_REQ = ConstantesGestorFicheros.ENVIO;
	private final String SUB_TIPO_DOCUMENTO_RESP = ConstantesGestorFicheros.RESPUESTA;

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
		sb.append(numExpediente).append("_");
		sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(fechaFichero.getTime())).append(SUF_NOMBRE_FICHERO_REQ);
		return sb.toString();
	}

	@Override
	public String getNombreFicheroResponse() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		sb.append(numExpediente).append("_");
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

}