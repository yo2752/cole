package org.gestoresmadrid.core.pegatinas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockVO;

public interface PegatinasStockDao extends GenericDao<PegatinasStockVO>, Serializable{

	public List<PegatinasStockVO> getListaStock();
	
	public PegatinasStockVO getStockByTipoPegatina(String tipo);
	
	public PegatinasStockVO getStockByJefaturayTipoPegatina(String tipo, String jefatura);
	
	public PegatinasStockVO getStockByIdStock(int idStock);

	public int restarStockByTipo(String tipo);

	public String getTipoByIdStock(int idStock);

	public int restarStockByTipoyJefatura(String tipo, String jefatura);
	
	public PegatinasStockVO getStockByTipoPegatinaJefatura(String tipo, String jefatura);
}
