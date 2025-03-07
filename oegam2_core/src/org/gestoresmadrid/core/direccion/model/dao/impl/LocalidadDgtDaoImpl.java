package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.LocalidadDgtDao;
import org.gestoresmadrid.core.direccion.model.vo.LocalidadDgtVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LocalidadDgtDaoImpl extends GenericDaoImplHibernate<LocalidadDgtVO> implements LocalidadDgtDao {

	private static final long serialVersionUID = -6632097355496731022L;

	@Override
	public List<LocalidadDgtVO> getLocalidades(String localidad, String municipio) {
		Criteria criteria = getCurrentSession().createCriteria(LocalidadDgtVO.class);
		if (localidad != null && !localidad.isEmpty()) {
			criteria.add(Restrictions.eq("localidad", localidad.toUpperCase()));
		}

		if (municipio != null && !municipio.isEmpty()) {
			criteria.add(Restrictions.eq("municipio", municipio.toUpperCase()));
		}

		@SuppressWarnings("unchecked")
		List<LocalidadDgtVO> lista = (List<LocalidadDgtVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<LocalidadDgtVO> getLocalidadesPorCodigoPostal(String codigoPostal, String localidad) {
		Criteria criteria = getCurrentSession().createCriteria(LocalidadDgtVO.class);
		if (localidad != null && !localidad.isEmpty()) {
			criteria.add(Restrictions.eq("localidad", localidad.toUpperCase()));
		}

		if (codigoPostal != null && !codigoPostal.isEmpty()) {
			criteria.add(Restrictions.eq("codigoPostal", codigoPostal.toUpperCase()));
		}

		@SuppressWarnings("unchecked")
		List<LocalidadDgtVO> lista = (List<LocalidadDgtVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<String> listaLocalidades(String codigoIne) {
		Criteria criteria = getCurrentSession().createCriteria(LocalidadDgtVO.class);
		if (codigoIne != null && !codigoIne.isEmpty()) {
			criteria.add(Restrictions.eq("codigoIne", codigoIne));
		}

		criteria.setProjection(Projections.distinct(Projections.property("localidad")));

		criteria.addOrder(Order.asc("localidad"));

		@SuppressWarnings("unchecked")
		List<String> lista = (List<String>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}