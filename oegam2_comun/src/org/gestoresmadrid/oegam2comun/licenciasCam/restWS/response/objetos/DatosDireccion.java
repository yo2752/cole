package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class DatosDireccion implements Serializable {

	private static final long serialVersionUID = -6091033279230634841L;

	private Integer estadoPais;

	private DatosDireccionFin datosDireccionFin;

	private DatosDireccionNoFin datosDireccionNoFin;

	public Integer getEstadoPais() {
		return estadoPais;
	}

	public void setEstadoPais(Integer estadoPais) {
		this.estadoPais = estadoPais;
	}

	public DatosDireccionFin getDatosDireccionFin() {
		return datosDireccionFin;
	}

	public void setDatosDireccionFin(DatosDireccionFin datosDireccionFin) {
		this.datosDireccionFin = datosDireccionFin;
	}

	public DatosDireccionNoFin getDatosDireccionNoFin() {
		return datosDireccionNoFin;
	}

	public void setDatosDireccionNoFin(DatosDireccionNoFin datosDireccionNoFin) {
		this.datosDireccionNoFin = datosDireccionNoFin;
	}
}
