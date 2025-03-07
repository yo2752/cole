package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.direccion.model.vo.PactoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.PactoDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PactoDaoImpl extends GenericDaoImplHibernate<PactoVO> implements PactoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5210377148563440302L;

	@Override
	public PactoVO getPacto(String id) {
		Criteria criteria = getCurrentSession().createCriteria(PactoVO.class);
		criteria.add(Restrictions.eq("idPacto", Long.parseLong(id)));
		return (PactoVO) criteria.uniqueResult();
	}
}
