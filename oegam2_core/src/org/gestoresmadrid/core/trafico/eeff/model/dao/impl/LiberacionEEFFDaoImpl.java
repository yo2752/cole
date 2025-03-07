package org.gestoresmadrid.core.trafico.eeff.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.eeff.model.dao.LiberacionEEFFDao;
import org.gestoresmadrid.core.trafico.eeff.model.vo.LiberacionEEFFVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LiberacionEEFFDaoImpl extends GenericDaoImplHibernate<LiberacionEEFFVO> implements LiberacionEEFFDao{

	private static final long serialVersionUID = -7532388482337635741L;
	
	@Override
	public LiberacionEEFFVO getLiberacionEEFFPorExpediente(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(LiberacionEEFFVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		return (LiberacionEEFFVO) criteria.uniqueResult();
	}

}
