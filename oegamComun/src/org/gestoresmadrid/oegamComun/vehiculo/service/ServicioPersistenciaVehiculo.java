package org.gestoresmadrid.oegamComun.vehiculo.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;

public interface ServicioPersistenciaVehiculo extends Serializable{

	VehiculoVO getVehiculoVO(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estado);

	Date calculoItvPQ(VehiculoVO vehiculoVO, Date fechaPresentacion, String tipoTramite);

	VehiculoVO guardarVehiculoConEvolucionVO(VehiculoVO vehiculo, EvolucionVehiculoVO evolucionVehiculo, Boolean guardarDir, Boolean guardarIntegrador, EvolucionPersonaVO evolucionIntegrador);

	VehiculoVO copiaVehiculoDesactivandoAnt(VehiculoVO vehiculoCopia, VehiculoVO vehiculoAnt, EvolucionVehiculoVO evolucionVehiculo, BigDecimal numExpediente, String tipoTramite, Long idUsuario, Boolean guardarDir, Boolean guardarIntegrador, EvolucionPersonaVO evolucionIntegrador);

	void actualizarVehiculoConEvolucionVO(VehiculoVO vehiculoCompleto, EvolucionVehiculoVO evolucionVehiculo,
			Boolean guardarDir, Boolean guardarIntegrador, EvolucionPersonaVO evolucionIntegrador);

}
