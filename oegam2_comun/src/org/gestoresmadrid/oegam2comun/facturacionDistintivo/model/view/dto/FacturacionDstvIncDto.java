package org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.dto;

import java.io.Serializable;
import java.util.Date;

public class FacturacionDstvIncDto implements Serializable{


	private static final long serialVersionUID = -5536974898481960924L;
	
	private String motivoIncidencia;
	private String cantidad;
	private Date fechaInc;
	private String idIncidencia;
	private String duplicado;
	public String getMotivoIncidencia() {
		return motivoIncidencia;
	}
	public void setMotivoIncidencia(String motivoIncidencia) {
		this.motivoIncidencia = motivoIncidencia;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public Date getFechaInc() {
		return fechaInc;
	}
	public void setFechaInc(Date fechaInc) {
		this.fechaInc = fechaInc;
	}
	public String getIdIncidencia() {
		return idIncidencia;
	}
	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	public String getDuplicado() {
		return duplicado;
	}
	public void setDuplicado(String duplicado) {
		this.duplicado = duplicado;
	}
	
	

	
}