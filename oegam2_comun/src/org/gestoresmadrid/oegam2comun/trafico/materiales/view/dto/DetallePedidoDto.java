package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;

public class DetallePedidoDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9066797565624629878L;
	
	private Long pedidoId;
	private Long materialId;
	private String material;
	private Long unidades;
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}

	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public Long getUnidades() {
		return unidades;
	}
	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	
}
