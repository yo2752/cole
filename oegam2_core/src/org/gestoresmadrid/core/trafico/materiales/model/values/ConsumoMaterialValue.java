package org.gestoresmadrid.core.trafico.materiales.model.values;

import java.io.Serializable;

public class ConsumoMaterialValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2660015124007076763L;
	
	private String  jefatura;
	private String  tipoMaterial;
	private String  nombreMaterial;
	private Integer consumo;
	
	public String getTipoMaterial() {
		return tipoMaterial;
	}
	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}
	public Integer getConsumo() {
		return consumo;
	}
	public void setConsumo(Integer consumo) {
		this.consumo = consumo;
	}
	public String getNombreMaterial() {
		return nombreMaterial;
	}
	public void setNombreMaterial(String nombreMaterial) {
		this.nombreMaterial = nombreMaterial;
	}
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

}
