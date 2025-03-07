package org.gestoresmadrid.core.licencias.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcDatosPortalAltaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosPortalAltaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcDatosPortalAltaDaoImpl extends GenericDaoImplHibernate<LcDatosPortalAltaVO> implements LcDatosPortalAltaDao {

	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcDatosPortalAltaVO getDatosPortalAlta(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcDatosPortalAltaVO.class);
		criteria.add(Restrictions.eq("idDatosPortalAlta", id));
		criteria.createAlias("lcDatosPlantasAlta", "lcDatosPlantasAlta", CriteriaSpecification.LEFT_JOIN);
		return (LcDatosPortalAltaVO) criteria.uniqueResult();
	}

	@Override
	public List<LcDatosPortalAltaVO> getPortalesAlta(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcDatosPortalAltaVO.class);
		if (id != 0) {
			criteria.add(Restrictions.eq("idInfoEdificioAlta", id));
			criteria.createAlias("lcDatosPlantasAlta", "lcDatosPlantasAlta", CriteriaSpecification.LEFT_JOIN);
		}
		@SuppressWarnings("unchecked")
		List<LcDatosPortalAltaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

}
