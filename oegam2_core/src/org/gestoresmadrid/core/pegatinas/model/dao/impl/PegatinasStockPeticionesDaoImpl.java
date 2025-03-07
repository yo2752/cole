package org.gestoresmadrid.core.pegatinas.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasStockPeticionesDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockPeticionesVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PegatinasStockPeticionesDaoImpl extends GenericDaoImplHibernate<PegatinasStockPeticionesVO> implements PegatinasStockPeticionesDao{
	
	private static final long serialVersionUID = 5446003534181996255L;

	public PegatinasStockPeticionesDaoImpl() {
		super();
	}

	@Override
	@Transactional(readOnly=true)
	public PegatinasStockPeticionesVO getPeticionStockById(String id) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("idPeticion", Integer.parseInt(id)));
		List<PegatinasStockPeticionesVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		return lista.get(0);
	}
	
	@Override
	@Transactional(readOnly=true)
	public PegatinasStockPeticionesVO getPeticionStockByIdentificador(String identificador) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("identificador", identificador));
		List<PegatinasStockPeticionesVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		return lista.get(0);
	}

}