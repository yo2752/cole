package org.gestoresmadrid.procesos.model.jobs.cambioServicio;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioExcelCambioServicio;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoCambioServicioExcel extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoCambioServicioExcel.class);

	@Autowired
	ServicioProcesos servicioProcesos;
	
	@Autowired
	ServicioExcelCambioServicio servicioExcelCambioServicio;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		log.info("INICIO PROCESO EXCELCAMBIO DE SERVICIO");
		boolean considerarFestivos = false;
		boolean ejecucion = false;
		try {
			if ("si".equals(gestorPropiedades.valorPropertie("excel.duplicados.lanza.proceso.considerando.festivos.nacionales"))) {
				considerarFestivos = true;
			}
			ejecucion = utilesFecha.esViernes(considerarFestivos);
			
		} catch (Throwable tr) {
			log.info(tr.getMessage());
			log.info("No se puede acceder a las propiedades del proceso Excel de Cambios de Servicio");
		}
		if (ejecucion) {
			try {
				servicioExcelCambioServicio.generarExcelCambioServicio();
				actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA, ConstantesProcesos.EJECUCION_CORRECTA, "0");
				peticionCorrecta();
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de enviar los cambios de servicio excel, error: ", e);
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
		return ProcesosEnum.EXCEL_CAMBIO_SERVICIO.getNombreEnum();
	}
}
