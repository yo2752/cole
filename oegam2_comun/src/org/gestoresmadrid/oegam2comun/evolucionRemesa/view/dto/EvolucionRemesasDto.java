package org.gestoresmadrid.oegam2comun.evolucionRemesa.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

public class EvolucionRemesasDto implements Serializable{
	
	private static final long serialVersionUID = 8407692931422799284L;
	
	private BigDecimal numExpediente;
	private Date fechaCambio;
	private BigDecimal estadoAnterior;
	private BigDecimal estadoNuevo;
	private UsuarioDto usuario;
	
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
	public UsuarioDto getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}
	
}
