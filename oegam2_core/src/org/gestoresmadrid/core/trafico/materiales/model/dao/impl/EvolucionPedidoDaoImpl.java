package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.EvolucionPedidoDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionPedidoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionPedidoDaoImpl extends GenericDaoImplHibernate<EvolucionPedidoVO> implements EvolucionPedidoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6454625899340629825L;

	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionPedidoVO> findByPedidoId(Long pedidoId) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionPedidoVO.class, "evolucionPedido");
		criteria.createAlias("evolucionPedido.pedidoVO", "pedido");
		criteria.add(Restrictions.eq("pedido.pedidoId", pedidoId));
		return criteria.list();
	}

}
