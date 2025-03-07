package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloqueProvinciaFin implements Serializable {

	private static final long serialVersionUID = 1351531154016927999L;

	private BloquePoblacion bloquePoblacion;

	public BloquePoblacion getBloquePoblacion() {
		return bloquePoblacion;
	}

	public void setBloquePoblacion(BloquePoblacion bloquePoblacion) {
		this.bloquePoblacion = bloquePoblacion;
	}
}
