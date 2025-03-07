package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the EVOLUCION_TRAMITE_TRAFICO database table.
 * 
 */
@Embeddable
public class EvolucionPresentacionJptPK implements Serializable {

	private static final long serialVersionUID = -2082351115301670943L;

	@Column(name = "NUM_EXPEDIENTE")
	private Long numExpediente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAMBIO")
	private java.util.Date fechaCambio;

	@Column(name = "ESTADO_ANTERIOR")
	private Long estadoAnterior;

	@Column(name = "ESTADO_NUEVO")
	private Long estadoNuevo;

	public EvolucionPresentacionJptPK() {
		super();
	}

	public Long getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public java.util.Date getFechaCambio() {
		return this.fechaCambio;
	}

	public void setFechaCambio(java.util.Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Long getEstadoAnterior() {
		return this.estadoAnterior;
	}

	public void setEstadoAnterior(Long estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public Long getEstadoNuevo() {
		return this.estadoNuevo;
	}

	public void setEstadoNuevo(Long estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EvolucionPresentacionJptPK)) {
			return false;
		}
		EvolucionPresentacionJptPK castOther = (EvolucionPresentacionJptPK) other;
		return (this.numExpediente == castOther.numExpediente)
				&& this.fechaCambio.equals(castOther.fechaCambio)
				&& (this.estadoAnterior == castOther.estadoAnterior)
				&& (this.estadoNuevo == castOther.estadoNuevo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.numExpediente ^ (this.numExpediente >>> 32)));
		hash = hash * prime + this.fechaCambio.hashCode();
		hash = hash * prime + ((int) (this.estadoAnterior ^ (this.estadoAnterior >>> 32)));
		hash = hash * prime + ((int) (this.estadoNuevo ^ (this.estadoNuevo >>> 32)));
		return hash;
	}
}