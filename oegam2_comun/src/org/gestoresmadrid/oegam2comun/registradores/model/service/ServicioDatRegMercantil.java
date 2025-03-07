package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatRegMercantilDto;

public interface ServicioDatRegMercantil extends Serializable{

	public ResultRegistro getDatRegMercantil(String id);
	public ResultRegistro guardarOActualizarDatRegMercantil(DatRegMercantilDto datRegMercantilDto);
	public ResultRegistro borrarDatRegMercantil(String id);
	public ResultRegistro validarDatRegMercantil(DatRegMercantilDto datRegMercantilDto);
	public ResultRegistro validarDatRegMercantilCancelacion(DatRegMercantilDto datRegMercantilDto);
	public ResultRegistro guardarOActualizarDatRegMercantilCancelacion(DatRegMercantilDto datRegMercantilDto);

}
