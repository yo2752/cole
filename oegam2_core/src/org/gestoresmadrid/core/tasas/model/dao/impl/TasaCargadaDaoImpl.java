package org.gestoresmadrid.core.tasas.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.tasas.model.dao.TasaCargadaDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaCargadaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TasaCargadaDaoImpl extends GenericDaoImplHibernate<TasaCargadaVO> implements TasaCargadaDao {

	private static final long serialVersionUID = -5060276409381767382L;

	@Override
	public TasaCargadaVO detalle(String codigoTasa, Integer formatoTasa) {
		TasaCargadaVO result = null;
		Criteria criteria = getCurrentSession().createCriteria(TasaCargadaVO.class);
		criteria.add(Restrictions.eq("codigoTasa", codigoTasa));
		if (formatoTasa != null) {
			criteria.add(Restrictions.eq("formato", formatoTasa));
			result = (TasaCargadaVO) criteria.uniqueResult();
		} else {
			@SuppressWarnings("unchecked")
			List<TasaCargadaVO> lista = criteria.list();
			if (lista != null && !lista.isEmpty()) {
				result = lista.get(0);
			}
		}
		return result;
	}

}
