package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.registradores.model.dao.NotarioRegistroDao;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro;
import org.gestoresmadrid.core.registradores.model.vo.NotarioRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioNotarioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.NotarioRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;

@Service
public class ServicioNotarioRegistroImpl implements ServicioNotarioRegistro {

	private static final long serialVersionUID = -4792517761940552060L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioNotarioRegistroImpl.class);

	@Autowired
	private NotarioRegistroDao notarioDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public NotarioRegistroDto getNotarioPorId(String codigo) {
		try {
			if (codigo != null && !codigo.isEmpty()) {
				codigo = utiles.rellenarCeros(codigo, 7);
				NotarioRegistroVO notarioVO = notarioDao.getNotarioPorID(codigo);
				if (notarioVO != null) {
					return conversor.transform(notarioVO, NotarioRegistroDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el notario por su ID, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarNotario(IntervinienteRegistroDto interviniente) {

		ResultRegistro result;
		NotarioRegistroDto notario = interviniente.getNotario();

		result = validarNotario(interviniente);
		if (result.isError())
			return result;

		if (null != notario.getFecOtorgamientoNotario()) {
			try {
				notario.setFecOtorgamiento(notario.getFecOtorgamientoNotario().getDate());
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}

		NotarioRegistroVO notarioVO = conversor.transform(notario, NotarioRegistroVO.class);
		notarioVO = notarioDao.guardarOActualizar(notarioVO);

		if (null != notarioVO) {
			result.setObj(notarioVO.getCodigo());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar en notario");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro borrarNotario(String id) {
		ResultRegistro result = new ResultRegistro();
		if (notarioDao.borrar(notarioDao.getNotarioPorID(id))) {
			result.setMensaje("Notario eliminado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando los datos del notario");
		}
		return result;
	}

	@Override
	public ResultRegistro validarNotario(IntervinienteRegistroDto interviniente) {
		ResultRegistro result = new ResultRegistro();

		NotarioRegistroDto notario = interviniente.getNotario();
		// Se valida si se ha informado algún campo del bloque notario
		if (StringUtils.isNotBlank(notario.getTipoPersona()) || StringUtils.isNotBlank(notario.getNif()) || StringUtils.isNotBlank(notario.getNombre()) || StringUtils.isNotBlank(notario
				.getApellido1()) || StringUtils.isNotBlank(notario.getApellido2()) || StringUtils.isNotBlank(notario.getCodProvincia()) || StringUtils.isNotBlank(notario.getCodMunicipio())
				|| UtilesValidaciones.validarObligatoriedad(notario.getFecOtorgamientoNotario()) || StringUtils.isNotBlank(notario.getNumProtocolo()) || StringUtils.isNotBlank(notario
						.getPlazaNotario())) {

			if (StringUtils.isBlank(notario.getTipoPersona())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El tipo de persona del notario es obligatorio.");
			}

			if (StringUtils.isBlank(notario.getNif())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El DNI / NIE / NIF del notario es obligatorio.");
			} else {
				int tipo = NIFValidator.isValidDniNieCif(notario.getNif().toUpperCase());
				if (tipo < 0) {
					result.setError(Boolean.TRUE);
					result.addValidacion("El DNI / NIE / NIF del notario no tiene el formato correcto.");
					return result;
				}
			}

			if (StringUtils.isNotBlank(notario.getTipoPersona()) && (TipoPersonaRegistro.Juridica.getValorXML().equals(notario.getTipoPersona()) || TipoPersonaRegistro.Compania_Publica.getValorXML()
					.equals(notario.getTipoPersona())) && (StringUtils.isBlank(notario.getApellido1()))) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si selecciona el notario como persona Jurídica o Compañía pública debe rellenar el primer apellido (Razón Social).");
			}

			if (StringUtils.isNotBlank(notario.getTipoPersona()) && (TipoPersonaRegistro.Fisica.getValorXML().equals(notario.getTipoPersona()) || TipoPersonaRegistro.Extranjero.getValorXML().equals(
					notario.getTipoPersona())) && (StringUtils.isBlank(notario.getNombre()) || StringUtils.isBlank(notario.getApellido1()))) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si selecciona el notario como persona Física o Extranjero debe rellenar nombre y dos apellidos.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(notario.getFecOtorgamientoNotario())) {
				result.setError(Boolean.TRUE);
				result.addValidacion(" La fecha de otorgamiento del notario es obligatoria.");
			} else if (!UtilesValidaciones.validarFecha(notario.getFecOtorgamientoNotario())) {
				result.setError(Boolean.TRUE);
				result.addValidacion(" La fecha de otrogamiento del notario no tiene el formato correcto.");
			}

			if (TipoInterviniente.RepresentanteSolicitante.getValorEnum().equals(interviniente.getTipoInterviniente())) {
				if (StringUtils.isBlank(notario.getPlazaNotario())) {
					result.setError(Boolean.TRUE);
					result.addValidacion(" La plaza del notario del notario es obligatoria.");
				}
			} else {// Validamos provincia y municipio para el resto intervinientes

				if (StringUtils.isBlank(notario.getCodProvincia())) {
					result.setError(Boolean.TRUE);
					result.addValidacion(" El código INE provincia del notario es obligatorio.");
				}

				if (StringUtils.isBlank(notario.getCodMunicipio())) {
					result.setError(Boolean.TRUE);
					result.addValidacion(" El código INE municipio del notario es obligatorio.");
				}
			}

		}

		return result;
	}

}
