package org.gestoresmadrid.oegam2comun.remesa.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class RemesaFilterBean implements Serializable{

	private static final long serialVersionUID = -4055530848391938802L;
	
	@FilterSimpleExpression(name="numExpediente")
	private BigDecimal numExpediente;
	
	@FilterSimpleExpression(name="estado")
	private BigDecimal estado;
	
	@FilterSimpleExpression(name="id.modelo")
	@FilterRelationship(name="modelo", joinType = Criteria.LEFT_JOIN)
	private String modelo;
	
	@FilterSimpleExpression(name="id.version")
	@FilterRelationship(name="modelo", joinType = Criteria.LEFT_JOIN)
	private String version;
	
	@FilterSimpleExpression(name="id.concepto")
	@FilterRelationship(name="concepto", joinType = Criteria.LEFT_JOIN)
	private String concepto;
	
	@FilterSimpleExpression(name="codigoNotario")
	@FilterRelationship(name="notario", joinType = Criteria.LEFT_JOIN)
	private String codNotario;
	
	@FilterSimpleExpression(name="numColegiado")
	private String numColegiado;
	
	@FilterSimpleExpression(name="fechaAlta", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	@FilterSimpleExpression(name="fechaPresentacion", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;
	
	@FilterSimpleExpression(name="idContrato")
	@FilterRelationship(name="contrato", joinType = Criteria.LEFT_JOIN)
	private Long idContrato;
	
	@FilterSimpleExpression(name="referenciaPropia", restriction=FilterSimpleExpressionRestriction.LIKE_PORCENTAJE)
	private String referenciaPropia;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getCodNotario() {
		return codNotario;
	}

	public void setCodNotario(String codNotario) {
		this.codNotario = codNotario;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
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

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getReferenciaPropia() {
		return referenciaPropia;
	}

	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}
	
}
