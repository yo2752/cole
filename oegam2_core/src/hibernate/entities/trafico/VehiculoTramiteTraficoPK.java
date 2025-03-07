package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the VEHICULO_TRAMITE_TRAFICO database table.
 * 
 */
@Embeddable
public class VehiculoTramiteTraficoPK implements Serializable {
	// Default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_VEHICULO")
	private Long idVehiculo;

	@Column(name="NUM_EXPEDIENTE")
	private Long numExpediente;

	public VehiculoTramiteTraficoPK() {
	}
	public Long getIdVehiculo() {
		return this.idVehiculo;
	}
	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}
	public Long getNumExpediente() {
		return this.numExpediente;
	}
	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VehiculoTramiteTraficoPK)) {
			return false;
		}
		VehiculoTramiteTraficoPK castOther = (VehiculoTramiteTraficoPK) other;
		return (this.idVehiculo == castOther.idVehiculo) && (this.numExpediente == castOther.numExpediente);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idVehiculo ^ (this.idVehiculo >>> 32)));
		hash = hash * prime + ((int) (this.numExpediente ^ (this.numExpediente >>> 32)));

		return hash;
	}
}