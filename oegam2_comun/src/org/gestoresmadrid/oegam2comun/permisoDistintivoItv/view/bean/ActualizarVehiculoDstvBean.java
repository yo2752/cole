package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ActualizarVehiculoDstvBean {

	@FilterSimpleExpression(name = "vehiculo.idVehiculo")
	private Long idVehiculo;
	
	@FilterSimpleExpression(name = "vehiculo.bastidor")
	private String bastidor;

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

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
}
