package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.gestoresmadrid.core.model.enumerados.EstadoTasaBloqueo;
import org.hibernate.Criteria;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;

public class ConsultaTasaBean implements Cloneable {

	@FilterSimpleExpression
	@FilterRelationship(name = "tramiteTrafico", joinType = Criteria.LEFT_JOIN)
	private String tipoTramite;

	@FilterSimpleExpression(name = "numExpediente")
	@FilterRelationship(name = "tramiteTrafico", joinType = Criteria.LEFT_JOIN)
	private BigDecimal bNumExpediente;

	private String numExpediente;

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

	private String bloqueada;
	
	@FilterSimpleExpression
	private String tipoAlmacen;

	@FilterSimpleExpression(name = "bloqueada")
	private String bTasaBloqueada;

	@FilterSimpleExpression(name = "bloqueada", restriction = FilterSimpleExpressionRestriction.EQ_WITH_NULL)
	private String bTasaDesbloqueada;

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BigDecimal getbNumExpediente() {
		return bNumExpediente;
	}

	public void setbNumExpediente(BigDecimal bNumExpediente) {
		this.bNumExpediente = bNumExpediente;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
		if (numExpediente != null && !numExpediente.isEmpty()) {
			bNumExpediente = new BigDecimal(numExpediente);
		} else {
			bNumExpediente = null;
		}
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

	public String getBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(String bloqueada) {
		this.bloqueada = bloqueada;
		if (StringUtils.isBlank(bloqueada) || EstadoTasaBloqueo.DESBLOQUEADA.getValorEnum().equals(bloqueada)) {
			bTasaBloqueada = null;
			bTasaDesbloqueada = bloqueada;
		} else {
			bTasaBloqueada = bloqueada;
			bTasaDesbloqueada = null;
		}
	}

	public String getbTasaBloqueada() {
		return bTasaBloqueada;
	}

	public void setbTasaBloqueada(String bTasaBloqueada) {
		this.bTasaBloqueada = bTasaBloqueada;
	}

	public String getbTasaDesbloqueada() {
		return bTasaDesbloqueada;
	}

	public void setbTasaDesbloqueada(String bTasaDesbloqueada) {
		this.bTasaDesbloqueada = bTasaDesbloqueada;
	}

	@Override
	public ConsultaTasaBean clone() throws CloneNotSupportedException {
		return (ConsultaTasaBean) super.clone();
	}

	public Integer getFormato() {
		return formato;
	}

	public void setFormato(Integer formato) {
		this.formato = formato;
	}

	public String getTipoAlmacen() {
		return tipoAlmacen;
	}

	public void setTipoAlmacen(String tipoAlmacen) {
		this.tipoAlmacen = tipoAlmacen;
	}
}
