package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcEvolucionTramiteDto;

public interface ServicioLcEvolucionTramite extends Serializable {

	void guardar(LcEvolucionTramiteDto evolucion);
}
