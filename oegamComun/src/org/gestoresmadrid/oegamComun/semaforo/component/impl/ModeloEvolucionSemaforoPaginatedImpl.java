package org.gestoresmadrid.oegamComun.semaforo.component.impl;

import org.gestoresmadrid.core.evolucionSemaforo.model.dao.EvolucionSemaforoDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloEvolucionSemaforoPaginated")
@Transactional(readOnly = true)
public class ModeloEvolucionSemaforoPaginatedImpl extends AbstractModelPagination {
	@Autowired
	EvolucionSemaforoDao evolucionSemaforoDao;

	@Override
	protected GenericDao<?> getDao() {
		return evolucionSemaforoDao;
	}

	public EvolucionSemaforoDao getEvolucionSemaforoDao() {
		return evolucionSemaforoDao;
	}

	public void setEvolucionSemaforoDao(EvolucionSemaforoDao evolucionSemaforoDao) {
		this.evolucionSemaforoDao = evolucionSemaforoDao;
	}

}
