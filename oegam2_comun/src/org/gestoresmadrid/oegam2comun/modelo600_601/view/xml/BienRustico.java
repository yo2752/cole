package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"caracter","transmision","tipo","tipoDesc","provincia","provinciaDesc","municipio","municipioDesc"
		,"codigoPostal","paraje","poligono","parcela","subparcela","superficie","unidadSuperficie","unidadSuperficieDesc"
		,"tipoGanaderia","tipoGanaderiaDesc","tipoCultivo","tipoCultivoDesc","tipoOtros","tipoOtrosDesc","refCatastral"
		,"valorDeclarado","explotacion","explotacionDesc"})
@XmlRootElement(name = "bien_rustico")
public class BienRustico {
	@XmlElement(name = "Caracter", required = true)
	protected String caracter;
	@XmlElement(name = "Transmision", required = true)
	protected String transmision;
	@XmlElement(name = "tipo", required = true)
	protected String tipo;
	@XmlElement(name = "tipoDesc", required = true)
	protected String tipoDesc;
	@XmlElement(name = "provincia", required = true)
	protected String provincia;
	@XmlElement(name = "provinciaDesc", required = true)
	protected String provinciaDesc;
	@XmlElement(name = "municipio", required = true)
	protected String municipio;
	@XmlElement(name = "municipioDesc", required = true)
	protected String municipioDesc;
	@XmlElement(name = "codigoPostal", required = true)
	protected String codigoPostal;
	@XmlElement(name = "paraje", required = true)
	protected String paraje;
	@XmlElement(name = "poligono", required = true)
	protected String poligono;
	@XmlElement(name = "parcela", required = true)
	protected String parcela;
	@XmlElement(name = "subparcela", required = true)
	protected String subparcela;
	@XmlElement(name = "superficie", required = true)
	protected String superficie;
	@XmlElement(name = "UnidadSuperficie", required = true)
	protected String unidadSuperficie;
	@XmlElement(name = "UnidadSuperficieDesc", required = true)
	protected String unidadSuperficieDesc;
	@XmlElement(name = "TipoGanaderia", required = true)
	protected String tipoGanaderia;
	@XmlElement(name = "TipoGanaderiaDesc", required = true)
	protected String tipoGanaderiaDesc;
	@XmlElement(name = "TipoCultivo", required = true)
	protected String tipoCultivo;
	@XmlElement(name = "TipoCultivoDesc", required = true)
	protected String tipoCultivoDesc;
	@XmlElement(name = "TipoOtros", required = true)
	protected String tipoOtros;
	@XmlElement(name = "TipoOtrosDesc", required = true)
	protected String tipoOtrosDesc;
	@XmlElement(name = "ReferenciaCatastral", required = true)
	protected String refCatastral;
	@XmlElement(name = "ValorDeclarado", required = true)
	protected String valorDeclarado;
	@XmlElement(name = "explotacion", required = true)
	protected String explotacion;
	@XmlElement(name = "explotacionDesc", required = true)
	protected String explotacionDesc;
	
	public String getCaracter() {
		return caracter;
	}
	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}
	public String getTransmision() {
		return transmision;
	}
	public void setTransmision(String transmision) {
		this.transmision = transmision;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipoDesc() {
		return tipoDesc;
	}
	public void setTipoDesc(String tipoDesc) {
		this.tipoDesc = tipoDesc;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getProvinciaDesc() {
		return provinciaDesc;
	}
	public void setProvinciaDesc(String provinciaDesc) {
		this.provinciaDesc = provinciaDesc;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getMunicipioDesc() {
		return municipioDesc;
	}
	public void setMunicipioDesc(String municipioDesc) {
		this.municipioDesc = municipioDesc;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getParaje() {
		return paraje;
	}
	public void setParaje(String paraje) {
		this.paraje = paraje;
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
	public String getSubparcela() {
		return subparcela;
	}
	public void setSubparcela(String subparcela) {
		this.subparcela = subparcela;
	}
	public String getSuperficie() {
		return superficie;
	}
	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}
	public String getUnidadSuperficie() {
		return unidadSuperficie;
	}
	public void setUnidadSuperficie(String unidadSuperficie) {
		this.unidadSuperficie = unidadSuperficie;
	}
	public String getUnidadSuperficieDesc() {
		return unidadSuperficieDesc;
	}
	public void setUnidadSuperficieDesc(String unidadSuperficieDesc) {
		this.unidadSuperficieDesc = unidadSuperficieDesc;
	}
	public String getTipoGanaderia() {
		return tipoGanaderia;
	}
	public void setTipoGanaderia(String tipoGanaderia) {
		this.tipoGanaderia = tipoGanaderia;
	}
	public String getTipoGanaderiaDesc() {
		return tipoGanaderiaDesc;
	}
	public void setTipoGanaderiaDesc(String tipoGanaderiaDesc) {
		this.tipoGanaderiaDesc = tipoGanaderiaDesc;
	}
	public String getTipoCultivo() {
		return tipoCultivo;
	}
	public void setTipoCultivo(String tipoCultivo) {
		this.tipoCultivo = tipoCultivo;
	}
	public String getTipoCultivoDesc() {
		return tipoCultivoDesc;
	}
	public void setTipoCultivoDesc(String tipoCultivoDesc) {
		this.tipoCultivoDesc = tipoCultivoDesc;
	}
	public String getTipoOtros() {
		return tipoOtros;
	}
	public void setTipoOtros(String tipoOtros) {
		this.tipoOtros = tipoOtros;
	}
	public String getTipoOtrosDesc() {
		return tipoOtrosDesc;
	}
	public void setTipoOtrosDesc(String tipoOtrosDesc) {
		this.tipoOtrosDesc = tipoOtrosDesc;
	}
	public String getRefCatastral() {
		return refCatastral;
	}
	public void setRefCatastral(String refCatastral) {
		this.refCatastral = refCatastral;
	}
	public String getValorDeclarado() {
		return valorDeclarado;
	}
	public void setValorDeclarado(String valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}
	public String getExplotacion() {
		return explotacion;
	}
	public void setExplotacion(String explotacion) {
		this.explotacion = explotacion;
	}
	public String getExplotacionDesc() {
		return explotacionDesc;
	}
	public void setExplotacionDesc(String explotacionDesc) {
		this.explotacionDesc = explotacionDesc;
	}
}
