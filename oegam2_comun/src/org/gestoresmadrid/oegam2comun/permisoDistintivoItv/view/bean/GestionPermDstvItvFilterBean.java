package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class GestionPermDstvItvFilterBean implements Serializable {

	private static final long serialVersionUID = -4749542735746625273L;

	@FilterSimpleExpression(name = "id")
	private Long docId;

	private BigDecimal numExpediente;

	@FilterSimpleExpression(name = "numExpediente")
	@FilterRelationships(value = { @FilterRelationship(name = "listaTramitesPermiso", joinType = Criteria.INNER_JOIN) })
	private BigDecimal numExpedientePerm;

	@FilterSimpleExpression(name = "jefatura")
	private String jefatura;

	@FilterSimpleExpression(name = "numExpediente")
	@FilterRelationships(value = { @FilterRelationship(name = "listaTramitesEitv", joinType = Criteria.INNER_JOIN) })
	private BigDecimal numExpedienteEitv;

	@FilterSimpleExpression(name = "numExpediente")
	@FilterRelationships(value = { @FilterRelationship(name = "listaTramitesDistintivo", joinType = Criteria.INNER_JOIN) })
	private BigDecimal numExpedienteDstv;

	private String matricula;

	@FilterSimpleExpression(name = "matricula")
	@FilterRelationships(value = { @FilterRelationship(name = "listaTramitesPermiso", joinType = Criteria.INNER_JOIN), @FilterRelationship(name = "vehiculo", joinType = Criteria.INNER_JOIN) })
	private String matriculaPerm;

	@FilterSimpleExpression(name = "matricula")
	@FilterRelationships(value = { @FilterRelationship(name = "listaTramitesEitv", joinType = Criteria.INNER_JOIN), @FilterRelationship(name = "vehiculo", joinType = Criteria.INNER_JOIN) })
	private String matriculaEitv;

	@FilterSimpleExpression(name = "matricula")
	@FilterRelationships(value = { @FilterRelationship(name = "listaTramitesDistintivo", joinType = Criteria.INNER_JOIN), @FilterRelationship(name = "vehiculo", joinType = Criteria.INNER_JOIN) })
	private String matriculaDstv;

	@FilterSimpleExpression(name = "matricula")
	@FilterRelationships(value = { @FilterRelationship(name = "listaDuplicadoDistintivos", joinType = Criteria.INNER_JOIN) })
	private String matriculaDupDstv;

	@FilterSimpleExpression(name = "tipo")
	private String tipoDocumento;

	@FilterSimpleExpression(name = "tipoDistintivo")
	private String tipoDistintivo;

	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name = "contrato", joinType = Criteria.LEFT_JOIN)
	private Long idContrato;

	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	@FilterSimpleExpression(name = "estado")
	private BigDecimal estado;

	@FilterSimpleExpression(name = "docIdPerm")
	private String docIdPerm;

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getNumExpedientePerm() {
		return numExpedientePerm;
	}

	public void setNumExpedientePerm(BigDecimal numExpedientePerm) {
		this.numExpedientePerm = numExpedientePerm;
	}

	public BigDecimal getNumExpedienteEitv() {
		return numExpedienteEitv;
	}

	public void setNumExpedienteEitv(BigDecimal numExpedienteEitv) {
		this.numExpedienteEitv = numExpedienteEitv;
	}

	public BigDecimal getNumExpedienteDstv() {
		return numExpedienteDstv;
	}

	public void setNumExpedienteDstv(BigDecimal numExpedienteDstv) {
		this.numExpedienteDstv = numExpedienteDstv;
	}

	public String getMatriculaPerm() {
		return matriculaPerm;
	}

	public void setMatriculaPerm(String matriculaPerm) {
		this.matriculaPerm = matriculaPerm;
	}

	public String getMatriculaEitv() {
		return matriculaEitv;
	}

	public void setMatriculaEitv(String matriculaEitv) {
		this.matriculaEitv = matriculaEitv;
	}

	public String getMatriculaDstv() {
		return matriculaDstv;
	}

	public void setMatriculaDstv(String matriculaDstv) {
		this.matriculaDstv = matriculaDstv;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getDocIdPerm() {
		return docIdPerm;
	}

	public void setDocIdPerm(String docIdPerm) {
		this.docIdPerm = docIdPerm;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getMatriculaDupDstv() {
		return matriculaDupDstv;
	}

	public void setMatriculaDupDstv(String matriculaDupDstv) {
		this.matriculaDupDstv = matriculaDupDstv;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

}
