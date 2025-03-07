package org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.EvolucionDatosSensiblesMatriculaDao;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionDatosSensiblesMatriculaDaoImpl extends GenericDaoImplHibernate<EvolucionDatosSensiblesMatriculaVO> implements EvolucionDatosSensiblesMatriculaDao{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionDatosSensiblesMatriculaVO> getEvolucion(String matricula, String idGrupo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(EvolucionDatosSensiblesMatriculaVO.class);
		
		if(matricula != null){
			criteria.add(Restrictions.eq("matricula", matricula));
		}
		if(idGrupo != null){
			criteria.add(Restrictions.eq("grupo.idGrupo", idGrupo));
		}
		criteria.addOrder(Order.desc("fechaCambio"));
		criteria.createAlias("grupo", "grupo", CriteriaSpecification.LEFT_JOIN);
		
		return criteria.list();
	}



}
