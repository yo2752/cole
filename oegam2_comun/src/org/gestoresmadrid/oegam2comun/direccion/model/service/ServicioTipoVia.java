package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;

public interface ServicioTipoVia extends Serializable {

	TipoViaVO getTipoVia(String idTipoVia);

	TipoViaVO getTipoViaDgt(String idTipoViaDgt);

	TipoViaVO getIdTipoViaPorDesc(String descTipoVia);

	List<TipoViaDto> listadoTipoVias(String idProvincia);
}
