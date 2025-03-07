package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class SalidaErrorDTO implements Serializable {

	private static final long serialVersionUID = 6389877475136476643L;

	private String descripcionError;

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
}
