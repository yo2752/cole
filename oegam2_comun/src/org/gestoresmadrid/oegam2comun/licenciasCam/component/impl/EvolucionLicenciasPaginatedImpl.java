package org.gestoresmadrid.oegam2comun.licenciasCam.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.licencias.model.dao.LcEvolucionTramiteDao;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "evolucionLicenciasPaginated")
@Transactional(readOnly = true)
public class EvolucionLicenciasPaginatedImpl extends AbstractModelPagination {

	@Resource
	private LcEvolucionTramiteDao lcEvolucionTramiteDao;

	@Override
	protected GenericDao<?> getDao() {
		return lcEvolucionTramiteDao;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	public LcEvolucionTramiteDao getLcEvolucionTramiteDao() {
		return lcEvolucionTramiteDao;
	}

	public void setLcEvolucionTramiteDao(LcEvolucionTramiteDao lcEvolucionTramiteDao) {
		this.lcEvolucionTramiteDao = lcEvolucionTramiteDao;
	}

}
