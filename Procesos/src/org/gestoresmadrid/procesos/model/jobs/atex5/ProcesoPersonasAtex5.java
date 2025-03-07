package org.gestoresmadrid.procesos.model.jobs.atex5;

import java.math.BigDecimal;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.atex5.model.enumerados.TipoTramiteAtex5;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioWebServicePersonasAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoWSAtex5Bean;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import colas.constantes.ConstantesProcesos;

public class ProcesoPersonasAtex5 extends AbstractProcesoBase{

	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoPersonasAtex5.class);

	private static final String NOTIFICACION = "PROCESO CONSULTA PERSONAS ATEX5 FINALIZADO";
	
	@Autowired
	ServicioWebServicePersonasAtex5 servicioWebServicePersonasAtex5;
	
	@Autowired
	ServicioNotificacion servicioNotificacion;
	
	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean colaBean = null;
		try{
			colaBean = tomarSolicitud();
			if(colaBean == null){
				sinPeticiones();
			}else{
				new UtilesWSMatw().cargarAlmacenesTrafico();
				String resultadoEjecucion = null;
				String  excepcion = null;
				if(colaBean.getXmlEnviar() == null || colaBean.getXmlEnviar().isEmpty()){
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "No se ha recuperado el tipo de consulta de persona atex5 correspondiente a la solicitud con identificador: " + colaBean.getIdEnvio();
					log.error(excepcion);
					try {
						finalizarTransaccionConErrorNoRecuperable(colaBean, excepcion);
					} catch (BorrarSolicitudExcepcion e) {
						log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				}else{
					ResultadoWSAtex5Bean resultado = null;
					if(ConstantesProcesos.TIPO_SOLICITUD_PROCESO_CONSULTA_PERS_ATEX5.equals(colaBean.getXmlEnviar())){
						resultado = servicioWebServicePersonasAtex5.generarConsultaPersonaAtex5(colaBean);
					}
					if(resultado.getExcepcion() != null){
						throw resultado.getExcepcion();
					}else if(resultado.getError()){
						// Ocurrio un error en el servicio
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						String mensaje = null;
						if(resultado.getListaMensajes() != null){
							for(String mnsj : resultado.getListaMensajes()){
								mensaje += mnsj + ". ";
							}
						}else{
							mensaje = resultado.getMensajeError();
						}
						colaBean.setRespuesta(mensaje);
						try {
							finalizarTransaccionConErrorNoRecuperable(colaBean, resultado.getMensajeError());
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					}else{
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaBean.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
						finalizarTransaccionCorrecta(colaBean, resultadoEjecucion);
					}
				}
				actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
			}
		}catch(Exception e){
			log.error("Ocurrio un error no controlado en el proceso de consulta dev", e);
			String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
			if (colaBean != null && colaBean.getProceso() != null) {
				try {
					if(messageException.length() > 500){
						messageException = messageException.substring(0, 500);
					}
					finalizarTransaccionErrorRecuperableConErrorServico(colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		} catch (OegamExcepcion e1) {
			log.error("Ocurrio un error no controlado en el proceso de consulta personas atex5 al cargar los almacenes de claves", e1);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.CONSULTA_PERSONA_ATEX5.getNombreEnum();
	}
	
	private void crearNotificacion(ColaBean cola, BigDecimal estadoNuevo) {
		if (cola != null && cola.getIdTramite() != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum()));
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TipoTramiteAtex5.convertirTexto(cola.getTipoTramite()));
			notifDto.setIdTramite(cola.getIdTramite());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServicePersonasAtex5.cambiarEstadoConsulta(cola.getIdTramite(),cola.getIdUsuario(),EstadoAtex5.Finalizado_Con_Error);
		servicioWebServicePersonasAtex5.devolverCreditos(cola.getIdTramite(),cola.getIdContrato(),cola.getIdUsuario());
		crearNotificacion(cola, new BigDecimal(EstadoAtex5.Finalizado_Con_Error.getValorEnum()));
	}
	
	@Override
	protected void finalizarTransaccionCorrecta(ColaBean cola, String resultado) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		servicioWebServicePersonasAtex5.cambiarEstadoConsulta(cola.getIdTramite(),cola.getIdUsuario(), EstadoAtex5.Finalizado_PDF);
		crearNotificacion(cola, new BigDecimal(EstadoAtex5.Finalizado_PDF.getValorEnum()));
	}
	
}
