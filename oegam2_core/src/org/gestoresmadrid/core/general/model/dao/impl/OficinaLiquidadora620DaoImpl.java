package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.general.model.dao.OficinaLiquidadora620Dao;
import org.gestoresmadrid.core.general.model.vo.OficinaLiquidadora620VO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class OficinaLiquidadora620DaoImpl extends GenericDaoImplHibernate<OficinaLiquidadora620VO> implements OficinaLiquidadora620Dao {

	private static final long serialVersionUID = 3154808740079339341L;

	@Override
	public List<OficinaLiquidadora620VO> listadoOficinasLiquidadoras(String oficinaLiquidadora) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(OficinaLiquidadora620VO.class);
		if (StringUtils.isNotBlank(oficinaLiquidadora)) {
			criteria.add(Restrictions.eq("id.oficinaLiquidadora", oficinaLiquidadora));
		}

		@SuppressWarnings("unchecked")
		List<OficinaLiquidadora620VO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}