package org.gestoresmadrid.oegamImportacion.tasa.dto;

import java.util.List;

import org.gestoresmadrid.oegamImportacion.tasa.bean.ResumenTasas;

public class RespuestaTasas {
	private boolean error = false;
	private Exception exception;
	private String mensajeError;
	private List<String> mensajesError;
	private List<ResumenTasas> listaResumenTasas;

	public RespuestaTasas() {
		super();
	}

	public RespuestaTasas(boolean error, String mensajeError) {
		super();
		this.error = error;
		this.mensajeError = mensajeError;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public void setListaMensajes(List<String> mensajesError) {
		this.mensajesError = mensajesError;
	}

	public List<String> getMensajesError() {
		return mensajesError;
	}

	public void setMensajesError(List<String> mensajesError) {
		this.mensajesError = mensajesError;
	}

	public void setListaResumenTasas(List<ResumenTasas> listaResumenTasas) {
		this.listaResumenTasas = listaResumenTasas;
	}

	public List<ResumenTasas> getListaResumenTasas() {
		return listaResumenTasas;
	}

}