
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloqueVialNoFinDTO implements Serializable {

	private static final long serialVersionUID = 386073574240719890L;
	
	private Numeros numeros;

	public Numeros getNumeros() {
		return numeros;
	}

	public void setNumeros(Numeros numeros) {
		this.numeros = numeros;
	}
}
