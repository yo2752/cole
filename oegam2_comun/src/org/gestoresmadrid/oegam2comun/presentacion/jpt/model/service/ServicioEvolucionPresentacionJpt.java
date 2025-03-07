package org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service;

import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.EvolucionPresentacionJptPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionPresentacionJptVO;
import org.gestoresmadrid.oegam2comun.evolucionJPT.EvolucionPresentacionJptDTO;

public interface ServicioEvolucionPresentacionJpt {

	EvolucionPresentacionJptPK guardarEvolucion(EvolucionPresentacionJptVO evolucion);
	
	List<EvolucionPresentacionJptDTO> mostrarEvolucionExpediente(String numExpediente);
}
