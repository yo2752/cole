package org.gestoresmadrid.oegamComun.evolucionUsuario.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionUsuarioFilterBean implements Serializable {

	private static final long serialVersionUID = -3577188200253116649L;

	@FilterSimpleExpression(name = "id.fechaCambio")
	private Date fechaCambio;

	@FilterSimpleExpression(name = "estadoAnterior")
	private BigDecimal estadoAnt;

	@FilterSimpleExpression(name = "estadoNuevo")
	private BigDecimal estadoNuevo;

	@FilterSimpleExpression(name = "id.idUsuario")
	private Long idUsuario;

	@FilterSimpleExpression(name = "nif")
	private String nif;

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public BigDecimal getEstadoAnt() {
		return estadoAnt;
	}

	public void setEstadoAnt(BigDecimal estadoAnt) {
		this.estadoAnt = estadoAnt;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

}
