package org.gestoresmadrid.oegam2comun.pegatinas.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasStockDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloGestionPegatinasPaginatedImpl")
@Transactional(readOnly=true)
public class ModeloGestionPegatinasPaginatedImpl extends AbstractModelPagination{

	@Resource
	private PegatinasStockDao pegatinasStockDao;
	
	@Override
	protected GenericDao<?> getDao() {
		return pegatinasStockDao;
	}
	
}
