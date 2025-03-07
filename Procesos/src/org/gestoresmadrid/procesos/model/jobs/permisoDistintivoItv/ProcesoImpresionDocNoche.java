package org.gestoresmadrid.procesos.model.jobs.permisoDistintivoItv;

import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioImpNochePrmDstvFicha;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoImpresionDocNoche extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoImpresionDocNoche.class);

	@Autowired
	ServicioImpNochePrmDstvFicha servicioImpNochePrmDstvFicha;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Override
	protected void doExecute() throws JobExecutionException {
		String resultadoEjecucion = null;
		String mensajeResultado = null;
		if (!servicioProcesos.hayEnvio(getProceso())) {
			try {
				log.info("Inicio proceso: " + getProceso());
				ResultadoDistintivoDgtBean resultado = servicioImpNochePrmDstvFicha.imprimirDocumentos();
				if (resultado != null && resultado.getExcepcion() != null) {
					throw new Exception(resultado.getExcepcion());
				} else if (resultado != null && resultado.isError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					mensajeResultado = resultado.getMensaje();
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					mensajeResultado = "Solicitud procesada correctamente";
				}
				peticionCorrecta();
			} catch (Exception e) {
				log.error("Ocurrio un error no controlado en el proceso PERMISO DISTINTIVO, error: ", e);
				String messageException = getMessageException(e);
				peticionRecuperable();
				resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
				mensajeResultado = messageException;
			}
			actualizarEjecucion(getProceso(), mensajeResultado, resultadoEjecucion, "0");
			log.info("Fin proceso: " + getProceso());
		} else {
			peticionCorrecta();
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.IMPRESION_DOC_NOCHE.getNombreEnum();
	}
}
