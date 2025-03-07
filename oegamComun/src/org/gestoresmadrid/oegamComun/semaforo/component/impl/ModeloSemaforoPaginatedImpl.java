package org.gestoresmadrid.oegamComun.semaforo.component.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.semaforo.model.dao.SemaforoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloSemaforoPaginated")
@Transactional(readOnly = true)
public class ModeloSemaforoPaginatedImpl extends AbstractModelPagination {
	@Autowired
	SemaforoDao semaforoDao;

	@Override
	protected GenericDao<?> getDao() {
		return semaforoDao;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();

		/*listaAlias.add(new AliasQueryBean(SemaforoVO.class, "id.proceso", "proceso"));
		listaAlias.add(new AliasQueryBean(SemaforoVO.class, "id.nodo", "nodo"));*/

		return listaAlias;
	}

	public SemaforoDao getSemaforoDao() {
		return semaforoDao;
	}

	public void setSemaforoDao(SemaforoDao semaforoDao) {
		this.semaforoDao = semaforoDao;
	}

}