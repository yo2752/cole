package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;

public interface ServicioMunicipio extends Serializable {

	MunicipioVO getMunicipio(String idMunicipio, String idProvincia);

	List<MunicipioDto> listaMunicipios(String idProvincia);

	List<MunicipioVO> listadoOficinasLiquidadoras(String idMunicipio);

	MunicipioDto getMunicipioPorNombre(String nombreMunicipio, String idProvincia);

	MunicipioDto getMunicipioDto(String idProvincia, String idMunicipio);
}
