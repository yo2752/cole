package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import utilidades.estructuras.Fecha;

public class ModeloCamDto implements Serializable {

	private static final long serialVersionUID = 1500828895983373281L;

	private String cdModVeh;

	private String cdMarca;

	private String tipVehi;

	private Fecha fecDesde;

	private Fecha fecHasta;

	private String dsModVeh;

	private BigDecimal vCilindr;

	private BigDecimal vPotFisc;

	private BigDecimal vPreVehi;

	private BigDecimal numCilin;

	private String dsTecNic;

	public String getCdModVeh() {
		return cdModVeh;
	}

	public void setCdModVeh(String cdModVeh) {
		this.cdModVeh = cdModVeh;
	}

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

	public Fecha getFecHasta() {
		return fecHasta;
	}

	public void setFecHasta(Fecha fecHasta) {
		this.fecHasta = fecHasta;
	}

	public String getDsModVeh() {
		return dsModVeh;
	}

	public void setDsModVeh(String dsModVeh) {
		this.dsModVeh = dsModVeh;
	}

	public BigDecimal getvCilindr() {
		return vCilindr;
	}

	public void setvCilindr(BigDecimal vCilindr) {
		this.vCilindr = vCilindr;
	}

	public BigDecimal getvPotFisc() {
		return vPotFisc;
	}

	public void setvPotFisc(BigDecimal vPotFisc) {
		this.vPotFisc = vPotFisc;
	}

	public BigDecimal getvPreVehi() {
		return vPreVehi;
	}

	public void setvPreVehi(BigDecimal vPreVehi) {
		this.vPreVehi = vPreVehi;
	}

	public BigDecimal getNumCilin() {
		return numCilin;
	}

	public void setNumCilin(BigDecimal numCilin) {
		this.numCilin = numCilin;
	}

	public String getDsTecNic() {
		return dsTecNic;
	}

	public void setDsTecNic(String dsTecNic) {
		this.dsTecNic = dsTecNic;
	}
}