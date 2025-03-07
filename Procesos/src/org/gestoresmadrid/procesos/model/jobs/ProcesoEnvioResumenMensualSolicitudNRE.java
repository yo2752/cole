package org.gestoresmadrid.procesos.model.jobs;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.solicitudNRE06.service.ServicioSolicitudNRE06;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcesoEnvioResumenMensualSolicitudNRE extends AbstractProceso {

	private static final Logger log = LoggerFactory.getLogger(ProcesoEnvioResumenMensualSolicitudNRE.class);

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	ServicioSolicitudNRE06 servicioSolicitudNRE06;

	@Override
	protected void doExecute() throws JobExecutionException {
		String resultadoEjecucion = null;
		String mensajeResultado = "";
		if (!hayEnvioData(getProceso())) {
			try {
				log.error("El proceso: " + getProceso() + " arranco.");
				if (utilesFecha.esPrimerDiaMes()) {
					ResultadoSolicitudNRE06Bean resultado = servicioSolicitudNRE06.generarResumen();
					if (resultado != null && resultado.getException() != null) {
						resultado.getException();
					} else if (resultado.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						for (String mensaje : resultado.getListaError()) {
							mensajeResultado += mensaje;
						}
						if (mensajeResultado != null && mensajeResultado.length() > 500) {
							mensajeResultado = mensajeResultado.substring(0, 495);
						}
						errorNoRecuperable();
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						mensajeResultado = "Solicitud procesada correctamente";
						peticionCorrecta();
					}
					actualizarEjecucion(getProceso(), mensajeResultado, resultadoEjecucion, "0");
				}
			} catch (Exception e) {
				log.error("Excepcion Proceso: " + getProceso(), e);
				mensajeResultado = getMessageException(e);
				resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
				errorNoRecuperable();
			}
			actualizarEjecucion(getProceso(), mensajeResultado, resultadoEjecucion, "0");
			log.error("Fin proceso: " + getProceso());
		} else {
			peticionCorrecta();
			log.error("El proceso: " + getProceso() + " tiene envio data.");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.ENVIO_RESUMEN_NRE06.getNombreEnum();
	}
}