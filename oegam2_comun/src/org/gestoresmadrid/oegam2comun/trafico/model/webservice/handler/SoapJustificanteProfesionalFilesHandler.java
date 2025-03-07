package org.gestoresmadrid.oegam2comun.trafico.model.webservice.handler;

import javax.xml.rpc.handler.HandlerInfo;

import org.w3c.dom.Document;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;

/**
 * Manejador para las peticiones al WS de Compra de tasas de DGT
 */
public class SoapJustificanteProfesionalFilesHandler extends GenericWSHandler {

	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapJustificanteProfesionalFilesHandler.class);

	/* INICIO ATRIBUTOS/CONSTANTES */

	public static final String PROPERTY_KEY_ID = "id_justificante";
	public static final String PROPERTY_KEY_TIPO = "tipoOperacion";

	/* FIN ATRIBUTOS/CONSTANTES */

	private Long idJustificante;
	private String tipoPeticion;

	/**
	 * @see javax.xml.rpc.handler.Handler#init(javax.xml.rpc.handler.HandlerInfo)
	 */
	@Override
	public void init(HandlerInfo arg0) {
		super.init(arg0);
		if (arg0.getHandlerConfig() != null && arg0.getHandlerConfig().get(PROPERTY_KEY_ID) != null) {
			idJustificante = (Long) arg0.getHandlerConfig().get(PROPERTY_KEY_ID);
			tipoPeticion = (String) arg0.getHandlerConfig().get(PROPERTY_KEY_TIPO);
		}
	}

	/* INICIO MÉTODOS ABSTRACTOS A IMPLEMENTAR */

	@Override
	public String getTipoDocumentoRequest() {

		return ConstantesGestorFicheros.JUSTIFICANTES;
	}

	@Override
	public String getTipoDocumentoResponse() {

		return ConstantesGestorFicheros.JUSTIFICANTES;
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
		return "REQ_" + tipoPeticion + idJustificante;
	}

	@Override
	public String getNombreFicheroResponse() {
		return "RESP_" + tipoPeticion + idJustificante;
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

	/* FIN MÉTODOS ABSTRACTOS A IMPLEMENTAR */

}