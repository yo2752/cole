package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ComisionDto;

public interface ServicioComision extends Serializable{

	public ResultRegistro getComision(String id);
	public ResultRegistro guardarOActualizarComision(ComisionDto comisionDto, long idDatosFinancieros);
	public ResultRegistro borrarComision(String id);
	public ResultRegistro validarComision(ComisionDto comisionDto);

}
