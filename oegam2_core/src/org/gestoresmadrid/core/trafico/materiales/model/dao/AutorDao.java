package org.gestoresmadrid.core.trafico.materiales.model.dao;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.AutorVO;

public interface AutorDao extends GenericDao<AutorVO> {
	AutorVO findByPrimaryKey(Long autorlId);
}
