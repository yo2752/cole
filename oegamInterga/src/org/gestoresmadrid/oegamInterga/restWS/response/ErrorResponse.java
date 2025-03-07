package org.gestoresmadrid.oegamInterga.restWS.response;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 7573064647822927695L;

	private int code;

	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
