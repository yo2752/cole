package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.inteve.model.service.ServicioInteve;
import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.ResultInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoSolInfo;
import org.gestoresmadrid.oegam2comun.trafico.model.service.impl.ServicioTramiteTraficoSolInfoImpl;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoInteve3 extends AbstractProcesoBase {

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ProcesoInteve3.class);

	private static final String NOTIFICACION = "PROCESO INTEVE3 FINALIZADO";

	@Autowired
	private ServicioTramiteTraficoSolInfo servicioTramiteTraficoSolInfo;

	@Autowired
	private ServicioInteve servicioInteve;

	@Autowired
	private ServicioNotificacion servicioNotificacion;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean colaBean = null;
		try {
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				sinPeticiones();
			} else {
				TramiteTrafSolInfoDto tramiteTrafSolInfoDto = getTramiteTrafSolInfoDto(colaBean.getIdTramite());

				ResultInteveBean result;
				if (tramiteTrafSolInfoDto == null || tramiteTrafSolInfoDto.getSolicitudes() == null || tramiteTrafSolInfoDto.getSolicitudes().isEmpty()) {
					// No se ha podido recuperar el trámite o las solicitudes
					result = new ResultInteveBean();
					result.setError(true);
					result.setMensaje("No se han encontrado solicitudes de información para el expediente con número: " + colaBean.getIdTramite());
					LOG.warn(result.getMensaje());
				} else {
					result = servicioInteve.solicitarInforme(tramiteTrafSolInfoDto, colaBean.getIdUsuario());
				}
				String resultadoEjecucion;
				String excepcionMensaje = null;
				if (result.getException() != null) {
					// Reintentable
					resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
					excepcionMensaje = result.getException().getMessage() != null ? result.getException().getMessage() : result.getException().toString();
					finalizarTransaccionErrorRecuperable(colaBean, excepcionMensaje, tramiteTrafSolInfoDto);
				} else if (result.isError()) {
					// Finalizar con error
					finalizarTransaccionConErrorNoRecuperable(colaBean, result.getMensaje(), tramiteTrafSolInfoDto);
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					colaBean.setRespuesta(result.getMensaje());
				} else {
					// Finalizar guay
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					finalizarTransaccionCorrecta(colaBean,  resultadoEjecucion, tramiteTrafSolInfoDto);
					colaBean.setRespuesta(ConstantesProcesos.OK);
				}
				actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcionMensaje);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			LOG.error("Ocurrio un error interno en el proceso INTEVE3", e);
			// Finalizar con error
			String messageException = ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR  + (e.getMessage() != null ? e.getMessage() : e.toString());
			if (colaBean != null && colaBean.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperable(colaBean, messageException, null);
				} catch (BorrarSolicitudExcepcion e1) {
					LOG.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e1.toString());
				}

				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				LOG.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		} catch (BorrarSolicitudExcepcion e) {
			LOG.error("No se ha podido sacar la solicitud de la cola, idEnvio: " + colaBean.getIdEnvio() + ", numExpediente: " + colaBean.getIdTramite(), e);
			if (colaBean != null && colaBean.getProceso() != null) {
				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, "No se ha podido sacar la solicitud de la cola");
			} else {
				LOG.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		} 
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.INTEVE3.getNombreEnum();
	}

	private void finalizarTransaccionCorrecta(ColaBean cola, String resultado, TramiteTrafSolInfoDto tramiteTrafSolInfoDto) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		crearNotificacion(cola, tramiteTrafSolInfoDto);
	}

	private void finalizarTransaccionErrorRecuperable(ColaBean cola, String respuestaError, TramiteTrafSolInfoDto tramiteTrafSolInfoDto) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(cola.getIdEnvio(), respuestaError);
			peticionRecuperable();
		} else {		
			marcarSolicitudConErrorServicio(cola, respuestaError);
			servicioTramiteTrafico.cambiarEstadoConEvolucion(cola.getIdTramite(), EstadoTramiteTrafico.convertir(tramiteTrafSolInfoDto.getEstado()) , EstadoTramiteTrafico.Finalizado_Con_Error, false, null, cola.getIdUsuario());
		}
	}

	private void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError, TramiteTrafSolInfoDto tramiteTrafSolInfoDto) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		crearNotificacion(cola, tramiteTrafSolInfoDto);
	}

	private void crearNotificacion(ColaBean cola, TramiteTrafSolInfoDto tramiteTrafSolInfoDto) {
		if (tramiteTrafSolInfoDto != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
			notifDto.setEstadoNue(new BigDecimal(tramiteTrafSolInfoDto.getEstado()));
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TipoTramiteTrafico.Solicitud.getValorEnum());
			notifDto.setIdTramite(tramiteTrafSolInfoDto.getNumExpediente());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	private TramiteTrafSolInfoDto getTramiteTrafSolInfoDto(BigDecimal numExpediente) {
		// Recuperar el trámite
		ResultBean resultTramite = servicioTramiteTraficoSolInfo.getTramiteTraficoSolInfo(numExpediente);
		TramiteTrafSolInfoDto tramiteTrafSolInfoDto = null;
		if (resultTramite != null) {
			tramiteTrafSolInfoDto = (TramiteTrafSolInfoDto) resultTramite.getAttachment(ServicioTramiteTraficoSolInfoImpl.TRAMITE_DETALLE);
		}
		return tramiteTrafSolInfoDto;
	}

}