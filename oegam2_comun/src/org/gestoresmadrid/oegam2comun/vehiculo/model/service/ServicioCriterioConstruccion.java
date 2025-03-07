package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.CriterioConstruccionDto;

public interface ServicioCriterioConstruccion extends Serializable {
	
	public static String TODOS = "**";
	
	CriterioConstruccionDto getCriterioConstruccion(String criterioConstruccion);

	List<CriterioConstruccionDto> getListadoCriterioConstruccion(String[] criteriosConstruccion);
	
	List<CriterioConstruccionDto> listadoCriterioConstruccion(String tipoVehiculo);

	Boolean esVehiculoEspecialCriterioConstruccion(String idCriterioConstruccion);
}
