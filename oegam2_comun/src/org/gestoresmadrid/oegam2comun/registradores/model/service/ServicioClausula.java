package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ClausulaDto;

public interface ServicioClausula extends Serializable{

	public ResultRegistro getClausula(String id);
	public ResultRegistro guardarOActualizarClausula(ClausulaDto clausulaDto, BigDecimal idTramiteRegistro);
	public ResultRegistro borrarClausula(String id);
	public ResultRegistro validarClausula(ClausulaDto clausulaDto);

}
