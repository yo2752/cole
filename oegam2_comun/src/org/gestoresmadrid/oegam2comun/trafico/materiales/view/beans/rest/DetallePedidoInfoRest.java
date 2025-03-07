package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

public class DetallePedidoInfoRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4043199291435935775L;
	
	private Long   id;
	private Long   material_id;
	private Long   pedido_id;
	private Long   unidades;
	private Double precio;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(Long material_id) {
		this.material_id = material_id;
	}
	public Long getPedido_id() {
		return pedido_id;
	}
	public void setPedido_id(Long pedido_id) {
		this.pedido_id = pedido_id;
	}
	public Long getUnidades() {
		return unidades;
	}
	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	
}
