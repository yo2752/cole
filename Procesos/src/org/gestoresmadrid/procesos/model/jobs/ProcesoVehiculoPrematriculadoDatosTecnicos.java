package org.gestoresmadrid.procesos.model.jobs;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioWebServiceVehiculosPrematriculados;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.TrataMensajeExcepcion;

public class ProcesoVehiculoPrematriculadoDatosTecnicos extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoVehiculoPrematriculadoDatosTecnicos.class);

	@Autowired
	ServicioWebServiceVehiculosPrematriculados servicioWebServiceVehiculosPrematriculados;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

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
				ResultBean result = servicioWebServiceVehiculosPrematriculados.tramitarPeticion(solicitud);
				if (result.getError()) {
					if (result.getMensaje() != null && !result.getMensaje().isEmpty()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						if (result.getAttachment(ServicioWebServiceVehiculosPrematriculados.RECUPERABLE) != null) {
							boolean esRecuperable = (Boolean) result.getAttachment(ServicioWebServiceVehiculosPrematriculados.RECUPERABLE);
							respuestaError(solicitud, esRecuperable, resultadoEjecucion);
						} else {
							finalizarTransaccionErrorRecuperableConErrorServico(solicitud, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
						}
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						finalizarTransaccionErrorRecuperableConErrorServico(solicitud, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
					}
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					solicitud.setRespuesta(resultadoEjecucion);
					finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
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

	private void respuestaError(ColaDto solicitud, boolean esRecuperable, String respuestaError) throws TrataMensajeExcepcion, CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		if (esRecuperable) {
			finalizarTransaccionErrorRecuperableConErrorServico(solicitud, respuestaError);
		} else {
			servicioTramiteTrafico.actualizarRespuestaMateEitv(respuestaError, solicitud.getIdTramite());
			finalizarTransaccionConErrorNoRecuperable(solicitud, respuestaError);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.VPDATOSEITV.getNombreEnum();
	}
}
