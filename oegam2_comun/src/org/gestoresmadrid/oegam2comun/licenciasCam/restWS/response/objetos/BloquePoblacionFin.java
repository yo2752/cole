package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloquePoblacionFin implements Serializable {

	private static final long serialVersionUID = -1816472399363701398L;

	private BloqueVial bloqueVial;

	public BloqueVial getBloqueVial() {
		return bloqueVial;
	}

	public void setBloqueVial(BloqueVial bloqueVial) {
		this.bloqueVial = bloqueVial;
	}
}
