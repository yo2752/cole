package trafico.beans;

import java.math.BigDecimal;
import java.util.Date;

public class ModeloCAMBean{
	private String tipVehi;
	private String cdMarca;
	private String cdModVeh;
	private Date fecDesde;
	private Date fecHasta;
	private String dsModVeh;
	private BigDecimal vCilindr;
	private BigDecimal vPotFisc;
	private BigDecimal vPreVehi;
	private BigDecimal numCilin;
	private String dsTecNic;

	public ModeloCAMBean() {
		super();
	}

	public ModeloCAMBean(boolean inicializar) {
		super();
	}

	public String getTipVehi() {
		return tipVehi;
	}

	public void setTipVehi(String tipVehi) {
		this.tipVehi = tipVehi;
	}

	public String getCdMarca() {
		return cdMarca;
	}

	public void setCdMarca(String cdMarca) {
		this.cdMarca = cdMarca;
	}

	public String getCdModVeh() {
		return cdModVeh;
	}

	public void setCdModVeh(String cdModVeh) {
		this.cdModVeh = cdModVeh;
	}

	public Date getFecDesde() {
		return fecDesde;
	}

	public void setFecDesde(Date fecDesde) {
		this.fecDesde = fecDesde;
	}

	public Date getFecHasta() {
		return fecHasta;
	}

	public void setFecHasta(Date fecHasta) {
		this.fecHasta = fecHasta;
	}

	public String getDsModVeh() {
		return dsModVeh;
	}

	public void setDsModVeh(String dsModVeh) {
		this.dsModVeh = dsModVeh;
	}

	public BigDecimal getVCilindr() {
		return vCilindr;
	}

	public void setVCilindr(BigDecimal vCilindr) {
		this.vCilindr = vCilindr;
	}

	public BigDecimal getVPotFisc() {
		return vPotFisc;
	}

	public void setVPotFisc(BigDecimal vPotFisc) {
		this.vPotFisc = vPotFisc;
	}

	public BigDecimal getVPreVehi() {
		return vPreVehi;
	}

	public void setVPreVehi(BigDecimal vPreVehi) {
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