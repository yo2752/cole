package org.gestoresmadrid.oegam2comun.trafico.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoFactDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloTramiteTraficoFactPaginated")
@Transactional(readOnly = true)
public class ModeloTramiteTraficoFactPaginatedImpl extends AbstractModelPagination {

	@Resource
	private TramiteTraficoFactDao tramiteTraficoFactDao;

	@Override
	protected GenericDao<?> getDao() {
		return tramiteTraficoFactDao;
	}

	public TramiteTraficoFactDao getTramiteTraficoFactDao() {
		return tramiteTraficoFactDao;
	}

	public void setTramiteTraficoFactDao(TramiteTraficoFactDao tramiteTraficoFactDao) {
		this.tramiteTraficoFactDao = tramiteTraficoFactDao;
	}
}
