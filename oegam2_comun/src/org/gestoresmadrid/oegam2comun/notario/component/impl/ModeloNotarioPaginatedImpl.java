package org.gestoresmadrid.oegam2comun.notario.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.notario.model.dao.NotarioDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.oegam2comun.notario.model.service.ServicioNotario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloNotarioPaginatedImpl")
@Transactional(readOnly=true)
public class ModeloNotarioPaginatedImpl extends AbstractModelPagination{
	
	@Resource
	private NotarioDao notarioDao;
	
	@Autowired
	private ServicioNotario servicioNotario;

	@Override
	protected GenericDao<?> getDao() {
		return notarioDao;
	}

	public NotarioDao getNotarioDao() {
		return notarioDao;
	}

	public void setNotarioDao(NotarioDao notarioDao) {
		this.notarioDao = notarioDao;
	}

	public ServicioNotario getServicioNotario() {
		return servicioNotario;
	}

	public void setServicioNotario(ServicioNotario servicioNotario) {
		this.servicioNotario = servicioNotario;
	}
		

}
