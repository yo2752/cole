package org.gestoresmadrid.procesos.model.jobs.ivtm;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.quartz.JobExecutionException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoModificacionIvtm extends AbstractProcesoBase {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoModificacionIvtm.class);
	
	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean colaBean = null;
		try {
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				sinPeticiones();
			} else {
			

			
			}
		} catch (RuntimeException e) {
			e.printStackTrace();			
		} 
		
	}

	@Override
	protected String getProceso() {
		// TODO Auto-generated method stub
		return ProcesosEnum.IVTM_MODIFICACION.getNombreEnum();
	}

}
