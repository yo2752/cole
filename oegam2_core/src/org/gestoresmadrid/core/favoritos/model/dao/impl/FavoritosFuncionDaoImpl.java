package org.gestoresmadrid.core.favoritos.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.favoritos.model.dao.FavoritosFuncionDao;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FavoritosFuncionDaoImpl extends GenericDaoImplHibernate<FuncionVO> implements FavoritosFuncionDao{
	private static final long serialVersionUID = 1L;
	
	public List<FuncionVO> getFuncionCompleta(String descFuncion) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		listCriterion.add(Restrictions.eq("descFuncion", descFuncion));		
		List<FuncionVO> lista = buscarPorCriteria(listCriterion, null, null);
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}

}
