package org.gestoresmadrid.oegamComun.trafico.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;

public interface ServicioPersistenciaEvoTramiteTrafico extends Serializable{

	void guardar(EvolucionTramiteTraficoVO evolucionTramiteTraficoVO);

}
