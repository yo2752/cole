package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.Date;

public class DatosAdministrativosAtex5Dto implements Serializable{

	private static final long serialVersionUID = -8152472092068778937L;
	
	private String anotacion;
    private Date fecha;
    private String jefatura;
    private String sucursal;
    
    private String autoridad;
    private String expediente;
    private Date fechaMaterializacion;
    private Date fechaTramite;
    
    private String anyoImpago;
    private String doi;
    private String municipio;
    private String provincia;
    
    private String financieraDomicilio;
    private String registro;
    private String tipoLimitacion;
    
	public String getAnotacion() {
		return anotacion;
	}
	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getAutoridad() {
		return autoridad;
	}
	public void setAutoridad(String autoridad) {
		this.autoridad = autoridad;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public Date getFechaMaterializacion() {
		return fechaMaterializacion;
	}
	public void setFechaMaterializacion(Date fechaMaterializacion) {
		this.fechaMaterializacion = fechaMaterializacion;
	}
	public Date getFechaTramite() {
		return fechaTramite;
	}
	public void setFechaTramite(Date fechaTramite) {
		this.fechaTramite = fechaTramite;
	}
	public String getAnyoImpago() {
		return anyoImpago;
	}
	public void setAnyoImpago(String anyoImpago) {
		this.anyoImpago = anyoImpago;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getFinancieraDomicilio() {
		return financieraDomicilio;
	}
	public void setFinancieraDomicilio(String financieraDomicilio) {
		this.financieraDomicilio = financieraDomicilio;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getTipoLimitacion() {
		return tipoLimitacion;
	}
	public void setTipoLimitacion(String tipoLimitacion) {
		this.tipoLimitacion = tipoLimitacion;
	}
    
}
