package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;

public interface MaterialDao extends GenericDao<MaterialVO>, Serializable {
	List<MaterialVO> getListaMateriales();
	MaterialVO findByPrimaryKey(Long materialId);
	MaterialVO findMaterialByTipo(String tipo);
}
