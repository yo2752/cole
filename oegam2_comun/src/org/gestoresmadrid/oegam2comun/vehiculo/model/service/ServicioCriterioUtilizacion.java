package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.CriterioUtilizacionDto;

public interface ServicioCriterioUtilizacion extends Serializable {

	public static String TODOS = "**";

	CriterioUtilizacionDto getCriterioUtilizacion(String criterioUtilizacion);

	List<CriterioUtilizacionDto> getListadoCriterioUtilizacion(String[] criteriosUtilizacion);

	List<CriterioUtilizacionDto> listadoCriterioUtilizacion(String tipoVehiculo);
}
