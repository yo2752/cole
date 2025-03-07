package org.gestoresmadrid.core.evolucionDatosSensibles.model.dao;

import java.util.List;

import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesBastidorVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EvolucionDatosSensiblesBastidorDao extends GenericDao<EvolucionDatosSensiblesBastidorVO>{

	List<EvolucionDatosSensiblesBastidorVO> getEvolucion(String bastidor, String idGrupo);

	

}
