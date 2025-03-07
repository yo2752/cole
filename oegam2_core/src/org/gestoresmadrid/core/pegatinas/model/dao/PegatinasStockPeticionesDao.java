package org.gestoresmadrid.core.pegatinas.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockPeticionesVO;

public interface PegatinasStockPeticionesDao extends GenericDao<PegatinasStockPeticionesVO>, Serializable{

	PegatinasStockPeticionesVO getPeticionStockById(String id);
	
	PegatinasStockPeticionesVO getPeticionStockByIdentificador(String identificador);

}
