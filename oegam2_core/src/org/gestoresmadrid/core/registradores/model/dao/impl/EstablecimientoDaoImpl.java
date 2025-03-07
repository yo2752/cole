package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.EstablecimientoDao;
import org.gestoresmadrid.core.registradores.model.vo.EstablecimientoRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EstablecimientoDaoImpl extends GenericDaoImplHibernate<EstablecimientoRegistroVO> implements EstablecimientoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4856123802796197506L;

	@Override
	public EstablecimientoRegistroVO getEstablecimientoRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(EstablecimientoRegistroVO.class);
		criteria.add(Restrictions.eq("idEstablecimiento", Long.parseLong(id)));
		return (EstablecimientoRegistroVO) criteria.uniqueResult();
	}

	@Override
	public EstablecimientoRegistroVO getEstablecimientoPorPropiedad(BigDecimal id) {
		Criteria criteria = getCurrentSession().createCriteria(EstablecimientoRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedad", id));
		return (EstablecimientoRegistroVO)criteria.uniqueResult();
	}



}
