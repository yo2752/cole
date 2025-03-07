package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;

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
 * The persistent class for the ASISTENTE database table.
 */
@Entity
@Table(name = "ASISTENTE")
public class AsistenteVO implements Serializable {

	private static final long serialVersionUID = 8802311152697940323L;

	@EmbeddedId
	private AsistentePK id;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumns({ @JoinColumn(name = "NIF_CARGO", referencedColumnName = "NIF_CARGO", insertable = false, updatable = false),
			@JoinColumn(name = "CIF_SOCIEDAD", referencedColumnName = "CIF_SOCIEDAD", insertable = false, updatable = false),
			@JoinColumn(name = "CODIGO_CARGO", referencedColumnName = "CODIGO_CARGO", insertable = false, updatable = false),
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false) })
	private SociedadCargoVO sociedadCargo;

	public AsistentePK getId() {
		return id;
	}

	public void setId(AsistentePK id) {
		this.id = id;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public SociedadCargoVO getSociedadCargo() {
		return sociedadCargo;
	}

	public void setSociedadCargo(SociedadCargoVO sociedadCargo) {
		this.sociedadCargo = sociedadCargo;
	}
}