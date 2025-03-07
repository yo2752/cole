package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.PactoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;


public interface PactoDao extends GenericDao<PactoVO>, Serializable {

	public PactoVO getPacto(String id);

}
