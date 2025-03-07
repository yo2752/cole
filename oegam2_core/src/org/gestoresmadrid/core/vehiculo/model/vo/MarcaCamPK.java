package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the MARCA_CAM database table.
 */
@Embeddable
public class MarcaCamPK implements Serializable {

	private static final long serialVersionUID = -8649454917438364014L;

	private String cdMarca;

	private String tipVehi;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecDesde;

	public MarcaCamPK() {}

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

	public Date getFecDesde() {
		return fecDesde;
	}

	public void setFecDesde(Date fecDesde) {
		this.fecDesde = fecDesde;
	}
}