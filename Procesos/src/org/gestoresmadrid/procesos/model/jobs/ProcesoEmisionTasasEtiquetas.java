package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.SubTipoFicheros;
import org.gestoresmadrid.core.model.enumerados.TipoFicheros;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioGeneracionEtiquetasTasas;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoEmisionTasasEtiquetas extends AbstractProcesoBase {
	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoEmisionTasasEtiquetas.class);

	private static final String NOTIFICACION = "PROCESO EMISION TASAS ETIQUETAS FINALIZADO";
	private static final String TIPO_TRAMITE_NOTIFICACION = "N/A";

	@Autowired
	private ServicioGeneracionEtiquetasTasas servicioGeneracionEtiquetasTasas;

	@Autowired
	private ServicioNotificacion servicioNotificacion;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean colaBean = null;
		try {
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				sinPeticiones();
			} else {
				if (log.isInfoEnabled()) {
					log.info("Proceso " + ProcesosEnum.EMISIONTASAETIQUETAS.getNombreEnum()
							+ " -- Solicitud encontrada [" + colaBean.getIdEnvio() + "]");
				}
				String resultadoEjecucion = null;
				String excepcion = null;
				if (colaBean.getXmlEnviar() == null) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "No se ha recuperado el nombre de fichero que contiene las tasas de pegatinas de la solicitud con identificador: "
							+ colaBean.getIdEnvio();
					log.error(excepcion);
					try {
						finalizarTransaccionConErrorNoRecuperableBorrandoDoc(colaBean, excepcion);
					} catch (BorrarSolicitudExcepcion e) {
						log.error(
								"Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				} else {
					RespuestaTasas respuesta = servicioGeneracionEtiquetasTasas.generarTasasPegatinasProceso(colaBean);
					if (respuesta == null) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaBean.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
						finalizarTransaccionCorrecta(colaBean, resultadoEjecucion);
					} else if (respuesta.getException() != null) {
						throw respuesta.getException();
					} else if (respuesta.isError()) {
						// Ocurrio un error en el servicio
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						colaBean.setRespuesta(respuesta.getMensajeError());
						try {
							finalizarTransaccionConErrorNoRecuperableBorrandoDoc(colaBean, respuesta.getMensajeError());
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: "
									+ e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					}
				}
				actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
			}

		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de emision de tasas de etiquetas", e);
			String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
			if (colaBean != null && colaBean.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperableBorrandoDoc(colaBean,
							ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		}
	}

	@Override
	protected void finalizarTransaccionCorrecta(ColaBean cola, String resultado) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		crearNotificacion(cola, new BigDecimal(EstadoCompra.FINALIZADO_OK.getCodigo()));
	}

	private void crearNotificacion(ColaBean cola, BigDecimal estadoNuevo) {
		if (cola != null && cola.getIdTramite() != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TIPO_TRAMITE_NOTIFICACION);
			notifDto.setIdTramite(cola.getIdTramite());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	protected void finalizarTransaccionConErrorNoRecuperableBorrandoDoc(ColaBean cola, String respuestaError)
			throws BorrarSolicitudExcepcion {
		getModeloSolicitud().borrarSolicitudExcep(cola.getIdEnvio(), respuestaError);
		errorNoRecuperable();
		if (cola.getXmlEnviar() != null) {
			String nombreFich = ServicioGeneracionEtiquetasTasas.NOMBRE_DOCUMENTO + cola.getXmlEnviar().substring(
					ServicioGeneracionEtiquetasTasas.NOMBRE_DOCUMENTO_TEMP.length(), cola.getXmlEnviar().length());
			try {
				servicioGeneracionEtiquetasTasas.borrarDocTasasPegatinas(nombreFich, TipoFicheros.Tasas.toString(),
						SubTipoFicheros.TASAS_ETIQUETAS.toString());
			} catch (Exception eE) {
				log.error("Error al borrar el documento de la solicitud: " + cola.getIdEnvio() + ", Error: "
						+ eE.toString());
			}
		}
	}

	protected void finalizarTransaccionErrorRecuperableBorrandoDoc(ColaBean cola, String respuestaError)
			throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(cola.getIdEnvio(), respuestaError);
			peticionRecuperable();
		} else {
			if (cola.getXmlEnviar() != null) {
				String nombreFich = ServicioGeneracionEtiquetasTasas.NOMBRE_DOCUMENTO + cola.getXmlEnviar().substring(
						ServicioGeneracionEtiquetasTasas.NOMBRE_DOCUMENTO_TEMP.length(), cola.getXmlEnviar().length());
				try {
					servicioGeneracionEtiquetasTasas.borrarDocTasasPegatinas(nombreFich, TipoFicheros.Tasas.toString(),
							SubTipoFicheros.TASAS_ETIQUETAS.toString());
				} catch (Exception eE) {
					log.error("Error al borrar el documento de la solicitud: " + cola.getIdEnvio() + ", Error: "
							+ eE.toString());
				}
			}
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.EMISIONTASAETIQUETAS.getNombreEnum();
	}

	public ServicioGeneracionEtiquetasTasas getServicioGeneracionEtiquetasTasas() {
		return servicioGeneracionEtiquetasTasas;
	}

	public void setServicioGeneracionEtiquetasTasas(ServicioGeneracionEtiquetasTasas servicioGeneracionEtiquetasTasas) {
		this.servicioGeneracionEtiquetasTasas = servicioGeneracionEtiquetasTasas;
	}

	public ServicioNotificacion getServicioNotificacion() {
		return servicioNotificacion;
	}

	public void setServicioNotificacion(ServicioNotificacion servicioNotificacion) {
		this.servicioNotificacion = servicioNotificacion;
	}

}