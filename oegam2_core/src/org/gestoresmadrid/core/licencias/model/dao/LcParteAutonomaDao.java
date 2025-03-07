package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.licencias.model.vo.LcParteAutonomaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcParteAutonomaDao extends GenericDao<LcParteAutonomaVO>, Serializable {

	LcParteAutonomaVO getParteAutonoma(long id);

}
