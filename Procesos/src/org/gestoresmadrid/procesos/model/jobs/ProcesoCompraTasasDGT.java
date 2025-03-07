package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioCompraTasas;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import colas.constantes.ConstantesProcesos;

/**
 * Proceso encargado de la compra de las tasas
 * @author erds
 */
public class ProcesoCompraTasasDGT extends AbstractProcesoBase {
	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoCompraTasasDGT.class);

	private static final String NOTIFICACION = "PROCESO COMPRA DE TASAS FINALIZADO";
	private static final String TIPO_TRAMITE_NOTIFICACION = "N/A";

	@Autowired
	private ServicioCompraTasas servicioCompraTasas;

	@Autowired
	private ServicioNotificacion servicioNotificacion;

	@Override
	protected String getProceso() {
		return ProcesosEnum.COMPRA_TASAS_DGT.getNombreEnum();
	}

	@Override
	protected void doExecute() throws JobExecutionException {

		// En el caso de error, en los dos siguientes String se guarda el resultado para actualizar la ultima ejecucion del proceso

		ColaBean colaBean = null;

		try {
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				// En este momento no existen peticiones pendientes en la cola
				sinPeticiones();
	
			} else {
				
				if (log.isInfoEnabled()) {
					log.info("Proceso " + ProcesosEnum.COMPRA_TASAS_DGT.getNombreEnum() + " -- Solicitud encontrada [" + colaBean.getIdEnvio() + "]");
				}
	
				String resultadoEjecucion = null;
				String  excepcion = null;
				if (colaBean.getIdTramite() == null) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "No se ha recuperado el identificador de la compra de la solicitud con identificador: " + colaBean.getIdEnvio();
					log.error(excepcion);
					try {
						finalizarTransaccionConErrorNoRecuperable(colaBean, excepcion);
					} catch (BorrarSolicitudExcepcion e) {
						log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}

				} else {
					// Existe identificador de compra, se recupera
					RespuestaTasas respuesta = servicioCompraTasas.realizarCompraTasas(new Long(colaBean.getIdTramite().longValue()));

					if (respuesta.getException() != null) {
						// Si se obtuvo excepcion, se propaga para ser tratado como error recuperable
						throw respuesta.getException();

					} else if (respuesta.isError()) {
						// Ocurrio un error en el servicio
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						colaBean.setRespuesta(respuesta.getMensajeError());
						try {
							finalizarTransaccionConErrorNoRecuperable(colaBean, respuesta.getMensajeError());
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
	
					} else {
						// No hay errores
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaBean.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
						finalizarTransaccionCorrecta(colaBean, resultadoEjecucion);
					}
				}
				actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de compra de tasas", e);
			String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
			if (colaBean != null && colaBean.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperable(colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		}
	}

	private void crearNotificacion(ColaBean cola, BigDecimal estadoNuevo) {
		if (cola != null && cola.getIdTramite() != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoCompra.PENDIENTE_WS.getCodigo()));
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TIPO_TRAMITE_NOTIFICACION);
			notifDto.setIdTramite(cola.getIdTramite());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioCompraTasas.actualizarEstado(EstadoCompra.FINALIZADO_ERROR, cola.getIdTramite().longValue(), respuestaError);
		servicioCompraTasas.tratarDevolverCredito(cola.getIdTramite().longValue(), cola.getIdUsuario());
		crearNotificacion(cola, new BigDecimal(EstadoCompra.FINALIZADO_ERROR.getCodigo()));
	}

	@Override
	protected void finalizarTransaccionCorrecta(ColaBean cola, String resultado){
		super.finalizarTransaccionCorrecta(cola, resultado);
		servicioCompraTasas.actualizarEstado(EstadoCompra.FINALIZADO_OK, cola.getIdTramite().longValue(), null);
		crearNotificacion(cola, new BigDecimal(EstadoCompra.FINALIZADO_OK.getCodigo()));
	}

}
