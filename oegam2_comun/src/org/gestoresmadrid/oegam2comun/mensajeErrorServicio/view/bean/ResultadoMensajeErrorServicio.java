/**
 * 
 */
package org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean;

import java.io.File;
import java.io.Serializable;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author ext_fjcl
 *
 */
public class ResultadoMensajeErrorServicio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5067016903716200544L;
	
	private boolean error;
	private String mensaje;
	private String nombreFichero;
	private XSSFWorkbook fichero;
	private File file;
	
	public ResultadoMensajeErrorServicio(boolean _error){
		this.error = _error;
	}

	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the fichero
	 */
	public XSSFWorkbook getFichero() {
		return fichero;
	}

	/**
	 * @param fichero the fichero to set
	 */
	public void setFichero(XSSFWorkbook fichero) {
		this.fichero = fichero;
	}

	/**
	 * @return the nombreFichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}

	/**
	 * @param nombreFichero the nombreFichero to set
	 */
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
}
