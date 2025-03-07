package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class Response implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4804684663243403996L;
	
	private String message;
	private Long   id;
	private LinkedHashMap<String, List<String>> errors;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LinkedHashMap<String, List<String>> getErrors() {
		return errors;
	}

	public void setErrors(LinkedHashMap<String, List<String>> errors) {
		this.errors = errors;
	}
	
}
