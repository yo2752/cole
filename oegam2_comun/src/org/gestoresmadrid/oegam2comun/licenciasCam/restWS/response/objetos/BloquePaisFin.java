package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloquePaisFin implements Serializable {

	private static final long serialVersionUID = 8114662064386654566L;

	private BloqueProvincia bloqueProvincia;

	public BloqueProvincia getBloqueProvincia() {
		return bloqueProvincia;
	}

	public void setBloqueProvincia(BloqueProvincia bloqueProvincia) {
		this.bloqueProvincia = bloqueProvincia;
	}
}
