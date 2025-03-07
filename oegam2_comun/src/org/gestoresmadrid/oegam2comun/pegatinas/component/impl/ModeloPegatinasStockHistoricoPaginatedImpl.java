package org.gestoresmadrid.oegam2comun.pegatinas.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasStockPeticionesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloPegatinasPeticionStockPaginatedImpl")
@Transactional(readOnly=true)
public class ModeloPegatinasStockHistoricoPaginatedImpl extends AbstractModelPagination{

	@Resource
	private PegatinasStockPeticionesDao pegatinasStockPeticionesDao;
	
	@Override
	protected GenericDao<?> getDao() {
		return pegatinasStockPeticionesDao;
	}
	
	public PegatinasStockPeticionesDao getPegatinasDao() {
		return pegatinasStockPeticionesDao;
	}

	public void setPegatinasDao(PegatinasStockPeticionesDao pegatinasStockPeticionesDao) {
		this.pegatinasStockPeticionesDao = pegatinasStockPeticionesDao;
	}

}
