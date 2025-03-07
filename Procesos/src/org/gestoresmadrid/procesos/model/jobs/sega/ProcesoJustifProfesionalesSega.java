package org.gestoresmadrid.procesos.model.jobs.sega;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceJustifProfSega;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoJustificanteProfesionalBean;
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

public class ProcesoJustifProfesionalesSega extends AbstractProcesoBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoJustifProfesionalesSega.class);
	
	private static final String NOTIFICACION = "PROCESO JUSTIFICANTE PROFESIONAL FINALIZADO";
	
	@Autowired
	ServicioWebServiceJustifProfSega servicioWebServiceJustifProfesional;
	
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
				log.info("Inicio proceso: " + ProcesosEnum.JUSTIFICANTES_SEGA.toString());
				solicitud = tomarSolicitud();
				if(solicitud == null){
					sinPeticiones();
				} else {
					new UtilesWSMatw().cargarAlmacenesTrafico();
					if(solicitud.getXmlEnviar() == null || solicitud.getXmlEnviar().isEmpty()){
						try {
							finalizarTransaccionConErrorNoRecuperable(solicitud, "No existen el xml de envio.");
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					} else {
						ResultadoJustificanteProfesionalBean resultado = servicioWebServiceJustifProfesional.procesarSolicitudJustificanteProf(solicitud);
						if(resultado != null && resultado.getExcepcion() != null){
							throw new Exception(resultado.getExcepcion());
						}else if(resultado != null && resultado.isError()){
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
							solicitud.setRespuesta(resultado.getMensajeError());
							try {
								finalizarTransaccionConErrorNoRecuperable(solicitud, resultado);
							} catch (BorrarSolicitudExcepcion e) {
								log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
								resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
								excepcion = e.toString();
							}
						}else{
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							solicitud.setRespuesta(resultado.getRespuesta());
							finalizarTransaccionCorrectaJustifProf(solicitud, resultadoEjecucion, resultado);
						}
					}
					//solicitud.setEstado(new BigDecimal(EstadoJustificante.Ok.getValorEnum()));
					actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
				}
			} catch (Throwable e) {
				log.error("Ha sucedido un error en el proceso de justificantes profesional, error: ",e);
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
			}
		}
	}
		

	@Override
	protected String getProceso() {
		return ProcesosEnum.JUSTIFICANTES_SEGA.getNombreEnum();
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
	
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, ResultadoJustificanteProfesionalBean resultado) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, resultado.getMensajeError());
		if(resultado.getNumExpediente().contains(ProcesosEnum.JUSTIFICANTES_SEGA.getNombreEnum())){
			servicioWebServiceJustifProfesional.cambiarEstadoJustificanteProfSolicitudSega(cola.getIdTramite(),EstadoJustificante.Finalizado_Con_Error, cola.getIdUsuario());
			servicioWebServiceJustifProfesional.tratarDevolverCreditoSega(cola.getIdTramite(),cola.getIdUsuario());
		}else{
			servicioWebServiceJustifProfesional.cambiarEstadoJustificanteProfSolicitud(cola.getIdTramite(),EstadoJustificante.Finalizado_Con_Error, cola.getIdUsuario());
			servicioWebServiceJustifProfesional.tratarDevolverCredito(cola.getIdTramite(),cola.getIdUsuario());
		}
		crearNotificacion(cola, new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()), resultado.getNumExpediente());
	}
	
	protected void finalizarTransaccionCorrectaJustifProf(ColaBean cola, String resultadoEjecucion, ResultadoJustificanteProfesionalBean resultado) {
		super.finalizarTransaccionCorrecta(cola, resultadoEjecucion);
		if(cola.getProceso().contains(ProcesosEnum.JUSTIFICANTES_SEGA.getNombreEnum())){
			servicioWebServiceJustifProfesional.cambiarEstadoJustificanteProfSolicitudSega(cola.getIdTramite(),EstadoJustificante.Iniciado,EstadoJustificante.Ok, cola.getIdUsuario());
		}else{
			servicioWebServiceJustifProfesional.cambiarEstadoJustificanteProfSolicitud(cola.getIdTramite(),EstadoJustificante.Ok, cola.getIdUsuario());
		}
		
		crearNotificacion(cola, new BigDecimal(EstadoJustificante.Ok.getValorEnum()), resultado.getNumExpediente());
	}
	
	private void crearNotificacion(ColaBean cola, BigDecimal estadoNuevo, String numExpediente) {
		if (cola != null && numExpediente != null && !numExpediente.isEmpty()) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()));
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TipoTramiteTrafico.JustificantesProfesionales.getValorEnum());
			notifDto.setIdTramite(new BigDecimal(numExpediente));
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}
	
}
