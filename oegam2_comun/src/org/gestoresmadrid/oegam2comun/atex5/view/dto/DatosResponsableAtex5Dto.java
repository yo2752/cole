package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.Date;

import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Domicilio;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.DomicilioIne;

public class DatosResponsableAtex5Dto implements Serializable{

	private static final long serialVersionUID = 1175676190854207367L;
	
	private Date fechaFin;
    private Date fechaInicio;
    private String filiacion;
    private String nif;
    private String anotacion;
    private Domicilio domicilio;
    private DomicilioIne domicilioIne;
    private Date fechaPosesion;
    private String jefatura;
    private String situacionPosesion;
    private String sucursal;
    private String tipo;
    private Date fechaTramite;
    private String tramite;
    
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
	public String getFiliacion() {
		return filiacion;
	}
	public void setFiliacion(String filiacion) {
		this.filiacion = filiacion;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getAnotacion() {
		return anotacion;
	}
	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public DomicilioIne getDomicilioIne() {
		return domicilioIne;
	}
	public void setDomicilioIne(DomicilioIne domicilioIne) {
		this.domicilioIne = domicilioIne;
	}
	public Date getFechaPosesion() {
		return fechaPosesion;
	}
	public void setFechaPosesion(Date fechaPosesion) {
		this.fechaPosesion = fechaPosesion;
	}
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	public String getSituacionPosesion() {
		return situacionPosesion;
	}
	public void setSituacionPosesion(String situacionPosesion) {
		this.situacionPosesion = situacionPosesion;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getFechaTramite() {
		return fechaTramite;
	}
	public void setFechaTramite(Date fechaTramite) {
		this.fechaTramite = fechaTramite;
	}
	public String getTramite() {
		return tramite;
	}
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}
    
}
