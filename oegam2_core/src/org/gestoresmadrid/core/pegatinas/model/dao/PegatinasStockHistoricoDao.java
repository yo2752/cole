package org.gestoresmadrid.core.pegatinas.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockHistoricoVO;

public interface PegatinasStockHistoricoDao extends GenericDao<PegatinasStockHistoricoVO>, Serializable{

	void insertarHistorico(int idStock, String accion, int stockRestante, String tipo, String matricula);
	
}
