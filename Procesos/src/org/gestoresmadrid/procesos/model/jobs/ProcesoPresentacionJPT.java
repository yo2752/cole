package org.gestoresmadrid.procesos.model.jobs;

import java.util.List;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.presentacion.jpt.model.service.ServicioPresentacionJefaturaTrafico;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoPresentacionJPT extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoPresentacionJPT.class);

	@Autowired
	private ServicioPresentacionJefaturaTrafico servicioPresentacionJefaturaTrafico;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		log.info("INICIO PROCESO PRESENTACION JPT");
		String resultadoEjecucion = null;
		String excepcion = null;
		try {
			if (utilesFecha.esFechaLaborable(true) && !hayEnvioData(getProceso())) {
				ResultBean result = servicioPresentacionJefaturaTrafico.obtenerNoPresentados();

				if (!result.getError()) {
					List<TramiteTrafDto> noPresentados = (List<TramiteTrafDto>) result.getAttachment("NOPRESENTADOS");
					if (noPresentados != null && noPresentados.size() > 0) {
						servicioPresentacionJefaturaTrafico.enviarCorreo(noPresentados);
					} else {
						log.info("No existen documentos no presentados.");
					}
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					excepcion = ConstantesProcesos.EJECUCION_CORRECTA;
					peticionCorrecta();
					log.info("Proceso " + getProceso() + " -- Proceso ejecutado correctamente");
				} else {
					log.error("Error al obtener los expedientes no presentados");
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "Error al obtener los expedientes no presentados";
					peticionRecuperable();
				}
				actualizarEjecucion(getProceso(), excepcion, resultadoEjecucion, "0");
			} else {
				peticionCorrecta();
			}
		} catch (Throwable e) {
			String messageException = getMessageException(e);
			log.error("Error en presentacion JPT", e);
			peticionRecuperable();
			actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.PRESENTACION_JPT.getNombreEnum();
	}
}