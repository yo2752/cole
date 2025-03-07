package org.gestoresmadrid.procesos.model.jobs.permisoInternacional;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.rest.service.ServicioDuplPermConducirRestWS;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;
import org.gestoresmadrid.oegamPermisoInternacional.rest.service.ServicioPermisoInternacionalRestWS;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoGestionInterga extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoGestionInterga.class);

	@Autowired
	ServicioPermisoInternacionalRestWS servicioPermisoInternacionalRestWS;

	@Autowired
	ServicioDuplPermConducirRestWS servicioDuplPermConducirRestWS;

	@Override
	protected void doExecute() throws JobExecutionException {
		String resultadoEjecucion = null;
		String excepcion = null;
		try {
			ResultadoIntergaBean resultadoPermInter = servicioPermisoInternacionalRestWS.consultarRest();
			if (resultadoPermInter != null && resultadoPermInter.getError()) {
				resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				excepcion = resultadoPermInter.getMensaje();
				peticionRecuperable();
			} else {
				ResultadoIntergaBean resultadoDuplPermCond = servicioDuplPermConducirRestWS.consultarRest();
				if (resultadoPermInter != null && resultadoPermInter.getError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = resultadoPermInter.getMensaje();
					peticionRecuperable();
				} else {
					int numEjecuciones = resultadoPermInter.getNumeroEjecuciones() + resultadoDuplPermCond.getNumeroEjecuciones();
					log.error("Procesados correctamente " + resultadoPermInter.getNumeroEjecuciones() + " de permisos internacionales.");
					log.error("Procesados correctamente " + resultadoDuplPermCond.getNumeroEjecuciones() + " de duplicados de permisos de conducir.");
					if (numEjecuciones != 0) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						excepcion = ConstantesProcesos.EJECUCION_CORRECTA + " => Se han procesado correctamente " + numEjecuciones + " tramites interga.";
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						excepcion = ConstantesProcesos.EJECUCION_CORRECTA;
					}
					peticionCorrecta();
				}
			}
			actualizarEjecucion(getProceso(), excepcion, resultadoEjecucion, "0");
		} catch (Exception e) {
			log.error("Excepcion Proceso Gestion Interga", e);
			String messageException = getMessageException(e);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.GESTION_INTERGA.getNombreEnum();
	}
}