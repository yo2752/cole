package org.gestoresmadrid.oegam2comun.bien.view.dto;

import java.io.Serializable;

public class UnidadMetricaDto implements Serializable{
	
	private static final long serialVersionUID = 7672898646226366916L;
	
	private String unidadMetrica;
	private String descunidad;
	
	public String getUnidadMetrica() {
		return unidadMetrica;
	}
	public void setUnidadMetrica(String unidadMetrica) {
		this.unidadMetrica = unidadMetrica;
	}
	public String getDescunidad() {
		return descunidad;
	}
	public void setDescunidad(String descunidad) {
		this.descunidad = descunidad;
	}
	
	

}
