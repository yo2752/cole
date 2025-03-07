package org.oegam.gestor.distintivos.model;

import java.io.Serializable;

public class Status implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7337739329921754108L;
	
	private Long status;

	public Status() {}

	public Status(Long status) {
		this.status = status;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	
}
