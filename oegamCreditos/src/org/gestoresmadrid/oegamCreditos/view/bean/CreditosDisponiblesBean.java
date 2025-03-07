package org.gestoresmadrid.oegamCreditos.view.bean;

import java.math.BigDecimal;

public class CreditosDisponiblesBean {

	private BigDecimal creditos;
	private String descripcionTipoCredito;
	private String increDecre;
	private Integer creditosBloqueados;
	private Integer creditosDisponibles;
	private String tipoCredito;

	public CreditosDisponiblesBean() {}

	public BigDecimal getCreditos() {
		return creditos;
	}

	public void setCreditos(BigDecimal creditos) {
		this.creditos = creditos;
	}

	public String getDescripcionTipoCredito() {
		return descripcionTipoCredito;
	}

	public void setDescripcionTipoCredito(String descripcionTipoCredito) {
		this.descripcionTipoCredito = descripcionTipoCredito;
	}

	public String getIncreDecre() {
		return increDecre;
	}

	public void setIncreDecre(String increDecre) {
		this.increDecre = increDecre;
	}

	public Integer getCreditosBloqueados() {
		return creditosBloqueados;
	}

	public void setCreditosBloqueados(Integer creditosBloqueados) {
		this.creditosBloqueados = creditosBloqueados;
	}

	public Integer getCreditosDisponibles() {
		return creditosDisponibles;
	}

	public void setCreditosDisponibles(Integer creditosDisponibles) {
		this.creditosDisponibles = creditosDisponibles;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

}
