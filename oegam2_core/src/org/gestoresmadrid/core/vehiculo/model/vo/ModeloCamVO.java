package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the MDOELO_CAM database table.
 */
@Entity
@Table(name = "MODELO_CAM")
public class ModeloCamVO implements Serializable {

	private static final long serialVersionUID = -2230126710701998771L;

	@EmbeddedId
	private ModeloCamPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecHasta;

	private String dsModVeh;

	private BigDecimal vCilindr;

	private BigDecimal vPotFisc;

	private BigDecimal vPreVehi;

	private BigDecimal numCilin;

	private String dsTecNic;

	public ModeloCamPK getId() {
		return id;
	}

	public void setId(ModeloCamPK id) {
		this.id = id;
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