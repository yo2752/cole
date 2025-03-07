package org.gestoresmadrid.procesos.model.jobs.permisoDistintivoItv;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.consultaKo.model.service.ServicioConsultaKo;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoExcelKoImprNocturno extends AbstractProceso{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoExcelKoImprNocturno.class);
	
	@Autowired
	ServicioConsultaKo servicioConsultaKo;
	
	@Override
	protected void doExecute() throws JobExecutionException {
		String resultadoEjecucion = null;
		String mensajeResultado = "";
		log.info("Inicio proceso: " + getProceso());
		if(!hayEnvioData(getProceso())){
			try {
				ResultadoConsultaKoBean resultado = servicioConsultaKo.generarExcelImprNocturno();
				if (resultado != null && resultado.getExcepcion() != null) {
					throw new Exception(resultado.getExcepcion());
				} else if (resultado.getError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					mensajeResultado = resultado.getMensaje();
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					mensajeResultado = "Solicitud procesada correctamente";
				}
				peticionCorrecta();
			} catch (Exception e) {
				log.error("Ocurrio un error no controlado en el proceso de generacion de excel de KO Nocturno", e);
				mensajeResultado = getMessageException(e);
				resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
				peticionRecuperable();
			}
			actualizarEjecucion(getProceso(), mensajeResultado, resultadoEjecucion, "0");
		} else {
			peticionCorrecta();
		}
		log.info("Fin proceso: " + getProceso());
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.GEN_EXCEL_KO_IMPR_NOCTURNO.getNombreEnum();
	}

}
