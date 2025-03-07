package org.gestoresmadrid.core.presentacion.jpt.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.presentacion.jpt.model.dao.TipoEstadisticasJPTDao;
import org.gestoresmadrid.core.presentacion.jpt.model.vo.TipoEstadisticasJPTVO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoEstadisticasJPTDaoImpl extends GenericDaoImplHibernate<TipoEstadisticasJPTVO> implements TipoEstadisticasJPTDao {

	private static final long serialVersionUID = 5756593696384151697L;

	@Override
	public List<TipoEstadisticasJPTVO> getListaTipoEstadisticas(boolean visible) {

		Session sesion = getCurrentSession();

		Criteria criteria = sesion.createCriteria(TipoEstadisticasJPTVO.class);

		criteria.add(Restrictions.eq("visible", visible));

		if(!visible){
			criteria.add(Restrictions.eq("incidencia", false));
		}

		criteria.addOrder(Order.asc("descripcion"));

		return criteria.list();
	}

	@Override
	public List<TipoEstadisticasJPTVO> getListaIncidenciasEstadisticasJpt() {

		Session sesion = getCurrentSession();

		Criteria criteria = sesion.createCriteria(TipoEstadisticasJPTVO.class);

		criteria.add(Restrictions.eq("incidencia", true));

		criteria.addOrder(Order.asc("descripcion"));

		return criteria.list();
	}

	@Override
	public List<TipoEstadisticasJPTVO> getListaTipoEstadisticasFichero() {
		Session sesion = getCurrentSession();

		Criteria criteria = sesion.createCriteria(TipoEstadisticasJPTVO.class);

		Criterion crt1 = Restrictions.eq("visible", true);

		Criterion crt2 = Restrictions.eq("incidencia", true);

		criteria.add(Restrictions.or(crt1, crt2));

		criteria.addOrder(Order.asc("descripcion"));

		return criteria.list();
	}
}