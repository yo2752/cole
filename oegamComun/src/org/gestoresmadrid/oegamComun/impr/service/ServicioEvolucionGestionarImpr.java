package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;

import org.gestoresmadrid.core.impr.model.vo.EvolucionImprVO;

public interface ServicioEvolucionGestionarImpr extends Serializable{

	void guardarEvolucion(EvolucionImprVO evolucionVO);

}
