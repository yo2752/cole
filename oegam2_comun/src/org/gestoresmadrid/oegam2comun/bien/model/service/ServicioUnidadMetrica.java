package org.gestoresmadrid.oegam2comun.bien.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.bien.view.dto.UnidadMetricaDto;

public interface ServicioUnidadMetrica extends Serializable{

	List<UnidadMetricaDto> getListaUnidadesMetricas();

}
