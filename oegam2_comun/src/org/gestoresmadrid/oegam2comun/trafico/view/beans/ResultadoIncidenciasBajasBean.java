package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoIncidenciasBajasBean implements Serializable{

	private static final long serialVersionUID = 8029413289777183204L;

	private Boolean error;
	private Exception excepcion;
	private String mensaje;
	private List<String> listaMensajes;
	private File fichero;
	private String nombreFichero;

	public ResultadoIncidenciasBajasBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addListaMensaje(String mensaje){
		if(listaMensajes != null && !listaMensajes.isEmpty()){
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
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
	public List<String> getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

}