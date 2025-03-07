package org.gestoresmadrid.oegam2comun.registradores.model.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.registradores.model.dao.TramiteRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegistroVO;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloTramiteRegistroPaginated")
@Transactional(readOnly = true)
public class ModeloTramiteRegistroPaginatedImpl extends AbstractModelPagination {

	@Resource
	private TramiteRegistroDao tramiteRegistroDao;

	@Override
	protected GenericDao<?> getDao() {
		return tramiteRegistroDao;
	}
	
	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(TramiteRegistroVO.class, "sociedad", "sociedad", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(TramiteRegistroVO.class, "registro", "registro", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	public TramiteRegistroDao getTramiteRegistroDao() {
		return tramiteRegistroDao;
	}

	public void setTramiteRegistroDao(TramiteRegistroDao tramiteRegistroDao) {
		this.tramiteRegistroDao = tramiteRegistroDao;
	}
}
