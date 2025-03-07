package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaTramiteTraficoVehiculoBean {

	@FilterSimpleExpression(name = "vehiculo.idVehiculo")
	private Long idVehiculo;

	@FilterSimpleExpression(name = "contrato.idContrato")
	private Long idContrato;

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
}
