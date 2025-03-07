package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class EvolucionTramiteRegistroDto implements Serializable {

	private static final long serialVersionUID = 5723609122541172978L;

	private BigDecimal estadoAnterior;

	private BigDecimal estadoNuevo;

	private Fecha fechaCambio;

	private UsuarioDto usuario;

	private BigDecimal idTramiteRegistro;

	private String estadoAnteriorString;

	private String estadoNuevoString;

	private String fechaCambioString;

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

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public String getEstadoAnteriorString() {
		return estadoAnteriorString;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public void setEstadoAnteriorString(String estadoAnteriorString) {
		this.estadoAnteriorString = estadoAnteriorString;
	}

	public String getEstadoNuevoString() {
		return estadoNuevoString;
	}

	public void setEstadoNuevoString(String estadoNuevoString) {
		this.estadoNuevoString = estadoNuevoString;
	}

	public String getFechaCambioString() {
		return fechaCambioString;
	}

	public void setFechaCambioString(String fechaCambioString) {
		this.fechaCambioString = fechaCambioString;
	}
}