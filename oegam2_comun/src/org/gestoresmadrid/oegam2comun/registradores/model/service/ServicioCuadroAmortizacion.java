package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CuadroAmortizacionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatosFinancierosDto;

public interface ServicioCuadroAmortizacion extends Serializable{

	public ResultRegistro getCuadroAmortizacion(String id);
	public ResultRegistro guardarOActualizarCuadroAmortizacion(CuadroAmortizacionDto cuadroAmortizacionDto, long idDatosFinancieros);
	public ResultRegistro borrarCuadroAmortizacion(String id);
	public ResultRegistro validarCuadroAmortizacion(CuadroAmortizacionDto cuadroAmortizacionDto);
	public ResultRegistro validarCabeceraCuadroFI(DatosFinancierosDto datosFinancieros);
	public ResultRegistro validarCabeceraCuadroFS(DatosFinancierosDto datosFinancieros);


}
