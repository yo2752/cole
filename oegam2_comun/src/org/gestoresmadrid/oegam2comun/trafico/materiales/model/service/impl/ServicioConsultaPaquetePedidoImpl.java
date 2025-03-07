package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.PedPaqueteDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedPaquete;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedPaqueteVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPaquetePedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.PaquetePedidoResultadosBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioConsultaPaquetePedidoImpl implements ServicioConsultaPaquetePedido {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8242797808481574897L;

	@Resource PedPaqueteDao pedPaqueteDao;
	
	@Autowired Conversor conversor;
	

	@Override
	public List<PaquetePedidoResultadosBean> convertirListaEnBeanPantalla(List<PedPaqueteVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<PaquetePedidoResultadosBean> listaBean = new ArrayList<PaquetePedidoResultadosBean>();
			
			for (PedPaqueteVO pedPaqueteVO : lista) {
				PaquetePedidoResultadosBean paquetePedidoResultadosBean = conversor.transform(pedPaqueteVO, PaquetePedidoResultadosBean.class);
				
				String literalEstado = EstadoPedPaquete.convertirTexto(pedPaqueteVO.getEstado());
				paquetePedidoResultadosBean.setEstadoStr(literalEstado);
				
				listaBean.add(paquetePedidoResultadosBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly=true)
	public Long obtenerNumeroPaquetesPedido(Long pedidoId) {
		Long numPaquetesPedido = pedPaqueteDao.getNumPedPaquetes(pedidoId);
		return numPaquetesPedido;
	}
	
	

}
