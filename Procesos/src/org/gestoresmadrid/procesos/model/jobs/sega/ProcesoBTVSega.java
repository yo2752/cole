package org.gestoresmadrid.procesos.model.jobs.sega;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceBtv;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoBTVBean;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import procesos.enumerados.RetornoProceso;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoBTVSega extends AbstractProcesoBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoBTVSega.class);

	private static final String NOTIFICACION = "PROCESO BTV FINALIZADO";
	
	@Autowired
	ServicioWebServiceBtv servicioWebServiceBtv;
	
	@Autowired
	ServicioNotificacion servicioNotificacion;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean solicitud = null;
		Boolean esLaborable = Boolean.TRUE;
		String resultadoEjecucion = null;
		String  excepcion = null;
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}
		if (esLaborable) {
		try {
			log.info("Inicio proceso: " + ProcesosEnum.BTV_SEGA.toString());
			solicitud = tomarSolicitud();
			if(solicitud == null){
				sinPeticiones();
			}else{
				new UtilesWSMatw().cargarAlmacenesTrafico();
				if(solicitud.getXmlEnviar() == null || solicitud.getXmlEnviar().isEmpty()){
					try {
						finalizarTransaccionConErrorNoRecuperable(solicitud, "No existen el xml de envio.", "No existen el xml de envio.");
					} catch (BorrarSolicitudExcepcion e) {
						log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				}else{
					ResultadoBTVBean resultado = servicioWebServiceBtv.procesarSolicitudBTV(solicitud.getIdTramite(), solicitud.getXmlEnviar(), solicitud.getIdUsuario(), solicitud.getIdContrato());
					if(resultado != null && resultado.getExcepcion() != null){
						throw new Exception(resultado.getExcepcion());
					}else if(resultado != null && resultado.isError()){
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						solicitud.setRespuesta(resultado.getMensajeError());
						try {
							finalizarTransaccionConErrorNoRecuperable(solicitud, resultado.getMensajeError(),resultado.getRespuesta());
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					}else{
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta(resultado.getRespuesta());
						finalizarTransaccionCorrectaBTV(solicitud, resultadoEjecucion, resultado);
					}
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso BTV, error: ", e);
			String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
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
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (BorrarSolicitudExcepcion e2) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e2.toString());
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		}
		log.info("Fin proceso: " + ProcesosEnum.BTV_SEGA.toString());
	}
	}	
	

	@Override
	protected String getProceso() {
		return ProcesosEnum.BTV_SEGA.getNombreEnum();
	}
	
	private void crearNotificacion(ColaBean cola, BigDecimal estadoNuevo) {
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
	
	protected void finalizarTransaccionErrorRecuperable(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(cola.getIdEnvio(), respuestaError);
			peticionRecuperable();
		} else {
			finalizarTransaccionErrorServicio(cola, respuestaError);
		}
	}

	private void finalizarTransaccionErrorServicio(ColaBean cola, String respuestaError) {
		getModeloSolicitud().errorServicio(cola.getIdEnvio(), respuestaError);
		getModeloSolicitud().notificarErrorServicio(cola,respuestaError);
		getContext().getMergedJobDataMap().put(RETORNO, RetornoProceso.CORRECTO);
	}
	
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError, String respuesta) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServiceBtv.cambiarEstado(cola,EstadoTramiteTrafico.Finalizado_Con_Error,respuesta);
		servicioWebServiceBtv.tratarDevolverCredito(cola.getIdTramite(),cola.getIdUsuario(),cola.getIdContrato());
		crearNotificacion(cola, new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
	}
	
	protected void finalizarTransaccionCorrectaBTV(ColaBean cola, String resultadoEjecucion, ResultadoBTVBean resultado) {
		super.finalizarTransaccionCorrecta(cola, resultadoEjecucion);
		EstadoTramiteTrafico nuevoEstado = null;
		if(resultado.isNoTramitable()){
			nuevoEstado = EstadoTramiteTrafico.No_Tramitable;
		}else{
			nuevoEstado = EstadoTramiteTrafico.Finalizado_Telematicamente;
		}
		servicioWebServiceBtv.cambiarEstado(cola,nuevoEstado,resultado.getRespuesta());
		crearNotificacion(cola, new BigDecimal(nuevoEstado.getValorEnum()));
	}

}
