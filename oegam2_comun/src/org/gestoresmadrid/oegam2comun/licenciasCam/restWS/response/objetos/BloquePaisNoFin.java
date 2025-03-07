
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloquePaisNoFin implements Serializable {

	private static final long serialVersionUID = -2807811159805817898L;

	private Provincias provincias;

	public Provincias getProvincias() {
		return provincias;
	}

	public void setProvincias(Provincias provincias) {
		this.provincias = provincias;
	}
}
