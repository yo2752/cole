package org.gestoresmadrid.oegamSanciones.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.sancion.model.dao.SancionDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloSancionesPaginated")
@Transactional(readOnly = true)
public class ModeloSancionesPaginatedImpl extends AbstractModelPagination {

	@Resource
	SancionDao sancionDao;

	@Override
	protected GenericDao<?> getDao() {
		return sancionDao;
	}

}
