package org.gestoresmadrid.core.colegio.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.general.model.vo.ColegioVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ColegioDao extends GenericDao<ColegioVO>, Serializable{

	ColegioVO getColegio(String colegio);

}
