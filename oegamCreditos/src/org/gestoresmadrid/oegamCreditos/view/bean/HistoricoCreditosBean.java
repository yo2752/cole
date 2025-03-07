package org.gestoresmadrid.oegamCreditos.view.bean;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class HistoricoCreditosBean {

	@FilterSimpleExpression(name = "idContrato")
	private Long idContrato;

	@FilterSimpleExpression(name = "tipoCredito")
	private String tipoCredito;

	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

}
