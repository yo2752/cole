package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.ViaVO;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ViaDto;

public interface ServicioVia extends Serializable {

	ViaVO getVia(String idProvincia, String idMunicipio, String idTipoVia, String via);

	List<String> listadoViasUnicasPorTipoVia(String idProvincia);

	List<ViaDto> listadoVias(String idProvincia, String idMunicipio, String idTipoVia);
}
