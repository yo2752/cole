package org.gestoresmadrid.core.pegatinas.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PegatinasDaoImpl extends GenericDaoImplHibernate<PegatinasVO> implements PegatinasDao{
	
	private static final long serialVersionUID = 5446003534181996255L;


	public PegatinasDaoImpl() {
		super();
	}

	@Override
	@Transactional(readOnly=true)
	public List<PegatinasVO> getAllPegatinas(){
		
		return buscarPorCriteria(new ArrayList<Criterion>(), null, null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public PegatinasVO getPegatinaByIdPegatina(Integer idPegatina){
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("idPegatina", idPegatina));
		List<PegatinasVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		return lista.get(0);
	}
	
	
	@Override
	@Transactional
	public String getNumExpedienteByMatricula(String matricula) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("matricula", matricula));
		List<PegatinasVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		return lista.get(0).getNumExpediente().toString();
	}

	@Override
	@Transactional
	public void cambiarEstadoPegatina(String idPegatina, Integer estado, String descrEstado){
		PegatinasVO pegatina = getPegatinaByIdPegatina(Integer.parseInt(idPegatina));
		pegatina.setDescrEstado(descrEstado);
		pegatina.setEstado(estado);
		guardar(pegatina);
	}

	@Override
	@Transactional
	public PegatinasVO getPegatinaByExpediente(BigDecimal expediente) {
		
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("numExpediente", expediente));
		List<PegatinasVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		return lista.get(0);
	}

	
	@Override
	@Transactional(readOnly=true)
	public PegatinasVO getPegatinasByJefatura(String jefatura){
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("jefatura", jefatura));
		List<PegatinasVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		return lista.get(0);
	}
	
}
