package org.gestoresmadrid.oegam2comun.consultasTGate.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.consultasTGate.model.dao.ConsultasTGateDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConsultasTGatePaginated")
@Transactional(readOnly = true)
public class ModeloConsultasTGatePaginatedImpl extends AbstractModelPagination {

	@Resource
	private ConsultasTGateDao consultasTGateDao;

	@Override
	protected GenericDao<?> getDao() {
		return consultasTGateDao;
	}

	public ConsultasTGateDao getConsultasTGateDao() {
		return consultasTGateDao;
	}

	public void setConsultasTGateDao(ConsultasTGateDao consultasTGateDao) {
		this.consultasTGateDao = consultasTGateDao;
	}
}
