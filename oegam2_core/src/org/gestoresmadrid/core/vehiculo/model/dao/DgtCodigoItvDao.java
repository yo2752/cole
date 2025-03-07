package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.DgtCodigoItvVO;

public interface DgtCodigoItvDao extends GenericDao<DgtCodigoItvVO>, Serializable {

	DgtCodigoItvVO obtenerDatosItv(String codigoItv);
}
