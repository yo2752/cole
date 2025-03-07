package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AeronaveRegistroDto;

public interface ServicioAeronaveRegistro extends Serializable{

	public ResultRegistro getAeronaveRegistro(String id);
	public ResultRegistro borrarAeronaveRegistro(String id);
	public ResultRegistro guardarOActualizarAeronaveRegistro(AeronaveRegistroDto aeronaveRegistroDto, BigDecimal idPropiedad);
	public AeronaveRegistroDto getAeronavePorPropiedad(String idPropiedad);
	public ResultRegistro validarAeronaveRegistro(AeronaveRegistroDto aeronaveRegistroDto);

}
