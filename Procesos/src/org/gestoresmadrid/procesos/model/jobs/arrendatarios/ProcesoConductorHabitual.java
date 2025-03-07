package org.gestoresmadrid.procesos.model.jobs.arrendatarios;

import java.math.BigDecimal;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultadoWSCaycBean;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioWebServiceConductorHabitual;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoConductorHabitual extends AbstractProcesoBase {

	private static final String NOTIFICACION = "PROCESO CONDUCTOR FINALIZADO";
	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoConductorHabitual.class);
	@Autowired
	private ServicioNotificacion servicioNotificacion;
	@Autowired
	private ServicioWebServiceConductorHabitual servicioWebServiceConductorHabitual;
	@Autowired
	ServicioProcesos servicioProcesos;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean colaBean = null;
		try {
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				sinPeticiones();
			} else {
				new UtilesWSMatw().cargarAlmacenesTrafico();
				String resultadoEjecucion = null;
				String excepcion = null;
				if (colaBean.getXmlEnviar() == null || colaBean.getXmlEnviar().isEmpty()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "No se ha recuperado el tipo de consulta arrendatario correspondiente a la solicitud con identificador: "
							+ colaBean.getIdEnvio();
					log.error(excepcion);
					try {
						finalizarTransaccionConErrorNoRecuperable(colaBean, excepcion);
					} catch (BorrarSolicitudExcepcion e) {
						log.error(
								"Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				} else {

					ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
					// Hacer distincion entre alta y modificacio
					if (colaBean.getXmlEnviar().contains(ConstantesGestorFicheros.XML_ALTA_CONDUCTOR_HABITUAL)) {
						resultado = servicioWebServiceConductorHabitual.procesarAltaConductor(colaBean);
					} else {
						resultado = servicioWebServiceConductorHabitual.procesarModificacionConductor(colaBean);
					}

					if (resultado.getExcepcion() != null) {
						throw resultado.getExcepcion();
					} else if (resultado.getError()) {
						// Ocurrio un error en el servicio
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						colaBean.setRespuesta(resultado.getMensajeError());
						try {
							finalizarTransaccionConErrorNoRecuperable(colaBean, resultado.getMensajeError());
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: "
									+ e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaBean.setRespuesta(resultado.getRespuestaWS());
						finalizarTransaccionCorrectaConductor(colaBean, resultadoEjecucion, resultado.getNumRegistro());
						crearNotificacion(colaBean, new BigDecimal(EstadoCaycEnum.Finalizado.getValorEnum()));
					}
				}
				actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			//log.error("Ocurrio un error no controlado en el proceso de conductor habitual", e);
			String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
			if (colaBean != null && colaBean.getProceso() != null) {
				try {
					if (messageException.length() > 500) {
						messageException = messageException.substring(0, 500);
					}
					finalizarTransaccionErrorRecuperableConErrorServico(colaBean,
							ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			}
		} catch (OegamExcepcion e1) {
			log.error("Ocurrio un error no controlado en el proceso vehículos atex5 al cargar los almacenes de claves",
					e1);
			actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, e1.getMessage());
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.CONDUCTOR_HABITUAL.getNombreEnum();
	}

	@Override
	protected void finalizarTransaccionErrorRecuperableConErrorServico(ColaBean cola, String respuestaError)
			throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentosProceso2(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			servicioProcesos.tratarRecuperable(cola, respuestaError);
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError)
			throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServiceConductorHabitual.cambiarEstadoConsulta(cola.getIdTramite(), cola.getIdUsuario(),
				EstadoCaycEnum.Finalizado_Con_Error, respuestaError, null);
		crearNotificacion(cola, new BigDecimal(EstadoCaycEnum.Finalizado_Con_Error.getValorEnum()));
	}

	private void finalizarTransaccionCorrectaConductor(ColaBean cola, String resultado, String numRegistro) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		servicioWebServiceConductorHabitual.cambiarEstadoConsulta(cola.getIdTramite(), cola.getIdUsuario(), EstadoCaycEnum.Finalizado,	cola.getRespuesta(), numRegistro);
		crearNotificacion(cola, new BigDecimal(EstadoCaycEnum.Finalizado.getValorEnum()));
	}

	private void crearNotificacion(ColaBean cola, BigDecimal estadoNuevo) {
		if (cola != null && cola.getIdTramite() != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum()));
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(cola.getTipoTramite());
			notifDto.setIdTramite(cola.getIdTramite());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}
}
