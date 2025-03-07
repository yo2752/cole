package org.gestoresmadrid.core.pegatinas.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasStockDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class PegatinasStockDaoImpl extends GenericDaoImplHibernate<PegatinasStockVO> implements PegatinasStockDao{
	
	private static final long serialVersionUID = 5446003534181996255L;

	public PegatinasStockDaoImpl() {
		super();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<PegatinasStockVO> getListaStock(){
		return buscarPorCriteria(new ArrayList<Criterion>(), null, null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public PegatinasStockVO getStockByTipoPegatina(String tipo) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("tipo", tipo));
		List<PegatinasStockVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		if (lista.size()==0){
			
			return null;
		}else{
			PegatinasStockVO pegatinasStock = lista.get(0);
			return pegatinasStock;
		}

	}
	
	@Override
	@Transactional(readOnly=true)
	public PegatinasStockVO getStockByIdStock(int idStock) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("idStock", idStock));
		List<PegatinasStockVO> lista = buscarPorCriteria(listaCriterios, null, null);
		PegatinasStockVO pegatinasStock = lista.get(0);
		return pegatinasStock;
	}

	@Override
	@Transactional
	public int restarStockByTipo(String tipo) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("tipo", tipo));
		List<PegatinasStockVO> lista = buscarPorCriteria(listaCriterios, null, null);
		PegatinasStockVO pegatinasStock = lista.get(0);
		pegatinasStock.setStock(pegatinasStock.getStock() - 1);
		actualizar(pegatinasStock);
		return pegatinasStock.getStock();
		
	}

	@Override
	@Transactional(readOnly=true)
	public String getTipoByIdStock(int idStock) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("idStock", idStock));
		List<PegatinasStockVO> lista = buscarPorCriteria(listaCriterios, null, null);
		PegatinasStockVO pegatinasStock = lista.get(0);
		return pegatinasStock.getTipo();
	}

	
	@Override
	@Transactional
	public int restarStockByTipoyJefatura(String tipo, String jefatura) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		
		
		listaCriterios.add(Restrictions.eq("tipo", tipo));
		listaCriterios.add(Restrictions.eq("jefatura", jefatura));
		List<PegatinasStockVO> lista = buscarPorCriteria(listaCriterios, null, null);
		PegatinasStockVO pegatinasStock = lista.get(0);
		pegatinasStock.setStock(pegatinasStock.getStock() - 1);
		actualizar(pegatinasStock);
		return pegatinasStock.getStock();
		
	}

	@Override
	@Transactional
	public PegatinasStockVO getStockByJefaturayTipoPegatina(String tipo, String jefatura) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("tipo", tipo));
		listaCriterios.add(Restrictions.eq("jefatura", jefatura));
		List<PegatinasStockVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		if (lista.size()==0){
			
			return null;
		}else{
			PegatinasStockVO pegatinasStock = lista.get(0);
			return pegatinasStock;
		}
		
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public PegatinasStockVO getStockByTipoPegatinaJefatura(String tipo, String jefatura) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("tipo", tipo));
		listaCriterios.add(Restrictions.eq("jefatura", jefatura));
		List<PegatinasStockVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		if (lista.size()==0){
			
			return null;
		}else{
			PegatinasStockVO pegatinasStock = lista.get(0);
			return pegatinasStock;
		}
	}
}