package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoEITVVO;

public interface VehiculoEITVDao extends GenericDao<VehiculoEITVVO>, Serializable {

	VehiculoEITVVO getVehiculoEITV(Long id, String numColegiado, String cif, String estado);

}
