package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MaquinariaRegistroDto;

public interface ServicioMaquinariaRegistro extends Serializable{

	public ResultRegistro getMaquinariaRegistro(String id);
	public ResultRegistro borrarMaquinariaRegistro(String id);
	public ResultRegistro guardarOActualizarMaquinariaRegistro(MaquinariaRegistroDto maquinariaRegistroDto, BigDecimal idPropiedad);
	public MaquinariaRegistroDto getMaquinariaPorPropiedad(String idPropiedad);
	public ResultRegistro validarMaquinariaRegistro(MaquinariaRegistroDto maquinariaRegistroDto);

}
