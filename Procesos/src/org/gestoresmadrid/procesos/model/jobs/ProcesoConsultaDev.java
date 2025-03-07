package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoConsultaDev;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ResultadoWSConsultaDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioWebServiceConsultaDev;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoConsultaDev extends AbstractProceso {

	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoConsultaDev.class);

	private static final String NOTIFICACION = "PROCESO CONSULTA DEV FINALIZADO PARA EL CIF: ";

	@Autowired
	ServicioWebServiceConsultaDev servicioWebServiceConsultaDev;

	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto colaDto = null;
		String resultadoEjecucion = null;
		String excepcion = null;

		try {
			log.info("Proceso " + getProceso() + " -- Buscando Solicitud");
			colaDto = tomarSolicitud();
			if (colaDto == null) {
				sinPeticiones();
			} else {
				if (colaDto.getIdTramite() == null) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "No se ha recuperado el id de la consulta dev correspondiente a la solicitud con identificador: " + colaDto.getIdEnvio();
					finalizarTransaccionConErrorNoRecuperable(colaDto, excepcion);
				} else {
					new UtilesWSMatw().cargarAlmacenesTrafico();
					ResultadoWSConsultaDev respuesta = servicioWebServiceConsultaDev.generarConsultaDev(colaDto);
					if (respuesta.getExcepcion() != null) {
						throw respuesta.getExcepcion();
					} else if (respuesta.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						String mensaje = null;
						if (respuesta.getListaMensajes() != null) {
							for (String mnsj : respuesta.getListaMensajes()) {
								mensaje += mnsj + ". ";
							}
						} else {
							mensaje = respuesta.getMensajeError();
						}
						colaDto.setRespuesta(mensaje);
						finalizarTransaccionConErrorNoRecuperable(colaDto, mensaje, respuesta.getCifPeticion());
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaDto.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
						finalizarTransaccionCorrecta(colaDto, resultadoEjecucion, respuesta.getCifPeticion());
					}
				}
				actualizarUltimaEjecucion(colaDto, resultadoEjecucion, excepcion);
			}
		} catch (Throwable e) {
			log.error("Ocurrio un error no controlado en el proceso de consulta dev", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperableConErrorServico(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
			} catch (Exception e1) {
				log.error("Error al borrar la solicitud: " + colaDto.getIdEnvio() + ", Error: " + e1.toString());
			}
			actualizarUltimaEjecucion(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}
	}

	private void crearNotificacion(ColaDto cola, BigDecimal estadoNuevo, String cifPeticion) {
		if (cola != null && cola.getIdTramite() != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoConsultaDev.Pdte_Consulta_Dev.getValorEnum()));
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION + cifPeticion);
			notifDto.setTipoTramite(TipoTramiteTrafico.Consulta_Dev.getValorEnum());
			notifDto.setIdTramite(cola.getIdTramite());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	private void finalizarTransaccionConErrorNoRecuperable(ColaDto cola, String respuestaError, String cifPeticion) {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServiceConsultaDev.cambiarEstadoConsulta(cola.getIdTramite(), cola.getIdUsuario(), EstadoConsultaDev.Finalizado_Con_Error);
		servicioWebServiceConsultaDev.devolverCreditos(cola.getIdTramite(), cola.getIdContrato(), cola.getIdUsuario());
		crearNotificacion(cola, new BigDecimal(EstadoConsultaDev.Finalizado_Con_Error.getValorEnum()), cifPeticion);
	}

	private void finalizarTransaccionCorrecta(ColaDto cola, String resultado, String cifPeticion) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		servicioWebServiceConsultaDev.cambiarEstadoConsulta(cola.getIdTramite(), cola.getIdUsuario(), EstadoConsultaDev.Finalizado);
		crearNotificacion(cola, new BigDecimal(EstadoConsultaDev.Finalizado.getValorEnum()), cifPeticion);
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.CONSULTA_DEV.getNombreEnum();
	}
}
