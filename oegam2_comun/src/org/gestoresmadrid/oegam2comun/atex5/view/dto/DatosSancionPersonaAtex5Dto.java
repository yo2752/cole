package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.Date;

public class DatosSancionPersonaAtex5Dto implements Serializable{

	private static final long serialVersionUID = 3862889990980953501L;
	
	private String anotacion;
    private String autoridadSancionadora;
    private Integer duracion;
    private String expediente;
    private Date fecha;
    private Date fechaFin;
    private String motivo;
    private String tipoSancion;

    public String getAnotacion() {
		return anotacion;
	}
	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}
	public String getAutoridadSancionadora() {
		return autoridadSancionadora;
	}
	public void setAutoridadSancionadora(String autoridadSancionadora) {
		this.autoridadSancionadora = autoridadSancionadora;
	}
	public Integer getDuracion() {
		return duracion;
	}
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getTipoSancion() {
		return tipoSancion;
	}
	public void setTipoSancion(String tipoSancion) {
		this.tipoSancion = tipoSancion;
	}
    
}
