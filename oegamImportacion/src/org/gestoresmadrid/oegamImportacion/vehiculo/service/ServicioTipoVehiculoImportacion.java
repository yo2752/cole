package org.gestoresmadrid.oegamImportacion.vehiculo.service;

import java.io.Serializable;

import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;

public interface ServicioTipoVehiculoImportacion extends Serializable {

	TipoVehiculoVO getTipoVehiculo(String tipoVehiculo);
}
