package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.DireccionDao;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDaoImpl extends GenericDaoImplHibernate<DireccionVO> implements DireccionDao {

	private static final long serialVersionUID = -4057955119070568671L;

	@Override
	public DireccionVO getDireccion(Long idDireccion) {
		List<Criterion> listCriterion = new ArrayList<>();
		if (idDireccion != null) {
			listCriterion.add(Restrictions.eq("idDireccion", idDireccion));
		}

		List<DireccionVO> lista = buscarPorCriteria(listCriterion, null, null);
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}