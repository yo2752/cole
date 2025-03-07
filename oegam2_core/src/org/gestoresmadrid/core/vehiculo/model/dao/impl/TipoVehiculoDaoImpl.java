package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.TipoVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoVehiculoDaoImpl extends GenericDaoImplHibernate<TipoVehiculoVO> implements TipoVehiculoDao, Serializable {

	private static final long serialVersionUID = -6094327508416599463L;

	@Override
	public TipoVehiculoVO getTipoVehiculo(String tipoVehiculo) {
		List<Criterion> listCriterion = new ArrayList<>();
		listCriterion.add(Restrictions.like("tipoVehiculo", tipoVehiculo));

		List<TipoVehiculoVO> lista = buscarPorCriteria(listCriterion, null, null);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoVehiculoVO> getListaTipoVehiculos() {
		return getCurrentSession().createCriteria(TipoVehiculoVO.class).list();
	}

	@Override
	public List<TipoVehiculoVO> getTipoVehiculosPorTipoTramite(String tipoTramite) {
		Criteria criteria = getCurrentSession().createCriteria(TipoVehiculoVO.class);
		criteria.add(Restrictions.or(Restrictions.eq("tipoTramite", tipoTramite), Restrictions.isNull("tipoTramite")));
		criteria.addOrder(Order.asc("tipoVehiculo"));

		@SuppressWarnings("unchecked")
		List<TipoVehiculoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}