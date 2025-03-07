package org.gestoresmadrid.core.evolucionAtex5.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.evolucionAtex5.model.dao.EvolucionAtex5Dao;
import org.gestoresmadrid.core.evolucionAtex5.model.vo.EvolucionAtex5VO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionAtex5DaoImpl extends GenericDaoImplHibernate<EvolucionAtex5VO> implements EvolucionAtex5Dao{

	private static final long serialVersionUID = -7984722169937500400L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionAtex5VO> getListaEvolucionesPorNumExp(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionAtex5VO.class);
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		return criteria.list();
	}
}
