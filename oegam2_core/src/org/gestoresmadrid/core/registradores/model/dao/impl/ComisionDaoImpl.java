package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.ComisionDao;
import org.gestoresmadrid.core.registradores.model.vo.ComisionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ComisionDaoImpl  extends GenericDaoImplHibernate<ComisionVO> implements ComisionDao {

	private static final long serialVersionUID = 4297207233217605726L;
	
	@Override
	public ComisionVO getComision(String id) {
		Criteria criteria = getCurrentSession().createCriteria(ComisionVO.class);
		criteria.add(Restrictions.eq("idComision", Long.parseLong(id)));
		return (ComisionVO) criteria.uniqueResult();

	}
}
