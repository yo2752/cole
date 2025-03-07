package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BonificacionDto implements Serializable{

	private static final long serialVersionUID = -127935291550086204L;
	
	private String bonificacion;
	private BigDecimal monto;
	private String descripcion;
	private BigDecimal estado;
	public String getBonificacion() {
		return bonificacion;
	}
	public void setBonificacion(String bonificacion) {
		this.bonificacion = bonificacion;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getEstado() {
		return estado;
	}
	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
	
}
