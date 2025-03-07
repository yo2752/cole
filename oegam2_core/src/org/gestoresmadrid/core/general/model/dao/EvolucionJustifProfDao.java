package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesVO;

public interface EvolucionJustifProfDao extends GenericDao<EvolucionJustifProfesionalesVO>, Serializable {

	void guardarEvolucion(EvolucionJustifProfesionalesVO evolucionJustificanteProfVO);
}
