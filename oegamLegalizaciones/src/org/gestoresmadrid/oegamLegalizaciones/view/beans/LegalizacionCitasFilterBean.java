package org.gestoresmadrid.oegamLegalizaciones.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class LegalizacionCitasFilterBean implements Serializable {

	private static final long serialVersionUID = -7014093651237960670L;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "referencia", restriction = FilterSimpleExpressionRestriction.LIKE_PORCENTAJE)
	private String referencia;

	@FilterSimpleExpression(name = "fechaLegalizacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaLegalizacion;

	@FilterSimpleExpression(name = "tipoDocumento")
	private String tipoDocumento;

	@FilterSimpleExpression(name = "claseDocumento")
	private String claseDocumento;

	@FilterSimpleExpression(name = "pais")
	private String pais;

	@FilterSimpleExpression(name = "ficheroAdjunto")
	private Integer ficheroAdjunto;

	@FilterSimpleExpression(name = "nombre")
	private String nombre;

	@FilterSimpleExpression(name = "solicitado")
	private Integer solicitado;

	@FilterSimpleExpression(name = "estadoPeticion")
	private Integer estadoPeticion = null;

	@FilterSimpleExpression(name = "estado")
	private Integer estado = null;

	@FilterSimpleExpression(name = "idContrato")
	private Integer idContrato = null;

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public FechaFraccionada getFechaLegalizacion() {
		return fechaLegalizacion;
	}

	public void setFechaLegalizacion(FechaFraccionada fechaLegalizacion) {
		this.fechaLegalizacion = fechaLegalizacion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getFicheroAdjunto() {
		return ficheroAdjunto;
	}

	public void setFicheroAdjunto(Integer ficheroAdjunto) {
		this.ficheroAdjunto = ficheroAdjunto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getSolicitado() {
		return solicitado;
	}

	public void setSolicitado(Integer solicitado) {
		this.solicitado = solicitado;
	}

	public Integer getEstadoPeticion() {
		return estadoPeticion;
	}

	public void setEstadoPeticion(Integer estadoPeticion) {
		this.estadoPeticion = estadoPeticion;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getClaseDocumento() {
		return claseDocumento;
	}

	public void setClaseDocumento(String claseDocumento) {
		this.claseDocumento = claseDocumento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}
