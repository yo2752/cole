package org.gestoresmadrid.oegamComun.persona.service.impl;

import org.gestoresmadrid.core.personas.model.dao.EvolucionPersonaDao;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunEvolucionPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioComunEvolucionPersonaImpl implements ServicioComunEvolucionPersona {

	private static final long serialVersionUID = 2561587211544342171L;

	@Autowired
	EvolucionPersonaDao evolucionDao;

	@Override
	@Transactional
	public void guardarEvolucionPersona(EvolucionPersonaVO evolucion) {
		evolucionDao.guardar(evolucion);
	}
}
