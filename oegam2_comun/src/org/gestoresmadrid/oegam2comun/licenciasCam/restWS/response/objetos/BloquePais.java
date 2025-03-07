
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloquePais implements Serializable {

	private static final long serialVersionUID = 4773102708666198571L;

	private Integer codPais;

	private String nomPais;

	private Integer estadoProvincia;

	private BloquePaisFin bloquePaisFin;

	private BloquePaisNoFin bloquePaisNoFin;

	public Integer getCodPais() {
		return codPais;
	}

	public void setCodPais(Integer codPais) {
		this.codPais = codPais;
	}

	public String getNomPais() {
		return nomPais;
	}

	public void setNomPais(String nomPais) {
		this.nomPais = nomPais;
	}

	public Integer getEstadoProvincia() {
		return estadoProvincia;
	}

	public void setEstadoProvincia(Integer estadoProvincia) {
		this.estadoProvincia = estadoProvincia;
	}

	public BloquePaisFin getBloquePaisFin() {
		return bloquePaisFin;
	}

	public void setBloquePaisFin(BloquePaisFin bloquePaisFin) {
		this.bloquePaisFin = bloquePaisFin;
	}

	public BloquePaisNoFin getBloquePaisNoFin() {
		return bloquePaisNoFin;
	}

	public void setBloquePaisNoFin(BloquePaisNoFin bloquePaisNoFin) {
		this.bloquePaisNoFin = bloquePaisNoFin;
	}
}
