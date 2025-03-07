package org.gestoresmadrid.oegam2comun.pegatinas.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloImpresionPegatinasPaginatedImpl")
@Transactional(readOnly=true)
public class ModeloImpresionPegatinasPaginatedImpl extends AbstractModelPagination{

	@Resource
	private PegatinasDao pegatinasDao;
	
	@Override
	protected GenericDao<?> getDao() {
		return pegatinasDao;
	}
	
	public PegatinasDao getPegatinasDao() {
		return pegatinasDao;
	}

	public void setPegatinasDao(PegatinasDao pegatinasDao) {
		this.pegatinasDao = pegatinasDao;
	}

}
