package org.gestoresmadrid.procesos.model.jobs.permisoInternacional;

import java.math.BigDecimal;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.rest.service.ServicioDuplPermConducirRestWS;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioDuplicadoPermCond;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoEnvioDuplPermCond extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoEnvioDuplPermCond.class);

	@Autowired
	ServicioDuplPermConducirRestWS servicioDuplPermConducirRestWS;

	@Autowired
	ServicioDuplicadoPermCond servicioDuplicadoPermCond;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto colaDto = new ColaDto();
		String resultadoEjecucion = null;
		try {
			colaDto = tomarSolicitud();
			if (colaDto == null) {
				sinPeticiones();
			} else {
				if (colaDto.getIdTramite() == null) {
					finalizarTransaccionConErrorNoRecuperable(colaDto, "No existen el id tramite.");
					colaDto.setRespuesta("No existen el id tramite.");
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				} else {
					log.info("Proceso envio duplicado permiso conducir iniciado con identificador " + colaDto.getIdTramite());
					ResultadoIntergaBean resultado = servicioDuplPermConducirRestWS.enviarRest(colaDto.getIdTramite().longValue(), colaDto.getIdUsuario().longValue());
					if (resultado != null && resultado.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						colaDto.setRespuesta(resultado.getMensaje());
						finalizarTransaccionErrorRecuperableDPC(colaDto, colaDto.getRespuesta());
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaDto.setRespuesta(resultadoEjecucion);
						finalizarTransaccionCorrecta(colaDto, resultadoEjecucion);
					}
				}
				actualizarUltimaEjecucion(colaDto, resultadoEjecucion, null);
			}
		} catch (Exception e) {
			log.error("Excepcion Proceso Envio Duplicado Permiso Conducir", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperableDPC(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
			} catch (Exception e1) {
				log.error("No ha sido posible borrar la solicitud", e1);
			}
			actualizarUltimaEjecucion(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}
	}

	private void finalizarTransaccionErrorRecuperableDPC(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			servicioProcesos.errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			servicioDuplicadoPermCond.finalizarConError(cola.getIdTramite().longValue(), cola.getIdUsuario().longValue(), respuestaError);
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.ENVIO_DUPL_PERM_COND.getNombreEnum();
	}
}