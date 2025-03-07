package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.ReunionDao;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.ReunionVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioReunion;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ReunionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioReunionImpl implements ServicioReunion {

	private static final long serialVersionUID = -837877898752144884L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioReunionImpl.class);

	@Autowired
	private ReunionDao reunionDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ReunionDto getReunion(BigDecimal idTramiteRegistro) {
		ReunionDto result = null;
		try {
			if (idTramiteRegistro != null) {
				ReunionVO reunion = reunionDao.getReunion(idTramiteRegistro);
				if (reunion != null) {
					result = conversor.transform(reunion, ReunionDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar la reunion: " + idTramiteRegistro, e);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarReunion(ReunionDto reunion, BigDecimal idTramiteRegistro, String tipoTramite) {
		ResultBean result;

		result = validarReunion(reunion, tipoTramite);
		if (result.getError())
			return result;

		try {
			reunion.setIdTramiteRegistro(idTramiteRegistro);
			ReunionVO reunionVO = conversor.transform(reunion, ReunionVO.class);
			reunionVO = reunionDao.guardarOActualizar(reunionVO);
			if (reunionVO != null) {
				result.addAttachment(ID_REUNION, reunionVO.getIdReunion());
			}
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.setMensaje(e.getMessage());
			log.error("Error al guardar la reunion: " + reunion.getIdTramiteRegistro(), e.getMessage());
		}
		return result;
	}

	private ResultBean validarReunion(ReunionDto reunion, String tipoTramite) {
		ResultBean resultado = new ResultBean();

		if (null == reunion) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("Debe informar los datos de la reunión.");
			return resultado;
		}

		if (TipoTramiteRegistro.MODELO_1.getValorEnum().equals(tipoTramite) || TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tipoTramite) || TipoTramiteRegistro.MODELO_3.getValorEnum().equals(
				tipoTramite) || TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tipoTramite)) {
			if (StringUtils.isBlank(reunion.getTipoReunion())) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("El tipo de reunión es obligatorio.");
			}
		}

		if (!UtilesValidaciones.validarObligatoriedad(reunion.getFecha())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("La fecha de la reunión es obligatoria.");
		} else if (!UtilesValidaciones.validarFecha(reunion.getFecha())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("La fecha de la reunión no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(reunion.getLugar())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("El lugar de la reunión es obligatorio.");
		}

		if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tipoTramite) || TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tipoTramite)) {
			if (null != reunion.getConvocatoria() && StringUtils.isBlank(reunion.getConvocatoria().getNumeroConvo())) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("El orden de la convocatoria de la reunión es obligatorio.");
			}

			if (null == reunion.getPorcentajeCapital() || 0 == reunion.getPorcentajeCapital()) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("El porcentaje capital asistente de la reunión es obligatorio.");
			} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(reunion.getPorcentajeCapital()))) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("El porcentaje capital asistente de la reunión no tiene el formato correcto.");
			}

			if (null == reunion.getPorcentajeSocios() || 0 == reunion.getPorcentajeSocios()) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("El porcentaje socios asistente de la reunión es obligatorio.");
			} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(reunion.getPorcentajeSocios()))) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("El porcentaje socios asistente de la reunión no tiene el formato correcto.");
			}

			if (!TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tipoTramite)) {
				if (StringUtils.isBlank(reunion.getFormaAproActa())) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("La forma aprobación acta de la reunión es obligatoria.");
				}

				if (UtilesValidaciones.validarObligatoriedad(reunion.getFechaAproActa()) && !UtilesValidaciones.validarFecha(reunion.getFechaAproActa())) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("La fecha aprobación acta de la reunión no tiene el formato correcto.");
				}
			} else {
				if (StringUtils.isBlank(reunion.getContenidoConvo())) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("El Contenido integro de la convocatoria es obligatorio.");
				}
			}
		}

		return resultado;
	}

}
