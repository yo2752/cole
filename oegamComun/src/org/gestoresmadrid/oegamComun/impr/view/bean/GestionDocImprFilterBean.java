package org.gestoresmadrid.oegamComun.impr.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class GestionDocImprFilterBean implements Serializable{

	private static final long serialVersionUID = -6473054969658771065L;

	@FilterSimpleExpression(name = "id")
	private Long id;
	
	@FilterSimpleExpression(name = "docImpr")
	private Long docImpr;
	
	@FilterSimpleExpression(name = "matricula")
	private String matricula;
	
	@FilterSimpleExpression(name = "tipoImpr")
	private String tipoImpr;
	
	@FilterSimpleExpression(name = "tipoTramite")
	private String tipoTramite;
	
	@FilterSimpleExpression(name = "numExpediente")
	private String numExpediente;
	
	@FilterSimpleExpression(name = "estado")
	private String estado;
	
	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	@FilterSimpleExpression(name = "fechaImpresion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaImpresion;
	
	@FilterSimpleExpression(name = "jefatura")
	private String jefatura;
	
	@FilterSimpleExpression(name = "idContrato")
	private Long idContrato;
	
	@FilterSimpleExpression(name = "carpeta")
	private String carpeta;
	
	private String nuevoEstado;
	
	private Boolean tieneJefaturaImpr;


	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoImpr() {
		return tipoImpr;
	}

	public void setTipoImpr(String tipoImpr) {
		this.tipoImpr = tipoImpr;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FechaFraccionada getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(FechaFraccionada fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public String getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(String nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	public Boolean getTieneJefaturaImpr() {
		return tieneJefaturaImpr;
	}

	public void setTieneJefaturaImpr(Boolean tieneJefaturaImpr) {
		this.tieneJefaturaImpr = tieneJefaturaImpr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDocImpr() {
		return docImpr;
	}

	public void setDocImpr(Long docImpr) {
		this.docImpr = docImpr;
	}
}
