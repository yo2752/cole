package org.gestoresmadrid.procesos.model.jobs;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinas;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoPegatinas extends AbstractProcesoBase {

	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoPegatinas.class);

	@Autowired
	private ServicioPegatinas servicioPegatinas;

	@Override
	protected void doExecute() throws JobExecutionException {

		ColaBean solicitud = null;

		try {
			RespuestaPegatinas respuesta = null;
			log.info(" --INICIO PROCESO DE PEGATINAS--");
			log.info(" --Buscando Solicitud--");
			String resultadoEjecucion = null;
			String excepcion = null;
			solicitud = tomarSolicitud();
			if (solicitud == null) {
				sinPeticiones();
			} else {
				if (log.isInfoEnabled()) {
					log.info("Proceso " + ProcesosEnum.PEGATINAS.getNombreEnum() + " -- Solicitud encontrada [" + solicitud.getIdEnvio() + "]");
				}
				new UtilesWSMatw().cargarAlmacenesTrafico();
				if(solicitud.getXmlEnviar() == null){
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "No se ha recuperado el numero de expediente correspondiente a la solicitud con identificador: " + solicitud.getIdEnvio();
					log.error(excepcion);
					try {
						finalizarTransaccionConErrorNoRecuperable(solicitud, excepcion);
					} catch (BorrarSolicitudExcepcion e) {
						log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				}else{
					respuesta = servicioPegatinas.generaRespuesta(solicitud);
					if (respuesta == null || (respuesta.getException() == null && !respuesta.isError())) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
						finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
					} else if (respuesta.getException() != null) {
						throw respuesta.getException();
					} else if (respuesta.isError()) {
						// Ocurrio un error en el servicio
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						String mensaje = null;
						if (respuesta.getMensajesError() != null) {
							for (String mnsj : respuesta.getMensajesError()) {
								mensaje += mnsj + ". ";
							}
						} else {
							mensaje = respuesta.getMensajeError();
						}
						solicitud.setRespuesta(mensaje);
						try {
							finalizarTransaccionConErrorNoRecuperable(solicitud,respuesta.getMensajeError());
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					} 
				}	
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de pegatinas", e);
			String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					if(messageException.length() > 500){
						messageException = messageException.substring(0, 500);
					}
					finalizarTransaccionErrorRecuperableConErrorServico(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		} catch (OegamExcepcion e) {
			log.error("Ocurrio un error no controlado en el proceso de pegatinas cargar los almacenes de claves", e);
		}

	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.PEGATINAS.getNombreEnum();
	}

}
