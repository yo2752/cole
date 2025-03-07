package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.dao.EvolucionJustifProfesionalesDao;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloEvolucionJustifProfesionalesPaginated")
@Transactional(readOnly = true)
public class ModeloEvolucionJustifProfesionalesPaginatedImpl extends AbstractModelPagination {

	@Resource
	private EvolucionJustifProfesionalesDao evolucionJustifProfesionalesDao;

	@Autowired
	private Conversor conversor;

	@Override
	protected GenericDao<?> getDao() {
		return evolucionJustifProfesionalesDao;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> alias = new ArrayList<AliasQueryBean>();
		alias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
		return alias;
	}

	public EvolucionJustifProfesionalesDao getEvolucionJustifProfesionalesDao() {
		return evolucionJustifProfesionalesDao;
	}

	public void setEvolucionJustifProfesionalesDao(EvolucionJustifProfesionalesDao evolucionJustifProfesionalesDao) {
		this.evolucionJustifProfesionalesDao = evolucionJustifProfesionalesDao;
	}
}
