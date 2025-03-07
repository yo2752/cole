package org.gestoresmadrid.oegamCreditos.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.historicocreditos.model.dao.CreditoFacturadoDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloCreditoFacturadoPaginated")
@Transactional(readOnly = true)
public class ModeloCreditoFacturadoPaginatedImpl extends AbstractModelPagination {

	@Resource
	private CreditoFacturadoDao creditoFacturadoDao;

	@Override
	protected GenericDao<?> getDao() {
		return creditoFacturadoDao;
	}

}
