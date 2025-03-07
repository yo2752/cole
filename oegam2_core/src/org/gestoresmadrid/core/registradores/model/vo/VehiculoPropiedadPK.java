package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the VEHICULO_PROPIEDAD database table.
 * 
 */
@Embeddable
public class VehiculoPropiedadPK implements Serializable {

	private static final long serialVersionUID = -6996379033367996962L;

	@Column(name="ID_VEHICULO", insertable=false, updatable=false)
	private long idVehiculo;

	@Column(name="ID_PROPIEDAD", insertable=false, updatable=false)
	private long idPropiedad;

	public VehiculoPropiedadPK() {
	}
	public long getIdVehiculo() {
		return this.idVehiculo;
	}
	public void setIdVehiculo(long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}
	public long getIdPropiedad() {
		return this.idPropiedad;
	}
	public void setIdPropiedad(long idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VehiculoPropiedadPK)) {
			return false;
		}
		VehiculoPropiedadPK castOther = (VehiculoPropiedadPK)other;
		return 
			(this.idVehiculo == castOther.idVehiculo)
			&& (this.idPropiedad == castOther.idPropiedad);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idVehiculo ^ (this.idVehiculo >>> 32)));
		hash = hash * prime + ((int) (this.idPropiedad ^ (this.idPropiedad >>> 32)));
		
		return hash;
	}
}