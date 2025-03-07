package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionPedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.EvolucionPedidoResultadosBean;

public interface ServicioConsultaEvolucionPedido extends Serializable {
	public List<EvolucionPedidoResultadosBean> convertirListaEnBeanPantalla(List<EvolucionPedidoVO> lista);
}
