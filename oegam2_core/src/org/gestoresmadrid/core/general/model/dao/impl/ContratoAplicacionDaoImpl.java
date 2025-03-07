package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.ContratoAplicacionDao;
import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ContratoAplicacionDaoImpl extends GenericDaoImplHibernate<ContratoAplicacionVO> implements ContratoAplicacionDao {

	private static final long serialVersionUID = 4363254783381634713L;

	@Override
	public List<ContratoAplicacionVO> getAplicacionesPorContrato(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoAplicacionVO.class);
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.add(Restrictions.like("id.codigoAplicacion", "OEGAM%"));
		criteria.addOrder(Order.asc("id.codigoAplicacion"));
		@SuppressWarnings("unchecked")
		List<ContratoAplicacionVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	@Override
	public List<String> getCodigosAplicacionPorContrato(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoAplicacionVO.class);
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.setProjection(Projections.distinct(Projections.property("id.codigoAplicacion")));
		@SuppressWarnings("unchecked")
		List<String> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}
}
