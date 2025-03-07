package org.gestoresmadrid.core.semaforo.model.dao.impl;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.semaforo.model.dao.SemaforoDao;
import org.gestoresmadrid.core.semaforo.model.vo.SemaforoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SemaforoDaoImpl extends GenericDaoImplHibernate<SemaforoVO> implements SemaforoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8348649347789000567L;

	@Override
	public SemaforoVO obtenerSemaforoVO(SemaforoVO semaforo) {
		Criteria criteria = getCurrentSession().createCriteria(SemaforoVO.class);
		
		criteria.add(Restrictions.eq("proceso", semaforo.getProceso()));
		criteria.add(Restrictions.eq("nodo", semaforo.getNodo()));		
		
		return (SemaforoVO)criteria.uniqueResult();
	}

	@Override
	public SemaforoVO obtenerSemaforoVO(Long idSemaforo) {
		Criteria criteria = getCurrentSession().createCriteria(SemaforoVO.class);
		
		criteria.add(Restrictions.eq("idSemaforo", idSemaforo));				
		
		return (SemaforoVO)criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SemaforoVO> obtenerListadoSemaforosSes(String nodo) {
		Criteria criteria = getCurrentSession().createCriteria(SemaforoVO.class);
		
		criteria.add(Restrictions.eq("nodo", nodo));
		criteria.add(Restrictions.gt("estado", 0));
		
		return (List<SemaforoVO>)criteria.list();
	}

}
