package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.PedPaqueteDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedPaqueteVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PedPaqueteDaoImpl extends GenericDaoImplHibernate<PedPaqueteVO> implements PedPaqueteDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = -998858010670464966L;

	@Override
	public Long getNumPedPaquetes(Long pedidoId) {
		Criteria criteria = getCurrentSession().createCriteria(PedPaqueteVO.class, "pedPaquete");
		criteria.createAlias("pedPaquete.pedidoVO", "pedidoVO");
		
		criteria.add(Restrictions.eq("pedidoVO.pedidoId", pedidoId));
		criteria.setProjection(Projections.rowCount()); //;
		
		int numPaquetes = (int) criteria.uniqueResult();
		
		return new Long(numPaquetes);
	}

}
