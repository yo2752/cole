package org.gestoresmadrid.procesos.model.jobs.permisoDistintivoItv;

import java.math.BigDecimal;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioImpresionPermisosDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoImpresionPermisosMatw extends AbstractProceso{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoImpresionPermisosMatw.class);

	@Autowired
	ServicioImpresionPermisosDgt servicioImpresionPermisosDgt;
	
	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto solicitud = null;
		String resultadoEjecucion = null;
		String  excepcion = null;
		try {
			log.info("Inicio proceso: " + getProceso());
			solicitud = tomarSolicitud();
			if(solicitud == null){
				sinPeticiones();
			}else{
				ResultadoPermisoDistintivoItvBean resultado = servicioImpresionPermisosDgt.impresionPermisoDgt(solicitud);
				if(resultado != null && resultado.getExcepcion() != null){
					throw new Exception(resultado.getExcepcion());
				}else if(resultado != null && resultado.isError()){
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					solicitud.setRespuesta(resultado.getMensaje());
					finalizarTransaccionConErrorNoRecuperablePermisoDistintivoItv(solicitud, resultado.getMensaje());
				}else{
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					solicitud.setRespuesta("Solicitud procesada correctamente");
					finalizarTransaccionCorrectaPermisoDistintivoItv(solicitud, resultadoEjecucion, resultado);
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso IMPR_PERMISO_MATW, error: ", e);
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
		return  ProcesosEnum.IMP_PRM_MATW.getNombreEnum();
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
	
	private void finalizarTransaccionCorrectaPermisoDistintivoItv(ColaDto solicitud, String resultadoEjecucion,ResultadoPermisoDistintivoItvBean resultado) {
		super.finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
		servicioImpresionPermisosDgt.actualizarEstado(solicitud.getIdTramite(), EstadoPermisoDistintivoItv.GENERADO_JEFATURA, resultadoEjecucion, solicitud.getIdUsuario());
	}

	private void finalizarTransaccionConErrorNoRecuperablePermisoDistintivoItv(ColaDto solicitud, String respuestaError) {
		super.finalizarTransaccionConErrorNoRecuperable(solicitud, respuestaError);
		servicioImpresionPermisosDgt.actualizarEstado(solicitud.getIdTramite(), EstadoPermisoDistintivoItv.Finalizado_Error, respuestaError, solicitud.getIdUsuario());
	}
	
	
}