package org.gestoresmadrid.oegam2comun.bien.view.bean;

import java.io.Serializable;
import java.util.Date;

public class BienBean implements Serializable{

	private static final long serialVersionUID = -4829127538886015303L;
	
	private Long idBien;
	private String refCatrastal;
	private String provincia;
	private String municipio;
	private String tipoBien;
	private String tipoInmueble;
	private String nombreVia;
	private String parcela;
	private String poligono;
	private Date fechaAlta;
	
	private Long idufir;
	private Long numeroFinca;
	private Long numFincaDupl;
	private Long seccion;
//	@FilterSimpleExpression(name = "subnumFinca", restriction = FilterSimpleExpressionRestriction.ISNOTNULL)
	private String subnumFinca;
	
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Long getIdBien() {
		return idBien;
	}
	public void setIdBien(Long idBien) {
		this.idBien = idBien;
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
	public String getTipoBien() {
		return tipoBien;
	}
	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}
	public String getTipoInmueble() {
		return tipoInmueble;
	}
	public void setTipoInmueble(String tipoInmueble) {
		this.tipoInmueble = tipoInmueble;
	}
	public String getNombreVia() {
		return nombreVia;
	}
	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}
	public String getParcela() {
		return parcela;
	}
	public void setParcela(String parcela) {
		this.parcela = parcela;
	}
	public String getPoligono() {
		return poligono;
	}
	public void setPoligono(String poligono) {
		this.poligono = poligono;
	}
	public Long getIdufir() {
		return idufir;
	}
	public void setIdufir(Long idufir) {
		this.idufir = idufir;
	}
	public Long getNumeroFinca() {
		return numeroFinca;
	}
	public void setNumeroFinca(Long numeroFinca) {
		this.numeroFinca = numeroFinca;
	}
	public Long getNumFincaDupl() {
		return numFincaDupl;
	}
	public void setNumFincaDupl(Long numFincaDupl) {
		this.numFincaDupl = numFincaDupl;
	}
	public Long getSeccion() {
		return seccion;
	}
	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}
	public String getSubnumFinca() {
		return subnumFinca;
	}
	public void setSubnumFinca(String subnumFinca) {
		this.subnumFinca = subnumFinca;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
