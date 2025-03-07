package org.gestoresmadrid.core.notario.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.notario.model.vo.NotarioVO;

public interface NotarioDao extends GenericDao<NotarioVO>, Serializable{

	NotarioVO getNotarioPorID(String codigo);

}
