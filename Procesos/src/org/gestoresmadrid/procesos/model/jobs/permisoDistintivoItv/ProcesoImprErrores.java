package org.gestoresmadrid.procesos.model.jobs.permisoDistintivoItv;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionImpr;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioImpNochePrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoImprErrores  extends AbstractProceso{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoImprErrores.class);
	
	@Autowired
	ServicioImpNochePrmDstvFicha servicioImpNochePrmDstvFicha;
	
	@Autowired
	ServicioGestionImpr servicioGestionImpr;

	@Autowired
	ServicioProcesos servicioProcesos;
	
	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto solicitud = null;
		String resultadoEjecucion = null;
		String  excepcion = null;
		try{
			log.info("Inicio proceso: " + getProceso());
			solicitud = tomarSolicitud();
			if(solicitud == null){
				sinPeticiones();
			}else{
				ResultadoPermisoDistintivoItvBean resultado = servicioGestionImpr.imprErrores(solicitud);
				if(resultado.getExcepcion()!=null){
					throw resultado.getExcepcion();
				}else if(resultado.getError()){
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					solicitud.setRespuesta(resultado.getMensaje());
					finalizarTransaccionErrorRecuperableConErrorServico(solicitud, resultado.getMensaje());
				}else{
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					solicitud.setRespuesta("Solicitud procesada correctamente");
					finalizarTransaccionCorrecta(solicitud, resultado.getMensaje());
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso " + getProceso()+", error: ", e);
			String messageException = getMessageException(e);
			if (solicitud != null && solicitud.getProceso() != null) {
				try {
					finalizarTransaccionConErrorNoRecuperable(solicitud, messageException);
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
	
	@Override
	protected String getProceso() {
		return ProcesosEnum.IMPR_ERRORES.getNombreEnum();
	}
}
