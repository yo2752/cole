package org.icogam.sanciones.Modelo;

import java.text.ParseException;

import org.icogam.sanciones.DTO.SancionDto;
import org.icogam.sanciones.DTO.SancionListadosMotivosDto;

import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ModeloSancion {
	public SancionDto getSancion(SancionDto sancionInDto, String numColegiado)  throws ParseException;
	public SancionDto getSancionPorId(Integer idSancion, String numColegiado)  throws ParseException;
	public SancionDto actualizar(SancionDto sancionDto, String numColegiado) throws ParseException;
	public SancionDto guardar(SancionDto sancionDto) throws ParseException, OegamExcepcion;
	public void cambiarEstado(String[] idsSancion, String cambioEstado, String numColegiado);
	public ResultBean borrarSancion (int idSancion, String numColegiado) throws Exception;
	public SancionListadosMotivosDto getListado(String numColegiado, Fecha fechaListado) throws Throwable;
	public String transformToXML(SancionListadosMotivosDto sancionListadosMotivosDto);
}
