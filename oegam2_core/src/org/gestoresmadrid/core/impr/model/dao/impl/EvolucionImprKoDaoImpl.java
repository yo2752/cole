package org.gestoresmadrid.core.impr.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.impr.model.dao.EvolucionImprKoDao;
import org.gestoresmadrid.core.impr.model.vo.EvolucionImprKoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionImprKoDaoImpl extends GenericDaoImplHibernate<EvolucionImprKoVO> implements EvolucionImprKoDao {

	private static final long serialVersionUID = 3789911207522047141L;

	@Override
	public List<EvolucionImprKoVO> getListEvoImprKo(Long idImprKo) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionImprKoVO.class);
		criteria.add(Restrictions.eq("idImprKo", idImprKo));
		return criteria.list();
	}
}