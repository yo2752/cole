package org.gestoresmadrid.oegamPermisoInternacional.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class PermisoInternacionalFilterBean implements Serializable {

	private static final long serialVersionUID = -1176079816150873772L;

	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name = "idContrato")
	private Long idContrato;

	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	@FilterSimpleExpression(name = "fechaPresentacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;

	@FilterSimpleExpression(name = "doiTitular")
	private String nifTitular;

	@FilterSimpleExpression(name = "estado")
	private String estado;

	@FilterSimpleExpression(name = "estadoImpresion")
	private String estadoImpresion;

	@FilterSimpleExpression(name = "refPropia")
	private String refPropia;

	@FilterSimpleExpression(name = "jefatura")
	private String jefatura;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getNifTitular() {
		return nifTitular;
	}

	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
}