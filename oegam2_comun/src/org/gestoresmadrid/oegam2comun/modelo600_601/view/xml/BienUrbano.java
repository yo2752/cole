package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"caracter","transmision","tipo","tipoDesc","via","viaDesc","nombreVia","numero","dupTrip"
		,"escalera","planta","puerta","numLocal","provincia","provinciaDesc","municipio","municipioDesc"
		,"codigoPostal","superfConstruida","anoConstruccion","situacion","situacionDesc","refCatastral"
		,"arrendamiento","anoContrato","vpo","descalificado","viviendaHabitual","precioMaximoVenta","valorDeclarado"})
@XmlRootElement(name = "bien_urbano")
public class BienUrbano {
	@XmlElement(name = "Caracter", required = true)
	protected String caracter;
	@XmlElement(name = "Transmision", required = true)
	protected String transmision;
	@XmlElement(name = "tipo", required = true)
	protected String tipo;
	@XmlElement(name = "tipoDesc", required = true)
	protected String tipoDesc;
	@XmlElement(name = "via", required = true)
	protected String via;
	@XmlElement(name = "viaDesc", required = true)
	protected String viaDesc;
	@XmlElement(name = "NombreVia", required = true)
	protected String nombreVia;
	@XmlElement(name = "Numero", required = true)
	protected String numero;
	@XmlElement(name = "DupTrip", required = true)
	protected String dupTrip;
	@XmlElement(name = "Escalera", required = true)
	protected String escalera;
	@XmlElement(name = "Planta", required = true)
	protected String planta;
	@XmlElement(name = "Puerta", required = true)
	protected String puerta;
	@XmlElement(name = "NumLocal", required = true)
	protected String numLocal;
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
	@XmlElement(name = "SuperficieConstruida", required = true)
	protected String superfConstruida;
	@XmlElement(name = "anoConstruccion", required = true)
	protected String anoConstruccion;
	@XmlElement(name = "Situacion", required = true)
	protected String situacion;
	@XmlElement(name = "SituacionDesc", required = true)
	protected String situacionDesc;
	@XmlElement(name = "ReferenciaCatastral", required = true)
	protected String refCatastral;
	@XmlElement(name = "Arrendamiento", required = true)
	protected String arrendamiento;
	@XmlElement(name = "AnoContrato", required = true)
	protected String anoContrato;
	@XmlElement(name = "VPO", required = true)
	protected String vpo;
	@XmlElement(name = "Descalificado", required = true)
	protected String descalificado;
	@XmlElement(name = "ViviendaHabitual", required = true)
	protected String viviendaHabitual;
	@XmlElement(name = "PrecioMaximoVenta", required = true)
	protected String precioMaximoVenta;
	@XmlElement(name = "ValorDeclarado", required = true)
	protected String valorDeclarado;

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
	public String getTipoDesc() {
		return tipoDesc;
	}
	public void setTipoDesc(String tipoDesc) {
		this.tipoDesc = tipoDesc;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getViaDesc() {
		return viaDesc;
	}
	public void setViaDesc(String viaDesc) {
		this.viaDesc = viaDesc;
	}
	public String getNombreVia() {
		return nombreVia;
	}
	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDupTrip() {
		return dupTrip;
	}
	public void setDupTrip(String dupTrip) {
		this.dupTrip = dupTrip;
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
	public String getPuerta() {
		return puerta;
	}
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}
	public String getNumLocal() {
		return numLocal;
	}
	public void setNumLocal(String numLocal) {
		this.numLocal = numLocal;
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
	public String getSuperfConstruida() {
		return superfConstruida;
	}
	public void setSuperfConstruida(String superfConstruida) {
		this.superfConstruida = superfConstruida;
	}
	public String getAnoConstruccion() {
		return anoConstruccion;
	}
	public void setAnoConstruccion(String anoConstruccion) {
		this.anoConstruccion = anoConstruccion;
	}
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	public String getSituacionDesc() {
		return situacionDesc;
	}
	public void setSituacionDesc(String situacionDesc) {
		this.situacionDesc = situacionDesc;
	}
	public String getRefCatastral() {
		return refCatastral;
	}
	public void setRefCatastral(String refCatastral) {
		this.refCatastral = refCatastral;
	}
	public String getArrendamiento() {
		return arrendamiento;
	}
	public void setArrendamiento(String arrendamiento) {
		this.arrendamiento = arrendamiento;
	}
	public String getAnoContrato() {
		return anoContrato;
	}
	public void setAnoContrato(String anoContrato) {
		this.anoContrato = anoContrato;
	}
	public String getVpo() {
		return vpo;
	}
	public void setVpo(String vpo) {
		this.vpo = vpo;
	}
	public String getDescalificado() {
		return descalificado;
	}
	public void setDescalificado(String descalificado) {
		this.descalificado = descalificado;
	}
	public String getViviendaHabitual() {
		return viviendaHabitual;
	}
	public void setViviendaHabitual(String viviendaHabitual) {
		this.viviendaHabitual = viviendaHabitual;
	}
	public String getPrecioMaximoVenta() {
		return precioMaximoVenta;
	}
	public void setPrecioMaximoVenta(String precioMaximoVenta) {
		this.precioMaximoVenta = precioMaximoVenta;
	}
	public String getValorDeclarado() {
		return valorDeclarado;
	}
	public void setValorDeclarado(String valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
