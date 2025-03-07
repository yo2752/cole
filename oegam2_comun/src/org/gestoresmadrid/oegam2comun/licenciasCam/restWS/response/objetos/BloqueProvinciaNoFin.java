
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloqueProvinciaNoFin implements Serializable {

	private static final long serialVersionUID = -4496580966784932773L;

	private Poblaciones poblaciones;

	public Poblaciones getPoblaciones() {
		return poblaciones;
	}

	public void setPoblaciones(Poblaciones poblaciones) {
		this.poblaciones = poblaciones;
	}
}
