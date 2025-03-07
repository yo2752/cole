package org.gestoresmadrid.procesos.model.jobs.mensajeErrorServicio;

import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.ServicioProcesoMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean.ResultadoMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoMensajeErrorServicio extends AbstractProceso {

	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoMensajeErrorServicio.class);

	@Autowired
	ServicioProcesoMensajeErrorServicio servicioMensaje;

	@Override
	protected void doExecute() throws JobExecutionException {
		try {
			if (!hayEnvioData(getProceso())) {
				ResultadoMensajeErrorServicio result = servicioMensaje.generarExcel();
				if (result.isError()) {
					actualizarEjecucion(getProceso(), result.getMensaje(), ConstantesProcesos.EJECUCION_NO_CORRECTA, "0");
				} else {
					actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO, ConstantesProcesos.EJECUCION_CORRECTA, "0");
				}
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de mensajes error servicio", e);
			String messageException = getMessageException(e);
			actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
			errorNoRecuperable();
		} finally {
			peticionCorrecta();
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.ERROR_SERVICIO.getNombreEnum();
	}
}
