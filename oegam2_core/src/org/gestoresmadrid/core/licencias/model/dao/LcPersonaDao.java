package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.licencias.model.vo.LcPersonaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcPersonaDao extends GenericDao<LcPersonaVO>, Serializable {

	LcPersonaVO getLcPersona(String nif, String numColegiado);
}
