package org.gestoresmadrid.core.modelos.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelos.model.dao.OficinaLiquidadoraDao;
import org.gestoresmadrid.core.modelos.model.vo.OficinaLiquidadoraVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class OficinaLiquidadoraDaoImpl extends GenericDaoImplHibernate<OficinaLiquidadoraVO> implements OficinaLiquidadoraDao{

	private static final long serialVersionUID = 24102394448348027L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OficinaLiquidadoraVO> getListaOficinas(String idProvinciaOficinaLiquid) {
		Criteria criteria =  getCurrentSession().createCriteria(OficinaLiquidadoraVO.class);
		criteria.add(Restrictions.eq("id.idProvincia", idProvinciaOficinaLiquid));
		return criteria.list();
	}

}
