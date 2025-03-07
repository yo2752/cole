package org.gestoresmadrid.oegam2comun.creditos.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreditoDto implements Serializable {

	private static final long serialVersionUID = -8106649756051067837L;

	private Long idContrato;

	private String tipoCredito;

	private BigDecimal creditos;

	private String descripcionTipoCredito;

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

	public String getDescripcionTipoCredito() {
		return descripcionTipoCredito;
	}

	public void setDescripcionTipoCredito(String descripcionTipoCredito) {
		this.descripcionTipoCredito = descripcionTipoCredito;
	}
}