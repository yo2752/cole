package org.gestoresmadrid.oegam2comun.registradores.model.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.registradores.model.dao.MedioDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloMedioPaginated")
@Transactional(readOnly = true)
public class ModeloMedioPaginatedImpl extends AbstractModelPagination {

	@Resource
	private MedioDao medioDao;

	@Override
	protected GenericDao<?> getDao() {
		return medioDao;
	}

	public MedioDao getMedioDao() {
		return medioDao;
	}

	public void setMedioDao(MedioDao medioDao) {
		this.medioDao = medioDao;
	}
}
