package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.TipoVehiculoCriteriosDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoCriteriosVO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoVehiculoCriteriosDaoImpl extends GenericDaoImplHibernate<TipoVehiculoCriteriosVO> implements TipoVehiculoCriteriosDao, Serializable {

	private static final long serialVersionUID = 1328693601644472193L;

	@Override
	public List<TipoVehiculoCriteriosVO> listaTipoVehiculoCriterios(String tipoVehiculo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TipoVehiculoCriteriosVO.class);
		if (tipoVehiculo != null && !tipoVehiculo.isEmpty()) {
			criteria.add(Restrictions.eq("id.tipoVehiculo", tipoVehiculo));
		}

		List<TipoVehiculoCriteriosVO> lista = (List<TipoVehiculoCriteriosVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<String> listaCriteriosConstruccion(String tipoVehiculo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TipoVehiculoCriteriosVO.class);
		if (tipoVehiculo != null && !tipoVehiculo.isEmpty()) {
			criteria.add(Restrictions.eq("id.tipoVehiculo", tipoVehiculo));
		}

		criteria.setProjection(Projections.distinct(Projections.property("id.criterioConstruccion")));

		List<String> lista = (List<String>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<String> listaCriteriosUtilizacion(String tipoVehiculo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TipoVehiculoCriteriosVO.class);
		if (tipoVehiculo != null && !tipoVehiculo.isEmpty()) {
			criteria.add(Restrictions.eq("id.tipoVehiculo", tipoVehiculo));
		}

		criteria.setProjection(Projections.distinct(Projections.property("id.criterioUtilizacion")));

		List<String> lista = (List<String>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}