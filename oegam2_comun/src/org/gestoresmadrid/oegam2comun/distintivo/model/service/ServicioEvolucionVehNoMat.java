package org.gestoresmadrid.oegam2comun.distintivo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.vo.EvolucionVehNoMatVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.EvolucionVehNoMatrOegamBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;

public interface ServicioEvolucionVehNoMat extends Serializable {

	ResultadoDistintivoDgtBean guardarEvolucionVehiculo(Long idVehNoMatOegam, String matricula, BigDecimal idUsuario, String tipoOperacion, String estadoAnt, String estadoNuevo, Date fecha, String docId);

	List<EvolucionVehNoMatrOegamBean> convertirListaVOEnBeanPantalla(List<EvolucionVehNoMatVO> listaBBDD);
}
