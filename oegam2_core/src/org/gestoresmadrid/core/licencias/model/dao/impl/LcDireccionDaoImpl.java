package org.gestoresmadrid.core.licencias.model.dao.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcDireccionDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcDireccionDaoImpl extends GenericDaoImplHibernate<LcDireccionVO> implements LcDireccionDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcDireccionVO getDireccion(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcDireccionVO.class);
		criteria.add(Restrictions.eq("idDireccion", id));
		return (LcDireccionVO) criteria.uniqueResult();
	}


}
