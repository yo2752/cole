package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.TipoReduccionDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoReduccionVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoReduccionDaoIml extends GenericDaoImplHibernate<TipoReduccionVO> implements TipoReduccionDao, Serializable {

	private static final long serialVersionUID = 3703141272752667493L;

	@Override
	public TipoReduccionVO getTipoReduccion(String tipoReduccion) {
		List<Criterion> listCriterion = new ArrayList<>();
		listCriterion.add(Restrictions.like("tipoReduccion", tipoReduccion));

		List<TipoReduccionVO> lista = buscarPorCriteria(listCriterion, null, null);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoReduccionVO> getListaTipoReducciones() {
		return getCurrentSession().createCriteria(TipoReduccionVO.class).list();
	}

}