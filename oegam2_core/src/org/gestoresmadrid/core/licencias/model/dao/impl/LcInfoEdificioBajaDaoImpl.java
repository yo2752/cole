package org.gestoresmadrid.core.licencias.model.dao.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcInfoEdificioBajaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcInfoEdificioBajaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcInfoEdificioBajaDaoImpl extends GenericDaoImplHibernate<LcInfoEdificioBajaVO> implements LcInfoEdificioBajaDao {

	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcInfoEdificioBajaVO getInfoEdificioBaja(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcInfoEdificioBajaVO.class);
		criteria.add(Restrictions.eq("idInfoEdificioBaja", id));
		criteria.createAlias("lcDirEdificacion", "lcDirEdificacion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("lcDatosPlantasBaja", "lcDatosPlantasBaja", CriteriaSpecification.LEFT_JOIN);
		return (LcInfoEdificioBajaVO) criteria.uniqueResult();
	}
}
