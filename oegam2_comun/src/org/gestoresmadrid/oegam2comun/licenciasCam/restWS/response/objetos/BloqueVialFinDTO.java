package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloqueVialFinDTO implements Serializable {

	private static final long serialVersionUID = 8333195771133904253L;
	
	private BloqueNumero bloqueNumero;

	public BloqueNumero getBloqueNumero() {
		return bloqueNumero;
	}

	public void setBloqueNumero(BloqueNumero bloqueNumero) {
		this.bloqueNumero = bloqueNumero;
	}
}
