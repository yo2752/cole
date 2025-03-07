package org.gestoresmadrid.procesos.model.jobs;

import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioExcelBajas;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoBajasExcel extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoBajasExcel.class);

	@Autowired
	ServicioExcelBajas servicioExcelBajas;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		log.info("INICIO PROCESO EXCEL BAJAS");
		boolean considerarFestivos = false;
		boolean ejecucion = false;
		try {
			if ("si".equals(gestorPropiedades.valorPropertie("excel.bajas.lanza.proceso.considerando.festivos.nacionales"))) {
				considerarFestivos = true;
			}
			if ("si".equals(gestorPropiedades.valorPropertie("excel.bajas.lanza.proceso.forzar"))) {
				ejecucion = true;
			} else {
				ejecucion = utilesFecha.esFechaLaborable(considerarFestivos);
			}
		} catch (Throwable tr) {
			log.info(tr.getMessage());
			log.info("No se puede acceder a las propiedades del proceso Excel Bajas");
		}
		if (ejecucion) {
			try {
				servicioExcelBajas.generarExcelBajas();
				actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA, ConstantesProcesos.EJECUCION_CORRECTA, "0");
				peticionCorrecta();
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de enviar las bajas excel, error: ", e);
				String messageException = getMessageException(e);
				actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
				errorNoRecuperable();
			}
		} else {
			actualizarEjecucion(getProceso(), "NO SE EJECUTA POR FECHA NO LABORAL", ConstantesProcesos.EJECUCION_CORRECTA, "0");
			peticionCorrecta();
		}
	}

	@Override
	protected String getProceso() {
		return ConstantesProcesos.PROCESO_EXCEL_BAJAS;
	}

}
