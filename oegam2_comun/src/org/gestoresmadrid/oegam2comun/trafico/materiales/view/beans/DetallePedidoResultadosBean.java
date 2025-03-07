package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;

public class DetallePedidoResultadosBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -209070034367614723L;
	
	private Long detallePedidoId;
	private Long pedidoId;
	private String     nombreMaterial;
	private Long unidades;
	
	public Long getDetallePedidoId() {
		return detallePedidoId;
	}
	public void setDetallePedidoId(Long detallePedidoId) {
		this.detallePedidoId = detallePedidoId;
	}
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getNombreMaterial() {
		return nombreMaterial;
	}
	public void setNombreMaterial(String nombreMaterial) {
		this.nombreMaterial = nombreMaterial;
	}
	public Long getUnidades() {
		return unidades;
	}
	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}
	
}
