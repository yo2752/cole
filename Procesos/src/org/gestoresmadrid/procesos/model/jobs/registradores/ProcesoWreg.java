package org.gestoresmadrid.procesos.model.jobs.registradores;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistroFueraSecuencia;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroFueraSecuenciaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.ws.service.ServicioWebServiceRegistro;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import net.consejogestores.sercon.core.model.integracion.dto.RegistroError;
import net.consejogestores.sercon.core.model.integracion.dto.RegistroResponse;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class ProcesoWreg extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger("ProcesoWREG");

	@Autowired
	ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	ServicioRegistroFueraSecuencia servicioRegistroFueraSecuencia;

	@Autowired
	ServicioWebServiceRegistro servicioWebServiceRegistro;

	@Override
	protected void doExecute() throws JobExecutionException {
		log.info("INICIO PROCESO WREG");
		ColaDto solicitud = null;
		String resultadoEjecucion = null;
		String excepcion = "";
		TramiteRegistroDto tramiteRegistro = null;
		try {
			log.info("Proceso " + getProceso() + " -- Buscando Solicitud");
			solicitud = tomarSolicitud();
			if (solicitud == null) {
				sinPeticiones();
			} else {
				log.info("Proceso " + getProceso() + " -- Solicitud " + getProceso() + " encontrada");
				RegistroResponse response = servicioWebServiceRegistro.procesarEnvioTramiteSercon(solicitud);
				
				tramiteRegistro = getTramiteRegistro(solicitud.getIdTramite(), solicitud.getTipoTramite());
				
				if (response != null && response.getCodigosError() != null && response.getCodigosError().length > 0) {
					for (RegistroError registroError : response.getCodigosError()) {
						excepcion += registroError.getDescripcion() + "(" + registroError.getCodigo() + ").";
					}
					solicitud.setRespuesta(excepcion);
					resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
					finalizarTransaccionErrorRecuperable(solicitud, excepcion, tramiteRegistro);
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					solicitud.setRespuesta("Se ha realizado correctamente el envío a Registro");
					finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
				}

				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
				tramiteRegistro.setRespuesta(solicitud.getRespuesta());
				servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, tramiteRegistro.getEstado(), solicitud.getIdUsuario());
			}
		} catch (Throwable e) {
			log.error("Excepcion Proceso Wreg", e);
			String messageException = getMessageException(e);
			try {
				finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION + messageException, tramiteRegistro);
			} catch (Exception e1) {
				log.error("No ha sido posible borrar la solicitud", e1);
			}
			actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
		}
	}

	private TramiteRegistroDto getTramiteRegistro(BigDecimal idTramiteSolicitud, String tipoTramite) {
		BigDecimal idTramite = null;
		// Si es una petición fuera de secuencia
		if ("RFS".equals(tipoTramite)) {
			RegistroFueraSecuenciaDto acusePendiente = servicioRegistroFueraSecuencia.getRegistroFueraSecuencia(idTramiteSolicitud.longValue());
			idTramite = acusePendiente.getIdTramiteRegistro();
		} else {
			idTramite = idTramiteSolicitud;
		}
		return servicioTramiteRegistro.getTramite(idTramite);
	}

	private void finalizarTransaccionErrorRecuperable(ColaDto cola, String error, TramiteRegistroDto tramiteRegistro) {
		log.error(Claves.CLAVE_LOG_PROCESO_WREG + "Finalizacion incorrecta. idTramite: " + cola.getIdTramite() + " Error: " + error);

		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperable(cola, error);
			if (!"RFS".equals(cola.getTipoTramite())) {
				if (tramiteRegistro == null) {
					tramiteRegistro = servicioTramiteRegistro.getTramite(cola.getIdTramite());
				}
				if (tramiteRegistro != null) {
					servicioTramiteRegistro.cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(EstadoTramiteRegistro.Finalizado_error
							.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), cola.getIdUsuario());
					tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Finalizado_error.getValorEnum()));
				}
			}
		}

		if (tramiteRegistro != null && tramiteRegistro.getIdTramiteOrigen() != null) {
			servicioWebServiceRegistro.enviarCorreoJustificantePago(cola.getIdTramite());
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.WREG.getNombreEnum();
	}
}
