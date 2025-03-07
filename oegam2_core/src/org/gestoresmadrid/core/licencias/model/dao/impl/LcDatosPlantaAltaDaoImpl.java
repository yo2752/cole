package org.gestoresmadrid.core.licencias.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcDatosPlantaAltaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosPlantaAltaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcDatosPlantaAltaDaoImpl extends GenericDaoImplHibernate<LcDatosPlantaAltaVO> implements LcDatosPlantaAltaDao {

	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcDatosPlantaAltaVO getDatosPlantaAlta(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcDatosPlantaAltaVO.class);
		criteria.add(Restrictions.eq("idDatosPlantaAlta", id));
		criteria.createAlias("lcSupNoComputablesPlanta", "lcSupNoComputablesPlanta", CriteriaSpecification.LEFT_JOIN);
		return (LcDatosPlantaAltaVO) criteria.uniqueResult();
	}

	@Override
	public List<LcDatosPlantaAltaVO> getPlantasAlta(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcDatosPlantaAltaVO.class);
		if (id != 0) {
			criteria.add(Restrictions.eq("idDatosPortalAlta", id));
			criteria.createAlias("lcSupNoComputablesPlanta", "lcSupNoComputablesPlanta", CriteriaSpecification.LEFT_JOIN);
		}
		@SuppressWarnings("unchecked")
		List<LcDatosPlantaAltaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

}
