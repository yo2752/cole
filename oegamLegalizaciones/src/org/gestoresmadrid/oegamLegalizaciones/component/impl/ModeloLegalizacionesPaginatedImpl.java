package org.gestoresmadrid.oegamLegalizaciones.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.legalizacion.model.dao.LegalizacionDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloLegalizacionesPaginated")
@Transactional(readOnly = true)
public class ModeloLegalizacionesPaginatedImpl extends AbstractModelPagination {

	@Resource
	LegalizacionDao legalizacionDao;

	@Override
	protected GenericDao<?> getDao() {
		return legalizacionDao;
	}

}
