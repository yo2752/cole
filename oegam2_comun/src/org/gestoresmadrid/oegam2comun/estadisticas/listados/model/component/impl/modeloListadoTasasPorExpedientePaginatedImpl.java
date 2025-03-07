package org.gestoresmadrid.oegam2comun.estadisticas.listados.model.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.tasas.model.dao.EvolucionTasaDao;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloListadoTasasPorExpedientePaginated")
@Transactional(readOnly = true)
public class modeloListadoTasasPorExpedientePaginatedImpl extends AbstractModelPagination {

	@Resource
	private EvolucionTasaDao evolucionTasaDao;

	@Override
	protected GenericDao<?> getDao() {
		return evolucionTasaDao;
	}
	
	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(EvolucionTasaVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(EvolucionTasaVO.class, "tasa", "tasa", CriteriaSpecification.INNER_JOIN));
		return listaAlias;
	}

}
