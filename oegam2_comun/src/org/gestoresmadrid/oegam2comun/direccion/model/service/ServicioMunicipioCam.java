package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioCamVO;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;

public interface ServicioMunicipioCam extends Serializable {

	List<MunicipioCamDto> getListaMunicipiosPorProvincia(String idProvincia);

	MunicipioCamVO getMunicipio(String idMunicipio, String idProvincia);

	String getMunicipioNombre(String idMunicipio, String idProvincia);
}
