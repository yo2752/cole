package org.gestoresmadrid.oegam2comun.consulta.tramite.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean.EvolucionConsultaTramiteTraficoBean;

public interface ServicioEvolConsultaTramiteTrafico extends Serializable {

	List<EvolucionConsultaTramiteTraficoBean> convertirListaEnBeanPantalla(List<EvolucionTramiteTraficoVO> list);

}
