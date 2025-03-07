package org.gestoresmadrid.procesos.model.jobs.permisoDistintivoItv;

import java.math.BigDecimal;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionImpr;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoImprDemanda extends AbstractProceso  {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoImprDemanda.class);
	
	@Autowired
	ServicioGestionImpr servicioGestionImpr;
	
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
				ResultadoPermisoDistintivoItvBean resultado = servicioGestionImpr.imprDemanda(solicitud);
				if(resultado.getExisteImprNocturno()){
					marcarSolicitudConErrorServicio(solicitud, "No se ha podido ejecutar el proceso de IMPR_DEMANDA al existir solicitudes encoladas para el proceso de IMPR_NOCTURNO.");
				} else if(resultado.getExcepcion()!=null){
					throw resultado.getExcepcion();
				}else if(resultado.getError()){
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					solicitud.setRespuesta(resultado.getMensaje());
					if(resultado.getNoExistenDoc()){
						finalizarTransaccionConErrorNoRecuperableImprDemanda(solicitud, resultado.getMensaje());
					} else {
						finalizarTransaccionErrorRecuperableImprDemanda(solicitud, resultado.getMensaje());
					}
				}else{
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					solicitud.setRespuesta("Solicitud procesada correctamente");
					finalizarTransaccionCorrectaImprDemanda(solicitud, resultado.getMensaje());
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso " + getProceso()+", error: ", e);
			String messageException = getMessageException(e);
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionConErrorNoRecuperableImprDemanda(solicitud, messageException);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		} 
		log.info("Fin proceso: " + getProceso());
	}

	private void finalizarTransaccionErrorRecuperableImprDemanda(ColaDto solicitud, String mensaje) {
		BigDecimal numIntentos = getNumeroIntentos(solicitud.getProceso());
		if (solicitud.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(solicitud.getIdEnvio());
			peticionRecuperable();
		} else {
			marcarSolicitudConErrorServicio(solicitud, mensaje);
		}
		
	}

	private void finalizarTransaccionConErrorNoRecuperableImprDemanda(ColaDto solicitud, String error) {
		super.finalizarTransaccionConErrorNoRecuperable(solicitud, error);
		servicioGestionImpr.actualizarEstadoPeticion(solicitud, EstadoPermisoDistintivoItv.Finalizado_Error);
	}

	private void finalizarTransaccionCorrectaImprDemanda(ColaDto solicitud, String mensaje) {
		super.finalizarTransaccionCorrecta(solicitud, mensaje);
		servicioGestionImpr.actualizarEstadoPeticion(solicitud, EstadoPermisoDistintivoItv.Doc_Recibido);
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.IMPR_DEMANDA.getNombreEnum();
	}
}
