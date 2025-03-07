package org.gestoresmadrid.oegamDocBase.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class GestionDocBaseFilterBean implements Serializable{

	private static final long serialVersionUID = -5519784114821894082L;

	@FilterSimpleExpression(name="fechaPresentacion", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;
	
	@FilterSimpleExpression(name="idContrato")
	private Long idContrato;
	
	@FilterSimpleExpression(name="numExpediente")
	@FilterRelationships (
			value= {
					@FilterRelationship(name="tramitesTrafico", joinType=Criteria.INNER_JOIN)
					}
						  )	
	private BigDecimal numExpediente;
	
	@FilterSimpleExpression(name="matricula")
	@FilterRelationships (
			value= {
					@FilterRelationship(name="tramitesTrafico", joinType=Criteria.INNER_JOIN),
					@FilterRelationship(name="vehiculo", joinType=Criteria.INNER_JOIN)
					}
						  )
	private String matricula;
	
	@FilterSimpleExpression(name="carpeta")
	private String tipoCarpeta;
	
	@FilterSimpleExpression(name="estado")
	private String estado;
	
	@FilterSimpleExpression(name="docId")
	private String docId;

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

	public String getTipoCarpeta() {
		return tipoCarpeta;
	}

	public void setTipoCarpeta(String tipoCarpeta) {
		this.tipoCarpeta = tipoCarpeta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
	
}
