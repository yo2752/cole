package org.gestoresmadrid.oegam2comun.impresion.masiva.model.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.impresion.masiva.model.dao.ImpresionMasivaDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloImpresionMasivaPaginated")
@Transactional(readOnly = true)
public class ModeloImpresionMasivaPaginatedImpl extends AbstractModelPagination {

	@Resource
	private ImpresionMasivaDao impresionMasivaDao;

	@Override
	protected GenericDao<?> getDao() {
		return impresionMasivaDao;
	}
}
