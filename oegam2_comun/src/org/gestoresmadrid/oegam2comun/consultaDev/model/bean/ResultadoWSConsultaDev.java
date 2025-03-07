package org.gestoresmadrid.oegam2comun.consultaDev.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoWSConsultaDev implements Serializable{

	private static final long serialVersionUID = -5702371312298575759L;
	
	private Boolean error;
	private Exception excepcion;
	private String mensajeError;
	private List<String> listaMensajes;
	private String cifPeticion;
	
	public ResultadoWSConsultaDev() {
		super();
		this.error = false;
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
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void addMensajeALista(String mensaje){
		if(this.listaMensajes == null){
			this.listaMensajes = new ArrayList<String>();
		}
		this.listaMensajes.add(mensaje);
	}
	public String getCifPeticion() {
		return cifPeticion;
	}
	public void setCifPeticion(String cifPeticion) {
		this.cifPeticion = cifPeticion;
	}
	
}
