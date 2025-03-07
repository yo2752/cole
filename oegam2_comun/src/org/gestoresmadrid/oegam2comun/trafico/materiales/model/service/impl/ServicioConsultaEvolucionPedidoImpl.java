package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionPedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaEvolucionPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.EvolucionPedidoResultadosBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioConsultaEvolucionPedidoImpl implements ServicioConsultaEvolucionPedido {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8242797808481574897L;

	@Autowired
	private Conversor conversor;

	@Override
	public List<EvolucionPedidoResultadosBean> convertirListaEnBeanPantalla(List<EvolucionPedidoVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<EvolucionPedidoResultadosBean> listaBean = new ArrayList<EvolucionPedidoResultadosBean>();
			
			for (EvolucionPedidoVO evolucionPedidoVO : lista) {
				EvolucionPedidoResultadosBean evolucionPedidoResultadosBean = conversor.transform(evolucionPedidoVO, EvolucionPedidoResultadosBean.class);
				
				String estado = EstadoPedido.convertirEstadoLong(evolucionPedidoVO.getEstadoFinal());
				evolucionPedidoResultadosBean.setEstado(estado);
				
				String estadoInicial = EstadoPedido.convertirEstadoLong(evolucionPedidoVO.getEstadoInicial());
				evolucionPedidoResultadosBean.setEstadoInicial(estadoInicial);
				
				listaBean.add(evolucionPedidoResultadosBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

}
