package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.MarcaCamDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaCamVO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MarcaCamDaoImpl extends GenericDaoImplHibernate<MarcaCamVO> implements MarcaCamDao {

	private static final long serialVersionUID = 305625480999857754L;

	@Override
	public List<MarcaCamVO> listaMarcaCam(String tipoVehiculo, String codigoMarca, Date fechaDesde) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(MarcaCamVO.class);
		if (tipoVehiculo != null && !tipoVehiculo.isEmpty()) {
			criteria.add(Restrictions.eq("id.tipVehi", tipoVehiculo));
		}

		if (codigoMarca != null && !codigoMarca.isEmpty()) {
			criteria.add(Restrictions.eq("id.cdMarca", codigoMarca));
		}

		if (fechaDesde != null) {
			criteria.add(Restrictions.eq("id.fecDesde", fechaDesde));
		}

		criteria.addOrder(Order.asc("dsMarca"));

		@SuppressWarnings("unchecked")
		List<MarcaCamVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}