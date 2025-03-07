package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PactoDto;

public interface ServicioPacto extends Serializable{

	public ResultRegistro getPacto(String id);
	public ResultRegistro guardarOActualizarPacto(PactoDto pactoDto, BigDecimal idTramiteRegistro);
	public ResultRegistro borrarPacto(String id);
	public ResultRegistro validarPacto(PactoDto pactoDto);

}
