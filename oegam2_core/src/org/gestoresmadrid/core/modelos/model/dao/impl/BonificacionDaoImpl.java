package org.gestoresmadrid.core.modelos.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelos.model.dao.BonificacionDao;
import org.gestoresmadrid.core.modelos.model.vo.BonificacionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class BonificacionDaoImpl extends GenericDaoImplHibernate<BonificacionVO> implements BonificacionDao{

	private static final long serialVersionUID = -4652393839792605946L;

	
	@Override
	public BonificacionVO getBonificacionPorId(String bonificacion) {
		Criteria criteria = getCurrentSession().createCriteria(BonificacionVO.class);
		criteria.add(Restrictions.eq("bonificacion", bonificacion));
		return (BonificacionVO) criteria.uniqueResult();
	}
	
}
