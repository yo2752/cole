
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class DatosDireccionFin implements Serializable {

	private static final long serialVersionUID = 5905129663339764756L;

	private BloquePais bloquePais;

	public BloquePais getBloquePais() {
		return bloquePais;
	}

	public void setBloquePais(BloquePais bloquePais) {
		this.bloquePais = bloquePais;
	}
}
