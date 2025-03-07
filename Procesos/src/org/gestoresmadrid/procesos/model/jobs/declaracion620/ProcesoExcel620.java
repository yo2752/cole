package org.gestoresmadrid.procesos.model.jobs.declaracion620;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.liquidacion620.model.service.ServicioLiquidacion620;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoExcel620 extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoExcel620.class);

	@Autowired
	ServicioLiquidacion620 servicio620;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		try {
			if (utilesFecha.esFechaLaborable(Boolean.TRUE) && !hayEnvioData(getProceso())) {
				ResultBean salida = servicio620.getExcelLiquidacion620DiaAnterior();
				if (salida.getError()) {
					actualizarEjecucion(getProceso(), salida.getMensaje(), ConstantesProcesos.EJECUCION_NO_CORRECTA, "0");
					peticionRecuperable();
				} else {
					actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO, ConstantesProcesos.EJECUCION_CORRECTA, "0");
					peticionCorrecta();
				}
			} else {
				peticionCorrecta();
			}
		} catch (OegamExcepcion e) {
			log.error("Error en Excel 620", e);
			String messageException = getMessageException(e);
			peticionRecuperable();
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION, messageException, "0");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.EXCEL_620.getNombreEnum();
	}
}
