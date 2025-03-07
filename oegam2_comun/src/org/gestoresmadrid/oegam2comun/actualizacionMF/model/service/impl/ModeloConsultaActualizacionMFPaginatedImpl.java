package org.gestoresmadrid.oegam2comun.actualizacionMF.model.service.impl;

import org.gestoresmadrid.core.actualizacionMF.model.dao.ActualizacionMFDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConsultaActualizacionMF")
@Transactional(readOnly=true)
public class ModeloConsultaActualizacionMFPaginatedImpl extends AbstractModelPagination {

	@Autowired
	ActualizacionMFDao actualizacionDAO;

	@Override
	protected GenericDao<?> getDao() {
		return actualizacionDAO;
	}

	public ActualizacionMFDao getActualizacionDAO() {
		return actualizacionDAO;
	}

	public void setActualizacionDAO(ActualizacionMFDao actualizacionDAO) {
		this.actualizacionDAO = actualizacionDAO;
	}

}