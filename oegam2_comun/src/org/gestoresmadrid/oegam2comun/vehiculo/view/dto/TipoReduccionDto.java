package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TipoReduccionDto implements Serializable {

	private static final long serialVersionUID = 394080613994366455L;

	private String descripcion;
	private String tipoReduccion;
	private BigDecimal porcentaje;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoReduccion() {
		return tipoReduccion;
	}

	public void setTipoReduccion(String tipoReduccion) {
		this.tipoReduccion = tipoReduccion;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

}