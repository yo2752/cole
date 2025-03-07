package org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.criterion.CriteriaSpecification;

import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.FechaFraccionada;

public class ConsultaInteveViewBean {

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "matricula")
	@FilterRelationship(name="solicitudes", joinType = CriteriaSpecification.LEFT_JOIN)
	private String matricula;

	@FilterSimpleExpression(name = "bastidor")
	@FilterRelationship(name="solicitudes", joinType = CriteriaSpecification.LEFT_JOIN)
	private String bastidor;

	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name = "estado")
	private BigDecimal estado;

	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	@FilterSimpleExpression(name = "tipoTramite")
	private String tipoTramite;

	@FilterSimpleExpression(name = "codigoTasa")
	@FilterRelationship(name="solicitudes", joinType = CriteriaSpecification.LEFT_JOIN)
	private String codigoTasa;

	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name="contrato", joinType = CriteriaSpecification.LEFT_JOIN)
	private Long idContrato;

	public ConsultaInteveViewBean(){
		super();
		this.tipoTramite = TipoTramiteTrafico.Solicitud_Inteve.getValorEnum();
		
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
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

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipo) {
		this.tipoTramite = tipo;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

}