package org.gestoresmadrid.core.evolucionSemaforo.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.evolucionSemaforo.model.vo.EvolucionSemaforoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EvolucionSemaforoDao extends Serializable, GenericDao<EvolucionSemaforoVO> {
	public EvolucionSemaforoVO obtenerEvolucionSemaforoVO(EvolucionSemaforoVO semaforo);
}
