package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioWebServiceEEFF;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;

public class ProcesoEEFF extends AbstractProcesoBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoEEFF.class);

	@Autowired
	private ServicioEEFF servicioEEFF;

	@Autowired
	private ServicioNotificacion servicioNotificacion;

	@Autowired
	ServicioWebServiceEEFF servicioWebServiceEEFF;

	@Override
	protected void doExecute() throws JobExecutionException {
		// Loggeo el inicio del proceso
		ColaBean solicitud = null;
		try {
			//Cargamos los almacenes de certificados que están en la máquina, en la carpeta datos/oegam/keystore.
			new UtilesWSMatw().cargarAlmacenesTrafico();
			log.info(ConstantesEEFF.LOG_PROCESO_EEFF_GENERATOR + " --INICIO--");
			log.info("Proceso " + ConstantesProcesos.PROCESO_EEFF + " -- Buscando Solicitud");
			solicitud = tomarSolicitud();
			ResultBean resultBean = null;
			if(solicitud == null){
				sinPeticiones();
			} else {
				if (solicitud.getXmlEnviar() == null) {
					log.info("La Solicitud " + solicitud.getIdTramite() + " no contiene xml de envio.");
					servicioEEFF.actualizarSolicitudSinXml(solicitud.getIdTramite());
					finalizarTransaccionConErrorNoRecuperableSinXML(solicitud, "La Solicitud " + solicitud.getIdTramite() + " no contiene xml de envio.");
					actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, "Error: La Solicitud " + solicitud.getIdTramite() + " no contiene xml de envio.");
				} else if (solicitud.getXmlEnviar().contains("EEFFLIBERACION")) {// Liberar EEFF
					try {
						resultBean = servicioEEFF.liberarProceso(solicitud);
						log.info("Solicitud obtenida con idTramite " + solicitud.getIdTramite());
						if (resultBean == null) {
							log.info("fin del liberar");
							finalizarTransaccionCorrectaCambiandoEstado(solicitud, EstadoTramiteTrafico.LiberadoEEFF, "Transaccion Correcta");
							actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, "OK Liberación");
						} else if (!resultBean.getError()) {
							log.info("fin del liberar con errores: " + resultBean.getListaMensajes().get(0));
							finalizarTransaccionConErrorNoRecuperable(solicitud,generarMensaje(resultBean));
							actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, "Error Liberación: " + resultBean.getListaMensajes().get(0));
						} else {
							log.info("fin del liberar, se vuelve a encolar la peticion, error: " + resultBean.getListaMensajes().get(0));
							finalizarTransaccionErrorRecuperable(solicitud, resultBean);
							actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, resultBean.getListaMensajes().get(0));
						}
					} catch (Exception e) {
						log.info("Error: " + e);
						finalizarTransaccionConErrorNoRecuperable(solicitud,"Error: " + e.toString());
						actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, "Excepción Liberación: " + e.toString());
					}
				} else if (solicitud.getXmlEnviar().contains("EEFFCONSULTA")) {// Liberar EEFF
					try {
						resultBean = servicioEEFF.procesoConsultar(solicitud);
						if (resultBean == null) {
							log.info("Fin del consultar");
							finalizarTransaccionCorrectaConsulta(solicitud, "Transaccion correcta");
							actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, "OK Consulta");
						} else if (!resultBean.getError()) {
							log.info("Fin del consultar con errores: "+ resultBean.getListaMensajes().get(0));
							finalizarTransaccionCorrectaConsulta(solicitud, "Transaccion correcta");
							actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, "Error Consulta: " + resultBean.getListaMensajes().get(0));
						} else {
							log.info("Respuesta con errores: "+ resultBean.getListaMensajes().get(0));
							finalizarTransaccionErrorRecuperableConsulta(solicitud, resultBean);
							actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, "Error Consulta: " + resultBean.getListaMensajes().get(0));
						}
					} catch (Exception e) {
						log.info("Error: " + e);
						resultBean = new ResultBean(true, "Error: " + e);
						finalizarTransaccionErrorRecuperableConsulta(solicitud, resultBean);
						actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, "Excepción Consulta: " + e.toString());
					}
				}
			}
		} catch (Exception e) {
			log.error("Error: " + e);
			if (solicitud != null) {
				try {
					finalizarTransaccionConErrorNoRecuperableSinXML(solicitud, "La Solicitud " + solicitud.getIdTramite() + " ha dado una excepción y ha sido borrada.");
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la el expediente: " + solicitud.getIdTramite() + ", Error: " + e1.toString());
					actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, e1.toString());
				}
			}
			actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, e.toString());
		} catch (BorrarSolicitudExcepcion e) {
			log.error("Error al borrar la el expediente: " + solicitud.getIdTramite() + ", Error: " + e.toString());
			actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, e.toString());
		} catch (OegamExcepcion e1) {
			log.error("Error al cargar los certificados de datos para EEFF" + e1.toString());
			actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, e1.toString());
		} finally {
			log.info(ConstantesEEFF.LOG_PROCESO_EEFF_GENERATOR + " --FIN--");
		}
	}

	private void finalizarTransaccionConErrorNoRecuperableSinXML(ColaBean solicitud, String respuestaError) throws BorrarSolicitudExcepcion {
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), respuestaError);
		errorNoRecuperable();
	}

	private void finalizarTransaccionErrorRecuperableConsulta(ColaBean solicitud, ResultBean resultBean) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(solicitud.getProceso());
		if (solicitud.getNumeroIntento().intValue()<numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(solicitud.getIdEnvio(), resultBean.getMensaje());
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperableConsulta(solicitud, generarMensaje (resultBean));
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.EEFF.getNombreEnum();
	}

	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean solicitud, String respuestaError) throws BorrarSolicitudExcepcion {
		servicioTramiteTrafico.cambiarEstadoConEvolucionEstadosPermitidos(solicitud.getIdTramite(), EstadoTramiteTrafico.Finalizado_Con_Error, getEstadosPermitidosLiberar(), true, respuestaError, solicitud.getIdUsuario());
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), respuestaError);
		errorNoRecuperable();
	}

	protected void finalizarTransaccionConErrorNoRecuperableConsulta(ColaBean solicitud, String respuestaError) throws BorrarSolicitudExcepcion {
		notificarConsulta(solicitud, respuestaError);
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), respuestaError);
		errorNoRecuperable();
		servicioWebServiceEEFF.cambiarEstadoConsulta(solicitud.getIdTramite(), solicitud.getIdUsuario(), EstadoConsultaEEFF.Finalizado_Con_Error,
				EstadoConsultaEEFF.Pdte_Respuesta, solicitud.getRespuesta());
	}

	protected void finalizarTransaccionErrorRecuperable(ColaBean solicitud, ResultBean resultBean) throws BorrarSolicitudExcepcion{
		BigDecimal numIntentos = getNumeroIntentos(solicitud.getProceso());
		if (solicitud.getNumeroIntento().intValue() < numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(solicitud.getIdEnvio(), resultBean.getMensaje());
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperable(solicitud, generarMensaje (resultBean));
		}
	}

	protected String generarMensaje(ResultBean resultBean) {
		String mensaje = "";
		for (String mens : resultBean.getListaMensajes()){
			mensaje += mens+ ". ";
		}
		return mensaje;
	}

	protected void finalizarTransaccionCorrectaCambiandoEstado(ColaBean solicitud, EstadoTramiteTrafico estado, String resultado) {
		servicioTramiteTrafico.cambiarEstadoConEvolucionEstadosPermitidos(solicitud.getIdTramite(), estado, getEstadosPermitidosLiberar(), true, resultado, solicitud.getIdUsuario());
		finalizarTransaccionCorrecta(solicitud, resultado);
	}

	protected EstadoTramiteTrafico[] getEstadosPermitidosLiberar() {
		return new EstadoTramiteTrafico[]{EstadoTramiteTrafico.Pendiente_Liberar};
	}

	protected void finalizarTransaccionCorrecta(ColaBean solicitud, String resultado){
		getModeloSolicitud().borrarSolicitud(solicitud.getIdEnvio(), resultado);
		peticionCorrecta();
	}

	protected void finalizarTransaccionCorrectaConsulta(ColaBean solicitud, String resultado) {
		notificarConsulta(solicitud, resultado);
		finalizarTransaccionCorrecta(solicitud, resultado);
		servicioWebServiceEEFF.cambiarEstadoConsulta(solicitud.getIdTramite(), solicitud.getIdUsuario(), EstadoConsultaEEFF.Finalizado,
				EstadoConsultaEEFF.Pdte_Respuesta, solicitud.getRespuesta());
	}

	protected void notificarConsulta(ColaBean solicitud, String resultado) {
		NotificacionDto notificacion = new NotificacionDto();
		notificacion.setDescripcion(resultado);
		notificacion.setIdTramite(solicitud.getIdTramite());
		notificacion.setIdUsuario(solicitud.getIdUsuario().longValue());
		notificacion.setTipoTramite(solicitud.getTipoTramite());
		servicioNotificacion.crearNotificacion(notificacion);
	}

	public ServicioEEFF getServicioEEFF() {
		return servicioEEFF;
	}

	public void setServicioEEFF(ServicioEEFF servicioEEFF) {
		this.servicioEEFF = servicioEEFF;
	}

	public ServicioNotificacion getServicioNotificacion() {
		return servicioNotificacion;
	}

	public void setServicioNotificacion(ServicioNotificacion servicioNotificacion) {
		this.servicioNotificacion = servicioNotificacion;
	}

}