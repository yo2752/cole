package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.gestoresmadrid.core.registradores.model.dao.CuadroAmortizacionDao;
import org.gestoresmadrid.core.registradores.model.vo.CuadroAmortizacionVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioCuadroAmortizacion;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CuadroAmortizacionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatosFinancierosDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioCuadroAmortizacionImpl implements ServicioCuadroAmortizacion {

	private static final long serialVersionUID = 7305605957238024710L;
	
	private static Logger log = Logger.getLogger(ServicioCuadroAmortizacionImpl.class);

	@Autowired
	private CuadroAmortizacionDao cuadroAmortizacionDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultRegistro getCuadroAmortizacion(String identificador) {
		ResultRegistro result = new ResultRegistro();

		CuadroAmortizacionDto cuadroAmortizacionDto = conversor.transform(cuadroAmortizacionDao.getCuadroAmortizacion(identificador), CuadroAmortizacionDto.class);

		if(null != cuadroAmortizacionDto){
			result.setObj(cuadroAmortizacionDto);
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener el cuadro de amortización");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarCuadroAmortizacion(CuadroAmortizacionDto cuadroAmortizacionDto, long idDatosFinancieros) {
		ResultRegistro result;

		result = validarCuadroAmortizacion(cuadroAmortizacionDto);
		if(result.isError())
			return result;

		//Si tenemos el identificador del cuadro entonces actualizamos, si no añadimos
		if (0 != cuadroAmortizacionDto.getIdCuadroAmortizacion()) {
			cuadroAmortizacionDto.setFecModificacion(utilesFecha.getTimestampActual());
		}else{
			cuadroAmortizacionDto.setIdDatosFinancieros(new BigDecimal(idDatosFinancieros));
			cuadroAmortizacionDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		if (null != cuadroAmortizacionDto.getFechaVencimientoCuadroAmort()){
			try {
				cuadroAmortizacionDto.setFechaVencimiento(cuadroAmortizacionDto.getFechaVencimientoCuadroAmort().getDate());
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}

		CuadroAmortizacionVO cuadroAmortizacionVO = cuadroAmortizacionDao.guardarOActualizar(conversor.transform(cuadroAmortizacionDto, CuadroAmortizacionVO.class));

		if(null != cuadroAmortizacionVO){
			result.setMensaje("Cuadro de amortización actualizado correctamente");
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar el cuadro de amortización");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarCuadroAmortizacion(String id){
		ResultRegistro result = new ResultRegistro();
		if (cuadroAmortizacionDao.borrar(cuadroAmortizacionDao.getCuadroAmortizacion(id))){
			result.setMensaje("Cuadro de amortización eliminado correctamente");
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando cuadro de amortización");
		}
		return result;
	}

	@Override
	public ResultRegistro validarCuadroAmortizacion(CuadroAmortizacionDto cuadroAmortizacionDto) {

		ResultRegistro result = new ResultRegistro();

		if(!UtilesValidaciones.validarFecha(cuadroAmortizacionDto.getFechaVencimientoCuadroAmort())){
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de vencimiento del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpRecupCostePte())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Coste pendiente del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpCargaFinan())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Carga financiera del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpCuotaNeta())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Carga neta del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpImpuesto())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Importe impuesto del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpTotalCuota())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Total cuota del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpDistiEntreCuenta())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Importe distribución del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpAmortizacion())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Importe amortizado del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpCapitalPendiente())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Capital pendiente del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpPlazo())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Importe del plazo del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpCapitalAmortizado())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Capital amortizado del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpInteresesPlazo())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Intereses plazo del cuadro de amortización no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarImporte(cuadroAmortizacionDto.getImpInteresDevengado())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Intereses devengados del cuadro de amortización no tiene el formato correcto.");
		}


		return result;
	}
	
	@Override
	public ResultRegistro validarCabeceraCuadroFI(DatosFinancierosDto datosFinancieros) {

		ResultRegistro result = new ResultRegistro();

		if(!UtilesValidaciones.validarObligatoriedad(datosFinancieros.getImpImpuesto()) 
				|| !UtilesValidaciones.validarObligatoriedad(datosFinancieros.getTipoInteresNominalAnualFi())
				|| !UtilesValidaciones.validarObligatoriedad(datosFinancieros.getTasaAnualEquivFi())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Para añadir o modificar un cuadro F.I deben estar rellenos el Importe impuesto, el Tipo nominal F.I. y el TAE F.I.");
		}

		return result;
	}
	
	@Override
	public ResultRegistro validarCabeceraCuadroFS(DatosFinancierosDto datosFinancieros) {

		ResultRegistro result = new ResultRegistro();

		if(!UtilesValidaciones.validarObligatoriedad(datosFinancieros.getImpSeguro()) 
				|| !UtilesValidaciones.validarObligatoriedad(datosFinancieros.getTipoInteresNominalAnualFs())
				|| !UtilesValidaciones.validarObligatoriedad(datosFinancieros.getTasaAnualEquivFs())){
			result.setError(Boolean.TRUE);
			result.addValidacion("Para añadir o modificar un cuadro F.S deben estar rellenos el Importe seguro, el Tipo nominal F.S. y el TAE F.S.");
		}

		return result;
	}

}
