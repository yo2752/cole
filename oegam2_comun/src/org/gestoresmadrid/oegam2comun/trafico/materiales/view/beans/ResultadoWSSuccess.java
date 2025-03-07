package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;

public class ResultadoWSSuccess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2873953613704575449L;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
