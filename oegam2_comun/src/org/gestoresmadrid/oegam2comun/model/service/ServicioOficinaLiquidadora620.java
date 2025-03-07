package org.gestoresmadrid.oegam2comun.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.OficinaLiquidadora620VO;
import org.gestoresmadrid.oegam2comun.view.dto.OficinaLiquidadora620Dto;

public interface ServicioOficinaLiquidadora620 extends Serializable {

	List<OficinaLiquidadora620Dto> listadoOficinasLiquidadoras(String idMunicipio);

	List<OficinaLiquidadora620VO> getOficinasLiquidadoras(String oficinaLiquidadora);
}
