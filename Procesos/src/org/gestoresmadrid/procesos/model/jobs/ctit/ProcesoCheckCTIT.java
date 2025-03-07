package org.gestoresmadrid.procesos.model.jobs.ctit;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.procesos.model.service.ServicioWebServiceCheckCtit;
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

public class ProcesoCheckCTIT extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoCheckCTIT.class);

	@Autowired
	ServicioWebServiceCheckCtit servicioWebServiceCheckCtit;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
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
						// Cambiar el estado del tramite
						finalizarTransaccionConErrorNoRecuperable(solicitud, "No existen el xml de envio.");
						solicitud.setRespuesta("Error: La Solicitud " + solicitud.getIdTramite() + " no contiene xml de envio.");
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					} else {
						ResultadoCtitBean resultado = servicioWebServiceCheckCtit.tramitarPeticion(solicitud);
						if (resultado.getExcepcion() != null) {
							throw new Exception(resultado.getExcepcion());
						} else if (resultado.getError()) {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
							solicitud.setRespuesta(resultado.getMensajeError());
							if (resultado.getEsRecuperable()) {
								finalizarTransaccionErrorRecuperableCtit(solicitud, solicitud.getRespuesta());
							} else {
								finalizarTransaccionConErrorNoRecuperableCtit(solicitud, resultado.getMensajeError());
							}
						} else {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							solicitud.setRespuesta(resultado.getMensajeError());
							finalizarTransaccionCorrectaCtit(solicitud, resultadoEjecucion, resultado.getEstadoNuevoProceso());
						}
					}
					actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
				}
			} catch (Exception e) {
				log.error("Excepcion CheckCTIT", e);
				String messageException = getMessageException(e);
				try {
					finalizarTransaccionErrorRecuperableCtit(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
				} catch (Exception e1) {
					log.error("No ha sido posible borrar la solicitud", e1);
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			}

		} else {
			peticionCorrecta();
		}
	}

	private void finalizarTransaccionCorrectaCtit(ColaDto cola, String resultadoEjecucion, EstadoTramiteTrafico estadoNuevoProceso) {
		this.finalizarTransaccionCorrecta(cola, resultadoEjecucion);
		ResultadoCtitBean resultActualizacion = servicioWebServiceCheckCtit.actualizarTramiteProceso(cola.getIdTramite(), estadoNuevoProceso, cola.getIdUsuario(), cola.getRespuesta());
		if (resultActualizacion.getError()) {
			marcarSolicitudConErrorServicio(cola, resultActualizacion.getMensajeError());
		}
	}

	private void finalizarTransaccionErrorRecuperableCtit(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			ResultadoCtitBean resultActualizacion = servicioWebServiceCheckCtit.actualizarTramiteProceso(cola.getIdTramite(), EstadoTramiteTrafico.Finalizado_Con_Error, cola.getIdUsuario(), cola.getRespuesta());
			if (resultActualizacion.getError()) {
				respuestaError += " " + resultActualizacion.getMensajeError();
			}
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	private void finalizarTransaccionConErrorNoRecuperableCtit(ColaDto cola, String mensajeError) {
		this.finalizarTransaccionConErrorNoRecuperable(cola, mensajeError);
		ResultadoCtitBean resultActualizacion = servicioWebServiceCheckCtit.actualizarTramiteProceso(cola.getIdTramite(), EstadoTramiteTrafico.Finalizado_Con_Error, cola.getIdUsuario(), cola.getRespuesta());
		if (resultActualizacion.getError()) {
			marcarSolicitudConErrorServicio(cola, resultActualizacion.getMensajeError());
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.CHECKCTIT.getNombreEnum();
	}
}