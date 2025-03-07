package org.gestoresmadrid.core.licencias.model.dao.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcIntervinienteDao;
import org.gestoresmadrid.core.licencias.model.vo.LcIntervinienteVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcIntervinienteDaoImpl extends GenericDaoImplHibernate<LcIntervinienteVO> implements LcIntervinienteDao {

	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcIntervinienteVO getInterviniente(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcIntervinienteVO.class);
		criteria.add(Restrictions.eq("idInterviniente", id));
		return (LcIntervinienteVO) criteria.uniqueResult();
	}
}
