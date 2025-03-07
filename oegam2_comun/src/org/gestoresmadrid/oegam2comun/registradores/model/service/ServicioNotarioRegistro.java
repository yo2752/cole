package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.NotarioRegistroDto;


public interface ServicioNotarioRegistro extends Serializable{

	NotarioRegistroDto getNotarioPorId(String codigo);
	
	ResultRegistro guardarOActualizarNotario(IntervinienteRegistroDto interviniente);
	
	ResultRegistro borrarNotario(String id);
	
	ResultRegistro validarNotario(IntervinienteRegistroDto interviniente);

}
