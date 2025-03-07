package org.gestoresmadrid.oegam2comun.model.service;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

public interface ServicioZipeador extends Serializable {

	/**
	 * Devuelve el array de bytes con el contenido en formato zip
	 * @param entry fichero que se incluye en el zip
	 * @return
	 */
	byte[] zipear(File entry);

	/**
	 * Devuelve el array de bytes con el contenido en formato zip
	 * @param entries ficheros que se incluyen el en zip
	 * @return
	 */
	byte[] zipear(Collection<File> entries);

	/**
	 * Crea un fichero zip
	 * @param destiny Fichero donde se guardará el zip
	 * @param entry fichero que se incluye en el zip
	 * @return true si se ha creado con exito, false en caso contrario
	 */
	boolean zipear(File destiny, File entry);

	/**
	 * Crea un fichero zip
	 * @param destiny Fichero donde se guardará el zip
	 * @param entries ficheros que se incluyen el en zip
	 * @return true si se han incluido todos los ficheros con exito, false en caso contrario
	 */
	boolean zipear(File destiny, Collection<File> entries);

	/**
	 * Crea un fichero zip
	 * @param destiny path del fichero donde se guardará el zip
	 * @param entry fichero que se incluye en el zip
	 * @return true si se ha creado con exito, false en caso contrario
	 */
	boolean zipear(String destiny, File entry);

	/**
	 * Crea un fichero zip
	 * @param destiny path del fichero donde se guardará el zip
	 * @param entries ficheros que se incluyen el en zip
	 * @return true si se han incluido todos los ficheros con exito, false en caso contrario
	 */
	boolean zipear(String destiny, Collection<File> entries);

}
