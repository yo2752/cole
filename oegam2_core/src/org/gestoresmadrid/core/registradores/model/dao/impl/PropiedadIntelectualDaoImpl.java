package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.PropiedadIntelectualDao;
import org.gestoresmadrid.core.registradores.model.vo.PropiedadIntelectualRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PropiedadIntelectualDaoImpl extends GenericDaoImplHibernate<PropiedadIntelectualRegistroVO> implements PropiedadIntelectualDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7993488320309366238L;

	@Override
	public PropiedadIntelectualRegistroVO getPropiedadIntelectualRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(PropiedadIntelectualRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedadIntelectual", Long.parseLong(id)));
		return (PropiedadIntelectualRegistroVO) criteria.uniqueResult();
	}

	@Override
	public PropiedadIntelectualRegistroVO getPropiedadIntelectualPorPropiedad(BigDecimal id) {
		Criteria criteria = getCurrentSession().createCriteria(PropiedadIntelectualRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedad", id));
		return (PropiedadIntelectualRegistroVO)criteria.uniqueResult();
	}

}
