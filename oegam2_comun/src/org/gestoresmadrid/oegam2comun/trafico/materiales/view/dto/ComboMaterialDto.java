package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;

public class ComboMaterialDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7103919857637157451L;
	
	private Long materialId;
	private String nombre;
	
	
	public ComboMaterialDto(Long materialId, String nombre) {
		this.materialId = materialId;
		this.nombre = nombre;
	}
	
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
