package org.gestoresmadrid.oegam2comun.view.dto;

import java.io.Serializable;

public class OficinaLiquidadora620Dto implements Serializable {

	private static final long serialVersionUID = 2333351238452293364L;

	private String oficinaLiquidadora;

	private String idProvincia;

	private String nombreOficinaLiq;

	public String getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(String oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombreOficinaLiq() {
		return nombreOficinaLiq;
	}

	public void setNombreOficinaLiq(String nombreOficinaLiq) {
		this.nombreOficinaLiq = nombreOficinaLiq;
	}
}
