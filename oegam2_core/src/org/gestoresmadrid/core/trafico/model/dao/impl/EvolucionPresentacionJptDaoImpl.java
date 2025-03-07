package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.model.dao.EvolucionPresentacionJptDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionPresentacionJptVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionPresentacionJptDaoImpl extends GenericDaoImplHibernate<EvolucionPresentacionJptVO> implements EvolucionPresentacionJptDao {

	private static final long serialVersionUID = 2674321854607453100L;

	public List<EvolucionPresentacionJptVO> obtenerEvolucion(BigDecimal numExpediente){
		Criterion c = Restrictions.eq("tramiteTrafico.numExpediente", numExpediente);
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(c);
		List<EvolucionPresentacionJptVO> l = buscarPorCriteria(-1, -1, criterion, null, null);
		if (l == null || l.isEmpty()) {
			return null;
		}
		return l;
	}

}