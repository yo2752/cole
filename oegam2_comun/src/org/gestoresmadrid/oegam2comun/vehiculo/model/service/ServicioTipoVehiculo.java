package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.TipoVehiculoDto;

public interface ServicioTipoVehiculo extends Serializable {

	TipoVehiculoVO getTipoVehiculo(String tipoVehiculo);

	List<TipoVehiculoDto> getListaTiposVehiculos();

	List<TipoVehiculoVO> getTipoVehiculosPorTipoTramite(String tipoTramite);
}
