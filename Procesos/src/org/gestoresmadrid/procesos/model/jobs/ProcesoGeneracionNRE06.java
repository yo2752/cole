package org.gestoresmadrid.procesos.model.jobs;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
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

import utilidades.web.OegamExcepcion;

public class ProcesoGeneracionNRE06 extends AbstractProceso {

	private static final Logger log = LoggerFactory.getLogger(ProcesoGeneracionNRE06.class);

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
		ColaDto solicitud = new ColaDto();
		Boolean esLaborable = true;
		Boolean forzarEjecucion = false;
		String resultadoEjecucion = null;

		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable(
						"SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (Exception | OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.FORZAR_EJECUCION_MATRICULACION))) {
			forzarEjecucion = true;
		}

		if (esLaborable || forzarEjecucion) {
			try {
				log.info("Proceso " + getProceso() + " -- Buscando Solicitud");
				solicitud = tomarSolicitud();
				if (solicitud == null) {
					sinPeticiones();
				} else {
					log.info("Solicitud encontrada " + getProceso() + ": " + solicitud.getIdTramite());
					ResultadoSolicitudNRE06Bean resultado = servicioSolicitudNRE06
							.solicitarNRE06(solicitud.getIdTramite(), solicitud.getIdUsuario());
					if (resultado != null && resultado.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						solicitud.setRespuesta(resultado.getMensaje());
						finalizarTransaccionErrorRecuperable(solicitud, solicitud.getRespuesta());
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta(resultadoEjecucion);
						finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
					}
					actualizarUltimaEjecucion(solicitud, resultadoEjecucion, null);
				}
			} catch (Exception e) {
				log.error("Excepcion Proceso: " + getProceso(), e);
				String messageException = getMessageException(e);
				try {
					finalizarTransaccionErrorRecuperable(solicitud,
							ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
				} catch (Exception e1) {
					log.error("No ha sido posible borrar la solicitud del proceso de " + getProceso(), e1);
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			}
		} else {
			peticionCorrecta();
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.GEN_NRE06.getNombreEnum();
	}
}