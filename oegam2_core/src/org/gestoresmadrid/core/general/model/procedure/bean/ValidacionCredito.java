package org.gestoresmadrid.core.general.model.procedure.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ValidacionCredito implements Serializable {

	private static final long serialVersionUID = -2615646721484813611L;

	private BigDecimal idContrato;

	private String tipoTramite;

	private BigDecimal numero;

	private BigDecimal creditos;

	private BigDecimal creditosDisponibles;

	private String code;

	private String sqlerrm;

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BigDecimal getNumero() {
		return numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public BigDecimal getCreditos() {
		return creditos;
	}

	public void setCreditos(BigDecimal creditos) {
		this.creditos = creditos;
	}

	public BigDecimal getCreditosDisponibles() {
		return creditosDisponibles;
	}

	public void setCreditosDisponibles(BigDecimal creditosDisponibles) {
		this.creditosDisponibles = creditosDisponibles;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSqlerrm() {
		return sqlerrm;
	}

	public void setSqlerrm(String sqlerrm) {
		this.sqlerrm = sqlerrm;
	}
}
