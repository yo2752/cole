package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;

import utilidades.estructuras.Fecha;

public class MarcaCamDto implements Serializable {

	private static final long serialVersionUID = -6742772137919554113L;

	private String cdMarca;

	private String tipVehi;

	private Fecha fecDesde;

	private String dsMarca;

	private Fecha fecHasta;

	public String getCdMarca() {
		return cdMarca;
	}

	public void setCdMarca(String cdMarca) {
		this.cdMarca = cdMarca;
	}

	public String getTipVehi() {
		return tipVehi;
	}

	public void setTipVehi(String tipVehi) {
		this.tipVehi = tipVehi;
	}

	public Fecha getFecDesde() {
		return fecDesde;
	}

	public void setFecDesde(Fecha fecDesde) {
		this.fecDesde = fecDesde;
	}

	public String getDsMarca() {
		return dsMarca;
	}

	public void setDsMarca(String dsMarca) {
		this.dsMarca = dsMarca;
	}

	public Fecha getFecHasta() {
		return fecHasta;
	}

	public void setFecHasta(Fecha fecHasta) {
		this.fecHasta = fecHasta;
	}
}