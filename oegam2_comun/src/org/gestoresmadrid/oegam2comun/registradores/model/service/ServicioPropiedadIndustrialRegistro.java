package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadIndustrialRegistroDto;

public interface ServicioPropiedadIndustrialRegistro extends Serializable{

	public ResultRegistro getPropiedadIndustrialRegistro(String id);
	public ResultRegistro borrarPropiedadIndustrialRegistro(String id);
	public ResultRegistro guardarOActualizarPropiedadIndustrialRegistro(PropiedadIndustrialRegistroDto propiedadIndustrialRegistroDto, BigDecimal idPropiedad);
	public PropiedadIndustrialRegistroDto getPropiedadIndustrialPorPropiedad(String idPropiedad);
	public ResultRegistro validarPropiedadIndustrialRegistro(PropiedadIndustrialRegistroDto propiedadIndustrialRegistroDto);

}
