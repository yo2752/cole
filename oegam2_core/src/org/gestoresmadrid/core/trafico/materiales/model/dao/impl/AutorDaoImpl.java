package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.AutorDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.AutorVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AutorDaoImpl extends GenericDaoImplHibernate<AutorVO> implements AutorDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233664115286392714L;

	@Override
	public AutorVO findByPrimaryKey(Long autorlId) {
		Criteria criteria = getCurrentSession().createCriteria(AutorVO.class, "autor");
		criteria.add(Restrictions.eq("autorId", autorlId));
		
		return (AutorVO) criteria.uniqueResult();
	}

}
