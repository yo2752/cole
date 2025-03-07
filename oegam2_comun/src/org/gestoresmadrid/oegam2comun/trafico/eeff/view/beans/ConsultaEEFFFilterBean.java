package org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaEEFFFilterBean implements Serializable{
	
	private static final long serialVersionUID = -7314160863464320339L;

	@FilterSimpleExpression(name="idContrato")
	private Long idContrato;
	
	@FilterSimpleExpression
	private String tarjetaNive;
	
	@FilterSimpleExpression(name="numExpediente")
	private BigDecimal numExpediente;
	
	@FilterSimpleExpression(name="numExpedienteTramite")
	private BigDecimal numExpedienteTramite;
	
	@FilterSimpleExpression
	private String tarjetaBastidor;
	
	@FilterSimpleExpression(name="fechaRealizacion", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaBusqueda;
	
	@FilterSimpleExpression(name="fechaAlta", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	@FilterSimpleExpression
	private String nifRepresentado;
	
	public String getTarjetaNive() {
		return tarjetaNive;
	}
	
	public void setTarjetaNive(String tarjetaNive) {
		this.tarjetaNive = tarjetaNive;
	}
	
	public String getTarjetaBastidor() {
		return tarjetaBastidor;
	}
	
	public void setTarjetaBastidor(String tarjetaBastidor) {
		this.tarjetaBastidor = tarjetaBastidor;
	}
	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	
	public BigDecimal getNumExpedienteTramite() {
		return numExpedienteTramite;
	}
	
	public void setNumExpedienteTramite(BigDecimal numExpedienteTramite) {
		this.numExpedienteTramite = numExpedienteTramite;
	}
	
	public FechaFraccionada getFechaBusqueda() {
		return fechaBusqueda;
	}
	
	public void setFechaBusqueda(FechaFraccionada fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}
	
	public String getNifRepresentado() {
		return nifRepresentado;
	}
	
	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}
	
	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
}
