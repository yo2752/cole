package org.gestoresmadrid.procesos.model.jobs.impresion;

import java.math.BigDecimal;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioDuplicadoPermCond;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.solicitudes.service.ServicioSolicitudes;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoSolicitudes extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoSolicitudes.class);

	@Autowired
	ServicioSolicitudes servicioSolicitudes;

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	ServicioContrato servicioContrato;

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
					resultado = servicioSolicitudes.imprimirSolicitud(colaDto.getIdTramite().longValue());
					if (resultado != null && resultado.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						colaDto.setRespuesta(resultado.getMensaje());
						finalizarTransaccionErrorRecuperableImp(colaDto, colaDto.getRespuesta(), resultado);
					} else {
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
				servicioDuplicadoPermCond.errorFirmaDocumentos(resultado.getListaTramites().get(0), cola.getIdUsuario().longValue());
			}
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	private void finalizarTransaccionCorrectaImp(ColaDto cola, String resultado, ResultadoImpresionBean result) {
		servicioDuplicadoPermCond.firmadaSolicitud(result.getListaTramites().get(0), cola.getIdUsuario().longValue());
		this.finalizarTransaccionCorrecta(cola, resultado);
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.IMP_SOLICITUDES.getNombreEnum();
	}
}