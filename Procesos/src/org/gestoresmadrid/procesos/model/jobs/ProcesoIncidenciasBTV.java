package org.gestoresmadrid.procesos.model.jobs;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioIncidenciasBajas;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoIncidenciasBTV extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoIncidenciasBTV.class);

	@Autowired
	ServicioIncidenciasBajas servicioIncidenciasBajas;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		boolean considerarFestivos = Boolean.FALSE;
		boolean ejecucion = Boolean.FALSE;

		String mensaje = "";

		log.info("Inicio proceso: " + ProcesosEnum.INCIDENCIAS_BTV.toString());

		try {
			ejecucion = utilesFecha.esFechaLaborable(considerarFestivos);
		} catch (OegamExcepcion e) {
			log.info(e.getMessage());
			log.info("No es posible saber si es fecha laborable");
		}

		if (ejecucion) {
			mensaje = servicioIncidenciasBajas.generarExcelIncidenciasPorJefatura();
			peticionCorrecta();
			if (StringUtils.isBlank(mensaje)) {
				mensaje = "Envios procesados correctamente para todas las jefaturas.";
			}
			actualizarEjecucion(getProceso(), mensaje, ConstantesProcesos.EJECUCION_CORRECTA, "0");
			log.info("Fin proceso: " + ProcesosEnum.INCIDENCIAS_BTV.toString());
		} else {
			actualizarEjecucion(getProceso(), "NO SE EJECUTA POR FECHA NO LABORAL",
					ConstantesProcesos.EJECUCION_CORRECTA, "0");
			peticionCorrecta();
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.INCIDENCIAS_BTV.getNombreEnum();
	}
}