package org.gestoresmadrid.core.evolucionConsultaDev.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.evolucionConsultaDev.model.vo.EvolucionConsultaDevVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EvolucionConsultaDevDao extends Serializable,GenericDao<EvolucionConsultaDevVO>{

	int getNumPeticionesWS(Long idConsultaDev);

}
