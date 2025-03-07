package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.NotarioRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.NotarioRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class NotarioRegistroDaoImpl extends GenericDaoImplHibernate<NotarioRegistroVO> implements NotarioRegistroDao {

	private static final long serialVersionUID = 1571230202333037102L;
	
	@Override
	public NotarioRegistroVO getNotarioPorID(String codigo) {
		Criteria criteria = getCurrentSession().createCriteria(NotarioRegistroVO.class);
		criteria.add(Restrictions.eq("codigo", Long.parseLong(codigo)));
		return (NotarioRegistroVO) criteria.uniqueResult();
	}
	
}
