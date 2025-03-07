package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoMatriculacionesBean implements Serializable {

	private static final long serialVersionUID = -4504382636931955095L;

	private Boolean error;
	private String mensaje;
	private Exception excepcion;
	private List<String> listaMensajes;

	public ResultadoMatriculacionesBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addListaMensaje(String mensaje) {
		if (listaMensajes != null && listaMensajes.isEmpty()) {
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}

	public void addListaMensaje(List<String> mensajes) {
		if (listaMensajes != null && listaMensajes.isEmpty()) {
			listaMensajes = new ArrayList<String>();
		}
		for (String mensaje : mensajes) {
			listaMensajes.add(mensaje);
		}
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

}