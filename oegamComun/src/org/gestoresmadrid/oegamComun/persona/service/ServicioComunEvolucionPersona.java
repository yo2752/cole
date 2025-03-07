package org.gestoresmadrid.oegamComun.persona.service;

import java.io.Serializable;

import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;

public interface ServicioComunEvolucionPersona extends Serializable {

	void guardarEvolucionPersona(EvolucionPersonaVO evolucion);
}
