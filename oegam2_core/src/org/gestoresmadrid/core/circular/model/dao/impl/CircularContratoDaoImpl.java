package org.gestoresmadrid.core.circular.model.dao.impl;

import org.gestoresmadrid.core.circular.model.dao.CircularContratoDao;
import org.gestoresmadrid.core.circular.model.vo.CircularContratoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CircularContratoDaoImpl extends GenericDaoImplHibernate<CircularContratoVO> implements CircularContratoDao{

	private static final long serialVersionUID = -2975434279667110490L;

	@Override
	public CircularContratoVO getCircularContrato(Long idCircular, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(CircularContratoVO.class);
		criteria.add(Restrictions.eq("idCircular", idCircular));
		criteria.add(Restrictions.eq("idContrato", idContrato));
		return (CircularContratoVO) criteria.uniqueResult();
	}
	
}
