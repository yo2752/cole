package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.ModeloCamDao;
import org.gestoresmadrid.core.vehiculo.model.vo.ModeloCamVO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ModeloCamDaoImpl extends GenericDaoImplHibernate<ModeloCamVO> implements ModeloCamDao {

	private static final long serialVersionUID = -8136874583318182976L;

	@Override
	public List<ModeloCamVO> listaModeloCam(String tipoVehiculo, Date fechaDesde, String codigoMarca, String codigoModelo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ModeloCamVO.class);
		if (tipoVehiculo != null && !tipoVehiculo.isEmpty()) {
			criteria.add(Restrictions.eq("id.tipVehi", tipoVehiculo));
		}
		if (fechaDesde != null) {
			criteria.add(Restrictions.eq("id.fecDesde", fechaDesde));
		}
		if (codigoMarca != null && !codigoMarca.isEmpty()) {
			criteria.add(Restrictions.eq("id.cdMarca", codigoMarca));
		}
		if (codigoModelo != null && !codigoModelo.isEmpty()) {
			criteria.add(Restrictions.eq("id.cdModVeh", codigoModelo));
		}

		criteria.addOrder(Order.asc("dsModVeh"));

		@SuppressWarnings("unchecked")
		List<ModeloCamVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}