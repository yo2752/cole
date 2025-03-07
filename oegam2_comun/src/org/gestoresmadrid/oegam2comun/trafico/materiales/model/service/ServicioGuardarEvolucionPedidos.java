package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;

public interface ServicioGuardarEvolucionPedidos extends Serializable {
	Long anadirEvolucion(PedidoVO pedidoVO, EstadoPedido estadoOrigen);
	Long anadirEvolucion(PedidoDto pedidoDto, EstadoPedido estadoOrigen);
	void eliminarEvolucion(Long pedidoId);

}
