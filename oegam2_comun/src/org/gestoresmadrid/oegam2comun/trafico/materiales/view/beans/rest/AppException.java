package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

public class AppException extends Exception implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3124204326771775922L;
	
	private String  name;
	private int     code;
	private Integer status;
	private String  type;

	public AppException() { }
	
	public AppException(String name, String message, int code, Integer status, String type) {
		super(message);
		this.name   = name;
		this.code   = code;
		this.status = status;
		this.type   = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
