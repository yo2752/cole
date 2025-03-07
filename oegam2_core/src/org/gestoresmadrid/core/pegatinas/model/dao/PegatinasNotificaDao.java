package org.gestoresmadrid.core.pegatinas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasNotificaVO;

public interface PegatinasNotificaDao extends GenericDao<PegatinasNotificaVO>, Serializable{
	
	List<PegatinasNotificaVO> getAllPegatinasHoyByJefatura(String jefatura);
	
}
