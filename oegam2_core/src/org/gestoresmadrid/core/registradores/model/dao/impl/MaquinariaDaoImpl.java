package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.MaquinariaDao;
import org.gestoresmadrid.core.registradores.model.vo.MaquinariaRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MaquinariaDaoImpl extends GenericDaoImplHibernate<MaquinariaRegistroVO> implements MaquinariaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5563581098988572199L;

	@Override
	public MaquinariaRegistroVO getMaquinariaRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(MaquinariaRegistroVO.class);
		criteria.add(Restrictions.eq("idMaquinaria", Long.parseLong(id)));
		return (MaquinariaRegistroVO) criteria.uniqueResult();
	}

	@Override
	public MaquinariaRegistroVO getMaquinariaPorPropiedad(BigDecimal id) {
		Criteria criteria = getCurrentSession().createCriteria(MaquinariaRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedad", id));
		return (MaquinariaRegistroVO)criteria.uniqueResult();
	}

}
