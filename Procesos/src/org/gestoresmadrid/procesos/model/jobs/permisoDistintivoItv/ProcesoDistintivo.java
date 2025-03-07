package org.gestoresmadrid.procesos.model.jobs.permisoDistintivoItv;

import java.math.BigDecimal;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioWebServiceDistintivo;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoDistintivo extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoDistintivo.class);

	@Autowired
	ServicioWebServiceDistintivo servicioWebServiceDistintivo;

	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto solicitud = null;
		String resultadoEjecucion = null;
		String excepcion = null;
		try {
			log.info("Inicio proceso: " + getProceso());
			solicitud = tomarSolicitud();
			if (solicitud == null) {
				sinPeticiones();
			} else {
				new UtilesWSMatw().cargarAlmacenesTrafico();
				ResultadoDistintivoDgtBean resultado = null;
				if (solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.SOLICITUD_DSTV)) {
					resultado = servicioWebServiceDistintivo.procesarSolicitudDstv(solicitud);
				} else if (solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.SOLICITUD_DUPLICADO_DSTV)) {
					resultado = servicioWebServiceDistintivo.procesarSolicitudDuplicadoDstv(solicitud);
				}
				if (resultado != null && resultado.getExcepcion() != null) {
					throw new Exception(resultado.getExcepcion());
				} else if (resultado != null && resultado.isError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					solicitud.setRespuesta(resultado.getMensaje());
					finalizarTransaccionConErrorNoRecuperablePermisoDistintivoItv(solicitud, resultado.getMensaje());
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					solicitud.setRespuesta("Solicitud procesada correctamente");
					finalizarTransaccionCorrectaPermisoDistintivoItv(solicitud, resultadoEjecucion, resultado);
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ocurrio un error no controlado en el proceso PERMISO DISTINTIVO, error: ", e);
			String messageException = getMessageException(e);
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperablePermisoDistintivoItv(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
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

	@Override
	protected String getProceso() {
		return ProcesosEnum.DSTV.getNombreEnum();
	}

	private void finalizarTransaccionErrorRecuperablePermisoDistintivoItv(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperablePermisoDistintivoItv(cola, respuestaError);
		}
	}

	private void finalizarTransaccionCorrectaPermisoDistintivoItv(ColaDto solicitud, String resultadoEjecucion, ResultadoDistintivoDgtBean resultado) {
		super.finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
	}

	private void finalizarTransaccionConErrorNoRecuperablePermisoDistintivoItv(ColaDto solicitud, String respuestaError) {
		super.finalizarTransaccionConErrorNoRecuperable(solicitud, respuestaError);
		if (solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.SOLICITUD_DSTV)) {
			String[] datos = solicitud.getXmlEnviar().split("_");
			servicioWebServiceDistintivo.actualizarEstadoDstv(new BigDecimal(datos[1]), EstadoPermisoDistintivoItv.Finalizado_Error, respuestaError);
		} else if (solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.SOLICITUD_DUPLICADO_DSTV)) {
			servicioWebServiceDistintivo.actualizarEstadoDstvVNMO(solicitud.getIdTramite().longValue(), EstadoPermisoDistintivoItv.Finalizado_Error, respuestaError, solicitud.getIdUsuario());
		}
	}

}