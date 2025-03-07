package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;

public class TipoVehiculoDto implements Serializable {

	private static final long serialVersionUID = 3200090036309780229L;

	private String descripcion;

	private String tipoVehiculo;

	private String tipoTramite;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
}