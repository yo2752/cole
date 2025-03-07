package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;

public class MaterialConsumoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5584381817170997705L;

	private String tipoMaterial;
	private String material;
	private Long   consumo;
	
	public String getTipoMaterial() {
		return tipoMaterial;
	}
	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Long getConsumo() {
		return consumo;
	}
	public void setConsumo(Long consumo) {
		this.consumo = consumo;
	}
}
