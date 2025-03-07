package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;

import utilidades.estructuras.FechaFraccionada;

public class CompraTasasBean {

	@FilterSimpleExpression
	private String numJustificante791;

	@FilterSimpleExpression
	private String nrc;

	@FilterSimpleExpression
	private String nAutoliquidacion;

	@FilterSimpleExpression(name = "estado")
	private BigDecimal estadoFiltro;

	@FilterSimpleExpression(name = "estado", restriction = FilterSimpleExpressionRestriction.NE)
	private BigDecimal estadoExcluidoFiltro;

	private String estado;

	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	@FilterSimpleExpression(name = "fechaCompra", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaCompra;
	private String datosBancarios;

	@FilterSimpleExpression(name = "contrato.idContrato")
	private Long idContrato;

	@FilterSimpleExpression
	private String refPropia;

	public String getNumJustificante791() {
		return numJustificante791;
	}

	public void setNumJustificante791(String numJustificante791) {
		this.numJustificante791 = numJustificante791;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getNAutoliquidacion() {
		return nAutoliquidacion;
	}

	public void setNAutoliquidacion(String nAutoliquidacion) {
		this.nAutoliquidacion = nAutoliquidacion;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FechaFraccionada getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(FechaFraccionada fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public String getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(String datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public boolean isVacio() {
		if (this.fechaAlta == null && this.fechaCompra == null && this.estado == null && this.idContrato == null && this.numJustificante791 == null && this.nAutoliquidacion == null
				&& this.nrc == null)
			return true;
		else
			return false;
	}

	public BigDecimal getEstadoFiltro() {
		return estadoFiltro;
	}

	public void setEstadoFiltro(BigDecimal estadoFiltro) {
		this.estadoFiltro = estadoFiltro;
	}

	public BigDecimal getEstadoExcluidoFiltro() {
		return estadoExcluidoFiltro;
	}

	public void setEstadoExcluidoFiltro(BigDecimal estadoExcluidoFiltro) {
		this.estadoExcluidoFiltro = estadoExcluidoFiltro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
		if (estado != null && !estado.isEmpty()) {
			estadoFiltro = new BigDecimal(estado);
			estadoExcluidoFiltro = null;
		} else {
			estadoFiltro = null;
			estadoExcluidoFiltro = new BigDecimal(EstadoCompra.ANULADO.getCodigo());
		}
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

}
