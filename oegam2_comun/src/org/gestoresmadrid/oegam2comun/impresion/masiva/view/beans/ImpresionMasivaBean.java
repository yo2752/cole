package org.gestoresmadrid.oegam2comun.impresion.masiva.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ImpresionMasivaBean {

	@FilterSimpleExpression
	private String nombreFichero;

	@FilterSimpleExpression
	private String tipoDocumento;

	@FilterSimpleExpression
	private Integer estadoImpresion;

	@FilterSimpleExpression
	private String numColegiado;

	@FilterSimpleExpression(name = "fechaEnviadoProceso", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaFiltradoEnviadoProceso;

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(Integer estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public FechaFraccionada getFechaFiltradoEnviadoProceso() {
		return fechaFiltradoEnviadoProceso;
	}

	public void setFechaFiltradoEnviadoProceso(FechaFraccionada fechaFiltradoEnviadoProceso) {
		this.fechaFiltradoEnviadoProceso = fechaFiltradoEnviadoProceso;
	}
}
