package org.gestoresmadrid.core.evolucionDatosSensibles.model.dao;

import java.util.List;

import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesNifVO;
import org.gestoresmadrid.core.model.dao.GenericDao;


public interface EvolucionDatosSensiblesNifDao extends GenericDao<EvolucionDatosSensiblesNifVO>{


	List<EvolucionDatosSensiblesNifVO> getEvolucion(String nif, String idGrupo);

}
