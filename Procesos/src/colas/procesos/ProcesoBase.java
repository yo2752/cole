package colas.procesos;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import escrituras.beans.ResultBean;
import procesos.enumerados.RetornoProceso;
import procesos.modelo.ModeloProcesos;
import trafico.modelo.ModeloTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;

/**
 * Clase base para cualquier proceso que se quiera monitorizar
 * 
 * Todos los procesos deben implementar dos metodos
 * execute                  Llevará la lógica que deba realizar el proceso (envío webservice,....).
 * (Interface Job)
 * 
 * cambioNumeroInstancias - Acciones a llevar a cabo cuando el número de instancias activas de
 * (interface IProceso)     un proceso cambia de número. El gestor llamará a este metodo cuando reciba
 *                          la orden de cambiar dicho valor
 * @author 
 * 
 * @deprecated use {@link AbstractProcesoBase} instead
 *
 */
@Deprecated
public abstract class ProcesoBase implements Job, IProceso {
	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoBase.class);

	protected static final String RETORNO = "retorno";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	protected ModeloSolicitud modeloSolicitud;
	
	@Autowired
	private ServicioProcesos servicioProcesos;

	@Autowired
	protected ServicioCola servicioCola;

	private ModeloProcesos modeloProcesos;

	private ModeloTrafico modeloTrafico;

	public ProcesoBase() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void tratarRecuperable(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuestaError) {
		if (solicitud == null) {
			return;
		}

		if ( solicitud.getProceso() != null && solicitud.getNumeroIntento() != null && solicitud.getIdEnvio() != null) {
			if (log.isInfoEnabled()) {
				log.info("tratar Recuperable:" + "proceso=" + solicitud.getProceso() + " cola=" + solicitud.getCola());
			}
			String nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
			BigDecimal numIntentos = getModeloProcesos().getIntentosMaximos(solicitud.getProceso(), nodo);
			if (solicitud.getNumeroIntento().intValue() < numIntentos.intValue()) {
				if (log.isInfoEnabled()){
					log.info("numero de intentos=" + solicitud.getNumeroIntento());
					log.info("numero maximo de intentos =" + numIntentos);
				}
				getModeloSolicitud().errorSolicitud(solicitud.getIdEnvio(), respuestaError);
				jobExecutionContext.getMergedJobDataMap().put(RETORNO, RetornoProceso.ERROR_RECUPERABLE);
			} else {
				log.info("superado el numero de intentos maximos permitidos para la cola");
				marcarSolicitudConErrorServicio(solicitud, respuestaError, jobExecutionContext);
			}
		} else {
			log.info("Ocurrio un error al recuperar la solicitud, se debe enviar la notificacion");
			marcarSolicitudConErrorServicio(solicitud, respuestaError, jobExecutionContext);
		}
	}

	protected void marcarSolicitudConErrorServicio(ColaBean cola, String respuestaError, JobExecutionContext jobExecutionContext) {
		getModeloSolicitud().errorServicio(cola.getIdEnvio(), respuestaError);
		getModeloSolicitud().notificarErrorServicio(cola,respuestaError);
		jobExecutionContext.getMergedJobDataMap().put(RETORNO, RetornoProceso.CORRECTO);
	}

	protected void sinPeticiones(JobExecutionContext jobExecutionContext) {
		jobExecutionContext.getMergedJobDataMap().put(ConstantesProcesos.RETORNO, RetornoProceso.SIN_DATOS);
	}

	protected void peticionCorrecta(JobExecutionContext jobExecutionContext){
		jobExecutionContext.getMergedJobDataMap().put(ConstantesProcesos.RETORNO, RetornoProceso.CORRECTO); 
	}

	protected void peticionNoRecuperable(JobExecutionContext jobExecutionContext, ResultBean resultBean, ColaBean cola) {
		// Ha ocurrido un error recuperando la petición
		jobExecutionContext.getMergedJobDataMap().put(ConstantesProcesos.RETORNO, RetornoProceso.ERROR_NO_RECUPERABLE);
	}

	protected void errorNoRecuperable(JobExecutionContext jobExecutionContext) {
		// Ha ocurrido un error recuperando la petición
		jobExecutionContext.getMergedJobDataMap().put(ConstantesProcesos.RETORNO, RetornoProceso.ERROR_NO_RECUPERABLE);
	}

	protected void finalizarTransaccionConAccionNoRecuperable(ColaBean solicitud,	String respuestaError, JobExecutionContext jobExecutionContext) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(),respuestaError);
		errorNoRecuperable(jobExecutionContext); 
	}

	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean solicitud,	String respuestaError, JobExecutionContext jobExecutionContext) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		getModeloTrafico().cambiarEstadoTramite(respuestaError, EstadoTramiteTrafico.Finalizado_Con_Error, solicitud.getIdTramite(), solicitud.getIdUsuario()); 
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(),respuestaError);
		errorNoRecuperable(jobExecutionContext); 
	}

	protected void finalizarTransaccionConError(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuestaError) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		log.info("finalizar Transaccion con error");
		getModeloTrafico().cambiarEstadoTramite(respuestaError, EstadoTramiteTrafico.Finalizado_Con_Error, solicitud.getIdTramite(), solicitud.getIdUsuario());
		errorNoRecuperable(jobExecutionContext);	
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(),ConstantesProcesos.OK);
	}

	public void actualizarEjecucion(String proceso, String tipoEjecucion, String resultado, String numCola) {
		servicioProcesos.actualizarEjecucion(proceso, resultado, tipoEjecucion, numCola);
	}

	// Mantis 17570. David Sierra. Se actualiza el trmaite con la respuesta para mostrase en Resumen
	protected void finalizarTransaccionConAccionNoRecuperableMateEitv(ColaBean solicitud, String respuestaError, JobExecutionContext jobExecutionContext) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		getModeloTrafico().actualizarRespuesta(respuestaError, solicitud.getIdTramite());
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(),respuestaError);
		errorNoRecuperable(jobExecutionContext); 
	}

	/* **************************************************************** */
	/* MODELOS ******************************************************** */
	/* **************************************************************** */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public ModeloProcesos getModeloProcesos() {
		if (modeloProcesos == null) {
			modeloProcesos = new ModeloProcesos();
		}
		return modeloProcesos;
	}

	public void setModeloProcesos(ModeloProcesos modeloProcesos) {
		this.modeloProcesos = modeloProcesos;
	}

	public ServicioProcesos getServicioProcesos() {
		return servicioProcesos;
	}

}