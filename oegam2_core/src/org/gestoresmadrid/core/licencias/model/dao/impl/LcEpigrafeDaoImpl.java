package org.gestoresmadrid.core.licencias.model.dao.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcEpigrafeDao;
import org.gestoresmadrid.core.licencias.model.vo.LcEpigrafeVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcEpigrafeDaoImpl extends GenericDaoImplHibernate<LcEpigrafeVO> implements LcEpigrafeDao {

	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcEpigrafeVO getEpigrafe(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcEpigrafeVO.class);
		criteria.add(Restrictions.eq("idEpigrafe", id));
		return (LcEpigrafeVO) criteria.uniqueResult();
	}

}
