package colas.procesos;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import procesos.enumerados.RetornoProceso;
import trafico.utiles.enumerados.TipoProceso;
import trafico.utiles.importacion.importadores.ImportadorAVPOAnuntis;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import colas.constantes.ConstantesProcesos;
import colas.procesos.utiles.UtilesProcesos;

public class ProcesoAVPOAnuntis extends ProcesoBase {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoAVPOAnuntis.class);
	private static final String ANUNTISM = "ANUNTISM";
	private static final String ANUNTIST = "ANUNTIST";

	private UtilesProcesos utilesProcesos;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			// Con esto se discrimina entre el proceso de la mañana (300) y el de la tarde (301)
			Integer tipo = (Integer) jobExecutionContext.getJobDetail().getJobDataMap().get("proceso");
			String nombreProceso = getUtilesProcesos().getNombreProcesoOrdenado(tipo);

			// Por defecto Anuntis Tarde
			TipoProceso tipoProceso = TipoProceso.Anuntis_Tarde;
			if (ANUNTISM.equals(nombreProceso)) {
				tipoProceso = TipoProceso.Anuntis_Maniana;
			} else if (ANUNTIST.equals(nombreProceso)) {
				tipoProceso = TipoProceso.Anuntis_Tarde;
			}

			if (!getModeloTrafico().hayEnvio(tipoProceso) ) {
				log.info("Proceso " + ConstantesProcesos.PROCESO_AVPO_ANUNTIS + " -- Buscando Peticion");
				new ImportadorAVPOAnuntis().execute(tipoProceso);
			}

			jobExecutionContext.getMergedJobDataMap().put("retorno", RetornoProceso.CORRECTO);
			log.info("Proceso " + ConstantesProcesos.PROCESO_AVPO_ANUNTIS + " -- Proceso ejecutado correctamente");
		} catch (OegamExcepcion e) {
			jobExecutionContext.getMergedJobDataMap().put("retorno", RetornoProceso.ERROR_NO_RECUPERABLE);
			log.info("Proceso " + ConstantesProcesos.PROCESO_AVPO_ANUNTIS + " -- Error Recibido: " + RetornoProceso.ERROR_NO_RECUPERABLE + " - " + e);
		} catch (Exception e) {
			jobExecutionContext.getMergedJobDataMap().put("retorno", RetornoProceso.ERROR_NO_RECUPERABLE);
			log.info("Proceso " + ConstantesProcesos.PROCESO_AVPO_ANUNTIS + " -- Error Recibido: " + RetornoProceso.ERROR_NO_RECUPERABLE + " - " + e);
		}
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		log.info("Soy el proceso Anuntis ejecutando el cambio de Número de instancias");
	}

	/* ********************************************************* */
	/* MODELOS ************************************************* */
	/* ********************************************************* */

	public UtilesProcesos getUtilesProcesos() {
		if (utilesProcesos == null) {
			utilesProcesos = new UtilesProcesos();
		}
		return utilesProcesos;
	}

	public void setUtilesProcesos(UtilesProcesos utilesProcesos) {
		this.utilesProcesos = utilesProcesos;
	}

}