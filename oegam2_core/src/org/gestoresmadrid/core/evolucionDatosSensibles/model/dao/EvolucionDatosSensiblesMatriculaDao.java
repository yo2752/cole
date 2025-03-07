package org.gestoresmadrid.core.evolucionDatosSensibles.model.dao;

import java.util.List;

import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EvolucionDatosSensiblesMatriculaDao extends GenericDao<EvolucionDatosSensiblesMatriculaVO>{

	List<EvolucionDatosSensiblesMatriculaVO> getEvolucion(String matricula, String idGrupo);

}
