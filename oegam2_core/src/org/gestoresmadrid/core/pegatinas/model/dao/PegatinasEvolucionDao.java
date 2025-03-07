package org.gestoresmadrid.core.pegatinas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasEvolucionVO;

public interface PegatinasEvolucionDao extends GenericDao<PegatinasEvolucionVO>, Serializable{
	
	List<PegatinasEvolucionVO> getPegatinasEvolucionByIdPegatina(Integer idPegatina);
	
}
