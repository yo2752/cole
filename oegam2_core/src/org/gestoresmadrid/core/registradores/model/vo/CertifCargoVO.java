package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the CERTIF_CARGO database table.
 */
@Entity
@Table(name = "CERTIF_CARGO")
public class CertifCargoVO implements Serializable {

	private static final long serialVersionUID = 219374961354519142L;

	@EmbeddedId
	private CertifCargoPK id;

	@Column(name = "IDENTIFICADOR")
	private BigDecimal identificador;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "ID_FIRMA")
	private String idFirma;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumns({ @JoinColumn(name = "NIF_CARGO", referencedColumnName = "NIF_CARGO", insertable = false, updatable = false),
			@JoinColumn(name = "CIF_SOCIEDAD", referencedColumnName = "CIF_SOCIEDAD", insertable = false, updatable = false),
			@JoinColumn(name = "CODIGO_CARGO", referencedColumnName = "CODIGO_CARGO", insertable = false, updatable = false),
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false) })
	private SociedadCargoVO sociedadCargo;

	public CertifCargoPK getId() {
		return id;
	}

	public void setId(CertifCargoPK id) {
		this.id = id;
	}

	public BigDecimal getIdentificador() {
		return identificador;
	}

	public void setIdentificador(BigDecimal identificador) {
		this.identificador = identificador;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getIdFirma() {
		return idFirma;
	}

	public void setIdFirma(String idFirma) {
		this.idFirma = idFirma;
	}

	public SociedadCargoVO getSociedadCargo() {
		return sociedadCargo;
	}

	public void setSociedadCargo(SociedadCargoVO sociedadCargo) {
		this.sociedadCargo = sociedadCargo;
	}
}