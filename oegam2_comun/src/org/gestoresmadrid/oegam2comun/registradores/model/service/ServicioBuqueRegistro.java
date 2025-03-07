package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.BuqueRegistroDto;

public interface ServicioBuqueRegistro extends Serializable{

	public ResultRegistro getBuqueRegistro(String id);
	public ResultRegistro borrarBuqueRegistro(String id);
	public ResultRegistro guardarOActualizarBuqueRegistro(BuqueRegistroDto buqueRegistroDto, BigDecimal idPropiedad);
	public BuqueRegistroDto getBuquePorPropiedad(String idPropiedad);
	public ResultRegistro validarBuqueRegistro(BuqueRegistroDto buqueRegistroDto);

}
