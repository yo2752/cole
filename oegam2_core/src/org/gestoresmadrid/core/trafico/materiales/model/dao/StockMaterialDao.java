package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;
import java.util.HashMap;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.StockMaterialVO;

public interface StockMaterialDao extends GenericDao<StockMaterialVO>, Serializable{
	StockMaterialVO getElementById(Long id);
	
	StockMaterialVO comprobarExistente(StockMaterialVO vo);
	
	StockMaterialVO findByJefaturaTipo(String jefatura, String tipo);

	HashMap<String, HashMap<String, Long>> obtenerFacturacionSemanalStock(String jefaturaProvincial, Long tipoDocumento);
}
