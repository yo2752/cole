package org.gestoresmadrid.oegam2comun.evolucionModelo600601.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.evolucionModelo600601.model.vo.EvolucionModelo600_601VO;
import org.gestoresmadrid.oegam2comun.evolucionModelo600601.view.bean.EvolucionModelo600_601Bean;

import escrituras.beans.ResultBean;

public interface ServicioEvolucionModelo600_601 extends Serializable{

	List<EvolucionModelo600_601Bean> convertirListaEnBeanPantalla(List<EvolucionModelo600_601VO> list);

	EvolucionModelo600_601VO guardarEvolucionModelo(BigDecimal numExpediente, BigDecimal estadoAnt,	BigDecimal estadoNuevo, BigDecimal idUsuario);

	ResultBean eliminarEvolucionModelo(BigDecimal numExpediente);

}
