package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class EvolucionTramiteTraficoDto implements Serializable {

	private static final long serialVersionUID = -4501138561264421265L;

	private BigDecimal estadoAnterior;

	private BigDecimal estadoNuevo;

	private Fecha fechaCambio;

	private UsuarioDto usuarioDto;

	private BigDecimal numExpediente;

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

	public Fecha getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Fecha fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public UsuarioDto getUsuarioDto() {
		return usuarioDto;
	}

	public void setUsuarioDto(UsuarioDto usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
}