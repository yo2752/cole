package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.CriterioConstruccionDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CriterioConstruccionVO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CriterioConstruccionDaoImpl extends GenericDaoImplHibernate<CriterioConstruccionVO> implements CriterioConstruccionDao {

	private static final long serialVersionUID = 7000399064014796238L;

	@Override
	public List<CriterioConstruccionVO> getCriterioConstruccion(String... criteriosConstruccion) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(CriterioConstruccionVO.class);
		if (criteriosConstruccion != null && criteriosConstruccion.length > 0) {
			criteria.add(Restrictions.in("idCriterioConstruccion", criteriosConstruccion));
		}

		criteria.addOrder(Order.asc("idCriterioConstruccion"));

		@SuppressWarnings("unchecked")
		List<CriterioConstruccionVO> lista = (List<CriterioConstruccionVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	@Override
	public CriterioConstruccionVO getCriteriosConstruccion(String idCriterioConstruccion) {
		Criteria criteria = getCurrentSession().createCriteria(CriterioConstruccionVO.class);
		criteria.add(Restrictions.eq("idCriterioConstruccion", idCriterioConstruccion));
		criteria.createAlias("claveMatriculacion", "claveMatriculacion", CriteriaSpecification.LEFT_JOIN);

		@SuppressWarnings("unchecked")
		List<CriterioConstruccionVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}

		return null;
	}
}