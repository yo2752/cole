package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.gestoresmadrid.core.registradores.model.dao.DatosInscripcionDao;
import org.gestoresmadrid.core.registradores.model.vo.DatosInscripcionVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioDatosInscripcion;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatosInscripcionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioDatosInscripcionImpl implements ServicioDatosInscripcion {

	private static final long serialVersionUID = 7305605957238024710L;

	private static Logger log = Logger.getLogger(ServicioDatosInscripcionImpl.class);

	@Autowired
	private DatosInscripcionDao datosInscripcionDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Transactional
	@Override
	public ResultRegistro guardarOActualizarDatosInscripcion(DatosInscripcionDto datosInscripcionDto) {
		ResultRegistro result;

		result = validarDatosInscripcion(datosInscripcionDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de datos inscripcion actualizamos, si no añadimos
		if (0 != datosInscripcionDto.getIdDatosInscripcion()) {
			datosInscripcionDto.setFecModificacion(utilesFecha.getTimestampActual());
		} else {
			datosInscripcionDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		if (null != datosInscripcionDto.getFechaInscripcionDatInscrip()) {
			try {
				datosInscripcionDto.setFechaInscripcion(datosInscripcionDto.getFechaInscripcionDatInscrip().getDate());
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}

		DatosInscripcionVO datosInscripcionVO = datosInscripcionDao.guardarOActualizar(conversor.transform(datosInscripcionDto, DatosInscripcionVO.class));

		if (null != datosInscripcionVO) {
			result.setMensaje("Datos inscripción actualizados correctamente");
			result.setObj(datosInscripcionVO.getIdDatosInscripcion());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar los datos de inscripción");
		}

		return result;
	}

	@Override
	public ResultRegistro validarDatosInscripcion(DatosInscripcionDto datosInscripcionDto) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isNotBlank(datosInscripcionDto.getCodigoRbm()) || UtilesValidaciones.validarObligatoriedad(datosInscripcionDto.getNumeroRegistralBien()) || UtilesValidaciones
				.validarObligatoriedad(datosInscripcionDto.getNumeroInscripcionBien()) || UtilesValidaciones.validarObligatoriedad(datosInscripcionDto.getFechaInscripcionDatInscrip())) {

			if (StringUtils.isBlank(datosInscripcionDto.getCodigoRbm())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El código RBM de los datos de inscripción es obligatorio.");
			} else if (!UtilesValidaciones.validarNumero(datosInscripcionDto.getCodigoRbm())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El código RBM de los datos de inscripción no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(datosInscripcionDto.getNumeroRegistralBien())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El número registral del bien de los datos de inscripción es obligatorio.");
			} else if (!UtilesValidaciones.validarNumero(datosInscripcionDto.getNumeroRegistralBien())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El número registral del bien de los datos de inscripción no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(datosInscripcionDto.getNumeroInscripcionBien())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El número de inscripción del bien de los datos de inscripción es obligatorio.");
			} else if (!UtilesValidaciones.validarNumero(datosInscripcionDto.getNumeroInscripcionBien())) {
				result.addValidacion("El número de inscripción del bien de los datos de inscripción no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(datosInscripcionDto.getFechaInscripcionDatInscrip())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("La fecha de inscripción de los datos de inscripción es obligatoria.");
			} else if (!UtilesValidaciones.validarFecha(datosInscripcionDto.getFechaInscripcionDatInscrip())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("La fecha de inscripción de los datos de inscripción no tiene el formato correcto.");
			}

		}
		return result;
	}

}
