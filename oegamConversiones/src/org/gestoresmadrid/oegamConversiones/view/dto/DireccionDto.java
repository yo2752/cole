package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DireccionDto implements Serializable {

	private static final long serialVersionUID = -8136673903214528485L;

	private String bloque;

	private String codPostal;

	private String codPostalCorreos;

	private String escalera;

	private BigDecimal hm;

	private BigDecimal idDireccion;

	private String idTipoVia;

	private String tipoViaDescripcion;

	private BigDecimal km;

	private String letra;

	private String idMunicipio;

	private String idProvincia;

	private String nombreProvincia;

	private String nombreMunicipio;

	private String nombreVia;

	private String numero;
	
	private String numeroBis;

	private BigDecimal numLocal;

	private String planta;

	private String pueblo;

	private String puebloCorreos;

	private String puerta;

	private String viaSinEditar;

	private boolean principal;

	// Para la importacion
	private String idTipoDgt;

	private String municipio;

	private String puebloLit;

	private String via;

	private String asignarPrincipal;

	// Para el XML Corpme
	private String pais;
	
	private String regionExtranjera;
	
	private String portal;
	
	private String lugarUbicacion;

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getCodPostalCorreos() {
		return codPostalCorreos;
	}

	public void setCodPostalCorreos(String codPostalCorreos) {
		this.codPostalCorreos = codPostalCorreos;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public BigDecimal getHm() {
		return hm;
	}

	public void setHm(BigDecimal hm) {
		this.hm = hm;
	}

	public BigDecimal getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(BigDecimal idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public String getTipoViaDescripcion() {
		return tipoViaDescripcion;
	}

	public void setTipoViaDescripcion(String tipoViaDescripcion) {
		this.tipoViaDescripcion = tipoViaDescripcion;
	}

	public BigDecimal getKm() {
		return km;
	}

	public void setKm(BigDecimal km) {
		this.km = km;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
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

	public BigDecimal getNumLocal() {
		return numLocal;
	}

	public void setNumLocal(BigDecimal numLocal) {
		this.numLocal = numLocal;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPueblo() {
		return pueblo;
	}

	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}

	public String getPuebloCorreos() {
		return puebloCorreos;
	}

	public void setPuebloCorreos(String puebloCorreos) {
		this.puebloCorreos = puebloCorreos;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getViaSinEditar() {
		return viaSinEditar;
	}

	public void setViaSinEditar(String viaSinEditar) {
		this.viaSinEditar = viaSinEditar;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getIdTipoDgt() {
		return idTipoDgt;
	}

	public void setIdTipoDgt(String idTipoDgt) {
		this.idTipoDgt = idTipoDgt;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getPuebloLit() {
		return puebloLit;
	}

	public void setPuebloLit(String puebloLit) {
		this.puebloLit = puebloLit;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getAsignarPrincipal() {
		return asignarPrincipal;
	}

	public void setAsignarPrincipal(String asignarPrincipal) {
		this.asignarPrincipal = asignarPrincipal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getNumeroBis() {
		return numeroBis;
	}

	public void setNumeroBis(String numeroBis) {
		this.numeroBis = numeroBis;
	}

	public String getRegionExtranjera() {
		return regionExtranjera;
	}

	public void setRegionExtranjera(String regionExtranjera) {
		this.regionExtranjera = regionExtranjera;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public String getLugarUbicacion() {
		return lugarUbicacion;
	}

	public void setLugarUbicacion(String lugarUbicacion) {
		this.lugarUbicacion = lugarUbicacion;
	}

}