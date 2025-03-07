package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.OtrosBienesDao;
import org.gestoresmadrid.core.registradores.model.vo.OtrosBienesRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class OtrosBienesDaoImpl extends GenericDaoImplHibernate<OtrosBienesRegistroVO> implements OtrosBienesDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7675609900161075672L;

	@Override
	public OtrosBienesRegistroVO getOtrosBienesRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(OtrosBienesRegistroVO.class);
		criteria.add(Restrictions.eq("idOtrosBienes", Long.parseLong(id)));
		return (OtrosBienesRegistroVO) criteria.uniqueResult();
	}

	@Override
	public OtrosBienesRegistroVO getOtrosBienesPorPropiedad(BigDecimal id) {
		Criteria criteria = getCurrentSession().createCriteria(OtrosBienesRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedad", id));
		return (OtrosBienesRegistroVO)criteria.uniqueResult();
	}

}
