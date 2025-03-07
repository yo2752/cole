package org.gestoresmadrid.oegamCreditos.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.general.model.dao.HistoricoCreditoDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConsultaHistCreditosPaginated")
@Transactional(readOnly = true)
public class ModeloConsultaHistCreditosPaginatedImpl extends AbstractModelPagination {

	@Resource
	private HistoricoCreditoDao historicoCreditoDao;

	@Override
	protected GenericDao<?> getDao() {
		return historicoCreditoDao;
	}
}
