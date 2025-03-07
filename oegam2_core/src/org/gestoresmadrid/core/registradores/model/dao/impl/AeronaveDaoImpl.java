package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.AeronaveDao;
import org.gestoresmadrid.core.registradores.model.vo.AeronaveRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AeronaveDaoImpl extends GenericDaoImplHibernate<AeronaveRegistroVO> implements AeronaveDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7130005645244742833L;

	@Override
	public AeronaveRegistroVO getAeronaveRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(AeronaveRegistroVO.class);
		criteria.add(Restrictions.eq("idAeronave", Long.parseLong(id)));
		return (AeronaveRegistroVO) criteria.uniqueResult();
	}

	@Override
	public AeronaveRegistroVO getAeronavePorPropiedad(BigDecimal id) {
		Criteria criteria = getCurrentSession().createCriteria(AeronaveRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedad", id));
		return (AeronaveRegistroVO)criteria.uniqueResult();
	}

}
