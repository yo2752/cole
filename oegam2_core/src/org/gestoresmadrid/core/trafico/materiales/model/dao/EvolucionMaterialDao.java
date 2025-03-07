package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionMaterialVO;

public interface EvolucionMaterialDao extends GenericDao<EvolucionMaterialVO>, Serializable {
	List<EvolucionMaterialVO> getListaEvolucionMateriales();
	EvolucionMaterialVO findEvolucionMaterialByPrimaryKey(Long evolucionMaterialId);
}
