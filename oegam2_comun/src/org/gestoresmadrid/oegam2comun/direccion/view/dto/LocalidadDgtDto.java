package org.gestoresmadrid.oegam2comun.direccion.view.dto;

import java.io.Serializable;

public class LocalidadDgtDto implements Serializable {

	private static final long serialVersionUID = 3143611889856777097L;

	private String localidad;

	private String codigoPostal;

	private String municipio;

	private String provincia;

	private String codigoIne;

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
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

	public String getCodigoIne() {
		return codigoIne;
	}

	public void setCodigoIne(String codigoIne) {
		this.codigoIne = codigoIne;
	}
}