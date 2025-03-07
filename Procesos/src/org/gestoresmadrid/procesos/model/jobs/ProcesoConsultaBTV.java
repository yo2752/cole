package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.consultaBTV.view.dto.ResultadoConsultaBTV;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.webService.model.service.ServicioWebServiceConsultaBtv;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamBajas.service.ServicioWebCheckBtv;
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

public class ProcesoConsultaBTV extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoConsultaBTV.class);

	private static final String NOTIFICACION = "PROCESO CONSULTABTV FINALIZADO";

	@Autowired
	ServicioWebServiceConsultaBtv servicioWebServiceConsultaBtv;

	@Autowired
	ServicioWebCheckBtv servicioWebCheckBtv;
	
	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		ResultadoConsultaBTV resultado = null;
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
							ResultadoBajasBean resultadoCheck = servicioWebCheckBtv.procesarCheckBtv(solicitud);
							if (resultadoCheck != null && resultadoCheck.getExcepcion() != null) {
								throw new Exception(resultadoCheck.getExcepcion());
							} else if (resultadoCheck != null && resultadoCheck.getError()) {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
								solicitud.setRespuesta(resultadoCheck.getMensaje());
								finalizarTransaccionConErrorNoRecuperable(solicitud, resultadoCheck.getMensaje(), resultadoCheck.getRespuesta());
							} else {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
								solicitud.setRespuesta(resultadoCheck.getResultadoConsuta());
								finalizarTransaccionCorrectaNuevasBajas(solicitud, resultadoEjecucion, resultadoCheck);
							}
						} else {
							resultado = servicioWebServiceConsultaBtv.procesarSolicitudConsultaBTV(solicitud.getIdTramite(), solicitud.getXmlEnviar(), solicitud.getIdUsuario(), solicitud.getIdContrato());
							if (resultado != null && resultado.getExcepcion() != null) {
								throw new Exception(resultado.getExcepcion());
							} else if (resultado != null && resultado.isError()) {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
								solicitud.setRespuesta(resultado.getMensajeError());
								finalizarTransaccionConErrorNoRecuperable(solicitud, resultado.getMensajeError(), resultado.getRespuesta());
							} else {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
								solicitud.setRespuesta(resultado.getResultadoConsuta());
								finalizarTransaccionCorrecta(solicitud, resultadoEjecucion, resultado);
							}
						}
					}
					actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
				}
			} catch (Throwable e) {
				log.error("Ocurrio un error no controlado en el proceso de checkBTV, error: ", e);
				String messageException = getMessageException(e);
				try {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
				} catch (Exception e1) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			}
		} else {
			peticionCorrecta();
		}
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
			errorSolicitud(cola.getIdEnvio().longValue());
			peticionRecuperable();
		} else {
			marcarSolicitudConErrorServicio(cola, respuestaError);
		}
	}

	private void finalizarTransaccionConErrorNoRecuperable(ColaDto cola, String respuestaError, String respuesta) {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
		if("SI".equals(nuevasBajas)){
			servicioWebCheckBtv.cambiarEstadoCheckBtv(cola, new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()), respuesta, cola.getIdUsuario());
		} else {
			servicioWebServiceConsultaBtv.cambiarEstadoConsultaBtv(cola, EstadoTramiteTrafico.Finalizado_Con_Error, respuesta);
		}
		crearNotificacion(cola, new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
	}

	private void finalizarTransaccionCorrectaNuevasBajas(ColaDto cola, String resultadoEjecucion, ResultadoBajasBean resultado) {
		servicioWebCheckBtv.cambiarEstadoCheckBtv(cola, new BigDecimal(resultado.getEstadoNuevo()), resultado.getRespuesta(), cola.getIdUsuario());
		crearNotificacion(cola, new BigDecimal(resultado.getEstadoNuevo()));
		super.finalizarTransaccionCorrecta(cola, resultadoEjecucion);
	}
	
	private void finalizarTransaccionCorrecta(ColaDto cola, String resultadoEjecucion, ResultadoConsultaBTV resultadoConsultaBtv) {
		servicioWebServiceConsultaBtv.cambiarEstadoConsultaBtv(cola, resultadoConsultaBtv.getEstadoNuevo(), resultadoConsultaBtv.getRespuesta());
		crearNotificacion(cola, new BigDecimal(resultadoConsultaBtv.getEstadoNuevo().getValorEnum()));
		super.finalizarTransaccionCorrecta(cola, resultadoEjecucion);
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.CONSULTABTV.getNombreEnum();
	}
}
