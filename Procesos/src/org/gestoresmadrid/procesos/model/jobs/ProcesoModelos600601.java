package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioWebServiceModelo600601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.ResultadoModelos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import colas.constantes.ConstantesProcesos;

public class ProcesoModelos600601 extends AbstractProcesoBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoModelos600601.class);
	
	@Autowired
	private ServicioWebServiceModelo600601 servicioWebServiceModelo600601;
	
	@Override
	protected void doExecute() throws JobExecutionException {
		log.info("Inicio proceso: " + getProceso());
		ResultadoModelos resultado = null;
		ColaBean solicitud = null;
		String resultadoEjecucion = null;
		String  excepcion = null;
	
		
		try {
			solicitud = tomarSolicitud();
			if(solicitud == null){
				sinPeticiones();
			}else{
				new UtilesWSMatw().cargarAlmacenesTrafico();
				if(solicitud.getXmlEnviar() == null || solicitud.getXmlEnviar().isEmpty()){
					try {
						finalizarTransaccionConErrorNoRecuperable(solicitud, "No existen el xml de envio.");
					} catch (BorrarSolicitudExcepcion e) {
						log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				}else{
					resultado = servicioWebServiceModelo600601.procesarSolicitud(solicitud);
					if(resultado != null && resultado.getExcepcion() != null){
						throw new Exception(resultado.getExcepcion());
					}else if(resultado != null && resultado.getErrorSubsanable()){
						
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						excepcion = resultado.getRespuesta();
						solicitud.setRespuesta(resultado.getRespuesta());
						finalizarTransaccionErrorRecuperable(solicitud, resultado.getRespuesta(),true);
					}else if(resultado != null && resultado.isError()){
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						solicitud.setRespuesta(resultado.getMensajeError());
						try {
							finalizarTransaccionConErrorNoRecuperable(solicitud, resultado.getMensajeError());
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					}else{
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
					}
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de presentacion de modelos 600/601, error: ", e);
			String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException,false);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		} catch (OegamExcepcion e1) {
			log.error("Ha sucedido un error a la hora de cargar los almacenes de trafico, error: ", e1);
			String messageException = e1.getMessage() != null ? e1.getMessage() : e1.toString();
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException,false);
				} catch (BorrarSolicitudExcepcion e2) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e2.toString());
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		}
		log.info("Fin proceso: " + getProceso());
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.MODELO_600_601.getNombreEnum();
	}

	protected void finalizarTransaccionErrorRecuperable(ColaBean cola, String respuestaError,Boolean tieneResultado) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(cola.getIdEnvio(), respuestaError);
			peticionRecuperable();
		} else {
			if(tieneResultado){
				String[] codigoError = respuestaError.split(",");
				servicioWebServiceModelo600601.guardarResultadoSubSanableFinalizado(cola.getIdTramite(),codigoError[0]);
			}
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}
	
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServiceModelo600601.cambiarEstadoModelo(cola.getIdTramite(),new BigDecimal(EstadoModelos.FinalizadoKO.getValorEnum()),cola.getIdUsuario());
		servicioWebServiceModelo600601.tratarDevolverCredito(cola.getIdTramite(),cola.getIdUsuario());
		servicioWebServiceModelo600601.crearNotificacion(cola.getIdTramite(),cola.getIdUsuario(),new BigDecimal(EstadoModelos.FinalizadoKO.getValorEnum()));
	}
	
	protected void finalizarTransaccionCorrecta(ColaBean cola, String resultado) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		servicioWebServiceModelo600601.cambiarEstadoModelo(cola.getIdTramite(),new BigDecimal(EstadoModelos.FinalizadoOK.getValorEnum()),cola.getIdUsuario());
		servicioWebServiceModelo600601.crearNotificacion(cola.getIdTramite(), cola.getIdUsuario(),  new BigDecimal(EstadoModelos.FinalizadoOK.getValorEnum()));
	}

}
