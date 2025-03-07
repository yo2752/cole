package org.gestoresmadrid.oegam2comun.ficheros.temporales.model.component.impl;

import org.gestoresmadrid.core.ficheros.temporales.model.dao.FicherosTemporalesDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloFicherosTemporalesPaginated")
@Transactional(readOnly = true)
public class ModeloFicherosTemporalesPaginatedImpl extends AbstractModelPagination {

	@Autowired
	private FicherosTemporalesDao ficherosTemporalesDao;

	@Override
	protected GenericDao<?> getDao() {
		return ficherosTemporalesDao;
	}

}