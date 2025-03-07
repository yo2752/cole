package org.gestoresmadrid.procesos.model.jobs.permisoDistintivoItv;

import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionImpr;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoGestionColasIMPR extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoGestionColasIMPR.class);

	@Autowired
	ServicioGestionImpr servicioGestionImpr;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		String resultadoEjecucion = null;
		String mensajeResultado = "";
		Boolean ejecutarseProceso = Boolean.TRUE;
		if(!"SI".equals(gestorPropiedades.valorPropertie("forzar.ejecucion.gestionColasImpr"))){
			if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
				try {
					ejecutarseProceso = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
				} catch (OegamExcepcion e1) {
					log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
				}
			}
		}
		if (ejecutarseProceso && !hayEnvioData(getProceso())) {
			try {
				log.info("Inicio proceso: " + getProceso());
				ResultadoPermisoDistintivoItvBean resultado = servicioGestionImpr.gestionColasImpr();
				if (resultado != null && resultado.getExcepcion() != null) {
					throw new Exception(resultado.getExcepcion());
				} else if (resultado.getError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					for (String mensaje : resultado.getListaMensajes()) {
						mensajeResultado += mensaje;
					}
					if (mensajeResultado != null && mensajeResultado.length() > 500) {
						mensajeResultado = mensajeResultado.substring(0, 495);
					}
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					mensajeResultado = "Solicitud procesada correctamente";
				}
				peticionCorrecta();
			} catch (Exception e) {
				log.error("Ocurrio un error no controlado en el proceso de gestion de colas IMPR", e);
				mensajeResultado = getMessageException(e);
				resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
				peticionRecuperable();
			}
			actualizarEjecucion(getProceso(), mensajeResultado, resultadoEjecucion, "0");
			log.info("Fin proceso: " + getProceso());
		} else {
			peticionCorrecta();
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.GESTION_COLAS_IMPR.getNombreEnum();
	}
}
