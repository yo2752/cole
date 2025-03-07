package org.gestoresmadrid.core.colegio.model.dao.impl;

import org.gestoresmadrid.core.colegio.model.dao.ColegioDao;
import org.gestoresmadrid.core.general.model.vo.ColegioVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ColegioDaoImpl extends GenericDaoImplHibernate<ColegioVO> implements ColegioDao{

	private static final long serialVersionUID = -19304868729324600L;

	@Override
	public ColegioVO getColegio(String colegio) {
		Criteria criteria = getCurrentSession().createCriteria(ColegioVO.class);
		criteria.add(Restrictions.eq("colegio", colegio));
		return (ColegioVO) criteria.uniqueResult();
	}
	
}
