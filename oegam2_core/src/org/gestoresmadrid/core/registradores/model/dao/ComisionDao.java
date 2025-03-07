package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.ComisionVO;




public interface ComisionDao extends GenericDao<ComisionVO>, Serializable {
	
	public ComisionVO getComision(String id);
	
}
