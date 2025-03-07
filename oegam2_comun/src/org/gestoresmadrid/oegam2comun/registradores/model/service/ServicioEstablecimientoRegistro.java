package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.EstablecimientoRegistroDto;

public interface ServicioEstablecimientoRegistro extends Serializable{

	public ResultRegistro getEstablecimientoRegistro(String id);
	public ResultRegistro borrarEstablecimientoRegistro(String id);
	public ResultRegistro guardarOActualizarEstablecimientoRegistro(EstablecimientoRegistroDto establecimientoRegistroDto, BigDecimal idPropiedad);
	public EstablecimientoRegistroDto getEstablecimientoPorPropiedad(String idPropiedad);
	public ResultRegistro validarEstablecimientoRegistro(EstablecimientoRegistroDto establecimientoRegistroDto);

}
