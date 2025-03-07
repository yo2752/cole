package org.gestoresmadrid.oegam2comun.pegatinas.model.dto;

import java.util.List;

public class RespuestaPegatinas {

	private boolean error = false;
	private Exception exception;
	private String mensajeError;
	private List<String> mensajesError;
	
	public RespuestaPegatinas() {
		super();
	}
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public List<String> getMensajesError() {
		return mensajesError;
	}

	public void setMensajesError(List<String> mensajesError) {
		this.mensajesError = mensajesError;
	}
	
}