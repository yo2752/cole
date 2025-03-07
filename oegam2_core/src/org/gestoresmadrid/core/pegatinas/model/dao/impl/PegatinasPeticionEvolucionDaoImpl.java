package org.gestoresmadrid.core.pegatinas.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasPeticionEvolucionDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasPeticionEvolucionVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PegatinasPeticionEvolucionDaoImpl extends GenericDaoImplHibernate<PegatinasPeticionEvolucionVO> implements PegatinasPeticionEvolucionDao{
	
	private static final long serialVersionUID = 5446003534181996255L;

	public PegatinasPeticionEvolucionDaoImpl() {
		super();
	}

	@Override
	@Transactional(readOnly=true)
	public List<PegatinasPeticionEvolucionVO> getPegatinasPeticionEvolucionByIdPeticion(Integer idPeticion){
		
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		List<String> listOrden = new ArrayList<String>();
		listOrden.add("idEvolucion");
		
		listaCriterios.add(Restrictions.eq("idStockPeti", idPeticion));
		
		List<PegatinasPeticionEvolucionVO> lista = buscarPorCriteria(listaCriterios, "asc", listOrden, null, null);
		
		return lista;
	}
	
}
