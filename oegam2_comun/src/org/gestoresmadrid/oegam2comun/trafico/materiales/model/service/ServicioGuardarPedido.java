package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;

import escrituras.beans.ResultBean;

public interface ServicioGuardarPedido extends Serializable {
	ResultBean salvarPedido(PedidoDto pedidoDto);
	ResultBean modifyPedido(PedidoDto pedidoDto);
	ResultBean confirmarPedido(PedidoDto pedidoDto);
	ResultBean solicitarPedidos(List<Long> pedidos);
	ResultBean entregarPedidos(List<Long> listadoPedidos);
	ResultBean salvarPedidoInventario(PedidoDto pedidoDto);
	ResultBean eliminarPedido(PedidoDto pedidoDto);
	ResultBean actualizarPedidoWithIdInventario(Long pedidoId, Long inventarioId);
	ResultBean actualizarSeriales(Long pedido, String serialIni, String serialFin);


	ResultBean salvarPedido(PedidoVO pedidoVO, ColaBean colaBean);

}
