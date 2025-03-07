package org.gestoresmadrid.core.bien.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.bien.model.dao.UnidadMetricaDao;
import org.gestoresmadrid.core.bien.model.vo.UnidadMetricaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class UnidadMetricaDaoImpl extends GenericDaoImplHibernate<UnidadMetricaVO> implements UnidadMetricaDao{

	private static final long serialVersionUID = 3263312325210592397L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadMetricaVO> getListaUnidadesMetricas() {
		Criteria criteria = getCurrentSession().createCriteria(UnidadMetricaVO.class);
		return criteria.list();
	}

}
