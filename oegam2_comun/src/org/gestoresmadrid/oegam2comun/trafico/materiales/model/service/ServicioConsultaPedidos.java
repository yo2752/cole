package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.PedidosResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.PedidoInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;

public interface ServicioConsultaPedidos extends Serializable {

	List<PedidosResultadosBean> convertirListaEnBeanPantalla(List<PedidoVO> lista);
	PedidoDto getPedidoDto(Long pedidoId);
	Long validadMaterial(String jefatura, Long materialId);
	PedidoVO obtenerPedidoInventario(PedidoDto pedidoDto);
	PedidoVO obtenerPedidoByJefaturaMaterialSerial(String jefatura, Long materialId, String serial);
	PedidoVO obtenerPedidoCompleto(Long pedidoId);
	PedidoVO obtenerPedidoForInvetarioId(Long inventarioId);
	PedidoVO obtenerPedidoForPrimaryKey(Long pedidoId);
	PedidoVO obtenerPedidoForSerialJefaturq(String serial, String jefatura);
	
	PedidoDto getDtoFromVO(PedidoVO pedidoVO);
	PedidoVO  getVOFromDto(PedidoDto pedidoDto);
	PedidoDto getDtoFromInfoRest(PedidoInfoRest pedidoInfoRest);
	PedidoVO  getVOFromInfoRest(PedidoInfoRest item);

	boolean isPedidoPermisosEntregado(PedidoVO pedidoVO);
	boolean isPedidoPermisos(PedidoVO pedidoVO);
}
