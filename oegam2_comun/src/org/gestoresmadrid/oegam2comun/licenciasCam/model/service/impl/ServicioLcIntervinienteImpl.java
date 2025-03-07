package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcIntervinienteDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcIntervinienteVO;
import org.gestoresmadrid.core.licencias.model.vo.LcPersonaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInterviniente;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcPersona;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcPersonaDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDireccionDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcIntervinienteDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcPersonaDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;

@Service
public class ServicioLcIntervinienteImpl implements ServicioLcInterviniente {

	private static final long serialVersionUID = 7305605957238024710L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcIntervinienteImpl.class);

	@Autowired
	LcIntervinienteDao intervinienteDao;

	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	ServicioLcPersona servicioLcPersona;

	@Autowired
	ServicioLcDireccion servicioLcDireccion;

	@Autowired
	ServicioLcPersonaDireccion servicioLcPersonaDireccion;

	@Autowired
	Conversor conversor;

	@Override
	@Transactional
	public ResultadoLicenciasBean guardarInterviniente(LcIntervinienteDto interviniente) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			// Nuevo Interviniente
			result = validarInterviniente(interviniente);
			if (result.getError()) {
				return result;
			}

			interviniente.getLcPersona().setNumColegiado(interviniente.getNumColegiado());

			// Guardar la persona
			ResultBean resultPersona = servicioLcPersona.guardarActualizar(interviniente.getLcPersona());
			if (!resultPersona.getError() && !resultPersona.getError()) {
				LcPersonaVO lcPersona = (LcPersonaVO) resultPersona.getAttachment(ServicioLcPersona.PERSONA);
				interviniente.setIdPersona(lcPersona.getIdPersona());
				if (interviniente.getLcDireccion() != null && StringUtils.isNotBlank(interviniente.getLcDireccion().getProvincia())) {
					LcDireccionVO direccion = conversor.transform(interviniente.getLcDireccion(), LcDireccionVO.class);
					ResultBean resultDireccion = servicioLcDireccion.guardarActualizarPersona(direccion, interviniente.getIdPersona(), interviniente.getLcPersona().getNif(), interviniente
							.getLcPersona().getNumColegiado());
					if (resultDireccion != null && !resultDireccion.getError()) {
						direccion = (LcDireccionVO) resultDireccion.getAttachment(ServicioLcDireccion.DIRECCION);
						interviniente.setIdDireccion(direccion.getIdDireccion());
					}
				}
				intervinienteDao.guardarOActualizar(conversor.transform(interviniente, LcIntervinienteVO.class));
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje(resultPersona.getListaMensajes().get(0));
			}
		} catch (Exception e) {
			log.error("Error a la hora de guardar el interviniente", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Error a la hora de guardar el interviniente");
		}

		if (result.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return result;
	}

	public ResultadoLicenciasBean validarInterviniente(LcIntervinienteDto interviniente) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		if (StringUtils.isBlank(interviniente.getLcPersona().getTipoSujeto())) {
			result.setError(Boolean.TRUE);
			result.addMensaje(" El tipo de sujeto es obligatorio.<br/><br/>");
		}

		if (StringUtils.isBlank(interviniente.getLcPersona().getNif())) {
			result.setError(Boolean.TRUE);
			result.addMensaje(" El DNI / NIE / NIF es obligatorio.<br/><br/>");
		} else {
			int tipo = NIFValidator.isValidDniNieCif(interviniente.getLcPersona().getNif().toUpperCase());
			if (tipo < 0) {
				result.setError(Boolean.TRUE);
				result.addMensaje("El DNI / NIE / NIF no tiene el formato correcto.<br/><br/>");
				return result;
			}
		}

		if (StringUtils.isBlank(interviniente.getLcPersona().getTipoDocumento())) {
			result.setError(Boolean.TRUE);
			result.addMensaje(" Se debe indicar el tipo de documento.<br/><br/>");
		}

		if (StringUtils.isBlank(interviniente.getLcPersona().getNumTelefono1())) {
			result.setError(Boolean.TRUE);
			result.addMensaje(" El teléfono principal es obligatorio.<br/><br/>");
		}

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoLicenciasBean buscarPersona(String nif, String numColegiado) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(numColegiado) && StringUtils.isNotBlank(nif)) {
				LcPersonaDto lcPersonaDto = servicioLcPersona.getLcPersonaDto(nif, numColegiado);
				if (lcPersonaDto != null) {
					LcIntervinienteDto intervinientLcDto = new LcIntervinienteDto();
					if (lcPersonaDto != null) {
						intervinientLcDto.setLcPersona(lcPersonaDto);
						LcDireccionDto direccion = servicioLcPersonaDireccion.obtenerDireccionActiva(numColegiado, nif);
						if (direccion != null) {
							intervinientLcDto.setLcDireccion(direccion);
						}
						resultado.setInterviniente(intervinientLcDto);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensaje("No existen datos suficientes para poder realizar la busqueda de los intervinientes.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la persona por el nif: " + nif + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Ha sucedido un error a la hora de obtener la persona");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoLicenciasBean borrarInterviniente(Long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);
		// Obtenermos el interviniente
		LcIntervinienteVO interviniente = intervinienteDao.getInterviniente(id);
		if (null == interviniente) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al recuperar el interviniente");
			return result;
		}
		if (intervinienteDao.borrar(interviniente)) {
			result.setMensaje("Interviniente borrado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error borrando interviniente");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	@Transactional
	public LcIntervinienteDto getInterviniente(Long id) {
		return conversor.transform(intervinienteDao.getInterviniente(id), LcIntervinienteDto.class);
	}
}
