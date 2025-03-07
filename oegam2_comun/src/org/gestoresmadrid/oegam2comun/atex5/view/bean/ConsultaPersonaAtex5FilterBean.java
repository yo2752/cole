package org.gestoresmadrid.oegam2comun.atex5.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaPersonaAtex5FilterBean implements Serializable{

	private static final long serialVersionUID = 3017636297437006492L;

	@FilterSimpleExpression(name="nif")
	private String nif;
	
	@FilterSimpleExpression(name="estado")
	private BigDecimal estado;
	
	@FilterSimpleExpression(name="numExpediente")
	private BigDecimal numExpediente;
	
	@FilterSimpleExpression(name="numColegiado")
	private String numColegiado;
	
	@FilterSimpleExpression(name="razonSocial",restriction = FilterSimpleExpressionRestriction.LIKE)
	private String razonSocial;
	
	@FilterSimpleExpression(name="idContrato")
	private Long idContrato;
	
	@FilterSimpleExpression(name="fechaAlta", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getRazonSocial() {
		if(razonSocial != null && !razonSocial.isEmpty()){
			if(razonSocial.contains("%")) {
				razonSocial = razonSocial.replace("%", "");
			}
		}
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
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

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

}
