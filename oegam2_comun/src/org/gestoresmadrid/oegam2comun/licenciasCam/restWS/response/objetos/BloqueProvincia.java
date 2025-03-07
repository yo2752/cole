package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloqueProvincia implements Serializable {

	private static final long serialVersionUID = 7117110596894505239L;

	private Integer codProvincia;

	private String nomProvincia;

	private Integer estadoPoblacion;

	private BloqueProvinciaNoFin bloqueProvinciaNoFin;

	private BloqueProvinciaFin bloqueProvinciaFin;

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

	public Integer getEstadoPoblacion() {
		return estadoPoblacion;
	}

	public void setEstadoPoblacion(Integer estadoPoblacion) {
		this.estadoPoblacion = estadoPoblacion;
	}

	public BloqueProvinciaNoFin getBloqueProvinciaNoFin() {
		return bloqueProvinciaNoFin;
	}

	public void setBloqueProvinciaNoFin(BloqueProvinciaNoFin bloqueProvinciaNoFin) {
		this.bloqueProvinciaNoFin = bloqueProvinciaNoFin;
	}

	public BloqueProvinciaFin getBloqueProvinciaFin() {
		return bloqueProvinciaFin;
	}

	public void setBloqueProvinciaFin(BloqueProvinciaFin bloqueProvinciaFin) {
		this.bloqueProvinciaFin = bloqueProvinciaFin;
	}
}
