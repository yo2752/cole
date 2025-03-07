package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;

public interface EvolucionVehiculoDao extends GenericDao<EvolucionVehiculoVO>, Serializable {

	public static final String SINCODIG = "SINCODIG";

	List<EvolucionVehiculoVO> getEvolucionVehiculo(Long idVehiculo, String numColegiado, List<String> tipoActualizacion);

	EvolucionVehiculoVO getEvolucionVehiculoSinCodig(Long idVehiculo, BigDecimal numExpediente);
}