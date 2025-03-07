package org.gestoresmadrid.core.codigoIae.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.codigoIae.model.dao.CodigoIAEDao;
import org.gestoresmadrid.core.codigoIae.model.vo.CodigoIAEVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository
public class CodigoIAEDaoImpl extends GenericDaoImplHibernate<CodigoIAEVO> implements CodigoIAEDao {


	private static final long serialVersionUID = 6012215548783375570L;

	@SuppressWarnings("unchecked")
	@Override
	public List<CodigoIAEVO> listaCodigosIAE() {
		Criteria criteria = getCurrentSession().createCriteria(CodigoIAEVO.class);
		criteria.addOrder(Order.asc("codigoIAE"));
		return criteria.list();
	}
}

