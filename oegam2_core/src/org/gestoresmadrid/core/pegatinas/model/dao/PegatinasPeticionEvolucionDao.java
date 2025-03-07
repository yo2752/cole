package org.gestoresmadrid.core.pegatinas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasPeticionEvolucionVO;

public interface PegatinasPeticionEvolucionDao extends GenericDao<PegatinasPeticionEvolucionVO>, Serializable{
	
	List<PegatinasPeticionEvolucionVO> getPegatinasPeticionEvolucionByIdPeticion(Integer idPeticion);
	
}
