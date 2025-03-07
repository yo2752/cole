package org.gestoresmadrid.procesos.model.jobs.ctit;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.procesos.model.service.ServicioWebServiceTradeCtit;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoTradeCTIT extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoTradeCTIT.class);

	@Autowired
	ServicioWebServiceTradeCtit servicioWebServiceTradeCtit;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto solicitud = new ColaDto();
		boolean esLaborable = true;
		boolean forzarEjecucion = false;
		String resultadoEjecucion = null;
		String excepcion = null;

		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}

		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.FORZAR_EJECUCION_TRANSMISION))) {
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
						finalizarTransaccionConErrorNoRecuperable(solicitud, "No existen el xml de envio.");
						solicitud.setRespuesta("Error: La Solicitud " + solicitud.getIdTramite() + " no contiene xml de envio.");
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					} else {
						ResultadoCtitBean resultado = servicioWebServiceTradeCtit.tramitarPeticion(solicitud);
						if (resultado.getExcepcion() != null) {
							throw new Exception(resultado.getExcepcion());
						} else if (resultado.getError()) {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
							solicitud.setRespuesta(resultado.getMensajeError());
							if (resultado.getEsRecuperable()) {
								finalizarTransaccionErrorRecuperable(solicitud, solicitud.getRespuesta(), resultado.getEstadoNuevoProceso());
							} else {
								finalizarTransaccionConErrorNoRecuperable(solicitud, resultado.getMensajeError(), resultado.getEstadoNuevoProceso());
							}
						} else {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							solicitud.setRespuesta(resultado.getMensajeError());
							finalizarTransaccionCorrecta(solicitud, resultadoEjecucion, resultado.getEstadoNuevoProceso());
						}
					}
					actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
				}
			} catch (Exception e) {
				log.error("Excepcion TradeCTIT", e);
				String messageException = getMessageException(e);
				try {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
				} catch (Exception e1) {
					log.error("No ha sido posible borrar la solicitud", e1);
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			}
		} else {
			peticionCorrecta();
		}
	}

	private void finalizarTransaccionCorrecta(ColaDto cola, String resultadoEjecucion, EstadoTramiteTrafico estadoNuevoProceso) {
		this.finalizarTransaccionCorrecta(cola, resultadoEjecucion);
		ResultadoCtitBean resultActualizacion = servicioWebServiceTradeCtit.descontarCreditos(cola.getIdTramite(), cola.getIdUsuario(), cola.getIdContrato());
		if (!resultActualizacion.getError()) {
			resultActualizacion = servicioWebServiceTradeCtit.actualizarTramiteProceso(cola.getIdTramite(), estadoNuevoProceso, cola.getIdUsuario(), cola.getRespuesta());
		} else {
			finalizarTransaccionConErrorNoRecuperable(cola, resultActualizacion.getMensajeError(), EstadoTramiteTrafico.Finalizado_Con_Error);
		}
		if (resultActualizacion.getError()) {
			marcarSolicitudConErrorServicio(cola, resultActualizacion.getMensajeError());
		}
	}

	private void finalizarTransaccionConErrorNoRecuperable(ColaDto cola, String mensajeError, EstadoTramiteTrafico estadoNuevoProceso) {
		this.finalizarTransaccionConErrorNoRecuperable(cola, mensajeError);
		ResultadoCtitBean resultActualizacion = servicioWebServiceTradeCtit.actualizarTramiteProceso(cola.getIdTramite(), estadoNuevoProceso, cola.getIdUsuario(), cola.getRespuesta());
		if (resultActualizacion.getError()) {
			marcarSolicitudConErrorServicio(cola, resultActualizacion.getMensajeError());
		}
	}

	private void finalizarTransaccionErrorRecuperable(ColaDto cola, String respuestaError, EstadoTramiteTrafico estadoNuevoProceso) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			marcarSolicitudConErrorServicio(cola, respuestaError);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.TRADECTIT.getNombreEnum();
	}
}