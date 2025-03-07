package org.gestoresmadrid.procesos.model.jobs.impresion;

import java.math.BigDecimal;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioDuplicadoPermCond;
import org.gestoresmadrid.oegamImpresion.declaraciones.service.ServicioDeclaraciones;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioPermisoInternacional;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoDeclaraciones extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoDeclaraciones.class);

	@Autowired
	ServicioDeclaraciones servicioDeclaraciones;

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	ServicioPermisoInternacional servicioPermisoInternacional;

	@Autowired
	ServicioDuplicadoPermCond servicioDuplicadoPermCond;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto colaDto = new ColaDto();
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		String resultadoEjecucion = null;
		try {
			colaDto = tomarSolicitud();
			if (colaDto == null) {
				sinPeticiones();
			} else {
				if (colaDto.getIdTramite() == null) {
					finalizarTransaccionConErrorNoRecuperable(colaDto, "No existen el id de la impresión.");
					colaDto.setRespuesta("No existen el id de la impresión.");
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				} else {
					log.info("Proceso impresion iniciado con identificador " + colaDto.getIdTramite());
					resultado = servicioDeclaraciones.imprimirDeclaracion(colaDto.getIdTramite().longValue());
					if (resultado != null && resultado.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						colaDto.setRespuesta(resultado.getMensaje());
						finalizarTransaccionErrorRecuperableImp(colaDto, colaDto.getRespuesta(), resultado);
					} else {
						if (!TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(resultado.getTipoTramite()) && !TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(resultado
								.getTipoTramite())) {
							enviarCorreo(resultado, colaDto.getIdTramite(), colaDto.getIdContrato(), colaDto.getTipoTramite());
						}
						if (resultado != null && !resultado.getError()) {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							colaDto.setRespuesta(resultadoEjecucion);
							finalizarTransaccionCorrectaImp(colaDto, resultadoEjecucion, resultado);
						} else {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
							colaDto.setRespuesta(resultado.getMensaje());
							finalizarTransaccionErrorRecuperableImp(colaDto, colaDto.getRespuesta(), resultado);
						}
					}
				}
				actualizarUltimaEjecucion(colaDto, resultadoEjecucion, null);
			}
		} catch (Exception e) {
			log.error("Excepcion Proceso Impresion Declaracion", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperableImp(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION + messageException, resultado);
			} catch (Exception e1) {
				log.error("No ha sido posible borrar la solicitud", e1);
			}
			actualizarUltimaEjecucion(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}
	}

	private void finalizarTransaccionErrorRecuperableImp(ColaDto cola, String respuestaError, ResultadoImpresionBean resultado) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			servicioProcesos.errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			servicioImpresion.finalizarConError(cola.getIdTramite().longValue(), cola.getIdUsuario().longValue(), respuestaError);
			if (resultado != null && resultado.getListaTramites() != null && !resultado.getListaTramites().isEmpty()) {
				if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(resultado.getTipoTramite())) {
					servicioPermisoInternacional.errorFirma(resultado.getListaTramites().get(0), cola.getIdUsuario().longValue());
				} else if (TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(resultado.getTipoTramite())) {
					servicioDuplicadoPermCond.errorFirmaDocumentos(resultado.getListaTramites().get(0), cola.getIdUsuario().longValue());
				}
			}
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	private void finalizarTransaccionCorrectaImp(ColaDto cola, String resultado, ResultadoImpresionBean result) {
		if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(result.getTipoTramite())) {
			servicioPermisoInternacional.firmada(result.getListaTramites().get(0), cola.getIdUsuario().longValue());
		} else if (TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(result.getTipoTramite())) {
			servicioDuplicadoPermCond.firmadaDeclaracion(result.getListaTramites().get(0), cola.getIdUsuario().longValue());
		}
		this.finalizarTransaccionCorrecta(cola, resultado);
	}

	private void enviarCorreo(ResultadoImpresionBean resultado, BigDecimal idImpresion, BigDecimal idContrato, String tipoTramite) {
		servicioImpresion.gestionEnviarCorreo(resultado, idContrato, tipoTramite, resultado.getTipoDocumento());
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.IMP_DECLARACIONES.getNombreEnum();
	}
}