package org.gestoresmadrid.oegam2comun.sistemaExplotacion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.sistemaExplotacion.view.dto.SistemaExplotacionDto;

public interface ServicioSistemaExplotacion extends Serializable{

	List<SistemaExplotacionDto> getListaSistemasExplotacion();

}
