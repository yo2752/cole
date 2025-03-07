package org.gestoresmadrid.oegam2comun.arrendatarios.view.beans;

import java.io.Serializable;
import java.util.List;

public class ResultadoWSCaycBean implements Serializable{
	
	private static final long serialVersionUID = -874717157000796758L;

	private Boolean error;

	private Exception excepcion;

	private String mensajeError;

	private List<String> listaMensajes;

	private String respuestaWS;
	
	private String numRegistro;
	
	public ResultadoWSCaycBean() {
		super();
		this.error = Boolean.FALSE;
	}
	
	public ResultadoWSCaycBean(Boolean error) {
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

	public String getRespuestaWS() {
		return respuestaWS;
	}

	public void setRespuestaWS(String respuestaWS) {
		this.respuestaWS = respuestaWS;
	}

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}

}
