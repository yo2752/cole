package org.gestoresmadrid.oegam2comun.modelos.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;

public interface ServicioOficinaLiquidadora extends Serializable{

	List<OficinaLiquidadoraDto> getListaOficinasLiquidadoras(String idProvinciaOficinaLiquid);

}
