package org.gestoresmadrid.procesos.model.jobs.ivtm;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmWS;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.ResultBeanAltaIVTM;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoAltaIvtm extends AbstractProcesoBase{

		
	@Autowired
	ServicioIvtmWS servicioWS;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger("ProcesoAltaIVTM");

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean solicitud = null;
		ResultBeanAltaIVTM	 resultado = null;
		try {
			solicitud = tomarSolicitud();
			if (solicitud == null) {
				sinPeticiones();
			} else {
				new UtilesWSMatw().cargarAlmacenesTrafico();
				resultado = servicioWS.procesarSolicitudAlta(solicitud.getIdTramite(), solicitud.getXmlEnviar(), solicitud.getIdUsuario(), solicitud.getIdContrato());
				if(resultado.isError()){
					errorNoRecuperable();
					actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_NO_CORRECTA, resultado.getMensajeError(),solicitud.getCola());
				}else{
					
					peticionCorrecta();
					actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA, ConstantesProcesos.EJECUCION_CORRECTA,solicitud.getCola());
				}
				
			}
		} catch (RuntimeException e) {
			log.error("Ha sucedido un error en el proceso "+ getProceso() +e.getMessage());
			peticionRecuperable();
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION, e.getMessage(),solicitud.getCola());
			e.printStackTrace();			
		} catch (OegamExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	@Override
	protected String getProceso() {

		return ProcesosEnum.IVTM_ALTA.getNombreEnum();
	}

}
