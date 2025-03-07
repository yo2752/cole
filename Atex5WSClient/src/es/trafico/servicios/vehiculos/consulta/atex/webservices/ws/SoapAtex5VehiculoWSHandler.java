package es.trafico.servicios.vehiculos.consulta.atex.webservices.ws;

import java.util.Date;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.Document;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandlerCXF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class SoapAtex5VehiculoWSHandler extends GenericWSHandlerCXF {

	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapAtex5VehiculoWSHandler.class);

	public static final String PROPERTY_KEY_ID = "numExpediente";

	private final String PREF_NOMBRE_FICHERO = "ATEX5_VEHICULO_";
	private final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private final String SUF_NOMBRE_FICHERO_RESP = "_RESP";

	private final String TIPO_DOCUMENTO = ConstantesGestorFicheros.ATEX5;
	private final String SUB_TIPO_DOCUMENTO= ConstantesGestorFicheros.ATEX5_VEHICULO_ENVIO;

	private String fecha;

	@Autowired
	private UtilesFecha utilesFecha;

	
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
		sb.append(numExpediente).append("_" + fecha).append(SUF_NOMBRE_FICHERO_REQ);
		return sb.toString();
	}

	@Override
	public String getNombreFicheroResponse() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		sb.append(numExpediente).append("_" + fecha).append(SUF_NOMBRE_FICHERO_RESP);
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

	public SoapAtex5VehiculoWSHandler() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		fecha = utilesFecha.formatoFecha("yyyyMMdd HHmmss", new Date()).replace(" ", "");
	}
	
	
	
}
