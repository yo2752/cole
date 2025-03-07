package org.gestoresmadrid.oegamInterga.restWS.response;

import java.io.Serializable;

public class FilePdfResponse implements Serializable {

	private static final long serialVersionUID = -8110080099448456939L;

	private String code;

	private String message;

	private String pdfB64;

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

	public String getPdfB64() {
		return pdfB64;
	}

	public void setPdfB64(String pdfB64) {
		this.pdfB64 = pdfB64;
	}
}
