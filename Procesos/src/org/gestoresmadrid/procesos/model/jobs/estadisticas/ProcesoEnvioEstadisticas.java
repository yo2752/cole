package org.gestoresmadrid.procesos.model.jobs.estadisticas;

import org.gestoresmadrid.core.model.enumerados.EstadisticasTiposEnum;
import org.gestoresmadrid.oegam2comun.estadisticas.service.ServicioEstadisticasCorreoCtit;
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

/**
 * Clase del proceso que realiza el envío de estadísticas diarias de MATE y CTIT
 */
public class ProcesoEnvioEstadisticas extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoEnvioEstadisticas.class);

	@Autowired
	ServicioEstadisticasCorreoCtit servicioEstadisticasCorreoCtit;

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

		if ("si".equals(gestorPropiedades.valorPropertie("estadisticas.lanza.proceso.considerando.festivos.nacionales"))) {
			considerarFestivos = true;
		}
		if ("si".equals(gestorPropiedades.valorPropertie("estadisticas.lanza.proceso.forzar"))) {
			forzarEjecucion = true;
		}

		try {
			log.info("Proceso " + getProceso() + " -- Procesando petición");
			if (!hayEnvioData(getProceso())) {
				if (forzarEjecucion || utilesFecha.esFechaLaborable(considerarFestivos)) {
					for (EstadisticasTiposEnum tipoEnvio : EstadisticasTiposEnum.values()) {
						realizarEnvio(tipoEnvio);
					}
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

	private void realizarEnvio(EstadisticasTiposEnum tipoEnvio) {
		ResultBean resultado = new ResultBean();
		log.info("Inicio Estadisticas " + tipoEnvio.getNombreEnum());
		try {
			switch (tipoEnvio) {
				case CTIT:
					resultado = servicioEstadisticasCorreoCtit.enviarMailEstadisticasCTIT();
					break;
				case MATE:
					resultado = servicioEstadisticasCorreoMate.enviarMailEstadisticasMate();
					break;
				case DELEGACIONES:
					resultado = servicioEstadisticasCorreoCtit.enviarMailEstadisticasCTITDelegaciones();
					break;
			}
			actualizaEnvioDataEstadisticas(resultado, tipoEnvio);
		} catch (Throwable e) {
			actualizaEnvioDataEstadisticas(resultado, tipoEnvio);
			log.error("Error Estadisticas " + tipoEnvio.getNombreEnum(), e);
		}
	}

	private void actualizaEnvioDataEstadisticas(ResultBean resultado, EstadisticasTiposEnum tipoEnvio) {
		if (!resultado.getError()) {
			log.info("Fin Correcto Estadisticas " + tipoEnvio.getNombreEnum());
			try {
				actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO, ConstantesProcesos.EJECUCION_CORRECTA, "0");
			} catch (Exception e) {
				log.error("Error al Guardar Envio Data para " + getProceso(), e);
			}
		} else {
			log.info("Fin Incorrecto Estadisticas " + tipoEnvio.getNombreEnum());
			try {
				actualizarEjecucion(getProceso(), ConstantesProcesos.ERROR_ESTADISTICAS, ConstantesProcesos.EJECUCION_NO_CORRECTA, "0");
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + getProceso(), e);
			}
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.ESTADISTICAS_NUEVO.getNombreEnum();
	}
}