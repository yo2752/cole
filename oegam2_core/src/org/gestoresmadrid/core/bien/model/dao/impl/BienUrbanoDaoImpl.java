package org.gestoresmadrid.core.bien.model.dao.impl;

import org.gestoresmadrid.core.bien.model.dao.BienUrbanoDao;
import org.gestoresmadrid.core.bien.model.vo.BienUrbanoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BienUrbanoDaoImpl extends GenericDaoImplHibernate<BienUrbanoVO> implements BienUrbanoDao{

	private static final long serialVersionUID = 6412158256543163467L;
	
	@Override
	public BienUrbanoVO getBienUrbanoPorId(Long idBien) {
		Criteria criteria = getCurrentSession().createCriteria(BienUrbanoVO.class);
		criteria.add(Restrictions.eq("idBien", idBien));
		criteria.createAlias("tipoInmueble", "tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion",CriteriaSpecification.LEFT_JOIN);
		return (BienUrbanoVO) criteria.uniqueResult();
	}

}
