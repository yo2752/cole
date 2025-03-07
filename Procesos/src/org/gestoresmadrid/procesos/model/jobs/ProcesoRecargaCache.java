package org.gestoresmadrid.procesos.model.jobs;

import java.util.List;

import org.gestoresmadrid.core.administracion.model.vo.RecargaCacheVO;
import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaCache;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoRecargaCache extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoRecargaCache.class);

	@Autowired
	private ServicioRecargaCache servicioRecargaCache;

	protected void doExecute() throws JobExecutionException {
		String resultadoEjecucion = null;
		String excepcion = null;
		try {
			log.info("Inicio ProcesoRecargaCache");

			List<RecargaCacheVO> peticionesPendientes = servicioRecargaCache.recuperarSolicitudesPendientes();
			if (peticionesPendientes != null && peticionesPendientes.size() > 0) {
				log.info("Se ha encontrado " + peticionesPendientes.size() + " peticiones de refresco.");
				ResultBean result = servicioRecargaCache.refrescarCache(peticionesPendientes);
				if (!result.getError() && result.getListaMensajes().size() == 0) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					peticionCorrecta();
				} else if (!result.getError() && result.getListaMensajes().size() > 0) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					peticionRecuperable();
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					errorNoRecuperable();
				}
				actualizarEjecucion(getProceso(), excepcion, resultadoEjecucion, "0");
			} else {
				sinPeticiones();
			}
		} catch (Exception e) {
			log.error("Excepcion Refreco de Caché", e);
			String messageException = getMessageException(e);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.RECARGACACHE.getNombreEnum();
	}
}
