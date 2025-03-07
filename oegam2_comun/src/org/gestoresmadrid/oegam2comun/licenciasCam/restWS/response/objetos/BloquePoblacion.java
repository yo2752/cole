package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloquePoblacion implements Serializable {

	private static final long serialVersionUID = 9029071143260528727L;

	private Integer codPoblacion;

	private String nomPoblacion;

	private Integer codMunicipio;

	private String nomMunicipio;

	private Integer estadoVial;

	private BloquePoblacionFin bloquePoblacionFin;

	private BloquePoblacionNoFin bloquePoblacionNoFin;

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

	public Integer getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(Integer codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public String getNomMunicipio() {
		return nomMunicipio;
	}

	public void setNomMunicipio(String nomMunicipio) {
		this.nomMunicipio = nomMunicipio;
	}

	public Integer getEstadoVial() {
		return estadoVial;
	}

	public void setEstadoVial(Integer estadoVial) {
		this.estadoVial = estadoVial;
	}

	public BloquePoblacionFin getBloquePoblacionFin() {
		return bloquePoblacionFin;
	}

	public void setBloquePoblacionFin(BloquePoblacionFin bloquePoblacionFin) {
		this.bloquePoblacionFin = bloquePoblacionFin;
	}

	public BloquePoblacionNoFin getBloquePoblacionNoFin() {
		return bloquePoblacionNoFin;
	}

	public void setBloquePoblacionNoFin(BloquePoblacionNoFin bloquePoblacionNoFin) {
		this.bloquePoblacionNoFin = bloquePoblacionNoFin;
	}
}
