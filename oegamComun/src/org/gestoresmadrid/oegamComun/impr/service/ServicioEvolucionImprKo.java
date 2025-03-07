package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;

import org.gestoresmadrid.core.impr.model.vo.EvolucionImprKoVO;

public interface ServicioEvolucionImprKo extends Serializable{

	void guardarEvolucion(EvolucionImprKoVO rellenarEvolucionImprKo);

}
