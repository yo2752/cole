package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioWebServiceJustificanteProf;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoJustificanteProfesional extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoJustificanteProfesional.class);

	private static final String NOTIFICACION = "PROCESO JUSTIFICANTE PROFESIONAL FINALIZADO";

	@Autowired
	ServicioWebServiceJustificanteProf servicioWebServiceJustificanteProf;

	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto solicitud = null;
		String resultadoEjecucion = null;
		String excepcion = null;

		try {
			solicitud = tomarSolicitud();
			if (solicitud == null) {
				sinPeticiones();
			} else {
				if (solicitud.getXmlEnviar() == null || solicitud.getXmlEnviar().isEmpty()) {
					finalizarTransaccionConErrorNoRecuperable(solicitud, "No existen el xml de envio.");
					solicitud.setRespuesta("Error: La Solicitud " + solicitud.getIdEnvio() + " no contiene xml de envio.");
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
				} else {
					new UtilesWSMatw().cargarAlmacenesTrafico();
					ResultadoJustificanteProfesional resultado = servicioWebServiceJustificanteProf.procesarEmisionJustificanteProf(solicitud);
					if (resultado != null && resultado.getExcepcion() != null) {
						throw new Exception(resultado.getExcepcion());
					} else if (resultado != null && resultado.isError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						solicitud.setRespuesta(resultado.getMensajeError());
						finalizarTransaccionConErrorNoRecuperable(solicitud, resultado);
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta(resultado.getRespuesta());
						finalizarTransaccionCorrectaJustifProf(solicitud, resultadoEjecucion, resultado);
					}
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error en el proceso de justificantes profesional, error: ", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperableConErrorServico(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
			} catch (Exception e1) {
				log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e1.toString());
			}
			actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}
	}

	private void finalizarTransaccionConErrorNoRecuperable(ColaDto cola, ResultadoJustificanteProfesional resultado) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, resultado.getMensajeError());
		servicioWebServiceJustificanteProf.cambiarEstadoJustificanteProfSolicitud(cola.getIdTramite(), EstadoJustificante.Finalizado_Con_Error, cola.getIdUsuario());
		servicioWebServiceJustificanteProf.tratarDevolverCredito(cola.getIdTramite(), cola.getIdUsuario());
		crearNotificacion(cola, new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()), resultado.getNumExpediente());
	}

	private void finalizarTransaccionCorrectaJustifProf(ColaDto cola, String resultadoEjecucion, ResultadoJustificanteProfesional resultado) {
		super.finalizarTransaccionCorrecta(cola, resultadoEjecucion);
		servicioWebServiceJustificanteProf.cambiarEstadoJustificanteProfSolicitud(cola.getIdTramite(), EstadoJustificante.Ok, cola.getIdUsuario());
		crearNotificacion(cola, new BigDecimal(EstadoJustificante.Ok.getValorEnum()), resultado.getNumExpediente());
	}

	private void crearNotificacion(ColaDto cola, BigDecimal estadoNuevo, String numExpediente) {
		if (cola != null && numExpediente != null && !numExpediente.isEmpty()) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoJustificante.Pendiente_DGT.getValorEnum()));
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TipoTramiteTrafico.JustificantesProfesionales.getValorEnum());
			notifDto.setIdTramite(new BigDecimal(numExpediente));
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.JUSTIFICANTE_PROFESIONAL.toString();
	}
}
