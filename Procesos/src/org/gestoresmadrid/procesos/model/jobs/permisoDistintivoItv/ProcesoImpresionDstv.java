package org.gestoresmadrid.procesos.model.jobs.permisoDistintivoItv;

import java.math.BigDecimal;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioWebServiceDistintivo;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoImpresionDstv extends AbstractProceso{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoImpresionDstv.class);

	@Autowired
	ServicioWebServiceDistintivo servicioWebServiceDistintivo;
	
	@Autowired
	ServicioNotificacion servicioNotificacion;
	
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
				ResultadoPermisoDistintivoItvBean resultado = null;
				if(solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.SOLICITUD_DUPLICADO_DSTV)){
					resultado = servicioWebServiceDistintivo.impresionDstvDuplicado(solicitud);
				} else if(solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.SOLICITUD_DSTV)){
					resultado = servicioWebServiceDistintivo.impresionDistintivo(solicitud, ServicioDistintivoDgt.SOLICITUD_DSTV);
				} else if(solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.IMPRESION_PROCESO_DSTV)){
					resultado = servicioWebServiceDistintivo.impresionDistintivo(solicitud, ServicioDistintivoDgt.IMPRESION_PROCESO_DSTV);
				}
				if(resultado != null && resultado.getExcepcion() != null){
					throw new Exception(resultado.getExcepcion());
				}else if(resultado != null && resultado.isError()){
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					solicitud.setRespuesta(resultado.getMensaje());
					try {
						finalizarTransaccionConErrorNoRecuperableDistintivo(solicitud, resultado.getMensaje());
					} catch (BorrarSolicitudExcepcion e) {
						log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				}else{
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					solicitud.setRespuesta("Solicitud procesada correctamente");
					finalizarTransaccionCorrectaDistintivo(solicitud, resultadoEjecucion, resultado);
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de impresion de permisos y fichas técnicas, error: ", e);
			String messageException = getMessageException(e);
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionErrorRecuperableDistintivo(solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (BorrarSolicitudExcepcion e1) {
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
		return  ProcesosEnum.IMP_DSTV.getNombreEnum();
	}
	
	private void finalizarTransaccionErrorRecuperableDistintivo(ColaDto cola, String respuestaError) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperableDistintivo(cola, respuestaError);
		}
	}
	
	private void finalizarTransaccionCorrectaDistintivo(ColaDto solicitud, String resultadoEjecucion,ResultadoPermisoDistintivoItvBean resultado) {
		super.finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
		EstadoPermisoDistintivoItv nuevoEstado = null;
		if(solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.SOLICITUD_DUPLICADO_DSTV)){
			nuevoEstado = EstadoPermisoDistintivoItv.Generado;
		} else if(solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.SOLICITUD_DSTV)){
			nuevoEstado = EstadoPermisoDistintivoItv.Generado;
		} else if(solicitud.getXmlEnviar().contains(ServicioDistintivoDgt.IMPRESION_PROCESO_DSTV)){
			nuevoEstado = EstadoPermisoDistintivoItv.GENERADO_JEFATURA;
		}
		servicioWebServiceDistintivo.actualizarEstado(solicitud.getIdTramite(), nuevoEstado, resultadoEjecucion, solicitud.getIdUsuario());
		if(EstadoPermisoDistintivoItv.GENERADO_JEFATURA.getValorEnum().equals(nuevoEstado.getValorEnum())){
			servicioWebServiceDistintivo.actualizarEstadosDistintivos(solicitud.getIdTramite().longValue(),
					EstadoPermisoDistintivoItv.ORDENADO_DOC.getValorEnum(),EstadoPermisoDistintivoItv.DOCUMENTO_ORDENADO.getValorEnum(), 
					solicitud.getIdUsuario(), "proceso");
		}
	}

	private void finalizarTransaccionConErrorNoRecuperableDistintivo(ColaDto solicitud, String respuestaError) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(solicitud, respuestaError);
		servicioWebServiceDistintivo.actualizarEstado(solicitud.getIdTramite(), EstadoPermisoDistintivoItv.Finalizado_Error, respuestaError, solicitud.getIdUsuario());
	}
	
	
}