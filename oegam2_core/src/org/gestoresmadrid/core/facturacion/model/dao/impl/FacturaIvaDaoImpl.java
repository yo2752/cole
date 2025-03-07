package org.gestoresmadrid.core.facturacion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.facturacion.model.dao.FacturaIvaDao;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import hibernate.entities.facturacion.FacturaIva;

@Repository
public class FacturaIvaDaoImpl extends GenericDaoImplHibernate<FacturaIva> implements FacturaIvaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1709384952672081180L;

	@SuppressWarnings("unchecked")
	@Override
	public List<FacturaIva> listaIvas() {
		Criteria criteria = getCurrentSession().createCriteria(FacturaIva.class);
		criteria.addOrder(Order.asc("idIva"));
		return criteria.list();
	}
}

