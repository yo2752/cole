package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoTramiteTraficoVO;

public interface VehiculoTramiteTraficoDao extends GenericDao<VehiculoTramiteTraficoVO>, Serializable {

	VehiculoTramiteTraficoVO getVehiculoTramite(BigDecimal numExpediente, Long idVehiculo);
}
