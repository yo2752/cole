package org.gestoresmadrid.procesos.model.jobs.sega;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceConsultaEitvSega;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoConsultaEitvBean;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoConsultaEITVSega extends AbstractProcesoBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoConsultaEITVSega.class);
	
	private static final String TIPO_TRAMITE_NOTIFICACION = "N/A";
	private static final String NOTIFICACION = "PROCESO CONSULTA TARJETA EITV FINALIZADO";
	
	@Autowired
	ServicioWebServiceConsultaEitvSega servicioWebServiceConsultaEitvSega;
	
	@Autowired
	ServicioNotificacion servicioNotificacion;
	
	@Autowired
	ServicioProcesos servicioProcesos;

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
		try{
			log.info("Inicio proceso: " + ProcesosEnum.CONSULTA_TARJETA_EITV_SEGA.toString());
			solicitud = tomarSolicitud();
			if(solicitud == null){
				sinPeticiones();
			}else{
				new UtilesWSMatw().cargarAlmacenesTrafico();
				/*if(log.isInfoEnabled()){
					log.info("Proceso " + ProcesosEnum.CONSULTA_TARJETA_EITV.getNombreEnum() + " -- Solicitud encontrada [" + solicitud.getIdEnvio() + "]");
				}*/
				if(solicitud.getXmlEnviar() == null || solicitud.getXmlEnviar().isEmpty()){
					//resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "No se ha recuperado el numero de expediente correspondiente a la consulta eitv de la solicitud con identificador: " + solicitud.getIdEnvio();
					log.error(excepcion);
					try{
						finalizarTransaccionConErrorNoRecuperable(solicitud, excepcion);
					}catch(BorrarSolicitudExcepcion e){
						log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				}else{
					ResultadoConsultaEitvBean respuesta = servicioWebServiceConsultaEitvSega.generarConsultaEitv(solicitud);
					if(respuesta == null){
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
						finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
					}else if(respuesta.getException() != null){
						throw respuesta.getException();	
					}else if(respuesta.isError()){
						// Ocurrio un error en el servicio
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						String mensaje = null;
						if(respuesta.getMensajesError() != null){
							for(String mnsj : respuesta.getMensajesError()){
								mensaje += mnsj + ". ";
							}
						}else{
							mensaje = respuesta.getMensaje();
						}
						solicitud.setRespuesta(mensaje);
						try {
							finalizarTransaccionConErrorNoRecuperable(solicitud, respuesta.getMensaje());
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					}
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
	

		}catch(Exception e){
			log.error("Ocurrio un error no controlado en el proceso de consulta tarjeta eitv", e);
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
		} catch (OegamExcepcion e1) {
			log.error("Ocurrio un error no controlado en el proceso de consulta tarjeta eitv al cargar los almacenes de claves", e1);
		}
		}
	}
		


	@Override
	protected String getProceso() {
		return ProcesosEnum.CONSULTA_TARJETA_EITV_SEGA.getNombreEnum();
	}
	
	private void crearNotificacion(ColaBean cola, BigDecimal estadoNuevo) {
		if (cola != null && cola.getIdTramite() != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoCompra.PENDIENTE_WS.getCodigo()));
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TIPO_TRAMITE_NOTIFICACION);
			notifDto.setIdTramite(cola.getIdTramite());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}
	
	@Override
	protected void finalizarTransaccionErrorRecuperableConErrorServico(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			servicioProcesos.tratarRecuperable(cola, respuestaError);
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}
	
	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServiceConsultaEitvSega.cambiarEstadoTramiteProceso(cola.getIdTramite(), new BigDecimal(EstadoTramiteTrafico.Error_Consulta_EITV.getValorEnum()),cola.getIdUsuario());
		crearNotificacion(cola, new BigDecimal(EstadoTramiteTrafico.Error_Consulta_EITV.getValorEnum()));
	}

	@Override
	protected void finalizarTransaccionCorrecta(ColaBean cola, String resultado){
		super.finalizarTransaccionCorrecta(cola, resultado);
		servicioWebServiceConsultaEitvSega.cambiarEstadoTramiteProceso(cola.getIdTramite(), new BigDecimal(EstadoTramiteTrafico.Consultado_EITV.getValorEnum()),cola.getIdUsuario());
		crearNotificacion(cola, new BigDecimal(EstadoTramiteTrafico.Consultado_EITV.getValorEnum()));
	}

}
