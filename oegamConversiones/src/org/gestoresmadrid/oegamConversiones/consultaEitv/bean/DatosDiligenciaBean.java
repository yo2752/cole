package org.gestoresmadrid.oegamConversiones.consultaEitv.bean;

import java.io.Serializable;

public class DatosDiligenciaBean implements Serializable{

	private static final long serialVersionUID = 2669312514284580652L;
	
	private String doi;
    private String importeFactura;
    private String bloque;
    private String codigoPostal;
    private String escalera;
    private String hectometro;
    private String kilometro;
    private String localidad;
    private String municipio;
    private String numero;
    private String pais;
    private String planta;
    private String portal;
    private String provincia;
    private String puerta;
    private String tipoVia;
    private String via;
    private String fechaFactura;
    
	public DatosDiligenciaBean() {
		super();
	}
	
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public String getImporteFactura() {
		return importeFactura;
	}
	public void setImporteFactura(String importeFactura) {
		this.importeFactura = importeFactura;
	}
	public String getBloque() {
		return bloque;
	}
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getEscalera() {
		return escalera;
	}
	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}
	public String getHectometro() {
		return hectometro;
	}
	public void setHectometro(String hectometro) {
		this.hectometro = hectometro;
	}
	public String getKilometro() {
		return kilometro;
	}
	public void setKilometro(String kilometro) {
		this.kilometro = kilometro;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getPlanta() {
		return planta;
	}
	public void setPlanta(String planta) {
		this.planta = planta;
	}
	public String getPortal() {
		return portal;
	}
	public void setPortal(String portal) {
		this.portal = portal;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getPuerta() {
		return puerta;
	}
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}
	public String getTipoVia() {
		return tipoVia;
	}
	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getFechaFactura() {
		return fechaFactura;
	}
	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}
    
}
