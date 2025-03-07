package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.Date;

public class DatosSeguroAtex5Dto implements Serializable{

	private static final long serialVersionUID = 3879615893145730575L;
	
	private String contratoSeguro;
	private String entidad;
	private Date fechaFin;
	private Date fechaInicio;
	
	public String getContratoSeguro() {
		return contratoSeguro;
	}
	public void setContratoSeguro(String contratoSeguro) {
		this.contratoSeguro = contratoSeguro;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
    
}
