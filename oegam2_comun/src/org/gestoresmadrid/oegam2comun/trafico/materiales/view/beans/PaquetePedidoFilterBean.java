package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class PaquetePedidoFilterBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 967447020472679357L;
	
	@FilterSimpleExpression(name = "pedidoVO.pedidoId") 
	private Long pedidoId;

	@FilterSimpleExpression(name = "pedPaqueteId") 
	private Long pedPaqueteId;

	@FilterSimpleExpression(name = "estado") 
	private Long estado;

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Long getPedPaqueteId() {
		return pedPaqueteId;
	}

	public void setPedPaqueteId(Long pedPaqueteId) {
		this.pedPaqueteId = pedPaqueteId;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	
}
