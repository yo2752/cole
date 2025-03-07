package org.gestoresmadrid.oegam2comun.evolucionRemesa.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.evolucionRemesas.model.vo.EvolucionRemesasVO;
import org.gestoresmadrid.oegam2comun.evolucionRemesa.view.bean.EvolucionRemesasBean;

public interface ServicioEvolucionRemesas extends Serializable{

	List<EvolucionRemesasBean> convertirListaEnBeanPantalla(List<EvolucionRemesasVO> lista);

	EvolucionRemesasVO guardarEvolucionRemesa(BigDecimal numExpediente, BigDecimal estadoAnt, BigDecimal estadoNuevo, BigDecimal idUsuario);

}
