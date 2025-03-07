package colas.procesos;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import colas.procesos.utiles.UtilesProcesos;
import trafico.servicio.implementacion.ServicioEitvImpl;
import trafico.servicio.interfaz.ServicioEitvInt;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;

public class ProcesoInfoWS extends ProcesoBase {
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoInfoWS.class);
	private Integer hiloActivo;
	private ServicioEitvInt servicioEITV;
	private UtilesProcesos utilesProcesos;

	@Autowired
	private ServicioVehiculosPrematriculados servicioVehiculosPrematriculados;

	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
		ColaBean solicitud = new  ColaBean(); 
		try {
			log.info(ConstantesProcesos.PROCESO_INFO_WS + " -- INICIO EJECUCION --");
			log.info(ConstantesProcesos.PROCESO_INFO_WS + " -- Buscando Solicitud");
			solicitud = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_INFO_WS, hiloActivo.toString());
			//COMPROBAMOS DE QUE WEBSERVICE ES LA SOLICITUD QUE SE QUIERE PROCESAR
			// MateEITV.
			if (solicitud.getXmlEnviar().equals(ConstantesProcesos.PROCESO_CONSULTAEITV)){
				construirXmlSolicitudEitv(solicitud);
				log.info(ConstantesProcesos.PROCESO_INFO_WS + " -- Solicitud " + ConstantesProcesos.PROCESO_CONSULTAEITV + " encontrada, llamando a WS");
				getUtilesProcesos().finalizarGenerarXmlEitvOk(jobExecutionContext,solicitud);
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_INFO_WS);
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, null);
				log.info(ConstantesProcesos.PROCESO_INFO_WS + " -- ejecutado correctamente");
			} else if (solicitud.getXmlEnviar().equals(ProcesosEnum.VEHICULO_PREMATICULADO_DATOS_EITV.getNombreEnum())){
				construirXmlSolicitudEitvPrematriculado(solicitud);
				log.info(ConstantesProcesos.PROCESO_INFO_WS + " -- Solicitud " + ConstantesProcesos.PROCESO_CONSULTAEITV + " encontrada, llamando a WS");
				getUtilesProcesos().finalizarGenerarXmlEitvPrematriculadosOk(jobExecutionContext,solicitud);
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_INFO_WS);
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, null);
				log.info(ConstantesProcesos.PROCESO_INFO_WS + " --  ejecutado correctamente");
			}
			peticionCorrecta(jobExecutionContext);
		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion){
			sinPeticiones(jobExecutionContext);
			log.info(sinSolicitudesExcepcion.getMensajeError1());
		} catch (OegamExcepcion oegamEx){
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ oegamEx.getMensajeError1());
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,"OegamExcepcion",ConstantesProcesos.PROCESO_INFO_WS);
			//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, "OegamExcepcion");
			log.logarOegamExcepcion(oegamEx.getMensajeError1(), oegamEx);
		} catch (Exception e){
			log.error("Error ejecucion proceso InfoWS", e); 
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,"Exception",ConstantesProcesos.PROCESO_INFO_WS);
			//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, "Exception");
			tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ e.getMessage());
		} finally {
			log.info(ConstantesProcesos.PROCESO_INFO_WS + " -- FIN EJECUCION --");
		}
	}

	@Override
	public void cambioNumeroInstancias(int numero) { 
		log.info("Ejecucion cambio numero de instancias" + ConstantesProcesos.PROCESO_INFO_WS);
	}

	// MÉTODOS PRIVADOS

	private void construirXmlSolicitudEitv(ColaBean solicitudInfoWs) {
		getServicioEITV().generarSolicitudXmlEitv(solicitudInfoWs.getIdTramite());
	}

	private void construirXmlSolicitudEitvPrematriculado(ColaBean solicitudInfoWs) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		servicioVehiculosPrematriculados.generarSolicitudXml(solicitudInfoWs.getIdTramite().longValue());
	}

	/* ************************************************************* */
	/* MODELOS ***************************************************** */
	/* ************************************************************* */

	public ServicioEitvInt getServicioEITV() {
		if (servicioEITV == null) {
			servicioEITV = new ServicioEitvImpl();
		}
		return servicioEITV;
	}

	public void setServicioEITV(ServicioEitvInt servicioEITV) {
		this.servicioEITV = servicioEITV;
	}

	public UtilesProcesos getUtilesProcesos() {
		if (utilesProcesos == null) {
			utilesProcesos = new UtilesProcesos();
		}
		return utilesProcesos;
	}

	public void setUtilesProcesos(UtilesProcesos utilesProcesos) {
		this.utilesProcesos = utilesProcesos;
	}

	public ServicioVehiculosPrematriculados getServicioVehiculosPrematriculados() {
		return servicioVehiculosPrematriculados;
	}

	public void setServicioVehiculosPrematriculados(ServicioVehiculosPrematriculados servicioVehiculosPrematriculados) {
		this.servicioVehiculosPrematriculados = servicioVehiculosPrematriculados;
	}

}