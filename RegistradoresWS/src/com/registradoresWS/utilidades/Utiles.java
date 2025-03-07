package com.registradoresWS.utilidades;

import org.apache.log4j.Logger;

public class Utiles {

	private static final Logger log = Logger.getLogger(Utiles.class);

	public static String incorporarAtributos(String cadenaXML) throws Exception {
		try {
			// Cadena que ha de insertarse:
			String atributos1 = " " + Propiedades.leer(Propiedades.XMLNS_XSI) + " " + Propiedades.leer(Propiedades.XSI_NON_NAME_SPACE_SCHEMA_LOCATION);
			StringBuilder modificada = new StringBuilder();
			// Para poder hacer inserciones en la cadena:
			modificada.append(cadenaXML);
			// Localiza la posición exacta de la segunda inserción:
			int pos = modificada.indexOf("/>");
			// Inserta la cadena en la posición:
			modificada.insert(pos, atributos1);
			return modificada.toString();
		} catch (Exception ex) {
			log.error(ex);
			throw ex;
		}
	}
}
