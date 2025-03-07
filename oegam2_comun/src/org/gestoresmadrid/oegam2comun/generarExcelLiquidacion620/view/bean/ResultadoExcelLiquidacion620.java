/**
 * 
 */
package org.gestoresmadrid.oegam2comun.generarExcelLiquidacion620.view.bean;

import java.io.File;
import java.io.Serializable;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ResultadoExcelLiquidacion620 implements Serializable{


	private static final long serialVersionUID = 4868022279933553104L;
	
	private boolean error;
	private String mensaje;
	private String nombreFichero;
	private XSSFWorkbook fichero;
	private File file;
	
	public ResultadoExcelLiquidacion620(boolean _error){
		this.error = _error;
	}


	public boolean isError() {
		return error;
	}


	public void setError(boolean error) {
		this.error = error;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public XSSFWorkbook getFichero() {
		return fichero;
	}


	public void setFichero(XSSFWorkbook fichero) {
		this.fichero = fichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}


	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}
}
