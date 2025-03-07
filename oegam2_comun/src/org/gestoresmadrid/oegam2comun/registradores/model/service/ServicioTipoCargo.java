package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.TipoCargoDto;

public interface ServicioTipoCargo extends Serializable {

	List<TipoCargoDto> getTipoCargo();
}
