package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ObtenerPedidoAjaxAction extends ActionBase {

	private static final long serialVersionUID = -4450960067950692287L;

	private static final String SUCCESS = "success";

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(ObtenerPedidoAjaxAction.class);

	@Autowired ServicioConsultaPedidos servicioConsultaPedidos;

	private Long pedido;

	public String detalle() {
		@SuppressWarnings("unused")
		PedidoDto pedidoDto = servicioConsultaPedidos.getPedidoDto(pedido);
		return SUCCESS;
	}

	// Getters & Setters

	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}

}