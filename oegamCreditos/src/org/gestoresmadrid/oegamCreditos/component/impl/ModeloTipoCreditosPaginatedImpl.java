package org.gestoresmadrid.oegamCreditos.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.general.model.dao.TipoCreditoDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloTipoCreditosPaginated")
@Transactional(readOnly = true)
public class ModeloTipoCreditosPaginatedImpl extends AbstractModelPagination {

	@Resource
	private TipoCreditoDao tipoCreditoDao;

	@Override
	protected GenericDao<?> getDao() {
		return tipoCreditoDao;
	}

}
