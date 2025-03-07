package org.gestoresmadrid.oegamImportacion.trafico.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.dao.IntervinienteTraficoDao;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamConversiones.conversion.Conversion;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioEvolucionPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioIntervinienteTraficoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioIntervinienteTraficoImportacionImpl implements ServicioIntervinienteTraficoImportacion {

	private static final long serialVersionUID = -6844598577092710882L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioIntervinienteTraficoImportacionImpl.class);

	@Autowired
	IntervinienteTraficoDao intervinienteTraficoDao;

	@Autowired
	ServicioPersonaDireccionImportacion servicioPersonaDireccion;

	@Autowired
	ServicioPersonaImportacion servicioPersona;

	@Autowired
	ServicioDireccionImportacion servicioDireccion;

	@Autowired
	ServicioEvolucionPersonaImportacion servicioEvolucionPersona;

	@Autowired
	Conversion conversor;

	@Override
	@Transactional
	public ResultadoBean guardarInterviniente(IntervinienteTraficoVO interviniente, BigDecimal numExpediente, ContratoVO contrato, Long idUsuario, String tipoTramite) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			String conversion = getConversion(tipoTramite, interviniente.getId().getTipoInterviniente());
			ResultadoBean resultGPersona = servicioPersona.guardarActualizarImportacion(interviniente.getPersona(), numExpediente, tipoTramite, idUsuario, conversion);
			if (!resultGPersona.getError()) {
				interviniente.getId().setNumExpediente(numExpediente);
				if (interviniente.getDireccion() != null) {
					ResultadoBean resultDirPersona = servicioDireccion.guardarActualizarDirPersona(interviniente.getDireccion(), interviniente.getPersona().getId().getNif(), interviniente.getPersona()
							.getId().getNumColegiado(), tipoTramite);
					if (resultDirPersona.getError()) {
						resultado.addListaMensaje("- " + TipoInterviniente.convertirTexto(interviniente.getId().getTipoInterviniente()) + ": " + resultDirPersona.getMensaje());
						interviniente.setIdDireccion(null);
					} else {
						interviniente.setIdDireccion(resultDirPersona.getIdDirPersona());
						if (resultDirPersona.getEsNuevaDir()) {
							servicioEvolucionPersona.guardarEvolucionPersonaDireccion(interviniente.getPersona().getId().getNif(), numExpediente, tipoTramite, interviniente.getPersona().getId()
									.getNumColegiado(), idUsuario);
						}
					}
				}
				intervinienteTraficoDao.guardar(interviniente);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El " + TipoInterviniente.convertirTexto(interviniente.getId().getTipoInterviniente()) + " no se ha guardado por el siguiente motivo: " + resultGPersona
						.getMensaje());
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el interviniente : " + TipoInterviniente.convertirTexto(interviniente.getId().getTipoInterviniente()) + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el : " + TipoInterviniente.convertirTexto(interviniente.getId().getTipoInterviniente()));
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private String getConversion(String tipoTramite, String tipoInterviniente) {
		if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
			if (tipoInterviniente.equals(TipoInterviniente.RepresentanteTitular.getValorEnum()) || tipoInterviniente.equals(TipoInterviniente.RepresentanteAdquiriente.getValorEnum())) {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_REPRESENTANTE_BAJA;
			} else {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_BAJA;
			}
		} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
			if (tipoInterviniente.equals(TipoInterviniente.RepresentanteArrendatario.getValorEnum())) {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_REPRE_ARRENDATARIO_MATW;
			} else if (tipoInterviniente.equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_REPRE_TITULAR_MATW;
			} else if (tipoInterviniente.equals(TipoInterviniente.Titular.getValorEnum())) {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_TITULAR_MATW;
			} else {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_MATW;
			}
		} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
			if (tipoInterviniente.equals(TipoInterviniente.RepresentanteAdquiriente.getValorEnum()) || tipoInterviniente.equals(TipoInterviniente.RepresentanteArrendador.getValorEnum())
					|| tipoInterviniente.equals(TipoInterviniente.RepresentanteCompraventa.getValorEnum()) || tipoInterviniente.equals(TipoInterviniente.RepresentanteTransmitente.getValorEnum())) {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_REPRESENTANTE_CTIT;
			} else if (tipoInterviniente.equals(TipoInterviniente.ConductorHabitual.getValorEnum())) {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_CONDUCTOR_CTIT;
			} else {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_CTIT;
			}
		} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
			if (tipoInterviniente.equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_REPRE_DUPLICADO;
			} else {
				return ServicioPersonaImportacion.CONVERSION_PERSONA_DUPLICADO;
			}
		} else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(tipoTramite)) {
			return ServicioPersonaImportacion.CONVERSION_PERSONA_SOLICITUD;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public IntervinienteTraficoVO getIntervinienteTrafico(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado) {
		try {
			return intervinienteTraficoDao.getIntervinienteTrafico(numExpediente, tipoInterviniente, nif, numColegiado);
		} catch (Exception e) {
			log.error("Error al obtener el interviniente", e);
		}
		return null;
	}
}
