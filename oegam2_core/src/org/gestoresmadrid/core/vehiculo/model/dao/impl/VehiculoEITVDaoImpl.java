package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoEITVDao;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoEITVVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VehiculoEITVDaoImpl extends GenericDaoImplHibernate<VehiculoEITVVO> implements VehiculoEITVDao {

	private static final long serialVersionUID = 9120474106886839658L;

	@Override
	public VehiculoEITVVO getVehiculoEITV(Long id, String numColegiado, String cif, String estado) {
		List<Criterion> listCriterion = new ArrayList<>();
		if (id != null) {
			listCriterion.add(Restrictions.eq("id", id));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			listCriterion.add(Restrictions.eq("numColegiado", numColegiado));
		}
		if (cif != null && !cif.isEmpty()) {
			listCriterion.add(Restrictions.eq("cif", cif));
		}
		if (estado != null && !estado.isEmpty()) {
			listCriterion.add(Restrictions.eq("estado", estado));
		}

		List<String> orden = new ArrayList<String>();
		orden.add("fechaAlta");

		List<VehiculoEITVVO> lista = buscarPorCriteria(listCriterion, GenericDaoImplHibernate.ordenDes, orden, null, null);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}