package org.gestoresmadrid.core.personas.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the EVOLUCION_PERSONA database table.
 */
@Embeddable
public class EvolucionPersonaPK implements Serializable {

	private static final long serialVersionUID = -5269639086069817354L;

	@Column(name = "NIF")
	private String nif;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA")
	private Date fechaHora;

	public EvolucionPersonaPK() {}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EvolucionPersonaPK)) {
			return false;
		}
		EvolucionPersonaPK castOther = (EvolucionPersonaPK) other;
		return this.numColegiado.equals(castOther.numColegiado) && this.nif.equals(castOther.nif) && this.fechaHora.equals(castOther.fechaHora);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + this.nif.hashCode();
		hash = hash * prime + this.fechaHora.hashCode();

		return hash;
	}
}