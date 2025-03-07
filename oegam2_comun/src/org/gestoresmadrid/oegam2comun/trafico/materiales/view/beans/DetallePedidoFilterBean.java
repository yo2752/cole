package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class DetallePedidoFilterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8132776021764983265L;

	@FilterSimpleExpression(name = "pedidoId")
	private Long pedidoId;

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

}
