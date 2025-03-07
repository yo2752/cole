package org.gestoresmadrid.oegam2comun.bien.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class BienUrbanoBean implements Serializable{

	private static final long serialVersionUID = -8580740406292177596L;
	
	private String idBien;
	private String codPostal;
	private String refCatrastal;
	private String provincia;
	private String municipio;
	private String numero;
	private String escalera;
	private String planta;
	private BigDecimal transmision;
	private String nombreVia;
	private BigDecimal superficieConst;
	private Long idufir;
	
	public String getIdBien() {
		return idBien;
	}
	public void setIdBien(String idBien) {
		this.idBien = idBien;
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getRefCatrastal() {
		return refCatrastal;
	}
	public void setRefCatrastal(String refCatrastal) {
		this.refCatrastal = refCatrastal;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
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
	public String getEscalera() {
		return escalera;
	}
	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}
	public String getPlanta() {
		return planta;
	}
	public void setPlanta(String planta) {
		this.planta = planta;
	}
	public BigDecimal getTransmision() {
		return transmision;
	}
	public void setTransmision(BigDecimal transmision) {
		this.transmision = transmision;
	}
	public String getNombreVia() {
		return nombreVia;
	}
	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}
	public Long getIdufir() {
		return idufir;
	}
	public void setIdufir(Long idufir) {
		this.idufir = idufir;
	}
	public BigDecimal getSuperficieConst() {
		return superficieConst;
	}
	public void setSuperficieConst(BigDecimal superficieConst) {
		this.superficieConst = superficieConst;
	}
	
}
