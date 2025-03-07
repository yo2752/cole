package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;

public interface ServicioProvincia extends Serializable {

	ProvinciaVO getProvincia(String idProvincia);

	ProvinciaDto getProvinciaDto(String idProvincia);

	ProvinciaDto getProvinciaPorNombre(String nombre);

	List<ProvinciaDto> getListaProvincias();
}
