package org.gestoresmadrid.oegam2comun.contrato.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.criterion.CriteriaSpecification;

import utilidades.estructuras.FechaFraccionada;

public class ContratoUsuarioFilterBean implements Serializable {

	private static final long serialVersionUID = -4766570423921953401L;

	@FilterSimpleExpression(name="cif")
	private String cif;

	@FilterSimpleExpression(name="estadoContrato")
	private BigDecimal estado;

	@FilterSimpleExpression(name="razonSocial")
	private String razonSocial;

	@FilterSimpleExpression(name="numColegiado")
	@FilterRelationship(name="colegiado", joinType = CriteriaSpecification.LEFT_JOIN)
	private String numColegiado;

	@FilterSimpleExpression(name="idProvincia")
	@FilterRelationship(name="provincia", joinType = CriteriaSpecification.LEFT_JOIN)
	private String idProvincia;

	@FilterSimpleExpression(name="idContrato")
	@FilterRelationship(name="contratoUsuario", joinType = CriteriaSpecification.LEFT_JOIN)
	private String idContrato;

	@FilterSimpleExpression(name="estadoUsuarioContrato")
	@FilterRelationship(name="contratoUsuario", joinType = CriteriaSpecification.LEFT_JOIN)
	private String estadoUsuarioContrato;

	@FilterSimpleExpression(name="alias")
	@FilterRelationship(name="colegiado", joinType = CriteriaSpecification.LEFT_JOIN)
	private String alias;

	@FilterSimpleExpression(name = "fechaCaducidad", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	@FilterRelationship(name="colegiado", joinType = CriteriaSpecification.LEFT_JOIN)
	private FechaFraccionada fechaCaducidad;

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

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getEstadoUsuarioContrato() {
		return estadoUsuarioContrato;
	}

	public void setEstadoUsuarioContrato(String estadoUsuarioContrato) {
		this.estadoUsuarioContrato = estadoUsuarioContrato;
	}

	public String getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public FechaFraccionada getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(FechaFraccionada fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

}