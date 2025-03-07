package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EVOLUCION_TRAMITE_TRAFICO database table.
 * 
 */
@Embeddable
public class EvolucionTramiteTraficoPK implements Serializable {
	// Default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_EXPEDIENTE")
	private Long numExpediente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAMBIO")
	private java.util.Date fechaCambio;

	@Column(name = "ESTADO_ANTERIOR")
	private Long estadoAnterior;

	@Column(name = "ESTADO_NUEVO")
	private Long estadoNuevo;

	public EvolucionTramiteTraficoPK() {
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
		if (!(other instanceof EvolucionTramiteTraficoPK)) {
			return false;
		}
		EvolucionTramiteTraficoPK castOther = (EvolucionTramiteTraficoPK) other;
		return (this.numExpediente == castOther.numExpediente) && this.fechaCambio.equals(castOther.fechaCambio)
				&& (this.estadoAnterior == castOther.estadoAnterior) && (this.estadoNuevo == castOther.estadoNuevo);

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