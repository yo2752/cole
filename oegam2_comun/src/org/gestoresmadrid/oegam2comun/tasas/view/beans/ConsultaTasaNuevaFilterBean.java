package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;

public class ConsultaTasaNuevaFilterBean implements Cloneable {

	@FilterSimpleExpression
	@FilterRelationship(name = "tramiteTrafico", joinType = Criteria.LEFT_JOIN)
	private String tipoTramite;

	@FilterSimpleExpression
	@FilterRelationship(name = "tramiteTrafico", joinType = Criteria.LEFT_JOIN)
	private BigDecimal numExpediente;

	@FilterSimpleExpression
	@FilterRelationships(value = { @FilterRelationship(name = "contrato", joinType = Criteria.LEFT_JOIN), @FilterRelationship(name = "colegiado", joinType = Criteria.LEFT_JOIN)

	})
	private String numColegiado;


	@FilterSimpleExpression
	@FilterRelationship(name = "contrato", joinType = Criteria.LEFT_JOIN)
	private Long idContrato;

	@FilterSimpleExpression
	@FilterRelationship(name = "tramiteTrafico", joinType = Criteria.LEFT_JOIN)
	private String refPropia;

	@FilterSimpleExpression
	private String codigoTasa;

	@FilterSimpleExpression
	private String tipoTasa;

	@FilterSimpleExpression(restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	@FilterSimpleExpression(restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAsignacion;

	@FilterSimpleExpression(restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private Fecha fechaFinVigencia;

	private String asignada;

	@FilterSimpleExpression(name = "fechaAsignacion", restriction = FilterSimpleExpressionRestriction.ISNOTNULL)
	private Boolean bAsignada;

	@FilterSimpleExpression(name = "fechaAsignacion", restriction = FilterSimpleExpressionRestriction.ISNULL)
	private Boolean bNoAsignada;

	@FilterSimpleExpression
	private Integer formato;
	
	@FilterSimpleExpression
	private BigDecimal importadoIcogam;
	
	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FechaFraccionada getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(FechaFraccionada fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Fecha getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Fecha fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public Integer getFormato() {
		return formato;
	}

	public void setFormato(Integer formato) {
		this.formato = formato;
	}

	public String getAsignada() {
		return asignada;
	}

	public void setAsignada(String asignada) {
		this.asignada = asignada;
		if (asignada != null && !asignada.isEmpty()) {
			bAsignada = "SI".equalsIgnoreCase(asignada);
			bNoAsignada = !bAsignada;
		} else {
			bAsignada = null;
			bNoAsignada = null;
		}
	}

	public Boolean getbAsignada() {
		return bAsignada;
	}

	public void setbAsignada(Boolean bAsignada) {
		this.bAsignada = bAsignada;
	}

	public Boolean getbNoAsignada() {
		return bNoAsignada;
	}

	public void setbNoAsignada(Boolean bNoAsignada) {
		this.bNoAsignada = bNoAsignada;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getImportadoIcogam() {
		return importadoIcogam;
	}

	public void setImportadoIcogam(BigDecimal importadoIcogam) {
		this.importadoIcogam = importadoIcogam;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}
