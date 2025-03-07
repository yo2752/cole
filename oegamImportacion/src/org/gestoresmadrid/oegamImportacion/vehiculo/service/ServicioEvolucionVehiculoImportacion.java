package org.gestoresmadrid.oegamImportacion.vehiculo.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;

public interface ServicioEvolucionVehiculoImportacion extends Serializable {

	public static final String TIPO_ACTUALIZACION_CRE = "CREACIÓN";
	public static final String TIPO_ACTUALIZACION_MOD = "MODIFICACIÓN";
	public static final String TIPO_ACTUALIZACION_COP = "COPIA";

	void guardarEvolucion(Long idVehiculo, String numColegiado, BigDecimal numExpediente, String tipoTramite, String tipoActualizacion, Long idUsuario, Long idVehiculoOrigen);

	void guardarEvolucionVO(EvolucionVehiculoVO evolucionVehiculo, Long idVehiculoNuevo, String numColegiado, BigDecimal numExpediente, String tipoTramite, String tipoActualizacionMod,
			Long idUsuario);
}
