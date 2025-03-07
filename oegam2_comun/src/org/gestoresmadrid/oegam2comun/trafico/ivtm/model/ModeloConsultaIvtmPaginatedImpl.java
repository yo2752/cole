package org.gestoresmadrid.oegam2comun.trafico.ivtm.model;

import org.gestoresmadrid.core.ivtmMatriculacion.model.dao.ConsultasIvtmDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConsultaIvtmPaginatedImpl")
@Transactional(readOnly = true)
public class ModeloConsultaIvtmPaginatedImpl extends AbstractModelPagination{

	@Autowired
	private ConsultasIvtmDao consultasIvtmDao;
			
	public ConsultasIvtmDao getConsultasIvtmDao() {
		return consultasIvtmDao;
	}

	public void setConsultasIvtmDao(ConsultasIvtmDao consultasIvtmDao) {
		this.consultasIvtmDao = consultasIvtmDao;
	}

	@Override
	protected GenericDao<?> getDao() {
		return consultasIvtmDao;		
	}

}
