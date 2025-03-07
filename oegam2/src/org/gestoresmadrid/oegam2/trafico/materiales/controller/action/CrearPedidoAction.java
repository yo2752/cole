package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarEvolucionPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class CrearPedidoAction extends ActionBase {

	private static final long serialVersionUID = 4745470136528617768L;

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(CrearPedidoAction.class);

	private static final String CREAR_PEDIDO = "crearPedido";
	private static final String PEDIDO_DTO = "pedidoDto";

	@Autowired ServicioGuardarPedido			servicioGuardarPedido;
	@Autowired ServicioConsultaPedidos			servicioConsultaPedidos;
	@Autowired ServicioGuardarEvolucionPedidos	servicioGuardarEvolucionPedidos;

	private Long		pedido;
	private PedidoDto	pedidoDto;
	private String		materiales;
	private String		accionPedido;
	private boolean		permitirCambioEstado;
	private boolean		pedidoEliminado;

	public String alta() {
		if (this.getPedidoDto() == null) {
			this.setPedidoDto(new PedidoDto());
		}

		if (pedido != null) {
			accionPedido = "modifica";
			this.setPedidoDto(servicioConsultaPedidos.getPedidoDto(pedido));
		} else {
			accionPedido = "crear";
		}

		this.setPedidoEliminado(false);
		return CREAR_PEDIDO;
	}

	public String guardar() {
		ResultBean result = servicioGuardarPedido.salvarPedido(pedidoDto);
		if (result.getError()) {
			addActionError(result.getMensaje());
		} else {
			addActionMessage(result.getMensaje());
			pedidoDto = (PedidoDto) result.getAttachment(PEDIDO_DTO);
			this.setPermitirCambioEstado(pedidoDto.isPedidoPermisos());

			EstadoPedido estadoOld = (EstadoPedido) result.getAttachment("estadoOld");
			Long newEvolution;
			if (estadoOld == null) {
				newEvolution = servicioGuardarEvolucionPedidos.anadirEvolucion(pedidoDto, null);
			} else {
				newEvolution = servicioGuardarEvolucionPedidos.anadirEvolucion(pedidoDto, estadoOld);
			}

			if (newEvolution == null) {
				result.setError(true);
				result.setMensaje("Error al crear evolucion del pedido");
				return CREAR_PEDIDO;
			}
		}

		accionPedido = "modifica";
		this.setPedidoEliminado(false);
		return CREAR_PEDIDO;
	}

	public String eliminar() {
		ResultBean result = servicioGuardarPedido.eliminarPedido(pedidoDto);
		if (result.getError()) {
			addActionError(result.getMensaje());
		} else {
			addActionMessage(result.getMensaje());
			pedidoDto = (PedidoDto) result.getAttachment(PEDIDO_DTO);
		}

		accionPedido = "consulta";
		this.setPedidoEliminado(true);
		return CREAR_PEDIDO;
	}

	public String confirmar() {
		Long estado = EstadoPedido.convertirNombre(pedidoDto.getNomEstado());
		pedidoDto.setEstado(estado);

		ResultBean result = servicioGuardarPedido.confirmarPedido(pedidoDto);

		if (result.getError()) {
			addActionError(result.getMensaje());
		} else {
			addActionMessage(result.getMensaje());
			pedidoDto = (PedidoDto) result.getAttachment(PEDIDO_DTO);
		}

		accionPedido = "consulta";
		this.setPedidoEliminado(false);
		return CREAR_PEDIDO;
	}

	// Getter & Setter
	public PedidoDto getPedidoDto() {
		return pedidoDto;
	}

	public void setPedidoDto(PedidoDto pedidoDto) {
		this.pedidoDto = pedidoDto;
	}

	public String getMateriales() {
		return materiales;
	}

	public void setMateriales(String materiales) {
		this.materiales = materiales;
	}

	public String getAccionPedido() {
		return accionPedido;
	}

	public void setAccionPedido(String accionPedido) {
		this.accionPedido = accionPedido;
	}

	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}

	public boolean isPedidoEliminado() {
		return pedidoEliminado;
	}

	public void setPedidoEliminado(boolean pedidoEliminado) {
		this.pedidoEliminado = pedidoEliminado;
	}

	public boolean isPermitirCambioEstado() {
		return permitirCambioEstado;
	}

	public void setPermitirCambioEstado(boolean permitirCambioEstado) {
		this.permitirCambioEstado = permitirCambioEstado;
	}

}