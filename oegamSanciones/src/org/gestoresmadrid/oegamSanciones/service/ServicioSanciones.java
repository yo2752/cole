package org.gestoresmadrid.oegamSanciones.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;

import org.gestoresmadrid.oegamSanciones.view.beans.ResultadoSancionesBean;
import org.gestoresmadrid.oegamSanciones.view.dto.SancionDto;
import org.gestoresmadrid.oegamSanciones.view.dto.SancionListadosMotivosDto;

import utilidades.estructuras.Fecha;

public interface ServicioSanciones extends Serializable {

	public SancionDto getSancion(SancionDto sancionInDto) throws ParseException;

	public SancionDto getSancionPorId(Integer idSancion);

	public SancionListadosMotivosDto getListado(String numColegiado, Fecha fechaListado) throws Throwable;

	public ResultadoSancionesBean generarInforme(String[] codSeleccionados) throws Exception;

	public ResultadoSancionesBean borrarSancion(String[] codSeleccionados);

	public ResultadoSancionesBean cambiarEstados(String[] idsSancion, String cambioEstado);

	public ResultadoSancionesBean guardarSancion(SancionDto sancionDto, BigDecimal idUsuario, boolean esAdmin) throws Exception;

}
