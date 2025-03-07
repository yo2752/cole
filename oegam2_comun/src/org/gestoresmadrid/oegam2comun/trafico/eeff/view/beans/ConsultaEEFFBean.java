package org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaEEFFBean implements Serializable{

	private static final long serialVersionUID = 8723322256075596475L;

	private BigDecimal numExpediente;
	
	private BigDecimal numExpedienteTramite;
	
	private Date fechaRealizacion;
	
	private BigDecimal estado;
	
	private String descEstado;
	
	private Date fechaAlta;
	
	private String tarjetaBastidor;

	private String tarjetaNive;
	
	private String firCif;

	private String firMarca;
	
	private String nifRepresentado;
	
	private String respuesta;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	
	public BigDecimal getNumExpedienteTramite() {
		return numExpedienteTramite;
	}

	public void setNumExpedienteTramite(BigDecimal numExpedienteTramite) {
		this.numExpedienteTramite = numExpedienteTramite;
	}

	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public String getTarjetaBastidor() {
		return tarjetaBastidor;
	}

	public void setTarjetaBastidor(String tarjetaBastidor) {
		this.tarjetaBastidor = tarjetaBastidor;
	}

	public String getTarjetaNive() {
		return tarjetaNive;
	}

	public void setTarjetaNive(String tarjetaNive) {
		this.tarjetaNive = tarjetaNive;
	}

	public String getFirCif() {
		return firCif;
	}

	public void setFirCif(String firCif) {
		this.firCif = firCif;
	}

	public String getFirMarca() {
		return firMarca;
	}

	public void setFirMarca(String firMarca) {
		this.firMarca = firMarca;
	}

	public String getNifRepresentado() {
		return nifRepresentado;
	}

	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getDescEstado() {
		return descEstado;
	}

	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}
	
}
