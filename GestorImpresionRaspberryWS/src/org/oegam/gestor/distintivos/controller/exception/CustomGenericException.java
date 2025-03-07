package org.oegam.gestor.distintivos.controller.exception;

import org.springframework.http.HttpStatus;

public class CustomGenericException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5877663051586079382L;
	
	private static final String NAME_EXCEPTION = "GenericException";

	private String     errName;
	private String     errCode;
	private String     errMsg;
	private HttpStatus status;
	
	public CustomGenericException(String errCode, String errMsg, HttpStatus status) {
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

	public String getErrName() {
		return errName;
	}

	public void setErrName(String errName) {
		this.errName = errName;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}	
}
