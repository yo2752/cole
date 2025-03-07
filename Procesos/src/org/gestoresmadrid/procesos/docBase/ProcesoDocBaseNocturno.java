package org.gestoresmadrid.procesos.docBase;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoDocBaseNocturno extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoDocBaseNocturno.class);

	@Autowired
	ServicioGestionDocBase servicioGestionDocBase;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	protected void doExecute() throws JobExecutionException {
		String resultadoEjecucion = null;
		String mensajeResultado = "";
		if (((!utilesFecha.esDomingo() && !utilesFecha.esLunes()) && !hayEnvioData(getProceso()))
				|| "SI".equals(gestorPropiedades.valorPropertie("forzar.docBase"))) {
			try {
				log.info("Inicio proceso: " + getProceso());
				ResultadoDocBaseBean resultado = servicioGestionDocBase.gestionDocBaseNocturno();
				if (resultado != null && resultado.getExcepcion() != null) {
					throw new Exception(resultado.getExcepcion());
				} else if (resultado.getError()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					if(resultado.getListaMensaje() != null && !resultado.getListaMensaje().isEmpty()) {
						for (String mensaje : resultado.getListaMensaje()) {
							mensajeResultado += mensaje;
						}
					}
					if (resultado.getMensaje() != null && !resultado.getMensaje().isEmpty()) {
						mensajeResultado += resultado.getMensaje();
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
				log.error("Ocurrio un error no controlado en el proceso de gestion de documento base nocturno", e);
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
		return ProcesosEnum.DOC_BASE_NOCTURNO.getNombreEnum();
	}

}