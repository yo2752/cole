package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.util.List;

public class ResultadoConsultaEitvBean {
	private boolean error;
	private Exception exception;
	private String mensaje;
	private List<String> mensajesError;

	public ResultadoConsultaEitvBean() {
		super();
	}

	public ResultadoConsultaEitvBean(boolean error, String mensaje) {
		super();
		this.error = error;
		this.mensaje = mensaje;
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<String> getMensajesError() {
		return mensajesError;
	}

	public void setMensajesError(List<String> mensajesError) {
		this.mensajesError = mensajesError;
	}

}