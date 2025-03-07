package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.PropiedadIndustrialDao;
import org.gestoresmadrid.core.registradores.model.vo.PropiedadIndustrialRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PropiedadIndustrialDaoImpl extends GenericDaoImplHibernate<PropiedadIndustrialRegistroVO> implements PropiedadIndustrialDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2274192810795857361L;

	@Override
	public PropiedadIndustrialRegistroVO getPropiedadIndustrialRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(PropiedadIndustrialRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedadIndustrial", Long.parseLong(id)));
		return (PropiedadIndustrialRegistroVO) criteria.uniqueResult();
	}

	@Override
	public PropiedadIndustrialRegistroVO getPropiedadIndustrialPorPropiedad(BigDecimal id) {
		Criteria criteria = getCurrentSession().createCriteria(PropiedadIndustrialRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedad", id));
		return (PropiedadIndustrialRegistroVO)criteria.uniqueResult();
	}


}
