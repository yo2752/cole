package org.gestoresmadrid.oegamInterga.restWS.response;

import java.io.Serializable;

public class FileResponse implements Serializable {

	private static final long serialVersionUID = 8978955126293157928L;

	private String code;

	private String message;

	private File file;

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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
