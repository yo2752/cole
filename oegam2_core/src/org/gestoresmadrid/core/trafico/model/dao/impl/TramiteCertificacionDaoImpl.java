package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.model.dao.TramiteCertificacionDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteCertificacionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TramiteCertificacionDaoImpl extends GenericDaoImplHibernate<TramiteCertificacionVO> implements TramiteCertificacionDao {

	private static final long serialVersionUID = 4369338960203900602L;

	@Override
	public String getCertificacion(BigDecimal numExpediente, String hash, String version) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteCertificacionVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}
		if (hash != null) {
			criteria.add(Restrictions.eq("hash", hash));
		}
		if (version != null) {
			criteria.add(Restrictions.eq("version", version));
		}
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.max("version"));
		criteria.setProjection(projectionList);
		String versionMax= (String) criteria.uniqueResult();
		if (versionMax == null) {
			return null;
		}
		return versionMax;
	}

}