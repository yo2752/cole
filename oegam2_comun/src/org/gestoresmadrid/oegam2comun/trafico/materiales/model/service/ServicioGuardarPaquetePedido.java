package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;

import escrituras.beans.ResultBean;

public interface ServicioGuardarPaquetePedido extends Serializable {
	public ResultBean paquetizarPedido(PedidoVO pedidoVO);
}
