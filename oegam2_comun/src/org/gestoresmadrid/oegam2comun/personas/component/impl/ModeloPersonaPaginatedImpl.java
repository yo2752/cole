package org.gestoresmadrid.oegam2comun.personas.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.personas.model.dao.PersonaDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloPersonaPaginated")
@Transactional(readOnly = true)
public class ModeloPersonaPaginatedImpl extends AbstractModelPagination {

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