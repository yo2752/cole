package org.gestoresmadrid.oegam2comun.evolucionConsultaDev.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionConsultaDevFilterBean implements Serializable{

	private static final long serialVersionUID = -8169849116388117881L;

	@FilterSimpleExpression(name="id.idConsultaDev")
	private Long idConsultaDev;

	@FilterSimpleExpression(name="id.fechaCambio")
	private Date fechaCambio;

	@FilterSimpleExpression(name="estadoAnterior")
	private BigDecimal estadoAnt;

	@FilterSimpleExpression(name="estadoNuevo")
	private BigDecimal estadoNuevo;

	@FilterSimpleExpression(name="id.idUsuario")
	private Long idUsuario;

	public Long getIdConsultaDev() {
		return idConsultaDev;
	}

	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}

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
	
}
