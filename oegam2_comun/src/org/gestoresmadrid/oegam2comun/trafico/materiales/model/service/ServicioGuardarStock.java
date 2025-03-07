package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.MaterialStockDto;

import escrituras.beans.ResultBean;

public interface ServicioGuardarStock extends Serializable {
	ResultBean salvarStock(MaterialStockDto materialStockDto);
	ResultBean salvarStock(MaterialStockVO materialStocVO);

	public ResultBean actualizarStockPedido(PedidoVO pedidoVO);
}
