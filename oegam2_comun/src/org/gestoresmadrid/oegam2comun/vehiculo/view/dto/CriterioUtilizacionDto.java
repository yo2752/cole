package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;

public class CriterioUtilizacionDto implements Serializable {

	private static final long serialVersionUID = -7786827078741957742L;

	private String descripcion;

	private String idCriterioUtilizacion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdCriterioUtilizacion() {
		return idCriterioUtilizacion;
	}

	public void setIdCriterioUtilizacion(String idCriterioUtilizacion) {
		this.idCriterioUtilizacion = idCriterioUtilizacion;
	}
}