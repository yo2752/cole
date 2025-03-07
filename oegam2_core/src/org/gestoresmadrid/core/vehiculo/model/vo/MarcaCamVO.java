package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the MARCA_CAM database table.
 */
@Entity
@Table(name = "MARCA_CAM")
public class MarcaCamVO implements Serializable {

	private static final long serialVersionUID = 7246691768976193064L;

	@EmbeddedId
	private MarcaCamPK id;

	private String dsMarca;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecHasta;

	public MarcaCamPK getId() {
		return id;
	}

	public void setId(MarcaCamPK id) {
		this.id = id;
	}

	public String getDsMarca() {
		return dsMarca;
	}

	public void setDsMarca(String dsMarca) {
		this.dsMarca = dsMarca;
	}

	public Date getFecHasta() {
		return fecHasta;
	}

	public void setFecHasta(Date fecHasta) {
		this.fecHasta = fecHasta;
	}
}