package org.gestoresmadrid.oegam2comun.modelo600_601.view.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.modelos.model.enumerados.ErroresWSModelo600601;

public class ResultadoModelos {

	private boolean error;
	private Boolean errorSubsanable;
	private ErroresWSModelo600601 errorWS;
	private String respuesta;
	private Date fechaEjecucion;
	private Exception excepcion;
	private String mensajeError;
	private List<String> listaMensajes;
	private Map<String, Object>	attachments;

	public ResultadoModelos(boolean error, List<String> listaMensajes) {
		super();
		this.error = error;
		this.listaMensajes = listaMensajes;
		this.errorSubsanable = false;
	}

	public ResultadoModelos(boolean error, String mensajeError) {
		super();
		this.error = error;
		this.mensajeError = mensajeError;
		this.errorSubsanable = false;
	}

	public ResultadoModelos(boolean error, Exception excepcion) {
		super();
		this.error = error;
		this.excepcion = excepcion;
		this.errorSubsanable = false;
	}

	public ResultadoModelos() {
		this.error = false;
		this.errorSubsanable = false;
	}

	public ResultadoModelos(boolean error) {
		this.error = error;
		this.errorSubsanable = false;
	}

	public ResultadoModelos(boolean error, Exception excepcion,
			String mensajeError, List<String> listaMensajes,
			Map<String, Object> attachments) {
		super();
		this.error = error;
		this.excepcion = excepcion;
		this.mensajeError = mensajeError;
		this.listaMensajes = listaMensajes;
		this.errorSubsanable = false;
	}

	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
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

	public Object getAttachment(String key) {
		if (null == attachments){
			return null;
		} else{
			return attachments.get(key);
		}
	}

	public void addAttachment(String key, Object value) {
		if (null == attachments){
			attachments = new HashMap<>();
		}
		attachments.put(key, value);
	}

	public Boolean getErrorSubsanable() {
		return errorSubsanable;
	}

	public void setErrorSubsanable(Boolean errorSubsanable) {
		this.errorSubsanable = errorSubsanable;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public Map<String, Object> getAttachments() {
		return attachments;
	}

	public ErroresWSModelo600601 getErrorWS() {
		return errorWS;
	}

	public void setErrorWS(ErroresWSModelo600601 errorWS) {
		this.errorWS = errorWS;
	}

}