package org.gestoresmadrid.core.trafico.prematriculados.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.prematriculados.model.dao.VehiculoPrematriculadoDaoInt;
import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VehiculoPrematriculadoDaoImpl extends GenericDaoImplHibernate<VehiculoPrematriculadoVO> implements VehiculoPrematriculadoDaoInt {

	private static final long serialVersionUID = 7002487631198868638L;

	@Override
	public List<VehiculoPrematriculadoVO> buscar(Long[] ids, String numColegiado) {
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.in("id", ids));
		if (numColegiado!=null && !numColegiado.isEmpty()){
			criterion.add(Restrictions.eq("numColegiado", numColegiado));
		}
		return buscarPorCriteria(criterion, null, null);
	}

	@Override
	public VehiculoPrematriculadoVO buscar(Long id, String numColegiado) {
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.eq("id", id));
		if (numColegiado!=null && !numColegiado.isEmpty()){
			criterion.add(Restrictions.eq("numColegiado", numColegiado));
		}
		List<VehiculoPrematriculadoVO> vehiculos = buscarPorCriteria(criterion, null, null);
		if (vehiculos==null || vehiculos.size()!=1 || vehiculos.get(0)==null){
			return null;
		}
		return vehiculos.get(0);
	}

}
