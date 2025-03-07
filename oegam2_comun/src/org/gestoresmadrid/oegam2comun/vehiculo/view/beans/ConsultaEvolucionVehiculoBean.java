package org.gestoresmadrid.oegam2comun.vehiculo.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaEvolucionVehiculoBean {

	@FilterSimpleExpression(name = "id.idVehiculo")
	private Long idVehiculo;

	@FilterSimpleExpression(name = "id.numColegiado")
	private String numColegiado;

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
}
