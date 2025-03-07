package org.gestoresmadrid.oegamImpresion.view.bean;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ImpresionFilterBean {

	@FilterSimpleExpression
	private String nombreDocumento;

	@FilterSimpleExpression
	private String tipoDocumento;

	@FilterSimpleExpression
	private String estado;

	@FilterSimpleExpression
	private Long idContrato;

	@FilterSimpleExpression
	private String tipoTramite;

	@FilterSimpleExpression(name = "fechaCreacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaFiltrado;

	@FilterSimpleExpression
	private BigDecimal numExpediente;

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public FechaFraccionada getFechaFiltrado() {
		return fechaFiltrado;
	}

	public void setFechaFiltrado(FechaFraccionada fechaFiltrado) {
		this.fechaFiltrado = fechaFiltrado;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
}
