
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class DatosDireccionNoFin implements Serializable {

	private static final long serialVersionUID = -1200017922020836926L;

	private Paises paises;

	public Paises getPaises() {
		return paises;
	}

	public void setPaises(Paises paises) {
		this.paises = paises;
	}
}
