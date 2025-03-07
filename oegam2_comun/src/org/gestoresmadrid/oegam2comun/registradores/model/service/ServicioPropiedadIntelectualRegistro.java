package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadIntelectualRegistroDto;

public interface ServicioPropiedadIntelectualRegistro extends Serializable{

	public ResultRegistro getPropiedadIntelectualRegistro(String id);
	public ResultRegistro borrarPropiedadIntelectualRegistro(String id);
	public ResultRegistro guardarOActualizarPropiedadIntelectualRegistro(PropiedadIntelectualRegistroDto propiedadIntelectualRegistroDto, BigDecimal idPropiedad);
	public PropiedadIntelectualRegistroDto getPropiedadIntelectualPorPropiedad(String idPropiedad);
	public ResultRegistro validarPropiedadIntelectualRegistro(PropiedadIntelectualRegistroDto propiedadIntelectualRegistroDto);

}
