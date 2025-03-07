package org.gestoresmadrid.oegam2comun.trafico.testra.model;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.trafico.testra.model.dao.ConsultaTestraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConsultaTestraPaginatedImpl")
@Transactional(readOnly = true)
public class ModeloConsultaTestraPaginatedImpl extends AbstractModelPagination {

	@Autowired
	private ConsultaTestraDao consultaTestraDao;

	public ConsultaTestraDao getMatriculaTestraDao() {
		return consultaTestraDao;
	}

	public void setMatriculaTestraDao(ConsultaTestraDao matriculaTestraDao) {
		this.consultaTestraDao = matriculaTestraDao;
	}

	@Override
	protected GenericDao<?> getDao() {

		return consultaTestraDao;
	}

}
