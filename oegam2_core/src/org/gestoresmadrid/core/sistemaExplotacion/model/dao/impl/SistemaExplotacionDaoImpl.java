package org.gestoresmadrid.core.sistemaExplotacion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.sistemaExplotacion.model.dao.SistemaExplotacionDao;
import org.gestoresmadrid.core.sistemaExplotacion.model.vo.SistemaExplotacionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class SistemaExplotacionDaoImpl extends GenericDaoImplHibernate<SistemaExplotacionVO> implements SistemaExplotacionDao{

	private static final long serialVersionUID = 4095100041813665563L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SistemaExplotacionVO> getListaSistemaExplotacion() {
		Criteria criteria = getCurrentSession().createCriteria(SistemaExplotacionVO.class);
		return criteria.list();
	}

}
