package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import procesos.enumerados.RetornoProceso;
import procesos.modelo.ModeloProcesos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

/**
 * Clase base para cualquier proceso que se quiera monitorizar y sea administrado por Spring Todos los procesos deben implementar dos metodos doExecute Llevará la lógica que deba realizar el proceso (envío webservice,....). getProceso Especifica que proceso es (Debe correponder con uno del enumerado
 * ProcesosEnum)
 */
public abstract class AbstractProcesoBase implements Job {
	private ILoggerOegam log = LoggerOegam.getLogger(this.getClass());
	protected static final String RETORNO = "retorno";

	private JobExecutionContext context;

	@Autowired
	private ServicioProcesos servicioProcesos;

	@Autowired
	protected ModeloSolicitud modeloSolicitud;

	@Autowired
	protected ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	private ModeloProcesos modeloProcesos = null;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		this.context = context;
		doExecute();
	}

	/* Inicio de Metodos abstractos que se deben implemnentar */
	protected abstract void doExecute() throws JobExecutionException;

	protected abstract String getProceso();

	/* Fin de Metodos abstractos que se deben implemnentar */

	/* Inicio de Gestion de cola */

	protected ColaBean tomarSolicitud() {
		// Hilo/cola en ejecución:
		Integer hiloActivo = context.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
		if (log.isInfoEnabled()) {
			log.info("Proceso " + getProceso() + " -- Buscando Solicitud en el hilo " + hiloActivo);
		}
		return servicioProcesos.tomarSolicitud(getProceso(), hiloActivo);
	}

	protected void finalizarTransaccionCorrecta(ColaBean cola, String resultado) {
		getModeloSolicitud().borrarSolicitud(cola.getIdEnvio(), resultado);
		peticionCorrecta();
	}

	protected void finalizarTransaccionErrorRecuperable(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(cola.getIdEnvio(), respuestaError);
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		getModeloSolicitud().borrarSolicitudExcep(cola.getIdEnvio(), respuestaError);
		errorNoRecuperable();
	}

	protected void actualizarUltimaEjecucion(ColaBean cola, String resultadoEjecucion, String excepcion) {
		servicioProcesos.actualizarUltimaEjecucion(cola, resultadoEjecucion, excepcion);
	}

	public void actualizarEjecucion(String proceso, String tipoEjecucion, String resultado, String numCola) {
		servicioProcesos.actualizarEjecucion(proceso, resultado, tipoEjecucion, numCola);
	}

	public boolean hayEnvioData(String proceso) {
		return servicioProcesos.hayEnvio(proceso);
	}

	protected BigDecimal getNumeroIntentos(String proceso) {
		String nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
		return getModeloProcesos().getIntentosMaximos(proceso, nodo);

	}

	protected BigDecimal getNumeroIntentosProceso2(String proceso) {
		String nodo = gestorPropiedades.valorPropertie("nombreHostSolicitudProcesos2");
		return getModeloProcesos().getIntentosMaximos(proceso, nodo);
	}

	protected Date ultimaFechaEnvioData(String proceso, String resultadoEjecucion) {
		return servicioProcesos.getUltimaFechaEnvioData(proceso, resultadoEjecucion);
	}

	protected void finalizarTransaccionErrorRecuperableConErrorServico(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(cola.getIdEnvio(), respuestaError);
			peticionRecuperable();
		} else {
			marcarSolicitudConErrorServicio(cola, respuestaError);
		}
	}

	protected void marcarSolicitudConErrorServicio(ColaBean cola, String respuestaError) {
		getModeloSolicitud().errorServicio(cola.getIdEnvio(), respuestaError);
		getModeloSolicitud().notificarErrorServicio(cola, respuestaError);
		getContext().getMergedJobDataMap().put(RETORNO, RetornoProceso.CORRECTO);
	}

	/* Fin de Gestion de cola */

	/* Inicio de Operaciones del contexto de quartz */

	public JobExecutionContext getContext() {
		return context;
	}

	public void setContext(JobExecutionContext context) {
		this.context = context;
	}

	protected void sinPeticiones() {
		context.getMergedJobDataMap().put(ConstantesProcesos.RETORNO, RetornoProceso.SIN_DATOS);
	}

	protected void peticionCorrecta() {
		context.getMergedJobDataMap().put(ConstantesProcesos.RETORNO, RetornoProceso.CORRECTO);
	}

	protected void peticionRecuperable() {
		context.getMergedJobDataMap().put(ConstantesProcesos.RETORNO, RetornoProceso.ERROR_RECUPERABLE);
	}

	protected void errorNoRecuperable() {
		context.getMergedJobDataMap().put(ConstantesProcesos.RETORNO, RetornoProceso.ERROR_NO_RECUPERABLE);
	}

	/* Fin de Operaciones del contexto de quartz */

	/* Inicio de Servicios y modelos */

	public ServicioProcesos getServicioProcesos() {
		return servicioProcesos;
	}

	public void setServicioProcesos(ServicioProcesos servicioProcesos) {
		this.servicioProcesos = servicioProcesos;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public ModeloProcesos getModeloProcesos() {
		if (modeloProcesos == null) {
			modeloProcesos = new ModeloProcesos();
		}
		return modeloProcesos;
	}
	/* Fin de Servicios y modelos */

}
