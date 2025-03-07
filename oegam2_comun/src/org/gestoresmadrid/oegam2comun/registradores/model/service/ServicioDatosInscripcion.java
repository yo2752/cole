package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatosInscripcionDto;

public interface ServicioDatosInscripcion extends Serializable{

	public ResultRegistro guardarOActualizarDatosInscripcion(DatosInscripcionDto datosInscripcionDto);
	public ResultRegistro validarDatosInscripcion(DatosInscripcionDto datosInscripcionDto);

}
