package org.gestoresmadrid.oegam2comun.licenciasCam.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcIntervinienteDto;

public class ResultadoLicenciasBean implements Serializable {

	private static final long serialVersionUID = 7558471888665499902L;

	private Boolean error;

	private String mensaje;

	private List<String> validaciones;

	private LcIntervinienteDto interviniente;

	private Object obj;

	private Map<String, Object> attachments;

	public LcIntervinienteDto getInterviniente() {
		return interviniente;
	}

	public void setInterviniente(LcIntervinienteDto interviniente) {
		this.interviniente = interviniente;
	}

	public void addValidacion(String mensaje) {
		if (validaciones == null || validaciones.isEmpty()) {
			validaciones = new ArrayList<String>();
		}
		validaciones.add(mensaje);
	}

	public ResultadoLicenciasBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addMensaje(String mensajeError) {
		if (mensaje == null || mensaje.isEmpty()) {
			mensaje = mensajeError;
		} else {
			mensaje += mensajeError;
		}
	}

	public void addAttachment(String key, Object value) {
		if (null == attachments) {
			attachments = new HashMap<String, Object>();
		}
		attachments.put(key, value);
	}

	public Object getAttachment(String key) {
		if (null == attachments) {
			return null;
		} else {
			return attachments.get(key);
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

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public List<String> getValidaciones() {
		return validaciones;
	}

	public void setValidaciones(List<String> validaciones) {
		this.validaciones = validaciones;
	}

	public Map<String, Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, Object> attachments) {
		this.attachments = attachments;
	}
}
