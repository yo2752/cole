package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.EvolucionPedidoDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.PedidoDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionPedidoVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarEvolucionPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioGuardarEvolucionPedidosImpl implements ServicioGuardarEvolucionPedidos {

	/**
	 * 
	 */
	private static final long serialVersionUID = 326441613039008121L;
	
	@Resource private EvolucionPedidoDao evolucionPedidoDao;
	@Resource private PedidoDao pedidoDao;

	@Override
	@Transactional
	public Long anadirEvolucion(PedidoVO pedidoVO, EstadoPedido estadoOrigen) {
		EvolucionPedidoVO epVO = new EvolucionPedidoVO();
		
		PedidoVO pedido = pedidoDao.findPedidoByInventarioId(pedidoVO.getPedidoInvId(), true);
		
		epVO.setPedidoVO(pedido);
		epVO.setPedidoInvId(pedido.getPedidoInvId());
		epVO.setFecha(pedido.getFecha());
		epVO.setEstadoFinal(pedido.getEstado());
		if (estadoOrigen != null) {
			epVO.setEstadoInicial(new Long(estadoOrigen.getValorEnum()));
		}

		return (Long) evolucionPedidoDao.guardar(epVO);
	}

	@Override
	@Transactional
	public Long anadirEvolucion(PedidoDto pedidoDto, EstadoPedido estadoOrigen) {
		// TODO Auto-generated method stub
		EvolucionPedidoVO epVO = new EvolucionPedidoVO();
		
		PedidoVO pedido = pedidoDao.findPedidoCompleto(pedidoDto.getPedidoId());
		
		epVO.setPedidoVO(pedido);
		epVO.setFecha(pedido.getFecha());
		epVO.setEstadoFinal(pedido.getEstado());
		if (estadoOrigen != null) {
			epVO.setEstadoInicial(new Long(estadoOrigen.getValorEnum()));
		}

		return (Long) evolucionPedidoDao.guardar(epVO);
	}

	@Override
	@Transactional
	public void eliminarEvolucion(Long pedidoId) {
		List<EvolucionPedidoVO> evolucionesPedidoVO = evolucionPedidoDao.findByPedidoId(pedidoId);
		for(EvolucionPedidoVO item: evolucionesPedidoVO) {
			evolucionPedidoDao.borrar(item);
		}
		
	}

}
