package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.licencias.model.vo.LcEpigrafeVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcEpigrafeDao extends GenericDao<LcEpigrafeVO>, Serializable {

	LcEpigrafeVO getEpigrafe(long id);

}
