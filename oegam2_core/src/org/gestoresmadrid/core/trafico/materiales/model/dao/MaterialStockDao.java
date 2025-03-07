package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;

public interface MaterialStockDao extends GenericDao<MaterialStockVO>, Serializable {

	List<MaterialStockVO> findStock();

	List<MaterialStockVO> findStockByJefaturaMaterial(String jefatura, Long material);

	MaterialStockVO findStockBystockInvId(Long stockInvId);

	List<MaterialStockVO> findStockByFecha(Date fecha);

	MaterialStockVO findStockPorJefaturaMaterial(String jefatura, Long material);

	MaterialStockVO findStockPorJefaturaTipo(String jefatura, String tipo);

	MaterialStockVO findStockByPrimaryKey(Long stockId);

	HashMap<String, HashMap<String, Long>> obtenerFacturacionSemanalStock(String jefaturaProvincial, Long tipoDocumento);
}
