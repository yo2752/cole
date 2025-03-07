package org.gestoresmadrid.procesos.model.jobs.inteve;

import java.math.BigDecimal;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamInteve.service.ServicioTramiteInteve;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoTramiteInteveBean;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoInteveCompletoWS extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoInteveCompletoWS.class);

	@Autowired
	ServicioTramiteInteve servicioTramiteTraficoInteve;
	
	@Autowired
	ServicioProcesos servicioProcesos;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto solicitud = null;
		String resultadoEjecucion = null;
		try {
			log.info("Inicio proceso: " + getProceso());
			solicitud = tomarSolicitud();
			if (solicitud == null) {
				sinPeticiones();
			} else {
				ResultadoTramiteInteveBean resultado = servicioTramiteTraficoInteve.solicitarInteveCompletoWS(solicitud.getIdTramite(), solicitud.getIdUsuario().longValue());
				if (resultado != null && resultado.getExcepcion() != null) {
					throw new Exception(resultado.getExcepcion());
				} else if (resultado != null && resultado.getError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					solicitud.setRespuesta(resultado.getMensaje());
					finalizarTransaccionConErrorNoRecuperableSolicitud(solicitud, resultado.getMensaje());
				} else {
					if (resultado.getNumErrorWS() != null && resultado.getNumOkWS() != null) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta("Ejecución correctamente parcial, compruebe los errores en las solicitudes.");
						finalizarTransaccionCorrectaInteveCompleto(solicitud, resultadoEjecucion, resultado);
					} else if (resultado.getNumErrorWS() != null) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						solicitud.setRespuesta("Ejecución erronea, compruebe los errores en las solicitudes.");
						finalizarTransaccionConErrorNoRecuperableInteveCompleto(solicitud, resultado.getMensaje(), resultado);
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						solicitud.setRespuesta("Ejecución correcta");
						finalizarTransaccionCorrectaInteveCompleto(solicitud, resultadoEjecucion, resultado);
					}
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, null);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de InteveCompletoWS, error: ", e);
			String messageException = getMessageException(e);
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperable(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (Exception e1) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		}
		log.info("Fin proceso: " + getProceso());

	}
	protected void finalizarTransaccionErrorRecuperable(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			servicioProcesos.errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperableSolicitud(cola, respuestaError);
		}
	}

	private void finalizarTransaccionConErrorNoRecuperableSolicitud(ColaDto solicitud, String mensaje) {
		this.finalizarTransaccionConErrorNoRecuperable(solicitud, mensaje);
		servicioTramiteTraficoInteve.finalizarSolicitudNoRecuperable(solicitud.getIdTramite(), mensaje, solicitud.getIdUsuario().longValue());
	}

	private void finalizarTransaccionConErrorNoRecuperableInteveCompleto(ColaDto solicitud, String mensaje, ResultadoTramiteInteveBean resultado) {
		this.finalizarTransaccionConErrorNoRecuperable(solicitud, mensaje);
		servicioTramiteTraficoInteve.finalizarSolicitudInteveCompleto(resultado.getListaResultadoWS(), resultado.getNumErrorWS(), resultado.getNumOkWS(),
				solicitud.getIdTramite(), solicitud.getIdUsuario().longValue());
	}

	private void finalizarTransaccionCorrectaInteveCompleto(ColaDto solicitud, String resultadoEjecucion, ResultadoTramiteInteveBean resultado) {
		ResultadoBean resultCredito = null;
		if(resultado.getNumOkWS() != null && resultado.getNumOkWS() > 0){
			resultCredito = servicioTramiteTraficoInteve.tratarCreditosInteve(solicitud.getIdTramite(), solicitud.getIdContrato().longValue(), solicitud.getIdUsuario().longValue(),resultado.getNumOkWS());
		}
		if(resultCredito == null || !resultCredito.getError()){
			this.finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
			servicioTramiteTraficoInteve.finalizarSolicitudInteveCompleto(resultado.getListaResultadoWS(), resultado.getNumErrorWS(), resultado.getNumOkWS(), 
				solicitud.getIdTramite(), solicitud.getIdUsuario().longValue());
		} else {
			finalizarTransaccionConErrorNoRecuperableSolicitud(solicitud, resultCredito.getMensaje());
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.INTEVE_COMPLETO_WS.getNombreEnum();
	}

}
