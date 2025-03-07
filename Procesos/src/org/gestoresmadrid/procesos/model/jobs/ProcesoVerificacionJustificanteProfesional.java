package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesionalImprimir;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioWebServiceJustificanteProf;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioWebserviceJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoVerificacionJustificanteProfesional extends AbstractProceso {

	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoVerificacionJustificanteProfesional.class);

	public static final String NOTIFICACION = "PROCESO VERIFIC JP FINALIZADO";
	public static final String TIPO_TRAMITE_NOTIFICACION = "N/A";

	@Autowired
	ServicioWebServiceJustificanteProf servicioWebServiceJustificanteProf;

	@Autowired
	ServicioWebserviceJustificanteProfesional servicioWebserviceJustificanteProfesional;

	@Autowired
	ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	ServicioJustificanteProfesionalImprimir servicioJustificanteProfesionalImprimir;

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
				log.info("Proceso " + getProceso() + " -- Solicitud encontrada [" + solicitud.getIdEnvio() + "]");
				if (solicitud.getIdTramite() == null) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					solicitud.setRespuesta(resultadoEjecucion);
					excepcion = "No se ha recuperado el identificador de la verificacion de justificante de la solicitud con identificador: " + solicitud.getIdEnvio();
					finalizarTransaccionConErrorNoRecuperable(solicitud, excepcion);
				} else {
					new UtilesWSMatw().cargarAlmacenesTrafico();
					ResultadoJustificanteProfesional resultado = servicioWebServiceJustificanteProf.procesarVerificacionJustificanteProf(solicitud);
					if (resultado != null && resultado.getExcepcion() != null) {
						throw new Exception(resultado.getExcepcion());
					} else if (resultado != null && resultado.isError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						solicitud.setRespuesta(resultado.getMensajeError());
						finalizarTransaccionConErrorNoRecuperable(solicitud, resultado.getMensajeError());
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta(resultadoEjecucion);
						finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
					}
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Throwable e) {
			log.error("Ocurrio un error en el proceso de verificacion de justificantes", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
			} catch (Exception e1) {
				log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e1.toString());
			}
			actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}
	}

	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaDto cola, String respuestaError) {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		if (cola.getIdTramite() != null) {
			BigDecimal estadoError = new BigDecimal(EstadoJustificante.Finalizado_Con_Error.getValorEnum());
			JustificanteProfDto justificanteProfDto = servicioJustificanteProfesional.getJustificanteProfesional(cola.getIdTramite().longValue());
			servicioJustificanteProfesional.guardarJustificanteProfesional(justificanteProfDto, justificanteProfDto.getNumExpediente(), cola.getIdUsuario(), justificanteProfDto.getEstado(),
					estadoError, respuestaError);
			crearNotificacion(cola, justificanteProfDto, estadoError);
		}
	}

	private void crearNotificacion(ColaDto cola, JustificanteProfDto justificanteProfDto, BigDecimal estadoNuevo) {
		if (justificanteProfDto != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(justificanteProfDto.getEstado());
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TIPO_TRAMITE_NOTIFICACION);
			notifDto.setIdTramite(BigDecimal.valueOf(justificanteProfDto.getIdJustificanteInterno()));
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.VERIFICACIONJPROF.toString();
	}
}
