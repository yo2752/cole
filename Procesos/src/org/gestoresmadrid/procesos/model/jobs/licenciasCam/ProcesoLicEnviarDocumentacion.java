package org.gestoresmadrid.procesos.model.jobs.licenciasCam;

import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicEnviarDocuRestWS;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoLicEnviarDocumentacion extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoLicEnviarDocumentacion.class);

	@Autowired
	ServicioLicEnviarDocuRestWS servicioLicEnviarDocuRestWS;

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
					finalizarTransaccionConErrorNoRecuperableLic(colaDto, "No existen el id del env�o de Licencias CAM.");
					colaDto.setRespuesta("No existen el id del env�o de Licencias CAM.");
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				} else if (colaDto.getXmlEnviar() == null && !colaDto.getXmlEnviar().isEmpty()) {
					finalizarTransaccionConErrorNoRecuperableLic(colaDto, "No existen el xml de envio.");
					colaDto.setRespuesta("Error: La Solicitud " + colaDto.getIdTramite() + " no contiene xml de envio.");
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				} else {
					log.info("Proceso Envio Documentacion Licencia CAM con identificador " + colaDto.getIdTramite());
					ResultBean resultado = servicioLicEnviarDocuRestWS.enviarDocumentacionRest(colaDto.getIdTramite(), colaDto.getXmlEnviar());
					if (resultado != null && resultado.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						colaDto.setRespuesta(resultado.getMensaje());
						finalizarTransaccionErrorRecuperableLic(colaDto, colaDto.getRespuesta());
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaDto.setRespuesta(resultadoEjecucion);
						finalizarTransaccionCorrecta(colaDto, resultadoEjecucion);
					}
				}
				actualizarUltimaEjecucion(colaDto, resultadoEjecucion, null);
			}
		} catch (Exception e) {
			log.error("Excepcion Proceso Env�o de Documentacion de Licencia CAM", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperableLic(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
			} catch (Exception e1) {
				log.error("No ha sido posible borrar la solicitud", e1);
			}
			actualizarUltimaEjecucion(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}
	}

	private void finalizarTransaccionErrorRecuperableLic(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	private void finalizarTransaccionConErrorNoRecuperableLic(ColaDto cola, String respuestaError) {
		this.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioLicEnviarDocuRestWS.finalizarEnvioDocumentacion(cola.getIdTramite(), respuestaError, cola.getIdUsuario());
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.LIC_ENVIAR_DOCUMENTACION.getNombreEnum();
	}
}