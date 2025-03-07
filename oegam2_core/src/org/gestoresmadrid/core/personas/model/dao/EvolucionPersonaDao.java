package org.gestoresmadrid.core.personas.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;

public interface EvolucionPersonaDao extends GenericDao<EvolucionPersonaVO> {

	List<EvolucionPersonaVO> getEvolucionPersona(String nif, String numColegiado, ArrayList<String> tipoActualizacion);
}
