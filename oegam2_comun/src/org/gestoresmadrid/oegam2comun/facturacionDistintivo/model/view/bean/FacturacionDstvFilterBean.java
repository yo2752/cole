package org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class FacturacionDstvFilterBean implements Serializable{

	private static final long serialVersionUID = -13631671681836869L;

	@FilterSimpleExpression(name = "numExpediente")
	@FilterRelationships (
			value= {
					@FilterRelationship(name="docDistintivo", joinType=Criteria.LEFT_JOIN),
					@FilterRelationship(name="listaTramitesDistintivo", joinType=Criteria.LEFT_JOIN)
					})
	private BigDecimal numExpediente;
	
	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name="contrato", joinType = Criteria.LEFT_JOIN)
	private Long idContrato;
	
	@FilterSimpleExpression(name="matricula")
	@FilterRelationships (
			value= {
					@FilterRelationship(name="docDistintivo", joinType=Criteria.LEFT_JOIN),
					@FilterRelationship(name="listaTramitesDistintivo", joinType=Criteria.LEFT_JOIN),
					@FilterRelationship(name="vehiculo", joinType=Criteria.LEFT_JOIN)
					})
	private String matriculaTramite;
	
	
	@FilterSimpleExpression(name="matricula")
	@FilterRelationships (
			value= {
					@FilterRelationship(name="docDistintivo", joinType=Criteria.LEFT_JOIN),
					@FilterRelationship(name="listaDuplicadoDistintivos", joinType=Criteria.LEFT_JOIN),
					})
	private String matriculaDup;
	
	private String matricula;
	
	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecha;
	
	@FilterSimpleExpression(name="docIdPerm")
	@FilterRelationship(name="docDistintivo", joinType = Criteria.LEFT_JOIN)
	private String docDistintivo;
	
	@FilterSimpleExpression(name="tipoDistintivo")
	private String tipoDistintivo;
	
	@FilterSimpleExpression(name="id.nif")
	@FilterRelationships (
			value= {
					@FilterRelationship(name="docDistintivo", joinType=Criteria.LEFT_JOIN),
					@FilterRelationship(name="listaTramitesDistintivo", joinType=Criteria.LEFT_JOIN),
					@FilterRelationship(name="intervinienteTraficos", joinType=Criteria.LEFT_JOIN)
					})
	private String nif;
	
	
	
	@FilterSimpleExpression(name="tipoInterviniente")
	@FilterRelationships (
			value= {
					@FilterRelationship(name="docDistintivo", joinType=Criteria.LEFT_JOIN),
					@FilterRelationship(name="listaTramitesDistintivo", joinType=Criteria.LEFT_JOIN),
					@FilterRelationship(name="intervinienteTraficos", joinType=Criteria.LEFT_JOIN),
					@FilterRelationship(name="tipoIntervinienteVO", joinType=Criteria.LEFT_JOIN)
					})
	private String tipoInterviniente;

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

	public String getMatriculaTramite() {
		return matriculaTramite;
	}

	public void setMatriculaTramite(String matriculaTramite) {
		this.matriculaTramite = matriculaTramite;
	}

	public String getMatriculaDup() {
		return matriculaDup;
	}

	public void setMatriculaDup(String matriculaDup) {
		this.matriculaDup = matriculaDup;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

	public String getDocDistintivo() {
		return docDistintivo;
	}

	public void setDocDistintivo(String docDistintivo) {
		this.docDistintivo = docDistintivo;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}
	
}
