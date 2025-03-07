package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.VehiculoRegistroDto;

public interface ServicioVehiculoRegistro extends Serializable{

	public ResultRegistro getVehiculoRegistro(String id);
	public ResultRegistro borrarVehiculoRegistro(String id);
	public ResultRegistro guardarOActualizarVehiculoRegistro(VehiculoRegistroDto vehiculoRegistroDto, BigDecimal idPropiedad);
	public VehiculoRegistroDto getVehiculoPorPropiedad(String idPropiedad);
	public ResultRegistro validarVehiculo(VehiculoRegistroDto vehiculo);

}
