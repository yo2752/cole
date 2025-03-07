package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.PropiedadVO;

public interface PropiedadDao extends GenericDao<PropiedadVO>, Serializable {
	
	public PropiedadVO getPropiedad(String id);
}
