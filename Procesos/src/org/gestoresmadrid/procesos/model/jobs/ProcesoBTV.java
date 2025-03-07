package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.btv.view.dto.ResultadoBTV;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.webService.model.service.ServicioWebServiceTramitarBtv;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamBajas.service.ServicioWebBtv;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoBTV extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoBTV.class);

	private static final String NOTIFICACION = "PROCESO BTV FINALIZADO";

	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Autowired
	ServicioWebServiceTramitarBtv servicioWebServiceTramitarBtv;
	
	@Autowired
	ServicioWebBtv servicioWebBtv;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		log.info("Inicio proceso: " + ProcesosEnum.BTV.getValorEnum());
		ResultadoBTV resultado = null;
		ColaDto solicitud = null;
		String resultadoEjecucion = null;
		String excepcion = null;
		Boolean esLaborable = true;
		Boolean forzarEjecucion = false;

		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}

		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.FORZAR_EJECUCION_BAJA))) {
			forzarEjecucion = true;
		}

		if (esLaborable || forzarEjecucion) {
			try {
				log.info("Proceso " + getProceso() + " -- Buscando Solicitud");
				solicitud = tomarSolicitud();
				if (solicitud == null) {
					sinPeticiones();
				} else {
					if (solicitud.getXmlEnviar() == null || solicitud.getXmlEnviar().isEmpty()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						solicitud.setRespuesta("Error: La Solicitud " + solicitud.getIdTramite() + " no contiene xml de envio.");
						finalizarTransaccionConErrorNoRecuperable(solicitud, "No existen el xml de envio.", "No existen el xml de envio.");
					} else {
						new UtilesWSMatw().cargarAlmacenesTrafico();
						String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
						if("SI".equals(nuevasBajas)){
							ResultadoBajasBean resultBajas = servicioWebBtv.procesarSolicitudBtv(solicitud);
							if (resultBajas != null && resultBajas.getExcepcion() != null) {
								throw new Exception(resultBajas.getExcepcion());
							} else if (resultBajas != null && resultBajas.getError()) {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
								solicitud.setRespuesta(resultBajas.getMensaje());
								finalizarTransaccionConErrorNoRecuperable(solicitud, resultBajas.getMensaje(), resultBajas.getRespuesta());
							} else {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
								solicitud.setRespuesta(resultBajas.getRespuesta());
								finalizarTransaccionCorrectaNuevasBajas(solicitud, resultadoEjecucion, resultBajas);
							}
						} else {
							resultado = servicioWebServiceTramitarBtv.procesarSolicitudBTV(solicitud.getIdTramite(), solicitud.getXmlEnviar(), solicitud.getIdUsuario(), solicitud.getIdContrato());
							if (resultado != null && resultado.getExcepcion() != null) {
								throw new Exception(resultado.getExcepcion());
							} else if (resultado != null && resultado.isError()) {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
								solicitud.setRespuesta(resultado.getMensajeError());
								finalizarTransaccionConErrorNoRecuperable(solicitud, resultado.getMensajeError(), resultado.getRespuesta());
							} else {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
								solicitud.setRespuesta(resultado.getRespuesta());
								finalizarTransaccionCorrecta(solicitud, resultadoEjecucion, resultado);
							}
						}
					}
					actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
				}
			} catch (Throwable e) {
				log.error("Ocurrio un error no controlado en el proceso de checkBTV, error: ", e);
				String messageException = getMessageException(e);
				if (solicitud != null && solicitud.getProceso() != null) {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
					actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
				} else {
					log.error("Se ha producido un error y no se ha podido recibir la solicitud");
				}
			}
		} else {
			peticionCorrecta();
		}
		log.info("Fin proceso: " + ProcesosEnum.BTV.toString());
	}

	private void finalizarTransaccionCorrectaNuevasBajas(ColaDto solicitud, String resultadoEjecucion, ResultadoBajasBean resultBajas) {
		BigDecimal nuevoEstado = null;
		if (resultBajas.getIsNoTramitable()) {
			nuevoEstado = new BigDecimal(EstadoTramiteTrafico.No_Tramitable.getValorEnum());
		} else {
			nuevoEstado = new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum());
		}
		servicioWebBtv.finalizarSolicitudBtv(solicitud.getIdTramite(), nuevoEstado, resultBajas.getRespuesta(), solicitud.getIdUsuario());
		crearNotificacion(solicitud, nuevoEstado);
		
	}

	private void crearNotificacion(ColaDto cola, BigDecimal estadoNuevo) {
		if (cola != null && cola.getIdTramite() != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TipoTramiteTrafico.Baja.getValorEnum());
			notifDto.setIdTramite(cola.getIdTramite());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	@Override
	protected void finalizarTransaccionErrorRecuperable(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			marcarSolicitudConErrorServicio(cola, respuestaError);
		}
	}

	private void finalizarTransaccionConErrorNoRecuperable(ColaDto cola, String respuestaError, String respuesta) {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
		if("SI".equals(nuevasBajas)){
			servicioWebBtv.finalizarSolicitudBtv(cola.getIdTramite(), new BigDecimal( EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())
					, respuesta, cola.getIdUsuario());
		} else {
			servicioWebServiceTramitarBtv.cambiarEstado(cola, EstadoTramiteTrafico.Finalizado_Con_Error, respuesta);
			servicioWebServiceTramitarBtv.tratarDevolverCredito(cola.getIdTramite(), cola.getIdUsuario(), cola.getIdContrato());
		}
		crearNotificacion(cola, new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
	}

	private void finalizarTransaccionCorrecta(ColaDto cola, String resultadoEjecucion, ResultadoBTV resultado) {
		super.finalizarTransaccionCorrecta(cola, resultadoEjecucion);
		EstadoTramiteTrafico nuevoEstado = null;
		if (resultado.isNoTramitable()) {
			nuevoEstado = EstadoTramiteTrafico.No_Tramitable;
		} else {
			nuevoEstado = EstadoTramiteTrafico.Finalizado_Telematicamente;
		}
		servicioWebServiceTramitarBtv.cambiarEstado(cola, nuevoEstado, resultado.getRespuesta());
		crearNotificacion(cola, new BigDecimal(nuevoEstado.getValorEnum()));
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.BTV.getNombreEnum();
	}
}
