
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class Poblacion implements Serializable {

	private static final long serialVersionUID = -9185315648873017634L;

	private Integer codPoblacion;

	private String nomPoblacion;

	private Integer orden;

	public Integer getCodPoblacion() {
		return codPoblacion;
	}

	public void setCodPoblacion(Integer codPoblacion) {
		this.codPoblacion = codPoblacion;
	}

	public String getNomPoblacion() {
		return nomPoblacion;
	}

	public void setNomPoblacion(String nomPoblacion) {
		this.nomPoblacion = nomPoblacion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
