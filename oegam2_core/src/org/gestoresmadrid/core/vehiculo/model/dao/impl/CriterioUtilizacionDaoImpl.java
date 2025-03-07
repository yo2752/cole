package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.CriterioUtilizacionDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CriterioUtilizacionVO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CriterioUtilizacionDaoImpl extends GenericDaoImplHibernate<CriterioUtilizacionVO> implements CriterioUtilizacionDao {

	private static final long serialVersionUID = 4821296514760583527L;

	@Override
	public List<CriterioUtilizacionVO> getCriterioUtilizacion(String... criterioUtilizacion) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(CriterioUtilizacionVO.class);
		if (criterioUtilizacion != null && criterioUtilizacion.length > 0) {
			criteria.add(Restrictions.in("idCriterioUtilizacion", criterioUtilizacion));
		}

		criteria.addOrder(Order.asc("idCriterioUtilizacion"));

		List<CriterioUtilizacionVO> lista = (List<CriterioUtilizacionVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}