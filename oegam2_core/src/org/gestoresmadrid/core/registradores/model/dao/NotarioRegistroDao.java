package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.NotarioRegistroVO;

public interface NotarioRegistroDao extends GenericDao<NotarioRegistroVO>, Serializable {

	NotarioRegistroVO getNotarioPorID(String codigo);
	
}
