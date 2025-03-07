package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

public class RequestIncidencia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2912146389217341889L;
	
	private String material;
	private String numSerie;
	private String observaciones;
	private String tipo;
	private String delegacion_id;
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDelegacion_id() {
		return delegacion_id;
	}
	public void setDelegacion_id(String delegacion_id) {
		this.delegacion_id = delegacion_id;
	}
}
