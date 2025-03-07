package org.gestoresmadrid.gescogroup.ws;

import java.sql.Timestamp;
import java.util.Date;




import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;

public class SoapConsultaTarjetaEitvWSHandler extends GenericWSHandler{
/* INICIO ATRIBUTOS/CONSTANTES */
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapConsultaTarjetaEitvWSHandler.class);
	
	private final String PREF_NOMBRE_FICHERO_REQ = "CONSULTA_TARJETA_EITV_";
	
	private final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private final String SUF_NOMBRE_FICHERO_RESP = "_RESP";
	
	private final String TIPO_DOCUMENTO = ConstantesGestorFicheros.MATE;
	private final String SUB_TIPO_DOCUMENTO = ConstantesGestorFicheros.EITV;
	
	private final String XML_NODO_BASTIDOR = "identificador";
	
	private Date fechaFichero;
	private String bastidor;
	
	/* FIN ATRIBUTOS/CONSTANTES */
	
	
	/* CONSTRUCTOR */
	public SoapConsultaTarjetaEitvWSHandler() {
		fechaFichero = new Date();
	}

	
	/* INICIO MÉTODOS PÚBLICOS */
	
	/* FIN MÉTODOS PÚBLICOS */

	
	/* INICIO GETTERS/SETTERS */

	/* FIN GETTERS/SETTERS */

	
	/* INICIO MÉTODOS ABSTRACTOS A IMPLEMENTAR */

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

		return componerNombreFichero(PREF_NOMBRE_FICHERO_REQ, bastidor, fechaFichero, SUF_NOMBRE_FICHERO_REQ);
	}

	@Override
	public String getNombreFicheroResponse() {

		return componerNombreFichero(PREF_NOMBRE_FICHERO_REQ, bastidor, fechaFichero, SUF_NOMBRE_FICHERO_RESP);
	}

	@Override
	public void setDOMRequest(Document doc) {
		// Guardo atributos de la request que luego necesito en el response
		guardarAtributosTemporales(doc);
	}

	@Override
	public void setDOMResponse(Document doc) {
	}

	@Override
	public ILoggerOegam getLogger() {
		
		return log;
	}

	/* FIN MÉTODOS ABSTRACTOS A IMPLEMENTAR */


	/* INICIO MÉTODOS PRIVADOS */
	
	/**
	 * 
	 * @param prefijo
	 * @param nombre
	 * @param fecha
	 * @param sufijo
	 * @return
	 */
	private String componerNombreFichero(String prefijo, String nombre, Date fecha, String sufijo) {
		
		String nombreFichero = emptyString(prefijo) + emptyString(nombre) + "_" + new Timestamp((fecha).getTime()).toString() + emptyString(sufijo);
		nombreFichero = nombreFichero.replace(':', '_');
        nombreFichero = nombreFichero.replace('-', '_');
        nombreFichero = nombreFichero.replace(' ', '_');
        nombreFichero = nombreFichero.replace('.', '_');
		
		return nombreFichero;
	}


	/**
	 * Método que devuelve una cadena vacía si ésta es nula.
	 * 
	 * @param cadena
	 * @return
	 */
	private String emptyString(String cadena) {
		
		return (cadena == null) ? "" : cadena;
	}

	
	/**
	 * Método que guarda atributos temporales del DOM para poder usarlos durante el ciclo de vida del objeto.
	 * 
	 * @param doc
	 */
	private void guardarAtributosTemporales(Document doc) {
		
		// Nodo 'bastidor'
		NodeList nodoBastidor = doc.getDocumentElement().getElementsByTagName(XML_NODO_BASTIDOR);
		if (nodoBastidor != null && nodoBastidor.getLength() > 0) {
			bastidor = obtenerValorNodo(nodoBastidor);
		}
	}
	
		
	/**
	 * 
	 * @param nodo
	 * @return
	 */
	private String obtenerValorNodo(NodeList nodo) {
		
		// Valor del nodo
		String valorNodo = (nodo.item(0) != null && nodo.item(0).getFirstChild() != null) ? nodo.item(0).getFirstChild().getNodeValue() : "";

		return valorNodo;
	}
	
	/* FIN MÉTODOS PRIVADOS */
}
