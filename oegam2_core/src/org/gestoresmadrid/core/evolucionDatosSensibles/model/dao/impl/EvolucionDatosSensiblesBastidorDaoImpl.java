package org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.EvolucionDatosSensiblesBastidorDao;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesBastidorVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionDatosSensiblesBastidorDaoImpl extends GenericDaoImplHibernate<EvolucionDatosSensiblesBastidorVO> implements EvolucionDatosSensiblesBastidorDao {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionDatosSensiblesBastidorVO> getEvolucion(String bastidor, String idGrupo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(EvolucionDatosSensiblesBastidorVO.class);

		if (bastidor != null && !bastidor.isEmpty()) {
			criteria.add(Restrictions.eq("bastidor", bastidor));
		}
		if (idGrupo != null && !idGrupo.isEmpty()) {
			criteria.add(Restrictions.eq("grupo.idGrupo", idGrupo));
		}
		criteria.addOrder(Order.desc("fechaCambio"));
		criteria.createAlias("grupo", "grupo", CriteriaSpecification.LEFT_JOIN);

		return criteria.list();
	}

}
