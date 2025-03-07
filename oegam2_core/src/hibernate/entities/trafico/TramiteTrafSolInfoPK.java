package hibernate.entities.trafico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the TRAMITE_TRAF_SOLINFO database table.
 * 
 */
@Embeddable
public class TramiteTrafSolInfoPK implements Serializable {
	// Default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_EXPEDIENTE")
	private long numExpediente;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="ID_VEHICULO")
	private long idVehiculo;

	public TramiteTrafSolInfoPK() {
	}
	public long getNumExpediente() {
		return this.numExpediente;
	}
	public void setNumExpediente(long numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getNumColegiado() {
		return this.numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public long getIdVehiculo() {
		return this.idVehiculo;
	}
	public void setIdVehiculo(long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TramiteTrafSolInfoPK)) {
			return false;
		}
		TramiteTrafSolInfoPK castOther = (TramiteTrafSolInfoPK) other;
		return (this.numExpediente == castOther.numExpediente) && this.numColegiado.equals(castOther.numColegiado)
				&& (this.idVehiculo == castOther.idVehiculo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.numExpediente ^ (this.numExpediente >>> 32)));
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + ((int) (this.idVehiculo ^ (this.idVehiculo >>> 32)));

		return hash;
	}
}