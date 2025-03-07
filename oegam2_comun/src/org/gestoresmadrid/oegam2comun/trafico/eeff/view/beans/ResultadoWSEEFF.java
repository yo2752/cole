package org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans;

import java.io.Serializable;
import java.util.List;

public class ResultadoWSEEFF implements Serializable{

	private static final long serialVersionUID = -8315986806082783341L;
	
	private Boolean error;
	private Exception excepcion;
	private String mensajeError;
	private List<String> listaMensajes;
	private String respuesta;
	
	public ResultadoWSEEFF(Boolean error) {
		super();
		this.error = error;
	}
	
	public ResultadoWSEEFF(Exception excepcion) {
		super();
		this.excepcion = excepcion;
		this.error = Boolean.FALSE;
	}

	public Boolean isError() {
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
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	
}
