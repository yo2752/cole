package org.gestoresmadrid.core.estacionITV.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.estacionITV.model.dao.EstacionITVDao;
import org.gestoresmadrid.core.estacionITV.model.vo.EstacionITVVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EstacionITVDaoImpl  extends GenericDaoImplHibernate<EstacionITVVO> implements EstacionITVDao{

	private static final long serialVersionUID = 1472033993387998489L;

	@Override
	public List<EstacionITVVO> getEstacionesItv(String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(EstacionITVVO.class);
		if (StringUtils.isNotBlank(idProvincia)) {
			criteria.add(Restrictions.sqlRestriction("SUBSTR(ESTACION_ITV, 0, 2) = ?", idProvincia, Hibernate.STRING));
		}
		criteria.addOrder(Order.asc("id"));

		@SuppressWarnings("unchecked")
		List<EstacionITVVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}
	
}
