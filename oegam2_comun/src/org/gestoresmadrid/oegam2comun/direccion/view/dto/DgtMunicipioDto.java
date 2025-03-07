package org.gestoresmadrid.oegam2comun.direccion.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DgtMunicipioDto implements Serializable {

	private static final long serialVersionUID = -4594264223664442753L;

	private BigDecimal idDgtMunicipio;

	private String codigoPostal;

	private String codigoIne;

	private String municipio;

	private String sinValor;

	private DgtProvinciaDto dgtProvincia;

	public BigDecimal getIdDgtMunicipio() {
		return idDgtMunicipio;
	}

	public void setIdDgtMunicipio(BigDecimal idDgtMunicipio) {
		this.idDgtMunicipio = idDgtMunicipio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCodigoIne() {
		return codigoIne;
	}

	public void setCodigoIne(String codigoIne) {
		this.codigoIne = codigoIne;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getSinValor() {
		return sinValor;
	}

	public void setSinValor(String sinValor) {
		this.sinValor = sinValor;
	}

	public DgtProvinciaDto getDgtProvincia() {
		return dgtProvincia;
	}

	public void setDgtProvincia(DgtProvinciaDto dgtProvincia) {
		this.dgtProvincia = dgtProvincia;
	}
}