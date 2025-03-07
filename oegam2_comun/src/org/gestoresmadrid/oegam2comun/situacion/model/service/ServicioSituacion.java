package org.gestoresmadrid.oegam2comun.situacion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.situacion.view.dto.SituacionDto;

public interface ServicioSituacion extends Serializable{

	List<SituacionDto> getListaSituacion();

}
