package org.gestoresmadrid.procesos.model.jobs.impresion;

import java.math.BigDecimal;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.modelo430.service.ServicioModelo430;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoModelo430 extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoModelo430.class);

	@Autowired
	ServicioModelo430 servicioModelo430;

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

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
					finalizarTransaccionConErrorNoRecuperable(colaDto, "No existen el id de la impresión.");
					colaDto.setRespuesta("No existen el id de la impresión.");
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				} else {
					log.info("Proceso impresion iniciado con identificador " + colaDto.getIdTramite());
					ResultadoImpresionBean resultado = servicioModelo430.imprimirModelo430(colaDto.getIdTramite().longValue());
					if (resultado != null && resultado.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						colaDto.setRespuesta(resultado.getMensaje());
						finalizarTransaccionErrorRecuperableImp(colaDto, colaDto.getRespuesta());
					} else {
						enviarCorreo(resultado, colaDto.getIdTramite(), colaDto.getIdContrato(), colaDto.getTipoTramite());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaDto.setRespuesta(resultadoEjecucion);
						finalizarTransaccionCorrecta(colaDto, resultadoEjecucion);
					}
				}
				actualizarUltimaEjecucion(colaDto, resultadoEjecucion, null);
			}
		} catch (Exception e) {
			log.error("Excepcion Proceso Impresion Pdf 417", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperableImp(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
			} catch (Exception e1) {
				log.error("No ha sido posible borrar la solicitud", e1);
			}
			actualizarUltimaEjecucion(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}
	}

	private void finalizarTransaccionErrorRecuperableImp(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (numIntentos != null && cola.getnIntento().intValue() < numIntentos.intValue()) {
			servicioProcesos.errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			servicioImpresion.finalizarConError(cola.getIdTramite().longValue(), cola.getIdUsuario().longValue(), respuestaError);
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	private void enviarCorreo(ResultadoImpresionBean resultado, BigDecimal idImpresion, BigDecimal idContrato, String tipoTramite) {
		servicioImpresion.gestionEnviarCorreo(resultado, idContrato, tipoTramite, TipoImpreso.TransmisionModelo430.toString());
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.IMP_MODELO_430.getNombreEnum();
	}
}