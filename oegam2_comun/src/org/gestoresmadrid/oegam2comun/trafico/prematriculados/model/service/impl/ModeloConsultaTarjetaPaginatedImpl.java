package org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.trafico.prematriculados.model.dao.VehiculoPrematriculadoDaoInt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloConsultaTarjetaPaginated")
@Transactional(readOnly=true)
public class ModeloConsultaTarjetaPaginatedImpl extends AbstractModelPagination {

	@Resource
	VehiculoPrematriculadoDaoInt vehiculoPrematriculado;
	
	@Override
	protected GenericDao<?> getDao() {
		return vehiculoPrematriculado;
	}

}
