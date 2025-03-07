package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.dto.RespuestaConsultaEitv;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioConsultaEitv;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoConsultaEITV extends AbstractProceso {

	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoConsultaEITV.class);

	private static final String NOTIFICACION = "PROCESO CONSULTA TARJETA EITV FINALIZADO";
	private static final String TIPO_TRAMITE_NOTIFICACION = "N/A";

	@Autowired
	ServicioConsultaEitv servicioConsultaEitv;

	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto cola = null;
		String resultadoEjecucion = null;
		String excepcion = null;
		try {
			log.info("Proceso " + getProceso() + " -- Buscando Solicitud");
			cola = tomarSolicitud();
			if (cola == null) {
				sinPeticiones();
			} else {
				if (cola.getXmlEnviar() == null) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					cola.setRespuesta("Error: La Solicitud " + cola.getIdEnvio() + " no contiene xml de envio.");
					finalizarTransaccionConErrorNoRecuperable(cola, "No existen el xml de envio.");
				} else {
					new UtilesWSMatw().cargarAlmacenesTrafico();
					RespuestaConsultaEitv respuesta = servicioConsultaEitv.generarConsultaEitv(cola);
					if (respuesta != null && respuesta.getException() != null) {
						throw respuesta.getException();
					} else if (respuesta != null && respuesta.isError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						String mensaje = null;
						if (respuesta.getMensajesError() != null) {
							for (String mnsj : respuesta.getMensajesError()) {
								mensaje += mnsj + ". ";
							}
						} else {
							mensaje = respuesta.getMensaje();
						}
						cola.setRespuesta(mensaje);
						finalizarTransaccionConErrorNoRecuperable(cola, mensaje);
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						cola.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
						finalizarTransaccionCorrecta(cola, resultadoEjecucion);
					}
				}
				actualizarUltimaEjecucion(cola, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de consulta tarjeta eitv", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperableConErrorServico(cola, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
			} catch (Exception e1) {
				log.error("Error al borrar la solicitud: " + cola.getIdEnvio() + ", Error: " + e1.toString());
			}
			actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		} catch (OegamExcepcion e1) {
			log.error("Ocurrio un error no controlado en el proceso de consulta tarjeta eitv al cargar los almacenes de claves", e1);
		}
	}

	private void crearNotificacion(ColaDto cola, BigDecimal estadoNuevo) {
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
	protected void finalizarTransaccionErrorRecuperableConErrorServico(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaDto cola, String respuestaError) {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		if (!cola.getXmlEnviar().contains(ServicioConsultaEitv.CONSULTA_TARJETA_PRE_MATRICULADOS_XML_ENVIAR)) {
			servicioConsultaEitv.cambiarEstadoTramiteProceso(cola.getIdTramite(), new BigDecimal(EstadoTramiteTrafico.Error_Consulta_EITV.getValorEnum()), cola.getIdUsuario());
		}
		crearNotificacion(cola, new BigDecimal(EstadoTramiteTrafico.Error_Consulta_EITV.getValorEnum()));
	}

	@Override
	protected void finalizarTransaccionCorrecta(ColaDto cola, String resultado) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		if (!cola.getXmlEnviar().contains(ServicioConsultaEitv.CONSULTA_TARJETA_PRE_MATRICULADOS_XML_ENVIAR)) {
			servicioConsultaEitv.cambiarEstadoTramiteProceso(cola.getIdTramite(), new BigDecimal(EstadoTramiteTrafico.Consultado_EITV.getValorEnum()), cola.getIdUsuario());
		}
		crearNotificacion(cola, new BigDecimal(EstadoTramiteTrafico.Consultado_EITV.getValorEnum()));
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.CONSULTA_TARJETA_EITV.getNombreEnum();
	}
}