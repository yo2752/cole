package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;

public interface PedidoDao extends GenericDao<PedidoVO>, Serializable {
	PedidoVO findPedidoForJefaturaMateria(String jefatura, Long Materia);
	PedidoVO findPedidoByPK(Long pedidoId);
	PedidoVO findPedidoByInventario(String codigoInicial, String codigoFinal, String jefaturaProvincial);
	PedidoVO findPedidoByJefaturaMaterialSerial(String jefatura, Long materialId, String serial);
	PedidoVO findPedidoCompleto(Long pedidoId);
	PedidoVO findPedidoByInventarioId(Long inventarioId, boolean completo);

	PedidoVO findPedidoByJefaturaSerial(String serial, String jefaturaProvincial); 
}
