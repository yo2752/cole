package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVO;

public interface IncidenciaSerialDao extends GenericDao<IncidenciaSerialVO>, Serializable {
	IncidenciaSerialVO findIncidenciaSerialBySerial(Long inciId, String serial);

	IncidenciaSerialVO findByIncidenciaConsejo(Long incidenciaInvId);
}
