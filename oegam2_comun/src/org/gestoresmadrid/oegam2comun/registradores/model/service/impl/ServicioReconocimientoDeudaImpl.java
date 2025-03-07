package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.gestoresmadrid.core.registradores.model.dao.ReconocimientoDeudaDao;
import org.gestoresmadrid.core.registradores.model.vo.ReconocimientoDeudaVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioReconocimientoDeuda;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ReconocimientoDeudaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioReconocimientoDeudaImpl implements ServicioReconocimientoDeuda {

	private static final long serialVersionUID = 7305605957238024710L;

	private static Logger log = Logger.getLogger(ServicioReconocimientoDeuda.class);

	@Autowired
	private ReconocimientoDeudaDao reconocimientoDeudaDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultRegistro getReconocimientoDeuda(String identificador) {
		ResultRegistro result = new ResultRegistro();

		ReconocimientoDeudaDto reconocimientoDeudaDto = conversor.transform(reconocimientoDeudaDao.getReconocimientoDeuda(identificador), ReconocimientoDeudaDto.class);

		if (null != reconocimientoDeudaDto) {
			result.setObj(reconocimientoDeudaDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener el Reconocimiento de deuda");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarReconocimientoDeuda(ReconocimientoDeudaDto reconocimientoDeudaDto, long idDatosFinancieros) {
		ResultRegistro result;
		result = validarReconocimientoDeuda(reconocimientoDeudaDto);

		if (result.isError())
			return result;

		// Si tenemos el identificador de reconocimiento entonces actualizamos, si no añadimos
		if (0 != reconocimientoDeudaDto.getIdReconocimientoDeuda()) {
			reconocimientoDeudaDto.setFecModificacion(utilesFecha.getTimestampActual());
		} else {
			reconocimientoDeudaDto.setIdDatosFinancieros(new BigDecimal(idDatosFinancieros));
			reconocimientoDeudaDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		if (null != reconocimientoDeudaDto.getFecPrimerVencimientoReconDeuda()) {
			try {
				reconocimientoDeudaDto.setFecPrimerVencimiento(reconocimientoDeudaDto.getFecPrimerVencimientoReconDeuda().getTimestamp());
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}

		ReconocimientoDeudaVO reconocimientoDeudaVO = reconocimientoDeudaDao.guardarOActualizar(conversor.transform(reconocimientoDeudaDto, ReconocimientoDeudaVO.class));

		if (null != reconocimientoDeudaVO) {
			result.setMensaje("Reconocimiento de Deuda actualizado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar el Reconocimiento de Deuda");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarReconocimientoDeuda(String id) {
		ResultRegistro result = new ResultRegistro();
		if (reconocimientoDeudaDao.borrar(reconocimientoDeudaDao.getReconocimientoDeuda(id))) {
			result.setMensaje("Reconocimiento de Deuda eliminado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando Reconocimiento de Deuda");
		}
		return result;
	}

	@Override
	public ResultRegistro validarReconocimientoDeuda(ReconocimientoDeudaDto reconocimientoDeuda) {

		ResultRegistro resultado = new ResultRegistro();

		if (!UtilesValidaciones.validarObligatoriedad(reconocimientoDeuda.getImpReconocido())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion(" El importe reconocido del reconocimiento de deuda es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(reconocimientoDeuda.getImpReconocido())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe reconocido del reconocimiento de deuda debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(reconocimientoDeuda.getNumPlazos())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de plazos del reconocimiento de deuda es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(reconocimientoDeuda.getNumPlazos())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de plazos del reconocimiento de deuda debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(reconocimientoDeuda.getImpPlazos())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe plazos del reconocimiento de deuda es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(reconocimientoDeuda.getImpPlazos())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe plazos del reconocimiento de deuda debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarNumero(reconocimientoDeuda.getDiaVencimiento())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El día de vencimiento del reconocimiento de deuda no tiene formato correcto.");
		}

		if (!UtilesValidaciones.validarFecha(reconocimientoDeuda.getFecPrimerVencimientoReconDeuda())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha de vencimiento del reconocimiento de deuda no tiene formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(reconocimientoDeuda.getTiempoEntrePagos())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tiempo entre pagos del reconocimiento de deuda no tiene formato correcto.");
		}

		return resultado;
	}

}
