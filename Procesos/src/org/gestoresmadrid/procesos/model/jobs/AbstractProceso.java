package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import colas.constantes.ConstantesProcesos;
import procesos.enumerados.RetornoProceso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Clase base para cualquier proceso que se quiera monitorizar y sea administrado por Spring Todos los procesos deben implementar dos metodos doExecute Llevará la lógica que deba realizar el proceso (envío webservice,....). getProceso Especifica que proceso es (Debe correponder con uno del enumerado
 * ProcesosEnum)
 */
public abstract class AbstractProceso implements Job {

	private ILoggerOegam log = LoggerOegam.getLogger(this.getClass());

	protected static final String RETORNO = "retorno";

	private JobExecutionContext context;

	@Autowired
	private ServicioProcesos servicioProcesos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
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

	protected ColaDto tomarSolicitud() {
		// Hilo/cola en ejecución:
		Integer hiloActivo = context.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
		if (log.isInfoEnabled()) {
			log.info("Proceso " + getProceso() + " -- Buscando Solicitud en el hilo " + hiloActivo);
		}
		return servicioProcesos.tomarSolicitudNuevo(getProceso(), hiloActivo);
	}

	protected void finalizarTransaccionCorrecta(ColaDto cola, String resultado) {
		servicioProcesos.borrarSolicitud(cola.getIdEnvio(), resultado, cola.getProceso(), cola.getNodo());
		peticionCorrecta();
	}

	protected void finalizarTransaccionErrorRecuperable(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			servicioProcesos.errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

	protected void finalizarTransaccionConErrorNoRecuperable(ColaDto cola, String respuestaError) {
		servicioProcesos.borrarSolicitud(cola.getIdEnvio(), respuestaError, cola.getProceso(), cola.getNodo());
		errorNoRecuperable();
	}

	protected void actualizarUltimaEjecucion(ColaDto cola, String resultadoEjecucion, String excepcion) {
		servicioProcesos.actualizarUltimaEjecucionNuevo(cola, resultadoEjecucion, excepcion);
	}

	public void actualizarEjecucion(String proceso, String respuesta, String resultadoEjecucion, String numCola) {
		servicioProcesos.actualizarEjecucion(proceso, respuesta, resultadoEjecucion, numCola);
	}

	public boolean hayEnvioData(String proceso) {
		return servicioProcesos.hayEnvio(proceso);
	}

	protected BigDecimal getNumeroIntentos(String proceso) {
		String nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
		return servicioProcesos.getIntentosMaximos(proceso, nodo);
	}

	protected Date ultimaFechaEnvioData(String proceso, String resultadoEjecucion) {
		return servicioProcesos.getUltimaFechaEnvioData(proceso, resultadoEjecucion);
	}

	protected void finalizarTransaccionErrorRecuperableConErrorServico(ColaDto cola, String respuestaError) {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getnIntento().intValue() < numIntentos.intValue()) {
			errorSolicitud(cola.getIdEnvio());
			peticionRecuperable();
		} else {
			marcarSolicitudConErrorServicio(cola, respuestaError);
		}
	}

	protected void errorSolicitud(Long idEnvio) {
		servicioProcesos.errorSolicitud(idEnvio);
	}

	protected void marcarSolicitudConErrorServicio(ColaDto cola, String respuestaError) {
		servicioProcesos.errorServicio(cola.getIdEnvio(), respuestaError);
		servicioProcesos.notificarErrorServicioNuevo(cola, respuestaError);
		getContext().getMergedJobDataMap().put(RETORNO, RetornoProceso.CORRECTO);
	}

	/* Fin de Gestion de cola */

	/* Inicio Otros métodos */

	protected String getMessageException(Throwable e) {
		String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
		if (messageException.length() > 500) {
			messageException = messageException.substring(0, 500);
		}
		return messageException;
	}

	/* Fin Otros métodos */

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
}
