package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesionalImprimir;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioWebserviceJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.JustificanteProfResult;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoEmisionJustificanteProfesional extends AbstractProcesoBase {

	// Constantes
	private static final int MAXIMA_LONGITUD_MENSAJE_COLA = 300;
	private static final String NOTIFICACION = "PROCESO GENERAR JP FINALIZADO";
	private static final String TIPO_TRAMITE_NOTIFICACION = "N/A";

	private static final int DIAS_VALIDEZ = 30;

	private ILoggerOegam log = LoggerOegam.getLogger(ConstantesProcesos.LOG_APPENDER_JUSTIFICANTES);

	@Autowired
	private ServicioWebserviceJustificanteProfesional servicioWebserviceJustificanteProfesional;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	private ServicioJustificanteProfesionalImprimir servicioJustificanteProfesionalImprimir;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioNotificacion servicioNotificacion;

	@Override
	protected String getProceso() {
		return ProcesosEnum.EMISIONJPROF.toString();
	}

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean colaBean = null;

		try {
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				// En este momento no existen peticiones pendientes en la cola
				sinPeticiones();

			} else {
				if (log.isInfoEnabled()) {
					log.info("Proceso " + ProcesosEnum.EMISIONJPROF.getNombreEnum() + " -- Solicitud encontrada [" + colaBean.getIdEnvio() + "]");
				}
				String resultadoEjecucion = null;
				String excepcion = null;
				if (colaBean.getIdTramite() == null) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "No se ha recuperado el identificador de la petición de justificante de la solicitud con identificador: " + colaBean.getIdEnvio();
					log.error(excepcion);
					try {
						finalizarTransaccionConErrorNoRecuperable(colaBean, excepcion);
					} catch (BorrarSolicitudExcepcion e) {
						log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}

				} else {
					// Existe id de justificante
					// Recuperar el DTO de justificante profesional
					JustificanteProfDto justificanteProfDto = servicioJustificanteProfesional.getJustificanteProfesional(colaBean.getIdTramite().longValue());
					TramiteTrafDto tramiteTraficoDto = null;

					JustificanteProfResult resultado;
					if (justificanteProfDto.getIdJustificante() != null) {
						// Ya tiene numero de justificante, se llama al WS de descarga.
						if (log.isInfoEnabled()) {
							log.info("Para el justificante con identificador interno " + justificanteProfDto.getIdJustificanteInterno()
									+ " ,se solicita la descarga del justificante al servicio, ya que tiene numero de justificante (" + justificanteProfDto.getIdJustificante().toString()
									+ "). Si se quiere emitir uno nuevo, se debe dar un alta nuevo");
						}
						resultado = servicioWebserviceJustificanteProfesional.descargarJustificante(justificanteProfDto);
					} else {
						// No tiene numero de justificante, es un intento de emision de nuevo justificante profesional

						// Validar el estado del justificante profesional
						tramiteTraficoDto = servicioTramiteTrafico.getTramiteDto(justificanteProfDto.getNumExpediente(), true);
						IntervinienteTraficoDto titular = servicioJustificanteProfesional.getTitularAdquiriente(tramiteTraficoDto);
						ResultBean resultadoValidacion = servicioJustificanteProfesional.validarDatosObligatorios(tramiteTraficoDto.getVehiculoDto(), titular, tramiteTraficoDto
								.getJefaturaTraficoDto());
						if (!resultadoValidacion.getError()) {
							// Validacion correcta, se llama al WS de emision.
							if (log.isInfoEnabled()) {
								log.info("Para el justificante con identificador interno " + justificanteProfDto.getIdJustificanteInterno()
										+ " ,se solicita la emision de un nuevo justificante profesional al servicio");
							}
							resultado = servicioWebserviceJustificanteProfesional.emisionJustificante(justificanteProfDto, tramiteTraficoDto);

							// Si la emision ha sido correcta, se actualiza la fecha de validez y el numero de justificante
							if (resultado != null && !resultado.isError() && resultado.getNumeroJustificante() != null && !resultado.getNumeroJustificante().isEmpty()) {
								justificanteProfDto.setIdJustificante(new BigDecimal(resultado.getNumeroJustificante()));

								Calendar calendar = Calendar.getInstance();
								justificanteProfDto.setFechaInicio(calendar.getTime());
								calendar.add(Calendar.DAY_OF_MONTH, DIAS_VALIDEZ);
								justificanteProfDto.setFechaFin(calendar.getTime());
								justificanteProfDto.setDiasValidez(new BigDecimal(DIAS_VALIDEZ));
							}
						} else {
							// El tramite no ha pasado la validacion
							resultado = new JustificanteProfResult();
							resultado.setError(true);
							resultado.setListaMensajes(resultadoValidacion.getListaMensajes());
						}
					}

					// Si se obtuvo excepcion, se propaga para ser tratado como error recuperable
					if (resultado.getException() != null) {
						throw resultado.getException();
					}

					// Guardar justificante profesional si se ha recibido
					if (!resultado.isError()) {
						boolean guardado = servicioJustificanteProfesionalImprimir.guardarDocumento(justificanteProfDto, resultado.getNumeroJustificante(), resultado.getJustificante());
						if (guardado) {
							resultado.setError(true);
							resultado.addMensaje("No se pudo guardar el fichero PDF con el justificante");
						}
					}

					if (resultado.isError()) {
						// Ocurrio un error en el servicio
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						String mensajeRespuesta = montarRespuesta(resultado.getListaMensajes(), MAXIMA_LONGITUD_MENSAJE_COLA);
						colaBean.setRespuesta(mensajeRespuesta);

						try {
							finalizarTransaccionConErrorNoRecuperable(colaBean, mensajeRespuesta);
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}

					} else {
						// No hay errores
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaBean.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
						finalizarTransaccionCorrecta(colaBean, resultadoEjecucion, justificanteProfDto, tramiteTraficoDto);
					}
				}
				actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
			}

		} catch (Exception e) {
			log.error("Ocurrio un error en el proceso de emision de justificantes", e);
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

	/**
	 * Recorre la lista de mensajes para formar un unico string
	 * @param listaMensajes
	 * @param maxlength
	 * @return
	 */
	private String montarRespuesta(List<String> listaMensajes, int maxlength) {
		StringBuilder sb = null;
		sb = new StringBuilder();
		if (listaMensajes != null) {
			for (String mensaje : listaMensajes) {
				if (sb.length() > 0) {
					sb.append("; ");
				}
				sb.append(mensaje);
			}
			// Si la longitud de los mensajes es mayor de lo que cabe en la tabla, se corta
			if (sb.length() > maxlength) {
				sb.setLength(maxlength);
			}
		}
		return sb != null ? sb.toString() : null;
	}

	private void finalizarTransaccionCorrecta(ColaBean cola, String resultado, JustificanteProfDto justificanteProfDto, TramiteTrafDto tramiteTrafico) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		// Actualizar el estado del justificante a finalizado OK
		BigDecimal estado = new BigDecimal(EstadoJustificante.Ok.getValorEnum());
		if (log.isInfoEnabled()) {
			log.info("Se actualiza el estado del justificante profesional con identificador interno " + justificanteProfDto.getIdJustificanteInterno() + " a finalizado");
		}

		servicioJustificanteProfesional.guardarJustificanteProfesional(justificanteProfDto, justificanteProfDto.getNumExpediente(), cola.getIdUsuario(), justificanteProfDto.getEstado(), estado, null);
		crearNotificacion(cola, justificanteProfDto, tramiteTrafico, estado);
	}

	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		BigDecimal estadoError = new BigDecimal(EstadoJustificante.Finalizado_Con_Error.getValorEnum());
		if (cola.getIdTramite() != null) {
			// Actualizar el estado del justificante a finalizado con error
			JustificanteProfDto justificanteProfDto = servicioJustificanteProfesional.getJustificanteProfesional(cola.getIdTramite().longValue());
			servicioJustificanteProfesional.guardarJustificanteProfesional(justificanteProfDto, justificanteProfDto.getNumExpediente(), cola.getIdUsuario(), justificanteProfDto.getEstado(),
					estadoError, respuestaError);
			TramiteTrafDto tramiteTrafico = servicioTramiteTrafico.getTramiteDto(justificanteProfDto.getNumExpediente(), false);
			crearNotificacion(cola, justificanteProfDto, tramiteTrafico, estadoError);
		}
	}

	private void crearNotificacion(ColaBean cola, JustificanteProfDto justificanteProfDto, TramiteTrafDto tramiteTrafico, BigDecimal estadoNuevo) {
		if (justificanteProfDto != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(justificanteProfDto.getEstado());
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			if (tramiteTrafico != null && tramiteTrafico.getTipoTramite() != null && !tramiteTrafico.getTipoTramite().isEmpty()) {
				notifDto.setTipoTramite(tramiteTrafico.getTipoTramite());
			} else {
				notifDto.setTipoTramite(TIPO_TRAMITE_NOTIFICACION);
			}
			notifDto.setIdTramite(BigDecimal.valueOf(justificanteProfDto.getIdJustificanteInterno()));
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

}
