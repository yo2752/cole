package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.EvolucionVehiculoDto;

import escrituras.beans.ResultBean;

public interface ServicioEvolucionVehiculo extends Serializable {

	public static final String EVOLUCION_VEHICULO = "EVOLUCIONVEHICULO";

	public static final String TIPO_ACTUALIZACION_CRE = "CREACIÓN";
	public static final String TIPO_ACTUALIZACION_MOD = "MODIFICACIÓN";
	public static final String TIPO_ACTUALIZACION_COP = "COPIA";
	public static final String TIPO_ACTUALIZACION_LIB_NIV = "LIBERAR NIVE";

	List<EvolucionVehiculoVO> getEvolucionVehiculo(Long idVehiculo, String numColegiado, ArrayList<String> tipoActualizacion);

	ResultBean guardarEvolucion(EvolucionVehiculoDto evolucion);

	void actualizarEvolucionVehiculoCreacionOCopia(Long idVehiculo, BigDecimal numExpediente);

	void eliminar(EvolucionVehiculoVO evolucion);

	String obtenerSinCodig(Long idVehiculo, BigDecimal numExpediente);
}
