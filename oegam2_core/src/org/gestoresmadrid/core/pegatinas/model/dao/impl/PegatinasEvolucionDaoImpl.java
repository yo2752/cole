package org.gestoresmadrid.core.pegatinas.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasEvolucionDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasEvolucionVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PegatinasEvolucionDaoImpl extends GenericDaoImplHibernate<PegatinasEvolucionVO> implements PegatinasEvolucionDao{
	
	private static final long serialVersionUID = 5446003534181996255L;

	public PegatinasEvolucionDaoImpl() {
		super();
	}

	@Override
	@Transactional(readOnly=true)
	public List<PegatinasEvolucionVO> getPegatinasEvolucionByIdPegatina(Integer idPegatina){
		
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		List<String> listOrden = new ArrayList<String>();
		listOrden.add("idEvolucion");
		
		listaCriterios.add(Restrictions.eq("idPegatina", idPegatina));
		
		List<PegatinasEvolucionVO> lista = buscarPorCriteria(listaCriterios, "asc", listOrden, null, null);
		
		return lista;
	}
	
}
