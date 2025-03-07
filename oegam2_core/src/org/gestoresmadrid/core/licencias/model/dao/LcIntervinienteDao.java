package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.licencias.model.vo.LcIntervinienteVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcIntervinienteDao extends GenericDao<LcIntervinienteVO>, Serializable {

	LcIntervinienteVO getInterviniente(long id);

}
