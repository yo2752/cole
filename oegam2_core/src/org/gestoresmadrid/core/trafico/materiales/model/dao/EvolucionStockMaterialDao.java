package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionStockMaterialesVO;

public interface EvolucionStockMaterialDao extends GenericDao<EvolucionStockMaterialesVO>, Serializable{
	public List<EvolucionStockMaterialesVO> getElementByIdStock(Long id);
	void eliminarByIdStock(Long id);
}
