package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface DireccionDao extends GenericDao<DireccionVO>, Serializable {
	
	public DireccionVO getDireccion(Long idDireccion);
}
