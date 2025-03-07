package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.ModeloCamDto;

public interface ServicioModeloCam extends Serializable {

	List<ModeloCamDto> listaModeloCam(String tipoVehiculo, String fechaDesde, String codigoMarca, String codigoModelo);
}
