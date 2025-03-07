package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoCriteriosVO;

public interface ServicioTipoVehiculoCriterios extends Serializable {

	List<TipoVehiculoCriteriosVO> listaTipoVehiculoCriterios(String tipoVehiculo);

	List<String> listaCriteriosConstruccion(String tipoVehiculo);

	List<String> listaCriteriosUtilizacion(String tipoVehiculo);
}
