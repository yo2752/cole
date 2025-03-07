package org.gestoresmadrid.core.tasas.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the EVOLUCION_TASA database table.
 * 
 */
@Embeddable
public class EvolucionTasaPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	//@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA")
	private java.util.Date fechaHora;
	

	public EvolucionTasaPK() {
	}

	public String getCodigoTasa() {
		return this.codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public java.util.Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(java.util.Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EvolucionTasaPK)) {
			return false;
		}
		EvolucionTasaPK castOther = (EvolucionTasaPK) other;
		return this.codigoTasa.equals(castOther.codigoTasa)
				&& this.fechaHora.equals(castOther.fechaHora);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codigoTasa.hashCode();
		hash = hash * prime + this.fechaHora.hashCode();

		return hash;
	}

}