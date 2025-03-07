package org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.vo.EvolucionPrmDstvFichaVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.view.bean.EvolucionPrmDstvFichaBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioEvolucionPrmDstvFicha extends Serializable{

	ResultadoPermisoDistintivoItvBean guardarEvolucion(BigDecimal numExpediente, BigDecimal idUsuario,
			TipoDocumentoImprimirEnum tipoDocumento, OperacionPrmDstvFicha operacion, Date fecha, String estadoAnt,
			String estadoNuevo, String docId, String ip);

	List<EvolucionPrmDstvFichaBean> convertirListaEnBeanPantallaConsulta(List<EvolucionPrmDstvFichaVO> lista);

}
