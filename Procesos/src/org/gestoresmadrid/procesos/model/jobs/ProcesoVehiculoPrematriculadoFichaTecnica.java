package org.gestoresmadrid.procesos.model.jobs;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoVehiculoPrematriculadoFichaTecnica extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoVehiculoPrematriculadoFichaTecnica.class);

	@Autowired
	ServicioVehiculosPrematriculados servicioVehiculosPrematriculados;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto solicitud = new ColaDto();
		String resultadoEjecucion = null;
		String excepcion = null;
		try {
			log.info("Proceso " + getProceso() + " -- Buscando Solicitud");
			solicitud = tomarSolicitud();
			if (solicitud == null) {
				sinPeticiones();
			} else {
				if (solicitud.getIdTramite() != null) {
					Long id = solicitud.getIdTramite().longValue();
					ResultBean rs = servicioVehiculosPrematriculados.consultaFichaTecnica(id);
					if (rs.getError()) {
						String respuesta = "";
						for (String error : rs.getListaMensajes()) {
							respuesta += error + ". ";
						}
						solicitud.setRespuesta(respuesta);
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						servicioVehiculosPrematriculados.guardarRespuestaFichaTecnica(false, respuesta, id);
						finalizarTransaccionConErrorNoRecuperable(solicitud, respuesta);
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						servicioVehiculosPrematriculados.guardarRespuestaFichaTecnica(true, ConstantesProcesos.OK, id);
						solicitud.setRespuesta(resultadoEjecucion);
						finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
					}
				} else {
					finalizarTransaccionConErrorNoRecuperable(solicitud, "No existe el idTramite.");
					solicitud.setRespuesta("No existe el idTramite.");
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Throwable e) {
			log.error("Excepcion " + getProceso(), e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperableConErrorServico(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
			} catch (Exception e1) {
				log.error("No ha sido posible borrar la solicitud", e1);
			}
			actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}

	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.VPFICHATECNICA.getNombreEnum();
	}
}
