package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.OtrosBienesRegistroDto;

public interface ServicioOtrosBienesRegistro extends Serializable{

	public ResultRegistro getOtrosBienesRegistro(String id);
	public ResultRegistro borrarOtrosBienesRegistro(String id);
	public ResultRegistro guardarOActualizarOtrosBienesRegistro(OtrosBienesRegistroDto otrosBienesRegistroDto, BigDecimal idPropiedad);
	public OtrosBienesRegistroDto getOtrosBienesPorPropiedad(String idPropiedad);
	public ResultRegistro validarOtrosBienesRegistro(OtrosBienesRegistroDto otrosBienesRegistroDto);

}
