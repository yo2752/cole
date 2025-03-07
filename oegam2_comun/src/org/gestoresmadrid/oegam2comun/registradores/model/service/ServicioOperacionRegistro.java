package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.OperacionRegistroDto;

public interface ServicioOperacionRegistro extends Serializable {

	OperacionRegistroDto getOperacionRegistro(String codigo, String tipoTramite);

	List<OperacionRegistroDto> getOperacionesRegistro();
}
