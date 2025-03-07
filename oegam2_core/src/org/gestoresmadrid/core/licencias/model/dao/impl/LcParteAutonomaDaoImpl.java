package org.gestoresmadrid.core.licencias.model.dao.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcParteAutonomaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcParteAutonomaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcParteAutonomaDaoImpl extends GenericDaoImplHibernate<LcParteAutonomaVO> implements LcParteAutonomaDao {

	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcParteAutonomaVO getParteAutonoma(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcParteAutonomaVO.class);
		criteria.add(Restrictions.eq("idParteAutonoma", id));
		return (LcParteAutonomaVO) criteria.uniqueResult();
	}

}
