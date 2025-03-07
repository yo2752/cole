package org.gestoresmadrid.oegam2comun.circular.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResumenCircularBean implements Serializable {

	private static final long serialVersionUID = 5672210717124092766L;

	public Integer numOk;
	public Integer numError;
	public List<String> listaOk;
	public List<String> listaErrores;
	private Map<String, Object> attachments;

	public void addListaOk(String mensaje) {
		if (listaOk == null || listaOk.isEmpty()) {
			listaOk = new ArrayList<String>();
			numOk = new Integer(0);
		}
		listaOk.add(mensaje);
		numOk++;
	}

	public void addListaKO(String mensaje) {
		if (listaErrores == null || listaErrores.isEmpty()) {
			listaErrores = new ArrayList<String>();
			numError = new Integer(0);
		}
		listaErrores.add(mensaje);
		numError++;
	}

	public Map<String, Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, Object> attachments) {
		this.attachments = attachments;
	}

	/**
	 * Add an attachment
	 * 
	 * @param key
	 * @param value
	 */
	public void addAttachment(String key, Object value) {
		if (null == attachments) {
			attachments = new HashMap<String, Object>();
		}
		attachments.put(key, value);
	}

	/**
	 * @return an attachment
	 */
	public Object getAttachment(String key) {
		if (null == attachments) {
			return null;
		} else {
			return attachments.get(key);
		}
	}

	public ResumenCircularBean() {
		super();
	}

	public void addNumError() {
		if (numError == null) {
			numError = 0;
		}
		numError++;
	}

	public void addNumOk() {
		if (numOk == null) {
			numOk = 0;
		}
		numOk++;
	}

	public void addListaMensajeOk(String mensaje) {
		if (listaOk == null || listaOk.isEmpty()) {
			listaOk = new ArrayList<String>();
		}
		listaOk.add(mensaje);
	}

	public void addListaMensajeError(String mensaje) {
		if (listaErrores == null || listaErrores.isEmpty()) {
			listaErrores = new ArrayList<String>();
		}
		listaErrores.add(mensaje);
	}

	public void addListaNumErrores(Integer numErrores) {
		if (numError == null) {
			numError = 0;
		}
		numError += numErrores;
	}

	public void addListaErrores(List<String> listaMensaje) {
		if (listaErrores == null) {
			listaErrores = new ArrayList<String>();
		}
		for (String mensaje : listaMensaje) {
			listaErrores.add(mensaje);
		}
	}

	public void addListaNumOk(Integer numOks) {
		if (numOk == null) {
			numOk = 0;
		}
		numOk += numOks;
	}

	public void addListaOk(List<String> listaMensaje) {
		if (listaOk == null) {
			listaOk = new ArrayList<String>();
		}
		for (String mensaje : listaMensaje) {
			listaOk.add(mensaje);
		}
	}

	public Integer getNumOk() {
		return numOk;
	}

	public void setNumOk(Integer numOk) {
		this.numOk = numOk;
	}

	public Integer getNumError() {
		return numError;
	}

	public void setNumError(Integer numError) {
		this.numError = numError;
	}

	public List<String> getListaOk() {
		return listaOk;
	}

	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
	}

	public List<String> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}

}
