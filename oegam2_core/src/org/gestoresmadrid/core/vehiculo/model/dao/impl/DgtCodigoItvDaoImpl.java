package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.DgtCodigoItvDao;
import org.gestoresmadrid.core.vehiculo.model.vo.DgtCodigoItvVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DgtCodigoItvDaoImpl extends GenericDaoImplHibernate<DgtCodigoItvVO> implements DgtCodigoItvDao {

	private static final long serialVersionUID = 4181024038964393407L;

	@Override
	public DgtCodigoItvVO obtenerDatosItv(String codigoItv) {
		List<Criterion> listCriterion = new ArrayList<>();
		if (codigoItv != null && !"".equals(codigoItv)) {
			listCriterion.add(Restrictions.eq("codigoItv", codigoItv));
		}

		List<DgtCodigoItvVO> lista = buscarPorCriteria(listCriterion, null, null);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}