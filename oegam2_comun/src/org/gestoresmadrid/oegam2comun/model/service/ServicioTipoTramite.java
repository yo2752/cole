package org.gestoresmadrid.oegam2comun.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;

public interface ServicioTipoTramite extends Serializable {

	List<String> busquedaTiposCreditosPorContrato(Long idContrato);

	List<TipoTramiteDto> getTipoTramitePorAplicacion(String aplicacion);

}
