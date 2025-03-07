
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;
import java.util.List;

public class Viales implements Serializable {

	private static final long serialVersionUID = -7505341117896243728L;

	private List<Vial> vial;

	private Integer numeroViales;

	public List<Vial> getVial() {
		return vial;
	}

	public void setVial(List<Vial> vial) {
		this.vial = vial;
	}

	public Integer getNumeroViales() {
		return numeroViales;
	}

	public void setNumeroViales(Integer numeroViales) {
		this.numeroViales = numeroViales;
	}
}
