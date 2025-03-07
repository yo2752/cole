package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcDireccionDao extends GenericDao<LcDireccionVO>, Serializable {
	LcDireccionVO getDireccion(long id);
}
