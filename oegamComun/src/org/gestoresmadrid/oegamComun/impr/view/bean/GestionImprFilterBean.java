package org.gestoresmadrid.oegamComun.impr.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class GestionImprFilterBean implements Serializable{

	private static final long serialVersionUID = -1865685499141205379L;

	@FilterSimpleExpression(name = "id")
	private Long id;
	
	@FilterSimpleExpression(name = "matricula")
	private String matricula;
	
	@FilterSimpleExpression(name = "bastidor")
	private String bastidor;
	
	@FilterSimpleExpression(name = "nive")
	private String nive;
	
	@FilterSimpleExpression(name = "tipoImpr")
	private String tipoImpr;
	
	@FilterSimpleExpression(name = "tipoTramite")
	private String tipoTramite;
	
	@FilterSimpleExpression(name = "numExpediente")
	private String numExpediente;
	
	@FilterSimpleExpression(name = "estadoSolicitud")
	private String estadoSolicitud;
	
	@FilterSimpleExpression(name = "estadoImpresion")
	private String estadoImpresion;
	
	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	@FilterSimpleExpression(name = "jefatura")
	private String jefatura;
	
	@FilterSimpleExpression(name = "docId")
	private Long docId;
	
	@FilterSimpleExpression(name = "idContrato")
	private Long idContrato;
	
	@FilterSimpleExpression(name = "carpeta")
	private String carpeta;
	
	@FilterSimpleExpression(name = "estadoImpr")
	private String estadoImpr;
	
	private String nuevoEstado;
	
	private String carpetaNueva;
	
	private Boolean tieneJefaturaImpr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
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

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
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

	public String getEstadoImpr() {
		return estadoImpr;
	}

	public void setEstadoImpr(String estadoImpr) {
		this.estadoImpr = estadoImpr;
	}

	public String getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(String nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	public String getCarpetaNueva() {
		return carpetaNueva;
	}

	public void setCarpetaNueva(String carpetaNueva) {
		this.carpetaNueva = carpetaNueva;
	}

	public Boolean getTieneJefaturaImpr() {
		return tieneJefaturaImpr;
	}

	public void setTieneJefaturaImpr(Boolean tieneJefaturaImpr) {
		this.tieneJefaturaImpr = tieneJefaturaImpr;
	}

}
