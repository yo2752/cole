package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;

public interface ServicioPropiedad extends Serializable{

	public ResultRegistro getPropiedad(String id);
	public ResultRegistro guardarOActualizarPropiedad(TramiteRegRbmDto tramiteRegRbmDto);
	public ResultRegistro borrarPropiedad(String id);
	public ResultRegistro validarPropiedad(TramiteRegRbmDto tramiteRegRbmDto);

}
