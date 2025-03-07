package org.gestoresmadrid.oegamImportacion.direccion.service;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;

public interface ServicioTipoViaImportacion extends Serializable {

	TipoViaVO getTipoVia(String idTipoVia);

	TipoViaVO getTipoViaDgt(String idTipoViaDgt);

	TipoViaVO getIdTipoViaPorDesc(String descTipoVia);

}
