package org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service;

import org.gestoresmadrid.core.ivtmMatriculacion.model.dao.IvtmMatriculacionDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloModificacionIVTMPaginatedImpl")
@Transactional(readOnly = true)
public class ModeloModificacionIVTMPaginatedImpl extends AbstractModelPagination {

	@Autowired
	private IvtmMatriculacionDao modificacionIVTMDao;


	public IvtmMatriculacionDao getModificacionIVTMDao() {
		return modificacionIVTMDao;
	}


	public void setModificacionIVTMDao(IvtmMatriculacionDao modificacionIVTMDao) {
		this.modificacionIVTMDao = modificacionIVTMDao;
	}


	@Override
	protected GenericDao<?> getDao() {

		return modificacionIVTMDao;
	}
	
	

	

}
