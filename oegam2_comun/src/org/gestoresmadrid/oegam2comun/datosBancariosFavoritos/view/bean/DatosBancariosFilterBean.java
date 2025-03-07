package org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class DatosBancariosFilterBean implements Serializable{
	
	private static final long serialVersionUID = 6208261889408139872L;
	
	@FilterSimpleExpression(name="formaPago")
	private BigDecimal formaPago;
	@FilterSimpleExpression(name="idContrato")
	private Long idContrato;
	@FilterSimpleExpression(name="estado")
	private BigDecimal estado;
	@FilterSimpleExpression(name="nif")
	private String nif;
	@FilterSimpleExpression(name="fechaAlta", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	public BigDecimal getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(BigDecimal formaPago) {
		this.formaPago = formaPago;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public BigDecimal getEstado() {
		return estado;
	}
	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
	
}
