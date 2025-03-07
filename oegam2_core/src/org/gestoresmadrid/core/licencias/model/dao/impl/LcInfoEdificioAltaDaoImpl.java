package org.gestoresmadrid.core.licencias.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcInfoEdificioAltaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcInfoEdificioAltaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcInfoEdificioAltaDaoImpl extends GenericDaoImplHibernate<LcInfoEdificioAltaVO> implements LcInfoEdificioAltaDao {

	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcInfoEdificioAltaVO getInfoEdificioAlta(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcInfoEdificioAltaVO.class);
		criteria.add(Restrictions.eq("idInfoEdificioAlta", id));
		criteria.createAlias("lcDirEdificacionAlta", "lcDirEdificacionAlta", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("lcDatosPortalesAlta", "lcDatosPortalesAlta", CriteriaSpecification.LEFT_JOIN);
		return (LcInfoEdificioAltaVO) criteria.uniqueResult();
	}

	@Override
	public List<LcInfoEdificioAltaVO> getInfoEdificiosAlta(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcInfoEdificioAltaVO.class);
		if (id != 0) {
			criteria.add(Restrictions.eq("idEdificacion", id));
			criteria.createAlias("lcDirEdificacionAlta", "lcDirEdificacionAlta", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcDatosPortalesAlta", "lcDatosPortalesAlta", CriteriaSpecification.LEFT_JOIN);

		}
		@SuppressWarnings("unchecked")
		List<LcInfoEdificioAltaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

}
