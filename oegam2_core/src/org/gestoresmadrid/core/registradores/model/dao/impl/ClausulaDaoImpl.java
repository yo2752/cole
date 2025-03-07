package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.ClausulaDao;
import org.gestoresmadrid.core.registradores.model.vo.ClausulaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ClausulaDaoImpl extends GenericDaoImplHibernate<ClausulaVO> implements ClausulaDao {

	private static final long serialVersionUID = 6279192150632687653L;

	@Override
	public ClausulaVO getClausula(String id) {
		Criteria criteria = getCurrentSession().createCriteria(ClausulaVO.class);
		criteria.add(Restrictions.eq("idClausula", Long.parseLong(id)));
		return (ClausulaVO) criteria.uniqueResult();
	}

}
