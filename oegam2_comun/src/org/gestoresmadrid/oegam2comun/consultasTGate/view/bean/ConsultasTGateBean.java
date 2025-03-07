package org.gestoresmadrid.oegam2comun.consultasTGate.view.bean;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultasTGateBean {

	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "tipoServicio")
	private String tipoServicio;

	@FilterSimpleExpression(name = "fechaHora", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaFiltradoHora;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public FechaFraccionada getFechaFiltradoHora() {
		return fechaFiltradoHora;
	}

	public void setFechaFiltradoHora(FechaFraccionada fechaFiltradoHora) {
		this.fechaFiltradoHora = fechaFiltradoHora;
	}
}
