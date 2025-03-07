package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.DirectivaCeeDao;
import org.gestoresmadrid.core.vehiculo.model.vo.DirectivaCeeVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DirectivaCeeDaoImpl extends GenericDaoImplHibernate<DirectivaCeeVO> implements DirectivaCeeDao {

	private static final long serialVersionUID = 265013869138016663L;

	@SuppressWarnings("unchecked")
	@Override
	public List<DirectivaCeeVO> getListaDirectivasCee(String criterioConstruccion) {
		Criteria criteria = getCurrentSession().createCriteria(DirectivaCeeVO.class);
		if (criterioConstruccion != null && !criterioConstruccion.isEmpty()) {
			criteria.add(Restrictions.like("criterioConstruccion", "%"+criterioConstruccion+"%"));
		}
		criteria.addOrder(Order.asc("idDirectivaCEE"));
		return criteria.list();
	}

	@Override
	public DirectivaCeeVO getDirectivaPorId(String idDirectivaCEE) {
		Criteria criteria = getCurrentSession().createCriteria(DirectivaCeeVO.class);
		criteria.add(Restrictions.eq("idDirectivaCEE", idDirectivaCEE));
		return (DirectivaCeeVO) criteria.uniqueResult();
	}
}