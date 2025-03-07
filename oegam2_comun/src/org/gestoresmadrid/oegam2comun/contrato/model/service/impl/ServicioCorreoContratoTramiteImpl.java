package org.gestoresmadrid.oegam2comun.contrato.model.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.general.model.dao.CorreoContratoTramiteDao;
import org.gestoresmadrid.core.impresion.model.enumerados.TipoDocumentoImpresiones;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioCorreoContratoTramite;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegamComun.contrato.view.dto.CorreoContratoTramiteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioCorreoContratoTramiteImpl implements ServicioCorreoContratoTramite {

	private static final long serialVersionUID = 4025581578587617369L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioCorreoContratoTramiteImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	private CorreoContratoTramiteDao correoContratoTramiteDao;

	@Override
	@Transactional
	public CorreoContratoTramiteVO getCorreoContratoTramite(Long idCorreo) {
		try {
			if (idCorreo != null) {
				return correoContratoTramiteDao.getCorreoContratoTramite(idCorreo);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el inmueble: " + idCorreo, e);
		}
		return null;
	}

	@Override
	@Transactional
	public CorreoContratoTramiteDto getCorreoContratoTramiteDto(Long idCorreo) {
		CorreoContratoTramiteDto correoContratoTramite = null;
		try {
			CorreoContratoTramiteVO vo = correoContratoTramiteDao.getCorreoContratoTramite(idCorreo);
			correoContratoTramite = conversor.transform(vo, CorreoContratoTramiteDto.class);
			correoContratoTramite.setIdTipoTramite(correoContratoTramite.getTipoTramite().getTipoTramite());
			correoContratoTramite.getTipoTramite().setCodigoAplicacion(correoContratoTramite.getTipoTramite().getAplicacion().getCodigoAplicacion());
		} catch (Exception e) {
			log.error("Error al recuperar el inmueble: " + idCorreo, e);
		}
		return correoContratoTramite;
	}

	@Transactional
	@Override
	public ResultBean getCorreosPorContrato(long idContrato) {
		ResultBean result = new ResultBean(Boolean.FALSE);

		List<CorreoContratoTramiteDto> correoContratoTramite = conversor.transform(correoContratoTramiteDao.getCorreosPorContrato(idContrato), CorreoContratoTramiteDto.class);

		if (null != correoContratoTramite) {
			result.addAttachment("CORREOS", correoContratoTramite);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los correos por contrato");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultBean guardarOActualizarCorreoContratoTramite(CorreoContratoTramiteDto correoContratoTramiteDto) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);

		validarCorreoContratoTramite(correoContratoTramiteDto, resultado);
		if (resultado.getError())
			return resultado;

		try {

			if (correoContratoTramiteDto.getTipoTramite() == null) {
				correoContratoTramiteDto.setTipoTramite(new TipoTramiteDto());
			}
			correoContratoTramiteDto.getTipoTramite().setTipoTramite(correoContratoTramiteDto.getIdTipoTramite());
			CorreoContratoTramiteVO correoContratoTramiteVO = correoContratoTramiteDao.guardarOActualizar(conversor.transform(correoContratoTramiteDto, CorreoContratoTramiteVO.class));
			if (null == correoContratoTramiteVO) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar los datos del correo por contrato y trámite");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos del correo por contrato y trámite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos del correo por contrato y trámite.");
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	public void validarCorreoContratoTramite(CorreoContratoTramiteDto correoContratoTramite, ResultBean resultado) {
		if (correoContratoTramite != null) {
			if (StringUtils.isBlank(correoContratoTramite.getCorreoElectronico())) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("Para establecer correo electrónico a un trámite debe indicar la dirección de mail.");
			} else if (!UtilesValidaciones.validarMail(correoContratoTramite.getCorreoElectronico())) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("Para establecer un correo electrónico a un trámite, debe indicar una dirección de mail con formato correcto.");
			}

			if (StringUtils.isBlank(correoContratoTramite.getIdTipoTramite())) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("Para establecer correo electrónico a un trámite debe indicar el tipo de trámite.");
				// Si no es modificación, se valida que sólo haya una dirección de correo por trámite
			} else if (correoContratoTramite.getIdCorreo() == null) {
				List<CorreoContratoTramiteDto> correos = null;
				ResultBean result = getCorreosPorContrato(correoContratoTramite.getIdContrato().longValue());
				if (result != null && !result.getError()) {
					correos = (List<CorreoContratoTramiteDto>) result.getAttachment("CORREOS");
				}
				if (correos != null && !correos.isEmpty()) {
					if (correoContratoTramite.getIdTipoTramite().equals(TipoTramiteTrafico.Baja.getValorEnum()) || correoContratoTramite.getIdTipoTramite().equals(TipoTramiteTrafico.Matriculacion
							.getValorEnum()) || correoContratoTramite.getIdTipoTramite().equals(TipoTramiteTrafico.TransmisionElectronica.getValorEnum()) || correoContratoTramite.getIdTipoTramite()
									.equals(TipoTramiteTrafico.Duplicado.getValorEnum())) {

						for (CorreoContratoTramiteDto correo : correos) {
							if (correo.getTipoTramite().getTipoTramite().equalsIgnoreCase(correoContratoTramite.getIdTipoTramite()) && ((StringUtils.isBlank(correo.getTipoImpresion()) && StringUtils
									.isBlank(correoContratoTramite.getTipoImpresion())) || (StringUtils.isNotBlank(correo.getTipoImpresion()) && correo.getTipoImpresion().equalsIgnoreCase(
											correoContratoTramite.getTipoImpresion())))) {
								resultado.setError(Boolean.TRUE);
								resultado.addMensajeALista("Ya se ha asignado una dirección de correo electrónico para el trámite " + correo.getTipoTramite().getDescripcion() + " y tipo de impresión "
										+ (StringUtils.isNotBlank(correoContratoTramite.getTipoImpresion()) ? TipoDocumentoImpresiones.convertirTexto(correoContratoTramite.getTipoImpresion()) +"."
												: "sin informar."));
								break;
							}
						}
					} else {
						for (CorreoContratoTramiteDto correo : correos) {
							if (correo.getTipoTramite().getTipoTramite().equalsIgnoreCase(correoContratoTramite.getIdTipoTramite())) {
								resultado.setError(Boolean.TRUE);
								resultado.addMensajeALista("Ya se ha asignado una dirección de correo electrónico para el trámite " + correo.getTipoTramite().getDescripcion() + ".");
								break;
							}
						}

					}
				}
			}

			if (StringUtils.isNotBlank(correoContratoTramite.getTipoImpresion()) && StringUtils.isBlank(correoContratoTramite.getEnviarCorreoImpresion())) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("Si se indica un tipo de impresión, debe decidir si enviar o no correo de notificación de impresión.");
			}

			if (StringUtils.isNotBlank(correoContratoTramite.getEnviarCorreoImpresion()) && StringUtils.isBlank(correoContratoTramite.getTipoImpresion())) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("Para decidir si enviar o no correo de notificación de impresión, se debe indicar un tipo de impresión.");
			}
		}
	}

	@Override
	@Transactional
	public void eliminarCorreoContratoTramite(Long idCorreo) {
		CorreoContratoTramiteVO correoContratoTramite = getCorreoContratoTramite(idCorreo);
		if (correoContratoTramite != null) {
			correoContratoTramiteDao.borrar(correoContratoTramite);
		}
	}
}