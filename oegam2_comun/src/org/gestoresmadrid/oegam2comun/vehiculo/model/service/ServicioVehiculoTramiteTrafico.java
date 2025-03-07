package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoTramiteTraficoVO;

public interface ServicioVehiculoTramiteTrafico extends Serializable {

	VehiculoTramiteTraficoVO getVehiculoTramite(BigDecimal numExpediente, Long idVehiculo);
}
