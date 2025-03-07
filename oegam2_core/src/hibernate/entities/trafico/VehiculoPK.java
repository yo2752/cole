package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the VEHICULO database table.
 * 
 */
@Embeddable
public class VehiculoPK implements Serializable {
	// Default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="ID_VEHICULO")
	private Long idVehiculo;

	public VehiculoPK() {
	}
	public String getNumColegiado() {
		return this.numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public Long getIdVehiculo() {
		return this.idVehiculo;
	}
	public void setIdVehiculo(long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VehiculoPK)) {
			return false;
		}
		VehiculoPK castOther = (VehiculoPK) other;
		return this.numColegiado.equals(castOther.numColegiado) && (this.idVehiculo == castOther.idVehiculo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + ((int) (this.idVehiculo ^ (this.idVehiculo >>> 32)));

		return hash;
	}
}