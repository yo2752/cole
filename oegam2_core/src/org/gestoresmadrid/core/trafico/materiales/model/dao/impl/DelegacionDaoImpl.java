package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.DelegacionDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.DelegacionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DelegacionDaoImpl extends GenericDaoImplHibernate<DelegacionVO> implements DelegacionDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8277306028107909019L;

	@Override
	public DelegacionVO findByPrimaryKey(Long delegacionId) {
		Criteria criteria = getCurrentSession().createCriteria(DelegacionVO.class, "delegacion");
		criteria.add(Restrictions.eq("delegacionId", delegacionId));
		
		return (DelegacionVO) criteria.uniqueResult();
	}

	@Override
	public DelegacionVO findByJefaturaProvincial(String jefaturaProvincial) {
		Criteria criteria = getCurrentSession().createCriteria(DelegacionVO.class, "delegacion");
		criteria.createAlias("delegacion.jefaturaProvincial", "jefatura");
		criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefaturaProvincial));
		
		return (DelegacionVO) criteria.uniqueResult();
	}

	
}
