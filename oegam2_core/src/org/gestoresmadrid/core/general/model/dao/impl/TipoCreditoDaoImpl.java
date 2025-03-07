package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.TipoCreditoDao;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoCreditoDaoImpl extends GenericDaoImplHibernate<TipoCreditoVO> implements TipoCreditoDao {

	private static final long serialVersionUID = -417178089016528193L;

	@Override
	public TipoCreditoVO getTipoCredito(String tipoCredito) {
		Criteria criteria = getCurrentSession().createCriteria(TipoCreditoVO.class);
		if (tipoCredito != null && !tipoCredito.isEmpty()) {
			criteria.add(Restrictions.eq("tipoCredito", tipoCredito));
		}
		criteria.add(Restrictions.eq("estado", BigDecimal.ONE));
		criteria.addOrder(Order.asc("ordenListado"));

		return (TipoCreditoVO) criteria.uniqueResult();
	}

	@Override
	public List<TipoCreditoVO> getTiposCreditos() {
		Criteria criteria = getCurrentSession().createCriteria(TipoCreditoVO.class);
		criteria.add(Restrictions.eq("estado", BigDecimal.ONE));
		criteria.addOrder(Order.asc("ordenListado"));
		@SuppressWarnings("unchecked")
		List<TipoCreditoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	@Override
	public List<TipoCreditoVO> getTiposCreditosIncrementales() {
		Criteria criteria = getCurrentSession().createCriteria(TipoCreditoVO.class);
		criteria.add(Restrictions.eq("estado", BigDecimal.ONE));
		criteria.add(Restrictions.eq("increDecre", "I"));
		criteria.addOrder(Order.asc("ordenListado"));
		@SuppressWarnings("unchecked")
		List<TipoCreditoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}
}
