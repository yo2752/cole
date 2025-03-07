package org.gestoresmadrid.core.trafico.materiales.model.dao;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.DelegacionVO;

public interface DelegacionDao extends GenericDao<DelegacionVO> {
	DelegacionVO findByPrimaryKey(Long delegacionId);
	DelegacionVO findByJefaturaProvincial(String jefaturaProvincial);
}
