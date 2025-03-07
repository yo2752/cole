package org.gestoresmadrid.oegamInterga.restWS.response;

import java.io.Serializable;
import java.util.List;

public class SendFilesResponse implements Serializable {

	private static final long serialVersionUID = -8801172478821933239L;

	private String result;

	private List<Errors> errors;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<Errors> getErrors() {
		return errors;
	}

	public void setErrors(List<Errors> errors) {
		this.errors = errors;
	}

}
