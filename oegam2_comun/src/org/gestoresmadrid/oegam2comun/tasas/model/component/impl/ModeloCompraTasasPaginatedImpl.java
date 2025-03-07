package org.gestoresmadrid.oegam2comun.tasas.model.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.tasas.model.dao.CompraTasasDao;
import org.gestoresmadrid.oegam2comun.tasas.model.transformer.ResultTransformCompraTasas;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloCompraTasasPaginated")
@Transactional(readOnly = true)
public class ModeloCompraTasasPaginatedImpl extends AbstractModelPagination {

	@Resource
	private CompraTasasDao compraTasasDaoInt;

	@Override
	protected GenericDao<?> getDao() {
		return compraTasasDaoInt;
	}

	@Override
	protected BeanResultTransformPaginatedList crearTransformer() {
		return new ResultTransformCompraTasas();
	}

}
