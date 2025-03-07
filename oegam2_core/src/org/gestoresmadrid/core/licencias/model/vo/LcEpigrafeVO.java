package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_EPIGRAFE database table.
 */
@Entity
@Table(name = "LC_EPIGRAFE")
public class LcEpigrafeVO implements Serializable {

	private static final long serialVersionUID = -3294779633464999045L;

	@Id
	@SequenceGenerator(name = "lc_epigrafe", sequenceName = "LC_EPIGRAFE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_epigrafe")
	@Column(name = "ID_EPIGRAFE")
	private Long idEpigrafe;

	@Column(name = "EPIGRAFE")
	private String epigrafe;

	@Column(name = "SECCION")
	private String seccion;

	@Column(name = "ID_INFO_LOCAL")
	private Long idInfoLocal;

	// bi-directional many-to-one association to LcInfoLocal
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INFO_LOCAL", referencedColumnName = "ID_INFO_LOCAL", insertable = false, updatable = false)
	private LcInfoLocalVO lcInfoLocal;

	public Long getIdEpigrafe() {
		return this.idEpigrafe;
	}

	public void setIdEpigrafe(Long idEpigrafe) {
		this.idEpigrafe = idEpigrafe;
	}

	public String getEpigrafe() {
		return this.epigrafe;
	}

	public void setEpigrafe(String epigrafe) {
		this.epigrafe = epigrafe;
	}

	public String getSeccion() {
		return this.seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public LcInfoLocalVO getLcInfoLocal() {
		return lcInfoLocal;
	}

	public void setLcInfoLocal(LcInfoLocalVO lcInfoLocal) {
		this.lcInfoLocal = lcInfoLocal;
	}

	public Long getIdInfoLocal() {
		return idInfoLocal;
	}

	public void setIdInfoLocal(Long idInfoLocal) {
		this.idInfoLocal = idInfoLocal;
	}

}