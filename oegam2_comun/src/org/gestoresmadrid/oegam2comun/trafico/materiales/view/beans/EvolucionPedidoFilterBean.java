package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionPedidoFilterBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 967447020472679357L;
	
	@FilterSimpleExpression(name = "pedidoVO.pedidoId") 
	private Long pedidoId;

	@FilterSimpleExpression(name = "inventarioId") 
	private Long pedidoInvId;

	public Long getPedidoInvId() {
		return pedidoInvId;
	}

	public void setPedidoInvId(Long pedidoInvId) {
		this.pedidoInvId = pedidoInvId;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	
}
