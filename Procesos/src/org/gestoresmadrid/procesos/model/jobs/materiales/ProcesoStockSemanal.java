package org.gestoresmadrid.procesos.model.jobs.materiales;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioFacturacionSemanal;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoStockSemanal extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoStockSemanal.class);

	public static final int success = 0;
	public static final int error = 1;

	@Autowired
	ServicioFacturacionSemanal servicioFacturacionSemanal;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		log.info(String.format(String.format("Proceso %s -- Procesando petición", ProcesosEnum.STOCK_SEMANAL)));
		try {
			if (utilesFecha.esFechaLaborable(Boolean.TRUE) && !hayEnvioData(getProceso()) && utilesFecha.esLunes()) {
				ResultBean resultBean = servicioFacturacionSemanal.executeStockSemanal();
				if (!resultBean.getError()) {
					actualizarEjecucion(getProceso(), "EJECUCION CORRECTA", ConstantesProcesos.EJECUCION_CORRECTA, "0");
					peticionCorrecta();
				} else {
					sinPeticiones();
				}
			} else {
				peticionCorrecta();
			}
		} catch (Throwable ex) {
			log.error("Excepcion Stock Semanal General", ex);
			String messageException = getMessageException(ex);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.STOCK_SEMANAL.getNombreEnum();
	}
}
