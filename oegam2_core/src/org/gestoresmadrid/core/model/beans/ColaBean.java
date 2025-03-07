package org.gestoresmadrid.core.model.beans;

import java.math.BigDecimal;

import utilidades.estructuras.FechaFraccionada;

public class ColaBean {

	private BigDecimal idEnvio; 
	private String proceso; 
	private String cola; 
	private String fecha_hora; //date en base de datos
	private BigDecimal estado; 
	private BigDecimal numeroIntento;
	private String tipoTramite; 
	private BigDecimal idTramite; 
	private BigDecimal idUsuario; 
	private String xmlEnviar;
	private String respuesta;
	private String numColegiado;
	private String horaEntrada;
	private FechaFraccionada fechaEntrada;
	private BigDecimal idContrato;
	
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public BigDecimal getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(BigDecimal idEnvio) {
		this.idEnvio = idEnvio;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getCola() {
		return cola;
	}
	public void setCola(String cola) {
		this.cola = cola;
	}
	public String getFecha_hora() {
		return fecha_hora;
	}
	public void setFecha_hora(String fechaHora) {
		fecha_hora = fechaHora;
	}
	public BigDecimal getEstado() {
		return estado;
	}
	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
	public BigDecimal getNumeroIntento() {
		return numeroIntento;
	}
	public void setNumeroIntento(BigDecimal numeroIntento) {
		this.numeroIntento = numeroIntento;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public BigDecimal getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(BigDecimal idTramite) {
		this.idTramite = idTramite;
	}
	public BigDecimal getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getXmlEnviar() {
		return xmlEnviar;
	}
	public void setXmlEnviar(String xmlEnviar) {
		this.xmlEnviar = xmlEnviar;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public FechaFraccionada getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(FechaFraccionada fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public BigDecimal getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

}
