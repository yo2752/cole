package org.gestoresmadrid.oegamInterga.restWS.response;

import java.io.Serializable;

public class Errors implements Serializable {

	private static final long serialVersionUID = -8686694555993716305L;

	private String code;

	private String message;

	private int line;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
}
