package org.oegam.gestor.distintivos.controller.exception;

import org.springframework.http.HttpStatus;

public class CustomIOException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4763311884994593793L;
	
	private static final String NAME_EXCEPTION = "OegamExcepcion";
	
	private String     errCode;
	private String     errMsg;
	private HttpStatus status;
	private String     errName;
	
	public CustomIOException(String errCode, String errMsg, HttpStatus status) {
		this.setErrName(NAME_EXCEPTION);
		this.setErrCode(errCode);
		this.setErrMsg(errMsg);
		this.setStatus(status);
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getErrName() {
		return errName;
	}

	public void setErrName(String errName) {
		this.errName = errName;
	}	

}
