package org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.view.bean.ResultadoConsultaIntervinienteTrafBean;

public interface ServicioConsultaInterviniente extends Serializable{

	ResultadoConsultaIntervinienteTrafBean eliminarIntervinientes(String codSeleccionados);

	List<IntervinienteTraficoVO> convertirListaEnBeanPantalla(List<IntervinienteTraficoVO> list);

}
