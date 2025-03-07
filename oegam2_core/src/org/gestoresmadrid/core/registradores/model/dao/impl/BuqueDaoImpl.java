package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.BuqueDao;
import org.gestoresmadrid.core.registradores.model.vo.BuqueRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BuqueDaoImpl extends GenericDaoImplHibernate<BuqueRegistroVO> implements BuqueDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2882577299894679917L;

	@Override
	public BuqueRegistroVO getBuqueRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(BuqueRegistroVO.class);
		criteria.add(Restrictions.eq("idBuque", Long.parseLong(id)));
		return (BuqueRegistroVO) criteria.uniqueResult();
	}

	@Override
	public BuqueRegistroVO getBuquePorPropiedad(BigDecimal id) {
		Criteria criteria = getCurrentSession().createCriteria(BuqueRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedad", id));
		return (BuqueRegistroVO)criteria.uniqueResult();
	}

}
