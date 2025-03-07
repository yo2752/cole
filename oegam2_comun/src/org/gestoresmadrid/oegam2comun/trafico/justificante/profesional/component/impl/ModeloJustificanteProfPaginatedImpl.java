package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.dao.JustificanteProfDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloJustificanteProfPaginated")
@Transactional(readOnly = true)
public class ModeloJustificanteProfPaginatedImpl extends AbstractModelPagination {

	@Resource
	private JustificanteProfDao justificanteProfDao;

	@Override
	protected GenericDao<?> getDao() {
		return justificanteProfDao;
	}

	public JustificanteProfDao getJustificanteProfDao() {
		return justificanteProfDao;
	}

	public void setJustificanteProfDao(JustificanteProfDao justificanteProfDao) {
		this.justificanteProfDao = justificanteProfDao;
	}
}