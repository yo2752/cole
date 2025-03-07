package org.gestoresmadrid.oegam2comun.registradores.model.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.registradores.model.dao.EvolucionTramiteRegistroDao;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloEvolucionTramiteRegistroPaginated")
@Transactional(readOnly = true)
public class ModeloEvolucionTramiteRegistroPaginatedImpl extends AbstractModelPagination {

	@Resource
	private EvolucionTramiteRegistroDao evolucionTramiteRegistroDao;

	@Autowired
	private Conversor conversor;

	@Override
	protected GenericDao<?> getDao() {
		return evolucionTramiteRegistroDao;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	public EvolucionTramiteRegistroDao getEvolucionTramiteRegistroDao() {
		return evolucionTramiteRegistroDao;
	}

	public void setEvolucionTramiteRegistroDao(EvolucionTramiteRegistroDao evolucionTramiteRegistroDao) {
		this.evolucionTramiteRegistroDao = evolucionTramiteRegistroDao;
	}
}
