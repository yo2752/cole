package org.gestoresmadrid.oegam2comun.modelo600_601.view.dto;

import java.io.Serializable;
import java.util.Date;

public class ResultadoModelo600601Dto implements Serializable{

	private static final long serialVersionUID = 1349701070047335010L;

	private Integer idResultado;
	private Modelo600_601Dto modelo600601;
	private String codigoResultado;
	private String justificante;
	private String nccm;
	private String cso;
	private Date fechaPresentacion;
	private String expedienteCAM;
	private String cartaPago;
	private String diligencia;
	private Date fechaEjecucion;
	
	public Integer getIdResultado() {
		return idResultado;
	}
	public void setIdResultado(Integer idResultado) {
		this.idResultado = idResultado;
	}
	public Modelo600_601Dto getModelo600601() {
		return modelo600601;
	}
	public void setModelo600601(Modelo600_601Dto modelo600601) {
		this.modelo600601 = modelo600601;
	}
	public String getCodigoResultado() {
		return codigoResultado;
	}
	public void setCodigoResultado(String codigoResultado) {
		this.codigoResultado = codigoResultado;
	}
	public String getJustificante() {
		return justificante;
	}
	public void setJustificante(String justificante) {
		this.justificante = justificante;
	}
	public String getNccm() {
		return nccm;
	}
	public void setNccm(String nccm) {
		this.nccm = nccm;
	}
	public String getCso() {
		return cso;
	}
	public void setCso(String cso) {
		this.cso = cso;
	}
	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public String getExpedienteCAM() {
		return expedienteCAM;
	}
	public void setExpedienteCAM(String expedienteCAM) {
		this.expedienteCAM = expedienteCAM;
	}
	public String getCartaPago() {
		return cartaPago;
	}
	public void setCartaPago(String cartaPago) {
		this.cartaPago = cartaPago;
	}
	public String getDiligencia() {
		return diligencia;
	}
	public void setDiligencia(String diligencia) {
		this.diligencia = diligencia;
	}
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}
	
	
}
