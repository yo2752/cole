package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.DatRegMercantilDao;
import org.gestoresmadrid.core.registradores.model.dao.EntidadCancelacionDao;
import org.gestoresmadrid.core.registradores.model.vo.DatRegMercantilVO;
import org.gestoresmadrid.core.registradores.model.vo.EntidadCancelacionVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioEntidadCancelacion;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatRegMercantilDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.EntidadCancelacionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.validaciones.NIFValidator;

@Service
public class ServicioEntidadCancelacionImpl implements ServicioEntidadCancelacion {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private EntidadCancelacionDao entidadCancelacionDao;

	@Autowired
	private DatRegMercantilDao datRegMercantilDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Transactional
	@Override
	public ResultRegistro guardarOActualizarEntidadCancelacion(EntidadCancelacionDto entidadCancelacionDto) {
		ResultRegistro result;
		long idDatRegMercantil = 0;

		result = validarEntidadCancelacion(entidadCancelacionDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de entidad actualizamos, si no añadimos
		if (0 != entidadCancelacionDto.getIdEntidad()) {
			entidadCancelacionDto.setFecModificacion(utilesFecha.getTimestampActual());
		} else {
			entidadCancelacionDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		// Datos Registro mercantil
		if (null != entidadCancelacionDto.getDatRegMercantil() && StringUtils.isNotBlank(entidadCancelacionDto.getDatRegMercantil().getCodRegistroMercantil())) {

			// Si tenemos el identificador de registro mercantil entonces actualizamos, si no añadimos
			if (0 != entidadCancelacionDto.getDatRegMercantil().getIdDatRegMercantil()) {
				entidadCancelacionDto.getDatRegMercantil().setFecModificacion(utilesFecha.getTimestampActual());
			} else {
				entidadCancelacionDto.getDatRegMercantil().setFecCreacion(utilesFecha.getTimestampActual());
			}

			DatRegMercantilVO datRegMercantilVO = datRegMercantilDao.guardarOActualizar(conversor.transform(entidadCancelacionDto.getDatRegMercantil(), DatRegMercantilVO.class));

			if (null != datRegMercantilVO) {
				idDatRegMercantil = datRegMercantilVO.getIdDatRegMercantil();
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("Error al actualizar los datos de registro mercantil");
				return result;

			}
		}
		if (0 != idDatRegMercantil) {
			entidadCancelacionDto.getDatRegMercantil().setIdDatRegMercantil(idDatRegMercantil);
		} else {
			entidadCancelacionDto.setDatRegMercantil(null);
		}

		EntidadCancelacionVO entidadCancelacionVO = entidadCancelacionDao.guardarOActualizar(conversor.transform(entidadCancelacionDto, EntidadCancelacionVO.class));

		if (null != entidadCancelacionVO) {
			result.setMensaje("Entidad cancelación actualizada correctamente");
			result.setObj(entidadCancelacionVO.getIdEntidad());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar la entidad cancelación");
		}

		return result;
	}

	@Override
	public ResultRegistro validarEntidadCancelacion(EntidadCancelacionDto entidadCancelacion) {
		ResultRegistro result = new ResultRegistro();

		// Entidad suscriptora
		if (null == entidadCancelacion.getDatRegMercantil()) {
			if (StringUtils.isBlank(entidadCancelacion.getCodigoIdentificacionFiscal())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El código de identificación fiscal de la entidad suscriptora es obligatorio.");
			} else if (!NIFValidator.isValidCIF(entidadCancelacion.getCodigoIdentificacionFiscal().toUpperCase())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El código de identificación fiscal de la entidad suscriptora no tiene el formato correcto.");
			}

			if (StringUtils.isBlank(entidadCancelacion.getRazonSocial())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("La razón social de la entidad suscriptora es obligatoria.");
			}
			// entidad sucesora
		} else if (StringUtils.isNotBlank(entidadCancelacion.getCodigoIdentificacionFiscal()) || StringUtils.isNotBlank(entidadCancelacion.getRazonSocial())) {

			if (StringUtils.isBlank(entidadCancelacion.getCodigoIdentificacionFiscal())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El código de identificación fiscal de la entidad sucesora es obligatorio.");
			} else if (!NIFValidator.isValidCIF(entidadCancelacion.getCodigoIdentificacionFiscal().toUpperCase())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El código de identificación fiscal de la entidad sucesora no tiene el formato correcto.");
			}

			if (StringUtils.isBlank(entidadCancelacion.getRazonSocial())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("La razón social de la entidad suscriptora es obligatoria.");
			}

			DatRegMercantilDto datRegMercantilDto = entidadCancelacion.getDatRegMercantil();
			if (StringUtils.isBlank(datRegMercantilDto.getCodRegistroMercantil())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El registro mercantil de la Entidad Sucesora es obligatorio.");
			} else if (!UtilesValidaciones.validarNumero(datRegMercantilDto.getCodRegistroMercantil())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El registro mercantil de la Entidad Sucesora no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getTomo())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El tomo inscrip. del registro mercantil de la Entidad Sucesora es obligatorio.");
			} else if (!UtilesValidaciones.validarNumero(datRegMercantilDto.getTomo())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El tomo inscrip. del registro mercantil de la Entidad Sucesora no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getFolio())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El folio inscrip. del registro mercantil de la Entidad Sucesora es obligatorio.");
			} else if (!UtilesValidaciones.validarNumero(datRegMercantilDto.getFolio())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El folio inscrip. del registro mercantil de la Entidad Sucesora no tiene el formato correcto.");
			}

			if (StringUtils.isBlank(datRegMercantilDto.getNumInscripcion())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El Número inscrip. del registro mercantil de la Entidad Sucesora es obligatorio.");
			}
		}

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public EntidadCancelacionDto buscarPorContratoNif(BigDecimal idContrato, String cifEntidad) {
		EntidadCancelacionDto resultado = null;
		resultado = conversor.transform(entidadCancelacionDao.buscarPorContratoNif(idContrato, cifEntidad), EntidadCancelacionDto.class);
		return resultado;
	}

}
