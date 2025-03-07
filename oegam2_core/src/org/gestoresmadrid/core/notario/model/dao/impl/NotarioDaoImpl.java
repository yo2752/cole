package org.gestoresmadrid.core.notario.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.notario.model.dao.NotarioDao;
import org.gestoresmadrid.core.notario.model.vo.NotarioVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class NotarioDaoImpl extends GenericDaoImplHibernate<NotarioVO> implements NotarioDao{

	private static final long serialVersionUID = -6735543815788317517L;
	
	@Override
	public NotarioVO getNotarioPorID(String codigo) {
		Criteria criteria = getCurrentSession().createCriteria(NotarioVO.class);
		criteria.add(Restrictions.eq("codigoNotario", codigo));
		return (NotarioVO) criteria.uniqueResult();
	}

}
