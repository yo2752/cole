package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the VEHICULO_TRAMITE_TRAFICO database table.
 */
@Embeddable
public class VehiculoTramiteTraficoPK implements Serializable {

	private static final long serialVersionUID = -8556121504872553866L;

	@Column(name = "ID_VEHICULO")
	private Long idVehiculo;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	public VehiculoTramiteTraficoPK() {}

	public Long getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
}