package org.gestoresmadrid.core.bien.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.bien.model.vo.BienUrbanoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface BienUrbanoDao extends Serializable, GenericDao<BienUrbanoVO>{

	BienUrbanoVO getBienUrbanoPorId(Long idBien);

}
