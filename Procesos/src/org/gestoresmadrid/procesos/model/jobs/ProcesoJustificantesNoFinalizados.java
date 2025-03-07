package org.gestoresmadrid.procesos.model.jobs;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoJustificantesNoFinalizados extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoJustificantesNoFinalizados.class);

	@Autowired
	ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		Boolean esLaborable = Boolean.TRUE;
		String resultadoEjecucion = null;
		String excepcion = null;
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}

		if (esLaborable && !hayEnvioData(getProceso())) {
			try {
				log.info("Inicio proceso: " + getProceso());
				ResultBean result = servicioJustificanteProfesional.executeEmailJustificantesNoFinalizados();
				if (result != null && !result.getError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					excepcion = "Ejecución correcta";
					peticionCorrecta();
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "Correo no enviado";
					peticionRecuperable();
				}
				actualizarEjecucion(getProceso(), excepcion, resultadoEjecucion, "0");
			} catch (Exception e) {
				log.error("Ocurrio un error no controlado en el proceso FINALIZACION_TRAMITE_JUSTIFICANTES, error: ", e);
				String messageException = getMessageException(e);
				errorNoRecuperable();
				actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
			}
			log.info("Fin proceso: " + ProcesosEnum.JUSTIFICANTES_NO_FINALIZADOS.toString());
		} else {
			peticionCorrecta();
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.JUSTIFICANTES_NO_FINALIZADOS.getNombreEnum();
	}
}