package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegamComun.vehiculo.view.dto.ServicioTraficoDto;

public interface ServicioServicioTrafico extends Serializable {

	List<ServicioTraficoDto> getServicioTraficoPorTipoTramite(String tipoTramite);

	ServicioTraficoDto getServicioTrafico(String idServicio);
}
