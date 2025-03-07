package org.gestoresmadrid.core.actualizacionMF.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.actualizacionMF.model.vo.ActualizacionMFVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ActualizacionMFDao extends Serializable, GenericDao<ActualizacionMFVO> {
	

	ActualizacionMFVO buscarActualizacion(Long idActualizacion);
	
	ActualizacionMFVO buscarActualizacionPendiente();
	
}
