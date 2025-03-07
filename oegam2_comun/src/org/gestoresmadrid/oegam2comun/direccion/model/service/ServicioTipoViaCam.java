package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.TipoViaCamVO;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaCamDto;

public interface ServicioTipoViaCam extends Serializable{

	List<TipoViaCamDto> getListaTipoVias();

	TipoViaCamVO getTipoVia(String idTipoVia);

}
