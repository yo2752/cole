package org.gestoresmadrid.oegam2comun.registradores.model.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.registradores.model.dao.SociedadCargoDao;
import org.gestoresmadrid.core.registradores.model.vo.SociedadCargoVO;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloSociedadCargoPaginated")
@Transactional(readOnly = true)
public class ModeloSociedadCargoPaginatedImpl extends AbstractModelPagination {

	@Resource
	private SociedadCargoDao sociedadCargoDao;

	@Override
	protected GenericDao<?> getDao() {
		return sociedadCargoDao;
	}
	
	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(SociedadCargoVO.class, "personaCargo", "personaCargo", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	public SociedadCargoDao getSociedadCargoDao() {
		return sociedadCargoDao;
	}

	public void setSociedadCargoDao(SociedadCargoDao sociedadCargoDao) {
		this.sociedadCargoDao = sociedadCargoDao;
	}
}
