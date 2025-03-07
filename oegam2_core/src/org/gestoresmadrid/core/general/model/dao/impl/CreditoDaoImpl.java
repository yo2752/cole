package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.CreditoDao;
import org.gestoresmadrid.core.general.model.vo.CreditoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CreditoDaoImpl extends GenericDaoImplHibernate<CreditoVO> implements CreditoDao {

	private static final long serialVersionUID = -2653675891666654909L;

	@Override
	public CreditoVO getCredito(String tipoCredito, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(CreditoVO.class);
		if (tipoCredito != null && !tipoCredito.isEmpty()) {
			criteria.add(Restrictions.eq("id.tipoCredito", tipoCredito));
		}
		if (idContrato != null) {
			criteria.add(Restrictions.eq("id.idContrato", idContrato));
		}

		@SuppressWarnings("unchecked")
		List<CreditoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	
	@Override
	public CreditoVO getCreditoConTipoCredito(String tipoCredito, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(CreditoVO.class);
		if (tipoCredito != null && !tipoCredito.isEmpty()) {
			criteria.add(Restrictions.eq("id.tipoCredito", tipoCredito));
		}
		if (idContrato != null) {
			criteria.add(Restrictions.eq("id.idContrato", idContrato));
		}
		criteria.createAlias("tipoCreditoVO", "tipoCredito", CriteriaSpecification.LEFT_JOIN);
		@SuppressWarnings("unchecked")
		List<CreditoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	
	@Override
	public List<CreditoVO> consultaCreditosDisponiblesPorColegiado(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(CreditoVO.class);
		criteria.createAlias("tipoCreditoVO", "tipoCredito", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.add(Restrictions.eq("tipoCredito.increDecre", "D"));
		criteria.addOrder(Order.asc("tipoCredito.tipoCredito"));
		@SuppressWarnings("unchecked")
		List<CreditoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}
	
}
