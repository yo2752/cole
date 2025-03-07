package org.gestoresmadrid.core.licencias.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcPersonaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcPersonaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcPersonaDaoImpl extends GenericDaoImplHibernate<LcPersonaVO> implements LcPersonaDao {

	private static final long serialVersionUID = -3995643486274281552L;

	@Override
	public LcPersonaVO getLcPersona(String nif, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(LcPersonaVO.class);
		criteria.add(Restrictions.eq("nif", nif));
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.addOrder(Order.desc("idPersona"));
		@SuppressWarnings("unchecked")
		List<LcPersonaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}
