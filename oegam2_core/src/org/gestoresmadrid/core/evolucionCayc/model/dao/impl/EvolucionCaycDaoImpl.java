package org.gestoresmadrid.core.evolucionCayc.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.evolucionCayc.model.dao.EvolucionCaycDao;
import org.gestoresmadrid.core.evolucionCayc.model.vo.EvolucionCaycVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionCaycDaoImpl extends GenericDaoImplHibernate<EvolucionCaycVO> implements EvolucionCaycDao {

	private static final long serialVersionUID = -4400054138762515875L;

	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionCaycVO> getListaEvolucionPorNump(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionCaycVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		return criteria.list();
	}

}
