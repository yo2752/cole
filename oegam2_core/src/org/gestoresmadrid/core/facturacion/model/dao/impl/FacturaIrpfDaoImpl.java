package org.gestoresmadrid.core.facturacion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.facturacion.model.dao.FacturaIrpfDao;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import hibernate.entities.facturacion.FacturaIrpf;

@Repository
public class FacturaIrpfDaoImpl extends GenericDaoImplHibernate<FacturaIrpf> implements FacturaIrpfDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1709384952672081180L;

	@SuppressWarnings("unchecked")
	@Override
	public List<FacturaIrpf> listaIrpfs() {
		Criteria criteria = getCurrentSession().createCriteria(FacturaIrpf.class);
		criteria.addOrder(Order.asc("idIrpf"));
		return criteria.list();
	}
}

