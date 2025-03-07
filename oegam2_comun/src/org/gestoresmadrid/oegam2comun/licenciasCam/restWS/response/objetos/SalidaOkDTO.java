package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class SalidaOkDTO implements Serializable {

	private static final long serialVersionUID = -6759901259340594573L;

	private Bloquedireccion bloquedireccion;

	public Bloquedireccion getBloquedireccion() {
		return bloquedireccion;
	}

	public void setBloquedireccion(Bloquedireccion bloquedireccion) {
		this.bloquedireccion = bloquedireccion;
	}
}
