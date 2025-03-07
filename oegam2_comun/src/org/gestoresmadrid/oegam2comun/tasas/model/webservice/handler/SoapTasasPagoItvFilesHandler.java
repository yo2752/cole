package org.gestoresmadrid.oegam2comun.tasas.model.webservice.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.rpc.handler.HandlerInfo;

import org.w3c.dom.Document;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;

/**
 * Manejador para las peticiones al WS de Compra de tasas de DGT
 */
public class SoapTasasPagoItvFilesHandler extends GenericWSHandler {

	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapTasasPagoItvFilesHandler.class);

	/* INICIO ATRIBUTOS/CONSTANTES */

	public static final String PROPERTY_KEY_ID = "idCompra";
	public static final String PROPERTY_KEY_SERVICE = "serviceType";

	private static final String PREF_NOMBRE_FICHERO_RESP = "CT_";
	private static final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private static final String SUF_NOMBRE_FICHERO_RESP = "_RESP";

	/* FIN ATRIBUTOS/CONSTANTES */

	private Date fechaFichero;
	private String idCompra;
	private String serviceType;

	/* CONSTRUCTOR */
	public SoapTasasPagoItvFilesHandler() {
		fechaFichero = new Date();
	}

	/**
	 * @see javax.xml.rpc.handler.Handler#init(javax.xml.rpc.handler.HandlerInfo)
	 */
	@Override
	public void init(HandlerInfo arg0) {
		super.init(arg0);
		if (arg0.getHandlerConfig() != null) {
			if (arg0.getHandlerConfig().get(PROPERTY_KEY_ID) != null) {
				idCompra = arg0.getHandlerConfig().get(PROPERTY_KEY_ID).toString();
			}
			if (arg0.getHandlerConfig().get(PROPERTY_KEY_SERVICE) != null) {
				serviceType = arg0.getHandlerConfig().get(PROPERTY_KEY_SERVICE).toString();
			}
		}
	}

	/* INICIO MÉTODOS ABSTRACTOS A IMPLEMENTAR */

	@Override
	public String getTipoDocumentoRequest() {

		return ConstantesGestorFicheros.COMPRA_TASAS;
	}

	@Override
	public String getTipoDocumentoResponse() {

		return ConstantesGestorFicheros.COMPRA_TASAS;
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
		if (serviceType!=null) {
			sb.append(serviceType).append("_");
		}
		if (idCompra!= null) {
			sb.append(idCompra).append("_");
		}
		sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(fechaFichero.getTime())).append(SUF_NOMBRE_FICHERO_REQ);
		return sb.toString();
	}

	@Override
	public String getNombreFicheroResponse() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO_RESP);
		if (serviceType!=null) {
			sb.append(serviceType).append("_");
		}
		if (idCompra!= null) {
			sb.append(idCompra).append("_");
		}
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

	/* FIN MÉTODOS ABSTRACTOS A IMPLEMENTAR */

}