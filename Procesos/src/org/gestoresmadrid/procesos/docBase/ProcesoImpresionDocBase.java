package org.gestoresmadrid.procesos.docBase;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoImpresionDocBase extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoImpresionDocBase.class);

	@Autowired
	ServicioGestionDocBase servicioGestionDocBase;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto solicitud = null;
		String resultadoEjecucion = null;
		try {
			log.info("Inicio proceso: " + getProceso());
			solicitud = tomarSolicitud();
			if (solicitud == null) {
				sinPeticiones();
			} else {
				ResultadoDocBaseBean resultado = servicioGestionDocBase.imprimirDocBase(solicitud.getIdTramite().longValue());
				if (resultado != null && resultado.getExcepcion() != null) {
					throw new Exception(resultado.getExcepcion());
				} else if (resultado != null && resultado.getError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					solicitud.setRespuesta(resultado.getMensaje());
					finalizarTransaccionConErrorNoRecuperable(solicitud, resultado.getMensaje());
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					solicitud.setRespuesta("Solicitud procesada correctamente");
					finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, null);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de Generar Doc.Base, error: ", e);
			String messageException = getMessageException(e);
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (Exception e1) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		}
		log.info("Fin proceso: " + getProceso());
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.IMPRIMIR_DOC_BASE.getNombreEnum();
	}

}