package org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.evolucionDocPrmDstvFicha.model.vo.EvolucionDocPrmDstvFichaVO;
import org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.view.bean.EvolucionDocPrmDstvFichaBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioEvolucionDocPrmDstvFicha extends Serializable{

	List<EvolucionDocPrmDstvFichaBean> convertirListaEnBeanPantallaConsulta(List<EvolucionDocPrmDstvFichaVO> lista);

	ResultadoPermisoDistintivoItvBean guardarEvolucion(String documento, Date fecha, String estadoAnt,
			String estadoNuevo, OperacionPrmDstvFicha operacion, String docId, BigDecimal idUsuario);

}
