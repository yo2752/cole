package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;

public class ResultadoExcelKOBean implements Serializable{

	private static final long serialVersionUID = -6332178817977582733L;
	
	private Boolean error;
	private Exception excepcion;
	private String mensaje;
	private String nombreFichero;
	
	public ResultadoExcelKOBean(Boolean error) {
		super();
		this.error = error;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public Exception getExcepcion() {
		return excepcion;
	}
	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
}
