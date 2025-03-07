package org.gestoresmadrid.oegam2comun.trafico.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.trafico.model.dao.EvolucionTramiteTraficoDao;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloEvolucionTramiteTraficoPaginated")
@Transactional(readOnly = true)
public class ModeloEvolucionTramiteTraficoPaginatedImpl extends AbstractModelPagination {

	@Resource
	private EvolucionTramiteTraficoDao evolucionTramiteTraficoDao;

	@Override
	protected GenericDao<?> getDao() {
		return evolucionTramiteTraficoDao;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	public EvolucionTramiteTraficoDao getEvolucionTramiteTraficoDao() {
		return evolucionTramiteTraficoDao;
	}

	public void setEvolucionTramiteTraficoDao(EvolucionTramiteTraficoDao evolucionTramiteTraficoDao) {
		this.evolucionTramiteTraficoDao = evolucionTramiteTraficoDao;
	}
}