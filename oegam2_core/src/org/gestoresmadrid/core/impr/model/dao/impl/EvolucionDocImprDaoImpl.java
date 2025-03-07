package org.gestoresmadrid.core.impr.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.impr.model.dao.EvolucionDocImprDao;
import org.gestoresmadrid.core.impr.model.vo.EvolucionDocImprVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionDocImprDaoImpl  extends GenericDaoImplHibernate<EvolucionDocImprVO> implements EvolucionDocImprDao{

	private static final long serialVersionUID = 1976492193622839583L;

	@Override
	public List<EvolucionDocImprVO> obtenerEvolucionDocImpr(Long docId, Boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionDocImprVO.class);
		criteria.add(Restrictions.eq("docId", docId));
		if (completo) {
			criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("docImpr", "docImpr", CriteriaSpecification.LEFT_JOIN);
		}
		return criteria.list();
	}

}