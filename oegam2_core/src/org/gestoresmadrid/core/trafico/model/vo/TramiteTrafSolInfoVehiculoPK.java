package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the TRAMITE_TRAF_SOLINFO database table.
 */
@Embeddable
public class TramiteTrafSolInfoVehiculoPK implements Serializable {

	private static final long serialVersionUID = 3462135248309173683L;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "ID_VEHICULO")
	private long idVehiculo;

	public TramiteTrafSolInfoVehiculoPK() {}

	public BigDecimal getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numColegiado == null) ? 0 : numColegiado.hashCode());
		result = prime * result + ((numExpediente == null) ? 0 : numExpediente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TramiteTrafSolInfoVehiculoPK other = (TramiteTrafSolInfoVehiculoPK) obj;
		if (numColegiado == null) {
			if (other.numColegiado != null)
				return false;
		} else if (!numColegiado.equals(other.numColegiado))
			return false;
		if (numExpediente == null) {
			if (other.numExpediente != null)
				return false;
		} else if (!numExpediente.equals(other.numExpediente))
			return false;
		return true;
	}

}