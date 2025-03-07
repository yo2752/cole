package org.gestoresmadrid.oegam2comun.solicitudPlacas.model.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.placasMatricula.model.dao.SolicitudPlacasDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "solicitudPlacasPaginated")
@Transactional(readOnly = true)
public class SolicitudPlacasPaginated extends AbstractModelPagination {

	@Resource
	private SolicitudPlacasDAO solicitudPlacasDao;

	@Override
	protected GenericDao<?> getDao() {
		return solicitudPlacasDao;
	}

}
