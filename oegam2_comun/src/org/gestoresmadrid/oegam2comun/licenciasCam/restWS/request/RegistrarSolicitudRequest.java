package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.request;

import java.io.Serializable;

public class RegistrarSolicitudRequest implements Serializable {

	private static final long serialVersionUID = -7791233022050380099L;

	private String ficheroBase64;

	public String getFicheroBase64() {
		return ficheroBase64;
	}

	public void setFicheroBase64(String ficheroBase64) {
		this.ficheroBase64 = ficheroBase64;
	}
}
