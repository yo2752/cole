package org.gestoresmadrid.oegam2comun.ficheros.temporales.views.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class FicherosTemporalesBean {
	
	@FilterSimpleExpression(name="nombre")
	private String nombreFichero;
	
	@FilterSimpleExpression
	private String numColegiado;
	
	@FilterSimpleExpression(name="tipoDocumento")
	private String tipoFichero;
	
	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	@FilterSimpleExpression
	private Long idFicheroTemporal;
	
	@FilterSimpleExpression
	private Long idContrato;
	
	@FilterSimpleExpression
	private Integer estado;
	
	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTipoFichero() {
		return tipoFichero;
	}

	public void setTipoFichero(String tipoFichero) {
		this.tipoFichero = tipoFichero;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIdFicheroTemporal() {
		return idFicheroTemporal;
	}

	public void setIdFicheroTemporal(Long idFicheroTemporal) {
		this.idFicheroTemporal = idFicheroTemporal;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

}
