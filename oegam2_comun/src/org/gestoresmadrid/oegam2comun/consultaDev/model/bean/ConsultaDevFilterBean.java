package org.gestoresmadrid.oegam2comun.consultaDev.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaDevFilterBean implements Serializable{

	private static final long serialVersionUID = 7730812944112139363L;

	@FilterSimpleExpression(name="cif")
	private String cif;
	
	@FilterSimpleExpression(name="estado")
	private BigDecimal estado;
	
	@FilterSimpleExpression(name="estadoCif")
	private BigDecimal estadoCif;
	
	@FilterSimpleExpression(name="idContrato")
	private Long idContrato;
	
	@FilterSimpleExpression(name="fechaAlta", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public BigDecimal getEstado() {
		return estado;
	}
	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public BigDecimal getEstadoCif() {
		return estadoCif;
	}
	public void setEstadoCif(BigDecimal estadoCif) {
		this.estadoCif = estadoCif;
	}
}
