package org.gestoresmadrid.oegam2comun.estacionITV.view.bean;

import java.io.Serializable;

public class EstacionITV implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String provincia;
	private String municipio;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	

}
