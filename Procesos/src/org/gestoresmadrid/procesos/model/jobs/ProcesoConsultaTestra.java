package org.gestoresmadrid.procesos.model.jobs;

import java.util.List;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.testra.service.ServicioConsultasTestra;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoConsultaTestra extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoConsultaTestra.class);

	@Autowired
	ServicioConsultasTestra servicioConsultasTestra;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		String resultadoEjecucion = "";
		try {
			if (utilesFecha.esFechaLaborable(Boolean.TRUE) && !hayEnvioData(getProceso())) {
				List<String> numerosColegiado = servicioConsultasTestra.obtenerNumerosColegiados();
				if (numerosColegiado != null && !numerosColegiado.isEmpty()) {
					resultadoEjecucion = servicioConsultasTestra.consultaTresta(numerosColegiado);
					peticionCorrecta();
				} else {
					sinPeticiones();
				}
				actualizarEjecucion(getProceso(), resultadoEjecucion, ConstantesProcesos.EJECUCION_CORRECTA, "0");
			} else {
				peticionCorrecta();
			}
		} catch (Throwable e) {
			log.error("Excepcion Consulta Testra General", e);
			String messageException = getMessageException(e);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION, messageException, "0");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.CONSULTA_TESTRA.getNombreEnum();
	}
}
