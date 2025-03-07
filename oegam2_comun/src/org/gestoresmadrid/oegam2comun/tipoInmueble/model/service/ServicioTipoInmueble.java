package org.gestoresmadrid.oegam2comun.tipoInmueble.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.oegam2comun.tipoInmueble.view.dto.TipoInmuebleDto;

public interface ServicioTipoInmueble extends Serializable{

	List<TipoInmuebleDto> getListaTiposInmueblesPorTipo(TipoBien tipoBien);

}
