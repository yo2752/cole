package org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.EvolucionDatosSensiblesNifDao;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesNifVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionDatosSensiblesNifDaoImpl extends GenericDaoImplHibernate<EvolucionDatosSensiblesNifVO> implements EvolucionDatosSensiblesNifDao{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionDatosSensiblesNifVO> getEvolucion(String nif, String idGrupo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(EvolucionDatosSensiblesNifVO.class);
		
		if(nif != null){
			criteria.add(Restrictions.eq("nif", nif));
		}
		if(idGrupo != null){
			criteria.add(Restrictions.eq("grupo.idGrupo", idGrupo));
		}
		criteria.addOrder(Order.desc("fechaCambio"));
		criteria.createAlias("grupo", "grupo", CriteriaSpecification.LEFT_JOIN);
		
		return criteria.list();
	}

}
