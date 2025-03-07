package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

public class LcEvolucionTramiteDto implements Serializable {

	private static final long serialVersionUID = -5323734927539038347L;

	private Long idEvolucion;

	private BigDecimal numExpediente;

	private BigDecimal estadoAnterior;

	private BigDecimal estadoNuevo;

	private Date fechaCambio;

	private Long idUsuario;

	private UsuarioDto usuario;

	public LcEvolucionTramiteDto() {}

	public Long getIdEvolucion() {
		return idEvolucion;
	}

	public void setIdEvolucion(Long idEvolucion) {
		this.idEvolucion = idEvolucion;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

}