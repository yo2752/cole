package org.gestoresmadrid.core.impr.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.EvolucionDocImprVO;
import org.gestoresmadrid.core.model.dao.GenericDao;


public interface EvolucionDocImprDao extends GenericDao<EvolucionDocImprVO>, Serializable{

	List<EvolucionDocImprVO> obtenerEvolucionDocImpr(Long docId, Boolean completo);


}
