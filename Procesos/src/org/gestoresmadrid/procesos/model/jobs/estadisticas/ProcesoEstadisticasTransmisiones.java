package org.gestoresmadrid.procesos.model.jobs.estadisticas;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.estadisticas.ctit.diarias.bean.RespuestaEstadisticasTransmisionBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoEstadisticasTransmisiones extends AbstractProceso {

	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoEstadisticasTransmisiones.class);

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		RespuestaEstadisticasTransmisionBean respuesta = null;
		String resultadoEjecucion = null;
		String excepcion = null;

		try {
			if (!hayEnvioData(getProceso()) && utilesFecha.esFechaLaborable(true)) {
				respuesta = servicioTramiteTrafico.getListadoTransmisionesDiarios();
				if (respuesta != null && respuesta.getException() != null) {
					throw respuesta.getException();
				} else if (respuesta != null && respuesta.isError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					String mensaje = null;
					if (respuesta.getMensajesError() != null) {
						for (String mnsj : respuesta.getMensajesError()) {
							mensaje += mnsj + ". ";
						}
					} else {
						mensaje = respuesta.getMensaje();
					}
					excepcion = mensaje;
				} else {
					excepcion = ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO;
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
				}
				actualizarEjecucion(getProceso(), excepcion, resultadoEjecucion, "0");
			}
			peticionCorrecta();
		} catch (Throwable e) {
			log.error("Ocurrio un error no controlado en el proceso de estadisticas de transmisiones", e);
			String messageException = getMessageException(e);
			actualizarEjecucion(getProceso(), messageException, resultadoEjecucion, "0");
			errorNoRecuperable();
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.ESTADISTICAS_TRANS_N.getNombreEnum();
	}
}
