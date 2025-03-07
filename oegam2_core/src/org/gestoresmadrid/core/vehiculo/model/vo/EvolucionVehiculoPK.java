package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The primary key class for the EVOLUCION_VEHICULO database table.
 * 
 */
@Embeddable
public class EvolucionVehiculoPK implements Serializable {

	private static final long serialVersionUID = 1873440626484542020L;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="ID_VEHICULO")
	private Long idVehiculo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_HORA")
	private Date fechaHora;

	public EvolucionVehiculoPK() {
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
	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
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
		if (!(other instanceof EvolucionVehiculoPK)) {
			return false;
		}
		EvolucionVehiculoPK castOther = (EvolucionVehiculoPK)other;
		return
			this.numColegiado.equals(castOther.numColegiado)
			&& (this.idVehiculo == castOther.idVehiculo)
			&& this.fechaHora.equals(castOther.fechaHora);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + ((int) (this.idVehiculo ^ (this.idVehiculo >>> 32)));
		hash = hash * prime + this.fechaHora.hashCode();

		return hash;
	}
}