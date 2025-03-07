package org.gestoresmadrid.procesos.model.jobs;

import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaStatusOegam;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoRecargaStatusOegam extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoRecargaStatusOegam.class);

	@Autowired
	ServicioRecargaStatusOegam servicioRecargaStatusOegam;

	@Override
	protected void doExecute() throws JobExecutionException {
		try {
			servicioRecargaStatusOegam.refrescarStatus();
			peticionCorrecta();
			log.info("Proceso recarga status oegam finalizado");
			actualizarEjecucion(getProceso(), null, ConstantesProcesos.EJECUCION_CORRECTA, "0");
		} catch (Exception e) {
			log.error("Excepcion Refreco Status Oegam", e);
			String messageException = getMessageException(e);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.RECARGA_STATUS_OEGAM.getNombreEnum();
	}
}
