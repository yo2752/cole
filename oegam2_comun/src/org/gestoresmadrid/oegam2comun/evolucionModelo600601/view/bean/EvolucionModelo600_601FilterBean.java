package org.gestoresmadrid.oegam2comun.evolucionModelo600601.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionModelo600_601FilterBean implements Serializable{
	
	private static final long serialVersionUID = 1486638927526736789L;
	
	@FilterSimpleExpression(name="id.numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name="id.fechaCambio")
	private Date fechaCambio;

	@FilterSimpleExpression(name="id.estadoAnterior")
	private BigDecimal estadoAnterior;

	@FilterSimpleExpression(name="id.estadoNuevo")
	private BigDecimal estadoNuevo;

	@FilterSimpleExpression(name="idUsuario")
	private BigDecimal idUsuario;
	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public Date getFechaCambio() {
		return fechaCambio;
	}
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
	public BigDecimal getEstadoAnterior() {
		return estadoAnterior;
	}
	public void setEstadoAnterior(BigDecimal estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}
	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}
	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}
	public BigDecimal getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
