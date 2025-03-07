package org.gestoresmadrid.oegam2comun.registradores.model.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.registradores.model.dao.IntervinienteRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.IntervinienteRegistroVO;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloFinanciadoresPaginated")
@Transactional(readOnly = true)
public class ModeloFinanciadoresPaginatedImpl extends AbstractModelPagination {

	@Resource
	private IntervinienteRegistroDao intervinienteRegistroDao;

	@Override
	protected GenericDao<?> getDao() {
		return intervinienteRegistroDao;
	}
	
	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(IntervinienteRegistroVO.class, "persona", "persona", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	public IntervinienteRegistroDao getTramiteRegistroDao() {
		return intervinienteRegistroDao;
	}

	public void setTramiteRegistroDao(IntervinienteRegistroDao intervinienteRegistroDao) {
		this.intervinienteRegistroDao = intervinienteRegistroDao;
	}
}
