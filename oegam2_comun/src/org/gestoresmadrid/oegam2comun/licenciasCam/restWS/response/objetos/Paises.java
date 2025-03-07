
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;
import java.util.List;

public class Paises implements Serializable {

	private static final long serialVersionUID = -4107628231791305085L;

	private List<Pais> pais;

	private Integer numeroPaises;

	public List<Pais> getPais() {
		return pais;
	}

	public void setPais(List<Pais> pais) {
		this.pais = pais;
	}

	public Integer getNumeroPaises() {
		return numeroPaises;
	}

	public void setNumeroPaises(Integer numeroPaises) {
		this.numeroPaises = numeroPaises;
	}
}
