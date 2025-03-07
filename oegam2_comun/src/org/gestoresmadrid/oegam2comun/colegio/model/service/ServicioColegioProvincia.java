package org.gestoresmadrid.oegam2comun.colegio.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;

public interface ServicioColegioProvincia extends Serializable {

	List<ColegioDto> listadoColegio(String idProvincia);
}
