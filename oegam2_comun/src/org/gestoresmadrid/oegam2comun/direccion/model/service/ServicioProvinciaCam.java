package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.ProvinciaCamVO;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ProvinciaCamDto;

public interface ServicioProvinciaCam extends Serializable {

	List<ProvinciaCamDto> getListaProvinciasCam();

	ProvinciaCamVO getProvincia(String idProvincia);

	String getProvinciaNombre(String idProvincia);

}
