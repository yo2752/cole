package org.gestoresmadrid.core.circular.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.circular.model.dao.CircularDao;
import org.gestoresmadrid.core.circular.model.vo.CircularVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CircularDaoImpl extends GenericDaoImplHibernate<CircularVO> implements CircularDao{

	private static final long serialVersionUID = -819805360195339412L;

	@Override
	public CircularVO getCircular(Long id) {
		Criteria criteria =  getCurrentSession().createCriteria(CircularVO.class);
		criteria.add(Restrictions.eq("id", id));
		return (CircularVO) criteria.uniqueResult();
	}

	@Override
	public List<CircularVO> getListaCircularesRepeticion() {
		Criteria criteria =  getCurrentSession().createCriteria(CircularVO.class);
		criteria.add(Restrictions.eq("estado", "0"));
		criteria.add(Restrictions.eq("repeticiones", BigDecimal.ONE));
		criteria.add(Restrictions.isNull("fechaFin"));
		criteria.addOrder(Order.asc("idCircular"));
		return criteria.list();
	}
	
	@Override
	public List<CircularVO> getListaCircularesActivasFecha() {
		Criteria criteria =  getCurrentSession().createCriteria(CircularVO.class);
		criteria.add(Restrictions.eq("estado", "0"));
		criteria.add(Restrictions.eq("fecha", BigDecimal.ONE));
		criteria.add(Restrictions.isNotNull("fechaFin"));
		criteria.addOrder(Order.asc("idCircular"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CircularVO> getListaCircularesActivas() {
		Criteria criteria =  getCurrentSession().createCriteria(CircularVO.class);
		criteria.add(Restrictions.eq("estado", "0"));
		Date fecha = new Date();
		criteria.add(Restrictions.le("fechaInicio", fecha));
		criteria.addOrder(Order.asc("idCircular"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CircularVO> getListaCircularesCaducadas() {
		Date fechaActual = new Date();
		Criteria criteria =  getCurrentSession().createCriteria(CircularVO.class);
		criteria.add(Restrictions.eq("estado", "1"));
		criteria.addOrder(Order.asc("idCircular"));
		return criteria.list();
	}
}
