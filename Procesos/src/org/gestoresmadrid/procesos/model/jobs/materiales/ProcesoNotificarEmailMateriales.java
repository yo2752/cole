package org.gestoresmadrid.procesos.model.jobs.materiales;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioEmailEstadisticasDistintivos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.procesos.ProcesoNotificarPegatinas;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoNotificarEmailMateriales extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoNotificarPegatinas.class);

	public static final int success = 0;
	public static final int error = 1;

	@Autowired
	ServicioEmailEstadisticasDistintivos servicioEmailEstadisticasDistintivos;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		log.info(String.format(String.format("Proceso %s -- Procesando petición", ProcesosEnum.STOCK_NOTIFICAR)));
		String resultadoEjecucion = null;
		String excepcion = null;
		try {
			if (utilesFecha.esFechaLaborable(Boolean.TRUE) && !hayEnvioData(getProceso())) {
				ResultBean resultBean = servicioEmailEstadisticasDistintivos.executeEmailEstadisticasDistintivos();
				if (resultBean.getError()) {
					log.error("No se ha podido enviar email");
					resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
					excepcion = "No se ha podido enviar email";
					errorNoRecuperable();
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					excepcion = "EJECUCION CORRECTA";
					peticionCorrecta();
				}
				actualizarEjecucion(getProceso(), excepcion, resultadoEjecucion, "0");
			} else {
				peticionCorrecta();
			}
		} catch (Throwable ex) {
			log.error("Excepcion Notificar Email Materiales General", ex);
			String messageException = getMessageException(ex);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION, messageException, "0");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.STOCK_NOTIFICAR.getNombreEnum();
	}
}
