package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioPresentacion576;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class Proceso576 extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger("Proceso576");

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	ServicioPresentacion576 servicioPresentacion576;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto solicitud = new ColaDto();
		TramiteTrafMatrDto tramiteTrafMatrDto = new TramiteTrafMatrDto();
		String resultadoEjecucion = null;
		String respuesta = "";

		try {
			solicitud = tomarSolicitud();
			if (solicitud != null && solicitud.getIdEnvio() != null) {
				log.debug("Recuperada solicitud con id envio: " + solicitud.getIdEnvio());
			}

			tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(solicitud.getIdTramite(), false, true);

			ContratoDto contrato = servicioContrato.getContratoDto(tramiteTrafMatrDto.getIdContrato());

			ResultBean resultsTramitacion = servicioPresentacion576.tramitar576(tramiteTrafMatrDto, contrato.getColegiadoDto().getAlias());

			if (resultsTramitacion != null && resultsTramitacion.getError()) {
				if (resultsTramitacion.getListaMensajes() != null) {
					for (String mensaje : resultsTramitacion.getListaMensajes()) {
						respuesta += mensaje + " - ";
					}
				}
				log.info("Respuesta errónea del trámite: " + solicitud.getIdTramite());
				resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				solicitud.setRespuesta(respuesta);
				finalizarTransaccionErrorRecuperable(solicitud, respuesta, tramiteTrafMatrDto);
			} else {
				log.info("La presentacion del 576 en la AEAT del expediente: " + solicitud.getIdTramite() + " se ha realizado correctamente. ");
				resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
				solicitud.setRespuesta(resultadoEjecucion);
				finalizarTransaccionCorrecta(solicitud, resultadoEjecucion, tramiteTrafMatrDto);
			}
		} catch (Throwable e) {
			log.error("Proceso 576: ", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + messageException, tramiteTrafMatrDto);
			} catch (Exception e1) {
				log.error("No ha sido posible borrar la solicitud", e1);
			}
			actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}
	}

	private void crearNotificacion(ColaDto cola, String textoNotificacion) {
		NotificacionDto notificacion = new NotificacionDto();
		notificacion.setDescripcion(textoNotificacion);
		notificacion.setIdTramite(cola.getIdTramite());
		notificacion.setEstadoAnt(new BigDecimal(0));
		notificacion.setEstadoNue(new BigDecimal(0));
		notificacion.setTipoTramite(cola.getTipoTramite());
		notificacion.setIdUsuario(cola.getIdUsuario().longValue());
		servicioNotificacion.crearNotificacion(notificacion);
	}

	private void finalizarTransaccionCorrecta(ColaDto cola, String resultadoEjecucion, TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion {
		tramiteTrafMatrDto.setRespuesta576("Presentacion 576 correcta. CEM actualizado");
		servicioTramiteTraficoMatriculacion.guardarTramite(tramiteTrafMatrDto, null, cola.getIdUsuario(), false, false, false);
		crearNotificacion(cola, "Presentacion 576 correcta. CEM actualizado");
		actualizarUltimaEjecucion(cola, resultadoEjecucion, null);
	}

	private void finalizarTransaccionErrorRecuperable(ColaDto cola, String respuestaError, TramiteTrafMatrDto tramiteTrafMatrDto) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			String respuesta = "Ha ocurrido un error durante la presentacion del 576 en la AEAT";
			tramiteTrafMatrDto.setRespuesta576(respuesta);
			try {
				ResultBean resultBean = servicioTramiteTraficoMatriculacion.guardarTramite(tramiteTrafMatrDto, null, cola.getIdUsuario(), false, false, false);
				if (resultBean.getError()) {
					log.error("Error al guardar el tramite: " + tramiteTrafMatrDto.getNumExpediente() + " tras la presentacion del 576 debido al siguiente error: " + resultBean.getMensaje());
				}
			} catch (OegamExcepcion e) {
				log.error("Error al guardar el trámite", e);
			}
			crearNotificacion(cola, "No se ha podido presentar el 576 en la AEAT");
			this.finalizarTransaccionConErrorNoRecuperable(cola, respuesta);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.PROCESO_576.getNombreEnum();
	}
}