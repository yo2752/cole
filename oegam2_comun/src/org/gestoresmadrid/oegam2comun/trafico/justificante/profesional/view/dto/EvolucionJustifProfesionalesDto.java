package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

public class EvolucionJustifProfesionalesDto implements Serializable {

	private static final long serialVersionUID = 2011519748226082766L;

	private String idJustificante;

	private Long idJustificanteInterno;

	private Long numExpediente;

	private Date fechaCambio;

	private String fechaCambioString;

	private BigDecimal estadoAnterior;

	private BigDecimal estado;

	private UsuarioDto usuarioDto;
	
	private String comentarios;

	public String getIdJustificante() {
		return idJustificante;
	}

	public void setIdJustificante(String idJustificante) {
		this.idJustificante = idJustificante;
	}

	public Long getIdJustificanteInterno() {
		return idJustificanteInterno;
	}

	public void setIdJustificanteInterno(Long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}

	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getFechaCambioString() {
		return fechaCambioString;
	}

	public void setFechaCambioString(String fechaCambioString) {
		this.fechaCambioString = fechaCambioString;
	}

	public BigDecimal getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(BigDecimal estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public UsuarioDto getUsuarioDto() {
		return usuarioDto;
	}

	public void setUsuarioDto(UsuarioDto usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
}
