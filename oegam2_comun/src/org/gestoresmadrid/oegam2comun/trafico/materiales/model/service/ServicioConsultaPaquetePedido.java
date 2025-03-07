package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.vo.PedPaqueteVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.PaquetePedidoResultadosBean;

public interface ServicioConsultaPaquetePedido extends Serializable {
	List<PaquetePedidoResultadosBean> convertirListaEnBeanPantalla(List<PedPaqueteVO> lista);
	Long obtenerNumeroPaquetesPedido(Long pedidoId);
}
