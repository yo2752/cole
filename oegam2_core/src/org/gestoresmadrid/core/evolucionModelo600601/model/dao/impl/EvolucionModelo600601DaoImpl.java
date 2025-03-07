package org.gestoresmadrid.core.evolucionModelo600601.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.evolucionModelo600601.model.dao.EvolucionModelo600601Dao;
import org.gestoresmadrid.core.evolucionModelo600601.model.vo.EvolucionModelo600_601VO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionModelo600601DaoImpl extends GenericDaoImplHibernate<EvolucionModelo600_601VO> implements EvolucionModelo600601Dao{

	private static final long serialVersionUID = 3140442338106644948L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionModelo600_601VO> getListaEvolucionPorNumExpediente(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionModelo600_601VO.class);
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		return criteria.list();
	}

}
