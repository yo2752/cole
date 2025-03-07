package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DetallePedidoAction extends ActionBase {
	
	private static final long serialVersionUID = 2058263958118900874L;

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(DetallePedidoAction.class);

	private static final String SUCCESS = "success";
	private static final String MODIFY = "modify";

	@Autowired private ServicioConsultaPedidos servicioConsultaPedidos;

	private Long		pedido;
	private PedidoDto	pedidoDto;
	private String		accionPedido;
	private String		materiales; 

	public String getMateriales() {
		return materiales;
	}

	public void setMateriales(String materiales) {
		this.materiales = materiales;
	}

	public PedidoDto getPedidoDto() {
		return pedidoDto;
	}

	public void setPedidoDto(PedidoDto pedidoDto) {
		this.pedidoDto = pedidoDto;
	}

	public String cargar() {
		accionPedido = "consulta";
		this.setPedidoDto(servicioConsultaPedidos.getPedidoDto(pedido));

		if ("Iniciado".equals(EstadoPedido.convertirTexto(pedidoDto.getEstado()))) {
			return MODIFY;
		} else {
			return SUCCESS;
		}
	}

	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}

	public String getAccionPedido() {
		return accionPedido;
	}

	public void setAccionPedido(String accionPedido) {
		this.accionPedido = accionPedido;
	}

}