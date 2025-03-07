package org.gestoresmadrid.core.ficheros.temporales.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.ficheros.temporales.model.vo.FicherosTemporalesVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface FicherosTemporalesDao extends GenericDao<FicherosTemporalesVO>, Serializable {

	List<FicherosTemporalesVO> getFicheroPorId(FicherosTemporalesVO ficherosTemporalesVO);

}
