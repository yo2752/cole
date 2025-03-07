package org.gestoresmadrid.procesos.model.jobs.materiales;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioWebServicePedidos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.procesos.ProcesoNotificarPegatinas;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoNotificarMateriales extends AbstractProcesoBase {
	
	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoNotificarPegatinas.class);

	private static final String PROCESANDO              = "Proceso %s -- Procesando petición";
	private static final String ERROR_DIA_LABORABLE     = "Proceso %s -- Día NO LABORABLE";
	private static final String ERROR_PROCESO_EJECUTADO = "Proceso %s -- El proceso ya se ha ejecutado para el día de hoy";

	public static final int success = 0;
	public static final int error   = 1;

	@Autowired ServicioWebServicePedidos servicioWebServicePedidos;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		log.info(String.format(String.format(PROCESANDO, ProcesosEnum.MATERIALES_NOTIFICAR)));

		try {
			if (utilesFecha.esFechaLaborable(true)) {
				boolean prueba = false;
				if (!hayEnvioData(getProceso()) || prueba) {
					
					log.info(String.format(String.format("Paso por aqui %s", ProcesosEnum.MATERIALES_NOTIFICAR)));
					ResultBean resultBean = servicioWebServicePedidos.executeNotificacines();
					
					if (!resultBean.getError()) {
						actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA, "EJECUCION CORRECTA", "0");
						peticionCorrecta();	
					} else {
						log.info(String.format(ERROR_PROCESO_EJECUTADO, ProcesosEnum.MATERIALES_NOTIFICAR));
						sinPeticiones();
					}
				} else {
					log.info(String.format(ERROR_PROCESO_EJECUTADO, ProcesosEnum.MATERIALES_NOTIFICAR));
					sinPeticiones();
				}				
				
			} else {
				log.info(String.format(ERROR_DIA_LABORABLE, ProcesosEnum.MATERIALES_NOTIFICAR));
				sinPeticiones();
			}
			
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION,ex.getMessage(), "0");
		} catch (OegamExcepcion e) {
			log.error(e.getMessage(),e);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION,e.getMessage(),"0");
		}
		
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.MATERIALES_NOTIFICAR.getNombreEnum();
	}

}
