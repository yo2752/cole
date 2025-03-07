package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaSerialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class IncidenciaSerialDaoImpl extends GenericDaoImplHibernate<IncidenciaSerialVO> implements IncidenciaSerialDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8944426616922745816L;

	@Override
	public IncidenciaSerialVO findIncidenciaSerialBySerial(Long inciId, String serial) {
		// TODO Auto-generated method stub
		Criteria criteria = getCurrentSession().createCriteria(IncidenciaSerialVO.class, "serial");
		criteria.add(Restrictions.eq("pk.incidenciaId", inciId));
		criteria.add(Restrictions.eq("pk.numSerie", serial));
		
		return (IncidenciaSerialVO) criteria.uniqueResult();
	}

	@Override
	public IncidenciaSerialVO findByIncidenciaConsejo(Long incidenciaInvId) {
		// TODO Auto-generated method stub
		Criteria criteria = getCurrentSession().createCriteria(IncidenciaSerialVO.class, "serial");

		criteria.add(Restrictions.eq("incidenciaInvId", incidenciaInvId));
		
		return (IncidenciaSerialVO) criteria.uniqueResult();
	}

}
