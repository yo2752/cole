package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioWebServiceEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoWSEEFF;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import colas.constantes.ConstantesProcesos;

public class ProcesoEEFFNuevo extends AbstractProcesoBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoEEFFNuevo.class);
	
	private static final String NOTIFICACION_LIBERACION = "PROCESO LIBERACION EEFF FINALIZADO";
	private static final String NOTIFICACION_CONSULTA = "PROCESO CONSULTA EEFF FINALIZADO";
	private static final String ORIGEN_SOLICITUD_LIBERACION = "EEFFLIBERACION";
	private static final String ORIGEN_SOLICITUD_CONSULTA = "EEFFCONSULTA";
	
	@Autowired
	ServicioWebServiceEEFF servicioWebServiceEEFF;
	
	@Autowired
	private ServicioNotificacion servicioNotificacion;
	
	@Override
	protected void doExecute() throws JobExecutionException {
		// Loggeo el inicio del proceso
		ColaBean solicitud = null;
		log.info(ConstantesEEFF.LOG_PROCESO_EEFF_GENERATOR + " --INICIO--");
		log.info("Proceso " + ProcesosEnum.PROCESO_EEFF.getNombreEnum() + " -- Buscando Solicitud");
		String resultadoEjecucion = null;
		String  excepcion = null;
		String origen = "";
		try{	
			solicitud = tomarSolicitud();
			if(solicitud == null){
				sinPeticiones();
			} else {
				if(solicitud.getXmlEnviar() != null && !solicitud.getXmlEnviar().isEmpty() && solicitud.getXmlEnviar().contains("EEFFLIBERACION")){
					origen = ORIGEN_SOLICITUD_LIBERACION;
				} else if(solicitud.getXmlEnviar() != null && !solicitud.getXmlEnviar().isEmpty() && solicitud.getXmlEnviar().contains("EEFFCONSULTA")){
					origen = ORIGEN_SOLICITUD_CONSULTA;
				}
				new UtilesWSMatw().cargarAlmacenesTrafico();
				if(solicitud.getXmlEnviar() == null){
					try {
						finalizarTransaccionConErrorNoRecuperable(solicitud, "No existen el xml de envio.",origen);
						solicitud.setRespuesta("Error: La Solicitud " + solicitud.getIdTramite() + " no contiene xml de envio.");
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					} catch (BorrarSolicitudExcepcion e) {
						log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				}else if(solicitud.getXmlEnviar().contains(ORIGEN_SOLICITUD_LIBERACION)){//Liberar EEFF
					ResultadoWSEEFF resultado = servicioWebServiceEEFF.liberar(solicitud);
					if(resultado != null && resultado.getExcepcion() != null){
						throw new Exception(resultado.getExcepcion());
					}else if(resultado != null && resultado.isError()){
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						solicitud.setRespuesta(resultado.getMensajeError());
						try {
							finalizarTransaccionConErrorNoRecuperable(solicitud,resultado.getRespuesta(), ORIGEN_SOLICITUD_LIBERACION);
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					}else{
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta(resultado.getRespuesta());
						finalizarTransaccionCorrecta(solicitud, resultadoEjecucion,ORIGEN_SOLICITUD_LIBERACION);
					}
				}else if(solicitud.getXmlEnviar().contains(ORIGEN_SOLICITUD_CONSULTA)){//Consulta EEFF
					ResultadoWSEEFF resultado = servicioWebServiceEEFF.consultar(solicitud);
					if(resultado != null && resultado.getExcepcion() != null){
						throw new Exception(resultado.getExcepcion());
					}else if(resultado != null && resultado.isError()){
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						solicitud.setRespuesta(resultado.getMensajeError());
						try {
							finalizarTransaccionConErrorNoRecuperable(solicitud,resultado.getRespuesta(), ORIGEN_SOLICITUD_CONSULTA);
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					}else{
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta(resultado.getRespuesta());
						finalizarTransaccionCorrecta(solicitud, resultadoEjecucion,ORIGEN_SOLICITUD_LIBERACION);
					}
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de EEFF, error: ", e);
			String messageException = origen + "_" +  e.getMessage() != null ? e.getMessage() : e.toString();
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException, origen);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		} catch (OegamExcepcion e1) {
			log.error("Ha sucedido un error a la hora de cargar los almacenes de trafico, error: ", e1);
			String messageException =  origen + "_" + e1.getMessage() != null ? e1.getMessage() : e1.toString();
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException,origen);
				} catch (BorrarSolicitudExcepcion e2) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e2.toString());
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		}
	}
	
	@Override
	protected String getProceso() {
		return ProcesosEnum.PROCESO_EEFF.getNombreEnum();
	}
	
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean solicitud, String respuestaError, String origen) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(solicitud, respuestaError);
		if(ORIGEN_SOLICITUD_LIBERACION.equals(origen)){
			servicioWebServiceEEFF.cambiarEstadoLiberacion(solicitud.getIdTramite(), solicitud.getIdUsuario(), EstadoTramiteTrafico.Finalizado_Con_Error, 
				EstadoTramiteTrafico.Pendiente_Liberar,respuestaError);
		} else if(ORIGEN_SOLICITUD_CONSULTA.equals(origen)){
			servicioWebServiceEEFF.cambiarEstadoConsulta(solicitud.getIdTramite(), solicitud.getIdUsuario(), EstadoConsultaEEFF.Finalizado_Con_Error, 
					EstadoConsultaEEFF.Pdte_Respuesta, solicitud.getRespuesta());

		}
	}
	
	protected void finalizarTransaccionErrorRecuperable(ColaBean solicitud, String respuesta, String origen) throws BorrarSolicitudExcepcion{
		BigDecimal numIntentos = getNumeroIntentos(solicitud.getProceso());
		if(solicitud.getNumeroIntento().intValue()<numIntentos.intValue()){
			getModeloSolicitud().errorSolicitud(solicitud.getIdEnvio(), respuesta);
			peticionRecuperable();
		}else{
			finalizarTransaccionConErrorNoRecuperable(solicitud, respuesta,origen);
		}
	}

	protected void finalizarTransaccionCorrecta(ColaBean solicitud, String resultado, String origen){
		super.finalizarTransaccionCorrecta(solicitud, resultado);
		if(ORIGEN_SOLICITUD_LIBERACION.equals(origen)){
			servicioWebServiceEEFF.cambiarEstadoLiberacion(solicitud.getIdTramite(), solicitud.getIdUsuario(), EstadoTramiteTrafico.LiberadoEEFF, 
				EstadoTramiteTrafico.Pendiente_Liberar, solicitud.getRespuesta());
			crearNotificacion(solicitud, new BigDecimal(EstadoTramiteTrafico.LiberadoEEFF.getValorEnum()),new BigDecimal(EstadoTramiteTrafico.Pendiente_Liberar.getValorEnum()), NOTIFICACION_LIBERACION);
		} else if(ORIGEN_SOLICITUD_CONSULTA.equals(origen)){
			servicioWebServiceEEFF.cambiarEstadoConsulta(solicitud.getIdTramite(), solicitud.getIdUsuario(), EstadoConsultaEEFF.Finalizado, 
					EstadoConsultaEEFF.Pdte_Respuesta, solicitud.getRespuesta());
				crearNotificacion(solicitud, new BigDecimal(EstadoConsultaEEFF.Finalizado.getValorEnum()),new BigDecimal(EstadoConsultaEEFF.Pdte_Respuesta.getValorEnum()), NOTIFICACION_CONSULTA);
		}
	}
	
	private void crearNotificacion(ColaBean cola, BigDecimal estadoNuevo, BigDecimal estadoAnt, String notificacion) {
		if (cola != null && cola.getIdTramite() != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(estadoAnt);
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(notificacion);
			notifDto.setTipoTramite(TipoTramiteTrafico.consultaEEFF.getValorEnum());
			notifDto.setIdTramite(cola.getIdTramite());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

}
