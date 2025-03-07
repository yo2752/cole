package colas.procesos;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import escrituras.beans.ResultBean;
import procesos.enumerados.EstadisticasTiposEnum;
import procesos.enumerados.RetornoProceso;
import trafico.modelo.ModeloEstadisticasCorreo;
import trafico.utiles.enumerados.TipoProceso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**	
 * Clase del proceso que realiza el env�o de estad�sticas diarias de MATE y CTIT
 *
 */
public class ProcesoEnvioEstadisticas extends ProcesoBase {
	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoEnvioEstadisticas.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		// Se determina si van a considerarse los festivos nacionales a partir de una property
		boolean considerarFestivos = false;
		boolean forzarEjecucion = false;
		try {
			if (gestorPropiedades.valorPropertie("estadisticas.lanza.proceso.considerando.festivos.nacionales").equals("si")){
				considerarFestivos = true;
			}
			if(gestorPropiedades.valorPropertie("estadisticas.lanza.proceso.forzar").equalsIgnoreCase("si")){
				forzarEjecucion = true;
			}
		} catch (Throwable tr) {
			log.info("Error leyendo el proceso.properties: " + tr.getMessage());
			log.info("No se puede acceder a las propiedades del proceso env�o de estad�sticas");
		}
		// Se realiza los env�os de los correos, intentando controlar las posibles excepciones que pudieran dar cada uno de los env�os para permitir que el resto contin�en con normalidad
		try {
			
			log.info("Proceso " + ConstantesProcesos.PROCESO_ENVIO_ESTADISTICAS + " -- Procesando petici�n");
			// Ricardo Rodriguez. Incidencia 3044. 27/11/2012
			// Si forzarEjecucion es true no se debe tener en cuenta si es festivo, s�bado o domingo para intentar el env�o.
			/* 
			 * Paso 1: Se intenta el env�o de CTIT. Si falla, se podr� continuar normalmente con el de MATE. Si va bien, actualiza a CORRECTA la �ltima ejecuci�n para impedir un segundo env�o aunque MATE falle.
			 * Paso 2: Se intenta el env�o de MATE. Si falla, se actualiza la �ltima ejecuci�n con excepci�n. Si va bien, actualiza a CORRECTA la �ltima ejecuci�n para impedir un segundo env�o aunque CTIT haya fallado.
			 * Paso 3: Se intenta el env�o para las Delegaciones.
			 */
			if(forzarEjecucion) {
				if(!getModeloTrafico().hayEnvio(TipoProceso.Estadisticas)){
					for (EstadisticasTiposEnum tipoEnvio : EstadisticasTiposEnum.values()) {
						realizarEnvio(tipoEnvio);
					}
				}
			} else {
				// FIN. Ricardo Rodriguez. Incidencia 3044. 27/11/2012
				if(!getModeloTrafico().hayEnvio(TipoProceso.Estadisticas) && utilesFecha.esFechaLaborable(considerarFestivos)){
					for (EstadisticasTiposEnum tipoEnvio : EstadisticasTiposEnum.values()) {
						realizarEnvio(tipoEnvio);
					}
				}
			}
			log.info("Proceso " + ConstantesProcesos.PROCESO_ENVIO_ESTADISTICAS + " -- Peticion Procesada");
			
			// Ejecuci�n correcta:
			jobExecutionContext.getMergedJobDataMap().put("retorno", RetornoProceso.CORRECTO);
			log.info("Proceso " + ConstantesProcesos.PROCESO_ENVIO_ESTADISTICAS + " -- Proceso ejecutado correctamente");
			
		} catch (Exception e){
			// Ricardo Rodriguez. Incidencia 3139. 18/12/2012
			try {
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(new ColaBean(), ConstantesProcesos.EJECUCION_EXCEPCION,e.toString(), TipoProceso.Estadisticas.getNombreEnum());
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(TipoProceso.Estadisticas.getValorEnum(), e.toString(), ConstantesProcesos.EJECUCION_EXCEPCION);
			} catch(Exception ex){
				log.error("ERROR: Fallo en la actualizaci�n de la �ltima ejecuci�n en ENVIO_DATA en " + ProcesosEnum.ESTADISTICAS.getNombreEnum());
			}
			log.info("Proceso " + ConstantesProcesos.PROCESO_ENVIO_ESTADISTICAS + " -- Error en el proceso env�o de estad�sticas. Error Recibido: " + e.getMessage());
			// Fin. Ricardo Rodriguez. Incidencia 3139. 18/12/2012
		} catch (OegamExcepcion e){
			// Ricardo Rodriguez. Incidencia 3139. 18/12/2012
			try{
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(new ColaBean(), ConstantesProcesos.EJECUCION_EXCEPCION,e.toString(), TipoProceso.Estadisticas.getNombreEnum() );
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(TipoProceso.Estadisticas.getValorEnum(), e.toString(), ConstantesProcesos.EJECUCION_EXCEPCION);
			}catch(Exception ex){
				log.error("ERROR: Fallo en la actualizaci�n de la �ltima ejecuci�n en ENVIO_DATA en " + ProcesosEnum.ESTADISTICAS.getNombreEnum());
			}
			log.info("Proceso " + ConstantesProcesos.PROCESO_ENVIO_ESTADISTICAS + " -- Error en el proceso env�o de estad�sticas. Error Recibido: " + e.getMessage());
			// Fin. Ricardo Rodriguez. Incidencia 3139. 18/12/2012
		}
		
	}
	
	/**
	 * Realiza los env�os de los correos, intentando controlar las posibles excepciones que pudieran dar cada uno de los env�os
	 */
	public void realizarEnvio(EstadisticasTiposEnum tipoEnvio) {
		
		ResultBean resultado = new ResultBean();
		ModeloEstadisticasCorreo modeloEstadisticasCorreo = new ModeloEstadisticasCorreo();
		try{
			switch(tipoEnvio){
				case CTIT:
					resultado = modeloEstadisticasCorreo.enviarMailEstadisticasCTIT();
				break;
				case MATE:
					resultado = modeloEstadisticasCorreo.enviarMailEstadisticasMATE();
				break;
				case DELEGACIONES:
					resultado = modeloEstadisticasCorreo.enviarMailEstadisticasCTITDelegaciones();
				break;
			}
			modeloEstadisticasCorreo.actualizaEnvioDataEstadisticas(resultado);
		} catch (OegamExcepcion e) {
			try{
				modeloEstadisticasCorreo.actualizaEnvioDataEstadisticas(resultado);
			} catch (Exception e1){
				log.error("Error al tratar de actualizar el ENVIO DATA de estad�sticas para " + tipoEnvio.getValorEnum() + ": " + e1.getMessage());
			}
		} catch (Exception e2){
			log.error("Error al tratar de enviar las estad�sticas de " + tipoEnvio.getValorEnum(), e2);
			try{
				modeloEstadisticasCorreo.actualizaEnvioDataEstadisticas(resultado);
			} catch (Exception e3){
				log.error("Error al tratar de actualizar el ENVIO DATA de estad�sticas para " + tipoEnvio.getValorEnum() + ": " + e3.getMessage());
			}
		}
			
	}
	
	@Override
	public void cambioNumeroInstancias(int numero) {
		log.info("Proceso " + ConstantesProcesos.PROCESO_ENVIO_ESTADISTICAS + " --  env�o de estad�sticas.");
	}

}