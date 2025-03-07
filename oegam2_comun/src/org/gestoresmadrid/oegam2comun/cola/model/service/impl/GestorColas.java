package org.gestoresmadrid.oegam2comun.cola.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.ProcesoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import procesos.enumerados.RetornoProceso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class GestorColas implements TriggerListener {
	private static final String RETORNO = "retorno";
	private static final String MENSAJE_TIEMPO_CORRECTO = "TIEMPO_CORRECTO";
	private static final String MENSAJE_TIEMPO_ERRONEO_RECUPERABLE="TIEMPO_ERRONEO_RECUPERABLE"; 
	private static final String MENSAJE_TIEMPO_ERRONEO_RECUPERABLE_TIMEOUT="TIEMPO_ERRONEO_RECUPERABLE_TIMEOUT";
	private static final String MENSAJE_TIEMPO_ERRONEO_NO_RECUPERABLE="TIEMPO_ERRONEO_NO_RECUPERABLE";
	private static final String MENSAJE_TIEMPO_SIN_DATOS="TIEMPO_SIN_DATOS";
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestorColas.class);
	private Scheduler planificador;
	private boolean activo;
	private HashMap<Integer, ProcesoDto> listaProcesos;
	
	@Autowired
	ServicioProcesos servicioProceso;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	public void iniciarGestorColas(){
		listaProcesos = new HashMap<Integer, ProcesoDto>();
		SchedulerFactory factoria = new StdSchedulerFactory();
		this.activo = true;
		try {
			planificador = factoria.getScheduler();
			if (planificador != null) {
				planificador.start();
				planificador.addGlobalTriggerListener(this);
			}
		} catch (SchedulerException e) {
			planificador = null;
		}
	}
	
	/**
	 * Devuelve una lista con información de todos los proceso gestionados
	 * @return
	 * @throws SchedulerException 
	 */
	public List<ProcesoDto> listarProcesos() {
		List<ProcesoDto> listaProcesosDto = new ArrayList<ProcesoDto>();
		if (listaProcesos != null && !listaProcesos.isEmpty()) {
			for (ProcesoDto proceso : listaProcesos.values()) {
				listaProcesosDto.add(proceso);
			}
		}
		return listaProcesosDto;
	}
	
	public List<ProcesoDto> listarProcesosOrdenados() {
		List<ProcesoDto> listaProcesosOrdenada = listarProcesos();
		Collections.sort(listaProcesosOrdenada,new Comparator<ProcesoDto>(){
			@Override
			public int compare(ProcesoDto pro1, ProcesoDto pro2) {
				return pro1.getOrden().compareTo(pro2.getOrden());
			}
		});
		return listaProcesosOrdenada;
	}
	

	/**
	 * Añade un nuevo proceso a la lista que gestiona el Gestor. 
	 * Además si se le indica así inicia todas las instancias asignadas al proceso
	 * @param proceso Identificativo de Proceso
	 * @param numColas Número de instancias que se ejecutarán simultáneamente del proceso
	 * @param arrancar Indicador de si se iniciano no las instancias en este momento
	 * @throws SchedulerException
	 */
	public synchronized void nuevoProceso(ProcesoDto proceso) throws SchedulerException {
		Boolean procesoActivo = (BigDecimal.ONE.compareTo(proceso.getEstado()) == 0) && activo;
		proceso.setActivo(procesoActivo);  //Se coloca como activo o no según lo esté el gestor
		listaProcesos.put(proceso.getOrden().intValue(), proceso);
		
		//Si el gestor no está activo no hago nada más.
		if ((!activo) || (!proceso.getActivo())) {
			return;
		}
		
		if (planificador.getJobDetail(proceso.getClase(), null) == null) {
			JobDetail trabajo = new JobDetail(proceso.getClase(), null, proceso.getClasse());
			//Se establece la duración a true para que no se elimine el job si todos los disparadores (instancias)
			//se detienen porque no tengan datos.
			trabajo.setDurability(true); 
			trabajo.getJobDataMap().put("proceso", proceso.getTipo());
			for (int i = 0; i < proceso.getHilosColas().intValue(); i++) {			
				Date startTime = utilesFecha.horaStringToDate(proceso.getHoraInicio());
				SimpleTrigger disparador = new SimpleTrigger();
				disparador.setStartTime(startTime!=null?startTime:new Date());
				disparador = estableceFechaFin(proceso, disparador); 
				disparador.setRepeatInterval(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);
				disparador.setName(generaNombreDisparador(proceso.getClase(), i + 1));
				disparador.getJobDataMap().put("indice", i + 1);
				if (i == 0) {
					planificador.scheduleJob(trabajo, disparador);
				} else {
					disparador.setJobName(trabajo.getName());
					planificador.scheduleJob(disparador);
				}
			}
		}
	}

	
	/**
	 * Detiene todas las instancias de un proceso
	 * Si una instancia se está ejecutando termina la ejecución antes de detenerse.
	 * Si una instancia está planificada se elimina 
	 */
	public synchronized void parar() throws SchedulerException {
		activo = false;
		log.info("Parando el gestor de procesos");
		for (ProcesoDto proceso: listaProcesos.values()) {
			pararProceso(proceso.getTipo().intValue());
		}
		planificador.shutdown(true);
	}
	
	/**
	 * Inicia todas las instancias de todos los procesos
	 * @throws SchedulerException
	 */
	public synchronized void arrancar() throws SchedulerException {
		activo = true;
		planificador.start();
		log.info("Arrancando el gestor de procesos");
		for (ProcesoDto proceso: listaProcesos.values()) {
			arrancarProceso(proceso.getTipo().intValue());
		}
	}
	
	/**
	 * Devuelve el número de instancias que actualmente tiene definidos 
	 * el proceso
	 * @param proceso Identifiactivo del proceso a consultar
	 * @throws SchedulerException
	 */
	public synchronized int getNumeroInstanciasProceso(int proceso) throws SchedulerException {
		log.info("getNumeroInstanciasProceso de proceso: " + proceso); 
		ProcesoDto elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			throw new SchedulerException("No existe el proceso solicitado");
		}
		return elProceso.getTipo().intValue();
	}

	/**
	 * Informa si un proceso está activo  
	 * el proceso
	 * @param proceso Identifiactivo del proceso a consultar
	 * @throws SchedulerException
	 */
	public synchronized boolean isProcesoActivo(Integer proceso) throws SchedulerException {
		ProcesoDto elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			throw new SchedulerException("No existe el proceso solicitado");
		}
		
		return elProceso.getActivo();
	}

	/**
	 * Informa si una instancia determinada de un proceso está activa  
	 * @param proceso Identifiactivo del proceso a consultar
	 * @throws SchedulerException
	 */
	public synchronized boolean isInstanciaProcesoActiva(Integer proceso, int instancia) throws SchedulerException {
		
		ProcesoDto elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			throw new SchedulerException("No existe el proceso solicitado");
		}
		
		if (instancia > elProceso.getHilosColas().intValue()) {
			return false;
		}
		
		//Para ver si está activa compruebo que hay un disparador con ese nombre programado
		SimpleTrigger disparador = (SimpleTrigger)planificador.getTrigger(generaNombreDisparador(elProceso.getClasse().getSimpleName(), instancia), null);
		if (disparador != null) {
			return true;
		}
		
		return false;
		
	}

	/**
	 * Detiene todas las instancias de un proceso
	 * @param proceso Identifiactivo del proceso a detener
	 * @throws SchedulerException
	 */
	public synchronized void pararProceso(Integer proceso) throws SchedulerException {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		log.info("pararProceso");
		ProcesoDto elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			return;
		}
		elProceso.setActivo(Boolean.FALSE);
		elProceso.setEstado(BigDecimal.ZERO);
		servicioProceso.actualizarProceso(elProceso);
		log.info("Parando el proceso " + elProceso.getDescripcion());
		
		//Elimino todos los disparadores asociados a este proceso
		planificador.deleteJob(elProceso.getClasse().getSimpleName(), null);
		listaProcesos.put(proceso.intValue(), elProceso);
	}

	/**
	 * Inicia todas las instancias de un proceso
	 * @param proceso Identificativo del proceso a iniciar
	 * @throws SchedulerException
	 */
	public synchronized void arrancarProceso(int proceso) throws SchedulerException {
		log.info("arrancar proceso");
		ProcesoDto elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			return;
		}
		
		//Elimino todos los disparadores asociados a este proceso
		planificador.deleteJob(elProceso.getClasse().getSimpleName(), null);
		
		elProceso.setActivo(Boolean.TRUE);
		elProceso.setEstado(BigDecimal.ONE);
		servicioProceso.actualizarProceso(elProceso);
		listaProcesos.put(proceso, elProceso);
		log.info("Arrancando el proceso " + elProceso.getDescripcion());
		if (planificador.getJobDetail(elProceso.getClasse().getSimpleName(), null) == null) {
			JobDetail trabajo = new JobDetail(elProceso.getClasse().getSimpleName(), null, elProceso.getClasse());
			trabajo.setDurability(true);
			trabajo.getJobDataMap().put("proceso", elProceso.getOrden());
			for (int i = 0; i < elProceso.getHilosColas().intValue(); i++) {
				Date startTime = utilesFecha.horaStringToDate(elProceso.getHoraInicio());
				SimpleTrigger disparador = new SimpleTrigger();
				disparador.setStartTime(startTime!=null?startTime:new Date());
				disparador.setRepeatInterval(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);
				disparador = estableceFechaFin(elProceso, disparador);
				disparador.setName(generaNombreDisparador(elProceso.getClasse().getSimpleName(), i + 1));
				disparador.getJobDataMap().put("indice", i + 1);
				if (i == 0) {
					planificador.scheduleJob(trabajo, disparador);
				} else {
					disparador.setJobName(trabajo.getName());
					planificador.scheduleJob(disparador);
				}
			}
		}
	}
	
	/**
	 *  Inicia una instancia de un proceso. Usado en los casos en los que una instancia se paró por falta de 
	 *  datos para volver a ejecutarse y han entrado datos.
	 *  @param proceso Identificativo del proceso que se quiere levantar
	 *  @param indice Número de instancia
	 */
	public synchronized void arrancarProcesoIndividual(int proceso, int indice) throws SchedulerException {
		ProcesoDto elProceso = listaProcesos.get(proceso);
		if ((elProceso == null) || (!elProceso.getActivo())) {
			log.info("No existe ningún proceso de ese tipo");
			return;
		}
		if (indice > elProceso.getHilosColas().intValue()) {
			log.info("El disparador " + indice + " es superior a lo configurado");
			return;
		}			
		JobDetail trabajo = planificador.getJobDetail(elProceso.getClasse().getSimpleName(), null);
		trabajo.getJobDataMap().put("proceso", elProceso.getOrden());
		
		Date startTime = utilesFecha.horaStringToDate(elProceso.getHoraInicio());
		
		SimpleTrigger disparador = (SimpleTrigger)planificador.getTrigger(generaNombreDisparador(elProceso.getClasse().getSimpleName(), indice), null);
		if (disparador != null) {
			log.info("El disparador " + indice + " ya está levantado");
			return;
		}
		disparador = new SimpleTrigger();
		disparador.setStartTime(startTime!=null?startTime:new Date());
		disparador = estableceFechaFin(elProceso, disparador);
		disparador.setName(generaNombreDisparador(elProceso.getClasse().getSimpleName(), indice + 1));
		disparador.getJobDataMap().put("indice", indice + 1);
		if (indice == 0) {
			planificador.scheduleJob(trabajo, disparador);
		} else {
			disparador.setJobName(trabajo.getName());
			planificador.scheduleJob(disparador);
		}
	}
	
	public void cambiarNumeroMaxIntentos(int proceso, BigDecimal maxIntentos) {
		ProcesoDto elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			return;
		}
		log.info("Cambiando el numMaximo de intentos de " + elProceso.getnIntentosMax() +  " a " + maxIntentos);
		if (elProceso.getnIntentosMax() == null){
			elProceso.setnIntentosMax(BigDecimal.ZERO);
		}
		if (maxIntentos!=null){
			if (maxIntentos.equals(elProceso.getnIntentosMax())){
				return;
			}
		}
		elProceso.setnIntentosMax(maxIntentos);
		servicioProceso.actualizarProceso(elProceso);
		listaProcesos.put(proceso, elProceso);
	}
	
	public void cambiarHoraFin(int tipoProceso, String horaFin) {
		ProcesoDto elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) 	{
			return;
		}
		log.info("Cambiando HoraFin de " + elProceso.getHoraFin() +  " a " + horaFin);
		if (elProceso.getHoraFin()==null){
			elProceso.setHoraFin("");
		}
		if(horaFin!=null){
			if (horaFin.equals(elProceso.getHoraFin())){
				return;
			}
		}
		elProceso.setHoraFin(horaFin);
		servicioProceso.actualizarProceso(elProceso);
		listaProcesos.put(tipoProceso, elProceso);
	}
		
	public void cambiarHoraInicio(int tipoProceso, String horaInicio) {
		ProcesoDto elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) 	{
			return;
		}
		log.info("Cambiando HoraInicio de " + elProceso.getHoraInicio() +  " a " + horaInicio);
		if (elProceso.getHoraInicio()==null) {
			elProceso.setHoraInicio("");
		}
		if(horaInicio!=null){
			if (horaInicio.equals(elProceso.getHoraInicio())) {
				return ;
			}
		}
		elProceso.setHoraInicio(horaInicio);
		servicioProceso.actualizarProceso(elProceso);
		listaProcesos.put(tipoProceso, elProceso);
	}
	
	/**
	 * Cambia el número de ejecuciones simultáneas de un proceso
	 *  
	 * @param proceso Proceso del que se quieren cambiar el número
	 * @param numero Nuevo número de procesos
	 * @throws SchedulerException
	 */
	public synchronized void cambiarNumeroEjecucionesProceso(int proceso, int numero) throws SchedulerException {
		ProcesoDto elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			return;
		}
		log.info("Cambiando el tamaño de " + elProceso.getHilosColas() +  " a " + numero);
		if (numero == elProceso.getHilosColas().intValue()) {
			return;
		}
		elProceso.setHilosColas(new Long(numero));
		servicioProceso.actualizarProceso(elProceso);
		listaProcesos.put(proceso, elProceso);
		boolean activo = elProceso.getActivo();
		if(activo) {
			pararProceso(proceso);
			arrancarProceso(proceso);
		}
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	/**
	 * Controla el resultado de la ejecución de una instancia y en función del valor de retorno decide el futuro 
	 * de la instancia. con cualquier resultado excepto RetornoProceso.SIN_DATOS la instancia se vuelve a planificar 
	 * con intervalos dependientes de la gravedad del error.
	 * Revisar los valores de TIEMPO_CORRECTO, TIEMPO_ERRONEO_RECUPERABLE y TIEMPO_ERRONEO_NO_RECUPERABLE
	 */
	@Override
	public void triggerComplete(Trigger arg0, JobExecutionContext arg1,
			int arg2) {
		RetornoProceso retorno = (RetornoProceso)arg1.getMergedJobDataMap().get(RETORNO);
		Long proceso = (Long) arg1.getMergedJobDataMap().get("proceso");
		int indice = arg1.getMergedJobDataMap().getIntValue("indice");
		ProcesoDto elProceso = listaProcesos.get(proceso.intValue());
		if (elProceso != null) {
			elProceso.setFechaUltimaEjecucion(new Date());
			listaProcesos.put(proceso.intValue(), elProceso);
		}
		
		if (arg0.getName()!=null) {
			 log.info("El disparador " + arg0.getName());
		} else { 
			log.error("trigger name era null");
		}
		if (retorno!=null) {
			if (retorno.name()!=null) {
			 log.info("ha terminado con retorno: " + retorno.name());
			} else {
				log.error("retorno era null");
			}
		}
		
		if (indice > elProceso.getHilosColas()) {
			log.info("El disparador " + arg0.getName() + " ya no tiene que ejecutarse porque hay menos colas");
			return;
		}
		//si ha ido mal genero un nuevo disparador que comienza un poco después
		SimpleTrigger disparador = new SimpleTrigger();
		Date startTime =null;
		if (elProceso.getHoraFin()==null)
			elProceso.setHoraFin("2359");
		Date dateHoraFin = utilesFecha.horaStringToDate(elProceso.getHoraFin());
		long longHoraFin = dateHoraFin.getTime(); 
		if (retorno.equals(RetornoProceso.CORRECTO)) {
			if (elProceso.getTiempoCorrecto()!=null && !elProceso.getTiempoCorrecto().equals(new BigDecimal(0))) {
				long longTiempoCorrecto= elProceso.getTiempoCorrecto().longValue()*1000L;
				long tiempoSiguienteEjecucion=System.currentTimeMillis()+longTiempoCorrecto; 
				if (tiempoSiguienteEjecucion>longHoraFin) {
					startTime = new Date(utilesFecha.horaStringToDate(elProceso.getHoraInicio()).getTime() + 86400*1000L); 
				} else{
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoCorrecto().longValue()*1000L); 
				}
			} else {
				startTime = new Date(System.currentTimeMillis() + Integer.parseInt(gestorPropiedades.valorPropertie(MENSAJE_TIEMPO_CORRECTO)) * 1000L);
			}
		} 
		if (retorno.equals(RetornoProceso.ERROR_RECUPERABLE)) {
			if (elProceso.getTiempoErroneoRecuperable() !=null && !elProceso.getTiempoErroneoRecuperable().equals(new BigDecimal(0))) {
				long longTiempoRecuperable = elProceso.getTiempoErroneoRecuperable().longValue()*1000L;
				long tiempoSiguienteEjecucion=System.currentTimeMillis()+longTiempoRecuperable; 
				if (tiempoSiguienteEjecucion>longHoraFin) {
					startTime = new Date(utilesFecha.horaStringToDate(elProceso.getHoraInicio()).getTime() + 86400*1000L); 
				} else {
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoErroneoRecuperable().longValue()*1000L);
				}
			} else {
				startTime = new Date(System.currentTimeMillis() + Integer.parseInt(gestorPropiedades.valorPropertie(MENSAJE_TIEMPO_ERRONEO_RECUPERABLE)) * 1000L);
			}
		}
		if (retorno.equals(RetornoProceso.ERROR_RECUPERABLE_TIMEOUT)) {
			if (elProceso.getTiempoErroneoRecuperable()!=null && !elProceso.getTiempoErroneoRecuperable().equals(new BigDecimal(0))) {
				long longTiempoRecuperable = elProceso.getTiempoErroneoRecuperable().longValue()*100L;
				long tiempoSiguienteEjecucion=System.currentTimeMillis()+longTiempoRecuperable; 
				if (tiempoSiguienteEjecucion>longHoraFin) {
					startTime = new Date(utilesFecha.horaStringToDate(elProceso.getHoraInicio()).getTime() + 86400*1000L); 
				} else {
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoErroneoRecuperable().longValue()*1000L);
				}
			} else {
				startTime = new Date(System.currentTimeMillis() + Integer.parseInt(gestorPropiedades.valorPropertie(MENSAJE_TIEMPO_ERRONEO_RECUPERABLE_TIMEOUT)) * 1000L);
			}
		}
		if (retorno.equals(RetornoProceso.ERROR_NO_RECUPERABLE)) {
			if (elProceso.getTiempoErroneoNoRecuperable()!=null && !elProceso.getTiempoErroneoNoRecuperable().equals(new BigDecimal(0))) {
				long longTiempoNoRecuperable = elProceso.getTiempoErroneoNoRecuperable().longValue()*1000L;
				long tiempoSiguienteEjecucion=System.currentTimeMillis()+longTiempoNoRecuperable; 
				if (tiempoSiguienteEjecucion>longHoraFin) {
					startTime = new Date(utilesFecha.horaStringToDate(elProceso.getHoraInicio()).getTime() + 86400*1000L); 
				} else { 
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoErroneoNoRecuperable().longValue()*1000L);
				}	
			} else {
				startTime = new Date(System.currentTimeMillis() + Integer.parseInt(gestorPropiedades.valorPropertie(MENSAJE_TIEMPO_ERRONEO_NO_RECUPERABLE)) * 1000L);
			}
		}
		if (retorno.equals(RetornoProceso.SIN_DATOS)) { 
			if (elProceso.getTiempoSinDatos()!=null && !elProceso.getTiempoSinDatos().equals(new BigDecimal(0))) {
				long longTiempoSinDatos = elProceso.getTiempoSinDatos().longValue()*1000L;
				long tiempoSiguienteEjecucion=System.currentTimeMillis()+longTiempoSinDatos; 
				if (tiempoSiguienteEjecucion>longHoraFin) {
					startTime = new Date(utilesFecha.horaStringToDate(elProceso.getHoraInicio()).getTime() + 86400*1000L); 
				} else {
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoSinDatos().longValue()*1000L);
				}
			} else {
				startTime = new Date(System.currentTimeMillis() + Integer.parseInt(gestorPropiedades.valorPropertie(MENSAJE_TIEMPO_SIN_DATOS)) * 1000L);
			}
		}
		disparador.setStartTime(startTime);
		disparador.setRepeatCount(0);
		disparador.setRepeatInterval(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);
		disparador.setName(arg0.getName());
		disparador.getJobDataMap().put("indice", arg1.getMergedJobDataMap().getInt("indice"));
		disparador.setJobName(arg0.getJobName());
		disparador = estableceFechaFin(elProceso, disparador);
		try {
			if (planificador.getTrigger(arg0.getName(), arg0.getGroup()) != null)  {
				planificador.unscheduleJob(arg0.getName(), arg0.getGroup());
			}
			planificador.scheduleJob(disparador);
		} catch (SchedulerException e)  {
			log.error(arg0.getJobName() + " -- " + e.getMessage());
		}
	}
	
	private SimpleTrigger estableceFechaFin(ProcesoDto elProceso, SimpleTrigger disparador) {
		return disparador;
	}

	/**
	 * Genera el nombre del disparador a partir del proceso y del índice de la instancia
	 * @param proceso
	 * @param indice
	 * @return
	 */
	private String generaNombreDisparador(String classProceso, int indice) {
		return "Disparador_" + indice + "_de_" + classProceso;
	}
	
	@Override
	public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
		
	}

	@Override
	public void triggerMisfired(Trigger arg0) {
		
	}

	@Override
	public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
		return false;
	}
	
	public void cambiarTiempoCorrecto(int tipoProceso,
		BigDecimal tiempoCorrecto) {
		ProcesoDto elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}
		log.info("Cambiando el tiempo Correcto " + elProceso.getTiempoCorrecto() +  " a " + tiempoCorrecto);
		if (elProceso.getTiempoCorrecto()==null) {
			elProceso.setTiempoCorrecto(new BigDecimal(gestorPropiedades.valorPropertie(MENSAJE_TIEMPO_CORRECTO)));
		}
		if (tiempoCorrecto!=null){
			if (tiempoCorrecto.equals(elProceso.getTiempoCorrecto())) {
				return;
			}
		}
		elProceso.setTiempoCorrecto(tiempoCorrecto);
		servicioProceso.actualizarProceso(elProceso);
		listaProcesos.put(tipoProceso, elProceso);
	}
	
	public void cambiarTiempoRecuperable(int tipoProceso,
		BigDecimal tiempoRecuperable) {
		ProcesoDto elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}
		log.info("Cambiando el tiempo Recuperable " + elProceso.getTiempoErroneoRecuperable() +  " a " + tiempoRecuperable);
		if (elProceso.getTiempoErroneoRecuperable()==null) {
			elProceso.setTiempoErroneoRecuperable(new BigDecimal(gestorPropiedades.valorPropertie(MENSAJE_TIEMPO_ERRONEO_RECUPERABLE)));
		}
		if (tiempoRecuperable!=null){
			if (tiempoRecuperable.equals(elProceso.getTiempoErroneoRecuperable())) {
				return;
			}
		}
		elProceso.setTiempoErroneoRecuperable(tiempoRecuperable);
		servicioProceso.actualizarProceso(elProceso);
		listaProcesos.put(tipoProceso, elProceso);
	}
	
	public void cambiarTiempoNoRecuperable(int tipoProceso,
		BigDecimal tiempoNoRecuperable) {
		ProcesoDto elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}
		log.info("Cambiando el tiempo Sin datos " + elProceso.getTiempoErroneoNoRecuperable() +  " a " + tiempoNoRecuperable);
		if (elProceso.getTiempoErroneoNoRecuperable()==null) {
			elProceso.setTiempoErroneoNoRecuperable(new BigDecimal(gestorPropiedades.valorPropertie(MENSAJE_TIEMPO_ERRONEO_NO_RECUPERABLE)));
		}
		if (tiempoNoRecuperable!=null) { 
			if (tiempoNoRecuperable.equals(elProceso.getTiempoErroneoNoRecuperable())) {
				return;
			}
		}
		elProceso.setTiempoErroneoNoRecuperable(tiempoNoRecuperable);
		servicioProceso.actualizarProceso(elProceso);
		listaProcesos.put(tipoProceso, elProceso);
	}
	
	public void cambiarTiempoSinDatos(int tipoProceso, BigDecimal tiempoSinDatos) {
		ProcesoDto elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}
		log.info("Cambiando el tiempo Sin datos " + elProceso.getTiempoSinDatos() +  " a " + tiempoSinDatos);
		if (elProceso.getTiempoSinDatos()==null){
			elProceso.setTiempoSinDatos(new BigDecimal(Integer.parseInt(gestorPropiedades.valorPropertie(MENSAJE_TIEMPO_SIN_DATOS))));
		}
		if (tiempoSinDatos!=null) {
			if (tiempoSinDatos.equals(elProceso.getTiempoSinDatos())) {
				return;
			}
		}
		elProceso.setTiempoSinDatos(tiempoSinDatos);
		servicioProceso.actualizarProceso(elProceso);
		listaProcesos.put(tipoProceso, elProceso);
	}

	public ProcesoDto getProcesoPorOrden(int ordenProceso) {
		return listaProcesos.get(ordenProceso);
	}

	public void setProcesoToLista(Integer ordenProceso, ProcesoDto procesoDto) {
		listaProcesos.put(ordenProceso,procesoDto);
	}

}
