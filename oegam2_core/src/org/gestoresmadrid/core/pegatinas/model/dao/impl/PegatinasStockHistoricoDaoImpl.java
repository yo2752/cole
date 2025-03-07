package org.gestoresmadrid.core.pegatinas.model.dao.impl;

import java.util.Date;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasStockHistoricoDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockHistoricoVO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PegatinasStockHistoricoDaoImpl extends GenericDaoImplHibernate<PegatinasStockHistoricoVO> implements PegatinasStockHistoricoDao{
	
	private static final long serialVersionUID = 5446003534181996255L;

	public PegatinasStockHistoricoDaoImpl() {
		super();
	}

	@Override
	@Transactional
	public void insertarHistorico(int idStock, String accion, int stockRestante, String tipo, String matricula) {
		PegatinasStockHistoricoVO historico = new PegatinasStockHistoricoVO();
		historico.setIdStock(idStock);
		historico.setAccion(accion);
		historico.setStock(stockRestante);
		historico.setFecha(new Date());
		historico.setTipo(tipo);
		historico.setMatricula(matricula);
		guardar(historico);
	}

}