
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class Provincia implements Serializable {

	private static final long serialVersionUID = -154275131930358891L;

	private Integer codProvincia;

	private String nomProvincia;

	private Integer orden;

	public Integer getCodProvincia() {
		return codProvincia;
	}

	public void setCodProvincia(Integer codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getNomProvincia() {
		return nomProvincia;
	}

	public void setNomProvincia(String nomProvincia) {
		this.nomProvincia = nomProvincia;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
