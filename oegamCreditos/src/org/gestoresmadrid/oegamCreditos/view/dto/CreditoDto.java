package org.gestoresmadrid.oegamCreditos.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreditoDto implements Serializable {

	private static final long serialVersionUID = -8106649756051067837L;

	private Long idContrato;

	private String tipoCredito;

	private BigDecimal creditos;

	private TipoCreditoDto tipoCreditoDto;

	public BigDecimal getCreditos() {
		return this.creditos;
	}

	public void setCreditos(BigDecimal creditos) {
		this.creditos = creditos;
	}

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

	public TipoCreditoDto getTipoCreditoDto() {
		return tipoCreditoDto;
	}

	public void setTipoCreditoDto(TipoCreditoDto tipoCreditoDto) {
		this.tipoCreditoDto = tipoCreditoDto;
	}

}