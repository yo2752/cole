package org.gestoresmadrid.oegam2comun.anuntis.model.webservice.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Document;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;

public class SoapAnuntisFilesHandler extends GenericWSHandler {

	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapAnuntisFilesHandler.class);

	private static final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private static final String SUF_NOMBRE_FICHERO_RESP = "_RESP";
	private static final String PREF_NOMBRE_FICHERO_RESP = "ANUNTIS_";

	private Date fechaFichero;

	public SoapAnuntisFilesHandler() {
		fechaFichero = new Date();
	}

	@Override
	public String getTipoDocumentoRequest() {
		return ConstantesGestorFicheros.ANUNTIS;
	}

	@Override
	public String getTipoDocumentoResponse() {
		return ConstantesGestorFicheros.ANUNTIS;
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
		return PREF_NOMBRE_FICHERO_RESP + new SimpleDateFormat("yyyyMMddHHmmss").format(fechaFichero.getTime()) + SUF_NOMBRE_FICHERO_REQ;
	}

	@Override
	public String getNombreFicheroResponse() {
		return PREF_NOMBRE_FICHERO_RESP + new SimpleDateFormat("yyyyMMddHHmmss").format(fechaFichero.getTime()) + SUF_NOMBRE_FICHERO_RESP;
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