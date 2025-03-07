package org.gestoresmadrid.oegam2comun.bien.view.bean;

import java.io.Serializable;

public class BienRusticoBean implements Serializable{

	private static final long serialVersionUID = -8580740406292177596L;
	
	private String idBien;
	private String descUsoRustico;
	private String codPostal;
	private String refCatrastal;
	private String poligono;
	private String parcela;
	private String subParcela;
	private String paraje;
	private String provincia;
	private String municipio;
	private Long idufir;
	
	public String getIdBien() {
		return idBien;
	}
	public void setIdBien(String idBien) {
		this.idBien = idBien;
	}
	public String getDescUsoRustico() {
		return descUsoRustico;
	}
	public void setDescUsoRustico(String descUsoRustico) {
		this.descUsoRustico = descUsoRustico;
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
	public String getPoligono() {
		return poligono;
	}
	public void setPoligono(String poligono) {
		this.poligono = poligono;
	}
	public String getParcela() {
		return parcela;
	}
	public void setParcela(String parcela) {
		this.parcela = parcela;
	}
	public String getSubParcela() {
		return subParcela;
	}
	public void setSubParcela(String subParcela) {
		this.subParcela = subParcela;
	}
	public String getParaje() {
		return paraje;
	}
	public void setParaje(String paraje) {
		this.paraje = paraje;
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
	public Long getIdufir() {
		return idufir;
	}
	public void setIdufir(Long idufir) {
		this.idufir = idufir;
	}
	
}
