package org.gestoresmadrid.oegam2comun.cola.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import utilidades.estructuras.Fecha;

public class ColaDto implements Serializable {

	private static final long serialVersionUID = -6364120663237616060L;

	private Long idEnvio;

	private String cola;

	private BigDecimal estado;

	private Fecha fechaHora;

	private BigDecimal idUsuario;

	private BigDecimal nIntento;

	private String nodo;

	private String proceso;

	private String respuesta;

	private String tipoTramite;

	private String xmlEnviar;

	private BigDecimal idTramite;

	private BigDecimal idContrato;

	public Long getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}

	public String getCola() {
		return cola;
	}

	public void setCola(String cola) {
		this.cola = cola;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Fecha getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Fecha fechaHora) {
		this.fechaHora = fechaHora;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public BigDecimal getnIntento() {
		return nIntento;
	}

	public void setnIntento(BigDecimal nIntento) {
		this.nIntento = nIntento;
	}

	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getXmlEnviar() {
		return xmlEnviar;
	}

	public void setXmlEnviar(String xmlEnviar) {
		this.xmlEnviar = xmlEnviar;
	}

	public BigDecimal getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(BigDecimal idTramite) {
		this.idTramite = idTramite;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

}