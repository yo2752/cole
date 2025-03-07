package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;

public class ResultadoWSError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7130278562526439529L;
	
	private String name;
	private String message;
	private Long code;
	private Long status;
	private String type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
