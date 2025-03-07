package org.gestoresmadrid.core.impr.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.impr.model.dao.ImprKoDao;
import org.gestoresmadrid.core.impr.model.vo.ImprKoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ImprKoDaoImpl  extends GenericDaoImplHibernate<ImprKoVO> implements ImprKoDao {

	private static final long serialVersionUID = 3693414682436035840L;

	@Override
	public ImprKoVO getImprKoTramite(BigDecimal numExpediente, String tipoImpr) {
		Criteria criteria = getCurrentSession().createCriteria(ImprKoVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.add(Restrictions.eq("tipoImpr", tipoImpr));
		return (ImprKoVO) criteria.uniqueResult();
	}

}