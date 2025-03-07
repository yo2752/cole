package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.dao.FuncionDao;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FuncionDaoImpl extends GenericDaoImplHibernate<FuncionVO> implements FuncionDao {

	private static final long serialVersionUID = 2446740830160261483L;

	@Override
	@SuppressWarnings("unchecked")
	public List<FuncionVO> getFunciones() {
		Criteria criteria = getCurrentSession().createCriteria(FuncionVO.class);
		criteria.add(Restrictions.eq("estadoFuncion", 1L));
		criteria.addOrder(Order.asc("id.codigoAplicacion"));
		criteria.addOrder(Order.asc("id.codigoFuncion"));
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FuncionVO> getFuncionesPorAplicacion(String codigoAplicacion) {
		Criteria criteria = getCurrentSession().createCriteria(FuncionVO.class);
		criteria.add(Restrictions.eq("id.codigoAplicacion", codigoAplicacion));
		criteria.addOrder(Order.asc("id.codigoFuncion"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FuncionVO> obtenerListadoFuncionesMenuPorAplicacion(String codigoAplicacion) {
		Criteria criteria = getCurrentSession().createCriteria(FuncionVO.class);
		criteria.add(Restrictions.eq("id.codigoAplicacion", codigoAplicacion));
		criteria.add(Restrictions.eq("estadoFuncion", 1L));
		criteria.createAlias("aplicacion", "aplicacion", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("id.codigoAplicacion"));
		criteria.addOrder(Order.asc("descFuncion"));
		criteria.addOrder(Order.asc("id.codigoFuncion"));
		criteria.addOrder(Order.asc("nivel"));
		criteria.addOrder(Order.asc("orden"));
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FuncionVO> getFuncionesHijos(String codigoAplicacion, String codigoFuncion) {
		Criteria criteria = getCurrentSession().createCriteria(FuncionVO.class);
		criteria.add(Restrictions.eq("id.codigoAplicacion", codigoAplicacion));
		criteria.add(Restrictions.eq("codFuncionPadre", codigoFuncion));
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FuncionVO> getFuncionesHijosYPadre(String codigoAplicacion, String codigoFuncion) {
		Criteria criteria = getCurrentSession().createCriteria(FuncionVO.class);
		criteria.add(Restrictions.eq("id.codigoAplicacion", codigoAplicacion));
		criteria.add(Restrictions.or(Restrictions.eq("codFuncionPadre", codigoFuncion), Restrictions.eq("id.codigoFuncion", codigoFuncion)));
		return criteria.list();
	}

	@Override
	public FuncionVO getFuncion(String codigoAplicacion, String codigoFuncion) {
		Criteria criteria = getCurrentSession().createCriteria(FuncionVO.class);
		criteria.add(Restrictions.eq("id.codigoAplicacion", codigoAplicacion));
		criteria.add(Restrictions.eq("id.codigoFuncion", codigoFuncion));
		return (FuncionVO) criteria.uniqueResult();
	}
}