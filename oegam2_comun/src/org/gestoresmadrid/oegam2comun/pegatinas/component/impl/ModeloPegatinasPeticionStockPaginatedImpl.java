package org.gestoresmadrid.oegam2comun.pegatinas.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasStockHistoricoDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloPegatinasStockHistoricoPaginatedImpl")
@Transactional(readOnly=true)
public class ModeloPegatinasPeticionStockPaginatedImpl extends AbstractModelPagination{

	@Resource
	private PegatinasStockHistoricoDao pegatinasStockHistoricoDao;
	
	@Override
	protected GenericDao<?> getDao() {
		return pegatinasStockHistoricoDao;
	}
	
	public PegatinasStockHistoricoDao getPegatinasDao() {
		return pegatinasStockHistoricoDao;
	}

	public void setPegatinasDao(PegatinasStockHistoricoDao pegatinasStockHistoricoDao) {
		this.pegatinasStockHistoricoDao = pegatinasStockHistoricoDao;
	}

}
