package org.gestoresmadrid.oegam2comun.licenciasCam.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.licencias.model.dao.LcTramiteDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "licenciasPaginatedImpl")
@Transactional(readOnly = true)
public class LicenciasPaginatedImpl extends AbstractModelPagination {

	@Resource
	private LcTramiteDao lcTramiteDao;

	@Override
	protected GenericDao<?> getDao() {
		return lcTramiteDao;
	}

	public LcTramiteDao getLcTramiteDao() {
		return lcTramiteDao;
	}

	public void setLcTramiteDao(LcTramiteDao lcTramiteDao) {
		this.lcTramiteDao = lcTramiteDao;
	}

}
