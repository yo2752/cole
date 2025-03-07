package org.gestoresmadrid.procesos.model.jobs.estadisticas;

import org.gestoresmadrid.oegam2comun.estadisticas.service.ServicioEstadisticasCorreoMate;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoEnvioFirstMate extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoEnvioFirstMate.class);

	@Autowired
	ServicioEstadisticasCorreoMate servicioEstadisticasCorreoMate;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		boolean considerarFestivos = false;
		boolean forzarEjecucion = false;

		if ("si".equals(gestorPropiedades.valorPropertie("primera.matricula.lanza.proceso.considerando.festivos.nacionales"))) {
			considerarFestivos = true;
		}

		if ("si".equals(gestorPropiedades.valorPropertie("primera.matricula.lanza.proceso.forzar"))) {
			forzarEjecucion = true;
		}

		try {
			log.info("Proceso " + getProceso() + " -- Procesando petición");
			if (!hayEnvioData(getProceso())) {
				if (forzarEjecucion || utilesFecha.esFechaLaborable(considerarFestivos)) {
					ResultBean resultado = servicioEstadisticasCorreoMate.enviarMailFirstMate();
					actualizarEjecucionPrimeraMatricula(resultado);
				}
			}
			peticionCorrecta();
			log.info("Proceso " + getProceso() + " -- Proceso ejecutado correctamente");
		} catch (Throwable e) {
			String messageException = getMessageException(e);
			log.error("Error en Estadísticas", e);
			peticionRecuperable();
			actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
		}
	}

	private void actualizarEjecucionPrimeraMatricula(ResultBean resultado) {
		if (!resultado.getError()) {
			try {
				actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO, ConstantesProcesos.EJECUCION_CORRECTA, "0");
			} catch (Exception e) {
				log.error("Error al Guardar Envio Data para " + getProceso(), e);
			}
		} else {
			try {
				String mensaje = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				if (resultado.getMensaje() != null && !resultado.getMensaje().isEmpty()) {
					mensaje = resultado.getMensaje();
				}
				actualizarEjecucion(getProceso(), mensaje, ConstantesProcesos.EJECUCION_NO_CORRECTA, "0");
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + getProceso(), e);
			}
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.FIRSTMATENUEVO.getNombreEnum();
	}
}