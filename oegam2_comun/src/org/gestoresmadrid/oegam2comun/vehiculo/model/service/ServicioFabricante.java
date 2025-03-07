package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.FabricanteDto;

public interface ServicioFabricante {

	FabricanteVO getFabricante(String fabricante);

	List<FabricanteDto> listaFabricantesPorMarca(String codMarca);

	List<FabricanteDto> listaFabricantesInactivos();

	FabricanteVO addFabricante(String fabricante);
}
