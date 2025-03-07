package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.EvolucionMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionMaterialVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionMaterialDaoImpl extends GenericDaoImplHibernate<EvolucionMaterialVO> implements EvolucionMaterialDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8117034452168487282L;

	@Override
	@SuppressWarnings("unchecked")
	public List<EvolucionMaterialVO> getListaEvolucionMateriales() {
		return getCurrentSession().createCriteria(EvolucionMaterialVO.class).list();	
	}

	@Override
	public EvolucionMaterialVO findEvolucionMaterialByPrimaryKey(Long evolucionMaterialId) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionMaterialVO.class, "evolucionMaterial");
		criteria.add(Restrictions.eq("evolucionMaterialId", evolucionMaterialId));
		
		return (EvolucionMaterialVO) criteria.uniqueResult();
	}
}
