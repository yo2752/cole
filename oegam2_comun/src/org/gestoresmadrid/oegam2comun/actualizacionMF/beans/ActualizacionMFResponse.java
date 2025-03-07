package org.gestoresmadrid.oegam2comun.actualizacionMF.beans;

import java.io.Serializable;

public class ActualizacionMFResponse implements Serializable {

	private static final long serialVersionUID = 8798651064482764118L;

	private byte[] ficheroResultado;

	private Long idActualizacion;

	private boolean error;

	private String mensajeError;

	private Throwable exception;

	public Long getIdActualizacion() {
		return idActualizacion;
	}

	public void setIdActualizacion(Long idActualizacion) {
		this.idActualizacion = idActualizacion;
	}

	public byte[] getFicheroResultado() {
		return ficheroResultado;
	}

	public void setFicheroResultado(byte[] ficheroResultado) {
		this.ficheroResultado = ficheroResultado;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensaje) {
		this.mensajeError = mensaje;
	}

}