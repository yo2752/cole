package org.gestoresmadrid.oegam2comun.registradores.model.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.personas.model.dao.PersonaDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloSociedadesPaginated")
@Transactional(readOnly = true)
public class ModeloSociedadesPaginatedImpl extends AbstractModelPagination {

	@Resource
	private PersonaDao personaDao;

	@Override
	protected GenericDao<?> getDao() {
		return personaDao;
	}

	public PersonaDao getPersonaDao() {
		return personaDao;
	}

	public void setPersonaDao(PersonaDao personaDao) {
		this.personaDao = personaDao;
	}
}
