package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import java.util.Date;

import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModifyPedidoAction extends ActionBase {

	private static final long serialVersionUID = -2702588195541584927L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModifyPedidoAction.class);

	private static final String MODIFY_PEDIDO = "modifyPedido";

	@Autowired ServicioConsultaPedidos	servicioConsultaPedidos;
	@Autowired ServicioGuardarPedido	servicioGuardarPedido;

	private Long		pedido;
	private PedidoDto	pedidoDto;
	private String		accionPedido;
	private String		materiales; 
	private boolean		permitirCambioEstado;

	public String modify() {
		log.info("Paso por modify");

		accionPedido = "modifica";
		this.setPedidoDto(servicioConsultaPedidos.getPedidoDto(pedido));

		this.setPermitirCambioEstado(pedidoDto.isPedidoPermisos());
		return MODIFY_PEDIDO;
	}

	public String guardar() {
		log.info("Paso por guardar modifcación");

		if (pedidoDto.getEstado() == null) {
			Long estado = EstadoPedido.convertirNombre(pedidoDto.getNomEstado());
			this.getPedidoDto().setEstado(estado);
		} else {
			EstadoPedido estado = EstadoPedido.convertir(pedidoDto.getEstado());
			this.getPedidoDto().setNomEstado(estado.name());
		}

		pedidoDto.setFecha(new Date());

		ResultBean result = servicioGuardarPedido.modifyPedido(pedidoDto);
		if (result.getError()) {
			addActionError(result.getMensaje());
		} else {
			addActionMessage(result.getMensaje());
			pedidoDto = (PedidoDto) result.getAttachment("pedidoDto");
		}

		if (pedidoDto.getEstado() > Long.valueOf(EstadoPedido.Iniciado.getValorEnum())) {
			accionPedido = "consulta";
		} else {
			accionPedido = "modifica";
		}

		return MODIFY_PEDIDO;
	}

	// Getters & Setters
	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}

	public PedidoDto getPedidoDto() {
		return pedidoDto;
	}

	public void setPedidoDto(PedidoDto pedidoDto) {
		this.pedidoDto = pedidoDto;
	}

	public String getAccionPedido() {
		return accionPedido;
	}

	public void setAccionPedido(String accionPedido) {
		this.accionPedido = accionPedido;
	}

	public String getMateriales() {
		return materiales;
	}

	public void setMateriales(String materiales) {
		this.materiales = materiales;
	}

	public boolean isPermitirCambioEstado() {
		return permitirCambioEstado;
	}

	public void setPermitirCambioEstado(boolean permitirCambioEstado) {
		this.permitirCambioEstado = permitirCambioEstado;
	}

}