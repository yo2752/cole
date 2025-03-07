package org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaTramiteTraficoFilterBean implements Serializable {

	private static final long serialVersionUID = 1777069984589304384L;

	private BigDecimal numExpediente;

	private Long idContrato;

	private FechaFraccionada fechaAlta;

	private FechaFraccionada fechaPresentacion;

	private String nifTitular;

	private String estado;

	private String refPropia;

	private String jefatura;

	private String tipoTramite;

	private String nifFacturacion;

	private String bastidor;

	private String matricula;

	private String nive;

	private String tipoTasa;

	private String presentadoJPT;

	private String conNive;
	
	private String respuesta;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getNifTitular() {
		return nifTitular;
	}

	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getNifFacturacion() {
		return nifFacturacion;
	}

	public void setNifFacturacion(String nifFacturacion) {
		this.nifFacturacion = nifFacturacion;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}
	
	public String getPresentadoJPT() {
		return presentadoJPT;
	}

	public void setPresentadoJPT(String presentadoJPT) {
		this.presentadoJPT = presentadoJPT;
	}

	public String getConNive() {
		return conNive;
	}

	public void setConNive(String conNive) {
		this.conNive = conNive;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}