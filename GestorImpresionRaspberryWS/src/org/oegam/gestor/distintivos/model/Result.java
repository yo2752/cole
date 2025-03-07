package org.oegam.gestor.distintivos.model;

import java.util.HashMap;

public class Result {
	public static final Integer OK = 0;
	public static final Integer KO = 0;
	
	private Integer status;
	private HashMap<String, Object> msg = new HashMap<String, Object>();
	
	public Result() {}
	public Result(Integer status) {
		this.setStatus(status);
	}
	
	public HashMap<String, Object> getMsg() {
		return msg;
	}
	public void setMsg(HashMap<String, Object> msg) {
		this.msg = msg;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
