package colas.procesos.gestor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import colas.procesos.IProceso;
import colas.procesos.ProcesoBase;
import procesos.beans.ProcesoBean;
import procesos.enumerados.RetornoProceso;
import procesos.modelo.ModeloProcesos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

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
	private HashMap<Integer, ProcesoBean> listaProcesos;

	private ModeloProcesos modeloProcesos;

	@Autowired
	UtilesFecha utilesFecha;

	public GestorColas() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		listaProcesos = new HashMap<Integer, ProcesoBean>();
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
	public ProcesoBean[] listarProcesos() throws SchedulerException {
		for (ProcesoBean proceso : listaProcesos.values()) {
			proceso.setEstadoInstancias(null);
			proceso.setNumActivos(0);
			StringBuilder titulo = new StringBuilder("<br/>");
			for (int j = 0; j < proceso.getNumero(); j++) {
				if ((SimpleTrigger)planificador.getTrigger(generaNombreDisparador(proceso, j + 1), null) == null) {
					titulo.append("  Instancia " + (j+1) + " : Parada.<br/>");
				} else {
					titulo.append("  Instancia " + (j+1) + " : Activa.<br/>");
					proceso.setNumActivos(proceso.getNumActivos() + 1);
				}
			}
			proceso.setEstadoInstancias(titulo.toString());
		}
		return listaProcesos.values().toArray(new ProcesoBean[0]);
	}

/*	public static ProcesoBean getProceso(int tipoProceso) {
		return getListaProcesos().get(tipoProceso);
	}*/

	public ProcesoBean[] listarProcesosOrdenados() throws SchedulerException {
		TreeMap<Integer, ProcesoBean> prueba = new TreeMap<Integer, ProcesoBean>(listaProcesos);
		return prueba.values().toArray(new ProcesoBean[0]);
	}

	/**
	 * Añade un nuevo proceso a la lista que gestiona el Gestor. 
	 * Además si se le indica así inicia todas las instancias asignadas al proceso
	 * @param proceso Identificativo de Proceso
	 * @param numColas Número de instancias que se ejecutarán simultáneamente del proceso
	 * @param arrancar Indicador de si se iniciano no las instancias en este momento
	 * @throws SchedulerException
	 */
	public synchronized void nuevoProceso(ProcesoBean proceso) throws SchedulerException {

		//Compruebo que la clase del proceso extienda la clase base ProcesoBase o AbstractProcesoBase.
		//En caso contrario lo rechazo
		if (!ProcesoBase.class.isAssignableFrom(proceso.getClase()) && !AbstractProcesoBase.class.isAssignableFrom(proceso.getClase())
				&& !AbstractProceso.class.isAssignableFrom(proceso.getClase())) {
			log.error("La clase " + proceso.getClase().getName() + " no extiende la clase base ProcesoBase ni AbstractProcesoBase necesaria para su correcta gestión");
			throw new SchedulerException("La clase " + proceso.getClase().getName() + " no extiende la clase base ProcesoBase ni AbstractProcesoBase necesaria para su correcta gestión");
		}

		//Me guardo el proceso en la lista si no está
//		if (listaProcesos.containsKey(proceso.getTipo())) {
//			throw new SchedulerException("El proceso ya está creado.");
//		}

		proceso.setActivo(activo && proceso.isActivo()); //Se coloca como activo o no según lo esté el gestor
		proceso.setNumero(proceso.getNumero());
		listaProcesos.put(proceso.getOrden(), proceso);

		//Si el gestor no está activo no hago nada más.
		if ((!activo) || (!proceso.isActivo())) {
			return;
		}

		if (planificador.getJobDetail(proceso.getClase().getSimpleName(), null) == null) {
			JobDetail trabajo = new JobDetail(proceso.getClase().getSimpleName(), null, proceso.getClase());
			//Se establece la duración a true para que no se elimine el job si todos los disparadores (instancias)
			//se detienen porque no tengan datos.
			trabajo.setDurability(true);
			trabajo.getJobDataMap().put("proceso", proceso.getTipo());
			for (int i = 0; i < proceso.getNumero(); i++) {

				Date startTime = utilesFecha.horaStringToDate(proceso.getHoraInicio());

				SimpleTrigger disparador = new SimpleTrigger();

				disparador.setStartTime(startTime!=null?startTime:new Date());
				disparador = estableceFechaFin(proceso, disparador);

				Integer intervalo = proceso.getIntervalo()!=null && !proceso.getIntervalo().equals("")?new Integer(proceso.getIntervalo()):null;
				//disparador.setRepeatInterval(intervalo!=null?intervalo.longValue()*1000L:SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				disparador.setRepeatInterval(intervalo!=null?intervalo.longValue()*1000L:SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);
				disparador.setName(generaNombreDisparador(proceso, i + 1));
//					disparador.setRepeatCount(proceso.getIntentosMax()!=null?proceso.getIntentosMax().intValue():0);

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
		for (ProcesoBean proceso: listaProcesos.values()) {
			pararProceso(proceso.getTipo());
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
		for (ProcesoBean proceso: listaProcesos.values()) {
			arrancarProceso(proceso.getTipo());
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
		ProcesoBean elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			throw new SchedulerException("No existe el proceso solicitado");
		}
		return elProceso.getTipo();
	}

	/**
	 * Informa si un proceso está activo
	 * el proceso
	 * @param proceso Identifiactivo del proceso a consultar
	 * @throws SchedulerException
	 */
	public synchronized boolean isProcesoActivo(int proceso) throws SchedulerException {
		ProcesoBean elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			throw new SchedulerException("No existe el proceso solicitado");
		}
		return elProceso.isActivo();
	}

	/**
	 * Informa si una instancia determinada de un proceso está activa
	 * @param proceso Identifiactivo del proceso a consultar
	 * @throws SchedulerException
	 */
	public synchronized boolean isInstanciaProcesoActiva(int proceso, int instancia) throws SchedulerException {

		ProcesoBean elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			throw new SchedulerException("No existe el proceso solicitado");
		}

		if (instancia > elProceso.getNumero()) {
			return false;
		}

		//Para ver si está activa compruebo que hay un disparador con ese nombre programado
		SimpleTrigger disparador = (SimpleTrigger)planificador.getTrigger(generaNombreDisparador(elProceso, instancia), null);
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
	public synchronized void pararProceso(int proceso) throws SchedulerException {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		log.info("pararProceso");
		ProcesoBean elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			return;
		}
		elProceso.setActivo(false);
		getModeloProcesos().guardarProceso(elProceso);
		log.info("Parando el proceso " + elProceso.getDescripcion());

		//Elimino todos los disparadores asociados a este proceso
		planificador.deleteJob(elProceso.getClase().getSimpleName(), null);

		// Quita el proceso de la lista de procesos arrancados
		// gestionListaProcesosArrancados(elProceso,true);
	}

	/**
	 * Inicia todas las instancias de un proceso
	 * @param proceso Identificativo del proceso a iniciar
	 * @throws SchedulerException
	 */
	public synchronized void arrancarProceso(int proceso) throws SchedulerException {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.

		log.info("arrancar proceso");

		ProcesoBean elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			return;
		}

		//Elimino todos los disparadores asociados a este proceso
		planificador.deleteJob(elProceso.getClase().getSimpleName(), null);

		elProceso.setActivo(true);
		getModeloProcesos().guardarProceso(elProceso);
		log.info("Arrancando el proceso " + elProceso.getDescripcion());
		if (planificador.getJobDetail(elProceso.getClase().getSimpleName(), null) == null) {
			JobDetail trabajo = new JobDetail(elProceso.getClase().getSimpleName(), null, elProceso.getClase());
			trabajo.setDurability(true);
			trabajo.getJobDataMap().put("proceso", elProceso.getOrden());
			for (int i = 0; i < elProceso.getNumero(); i++) {
				Date startTime = utilesFecha.horaStringToDate(elProceso.getHoraInicio());
				SimpleTrigger disparador = new SimpleTrigger();
				disparador.setStartTime(startTime!=null?startTime:new Date());
				disparador = estableceFechaFin(elProceso, disparador);
				Integer intervalo = elProceso.getIntervalo()!=null && !elProceso.getIntervalo().equals("")?new Integer(elProceso.getIntervalo()):null;
				//disparador.setRepeatInterval(intervalo!=null?intervalo.longValue()*1000L:SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				disparador.setRepeatInterval(intervalo!=null?intervalo.longValue()*1000L:SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);
				disparador.setName(generaNombreDisparador(elProceso, i + 1));
//					disparador.setRepeatCount(elProceso.getIntentosMax()!=null?elProceso.getIntentosMax().intValue():0);
				disparador.getJobDataMap().put("indice", i + 1);
				if (i == 0) {
					planificador.scheduleJob(trabajo, disparador);
				} else {
					disparador.setJobName(trabajo.getName());
					planificador.scheduleJob(disparador);
				}
			}
		}
		// Mete el proceso en la lista de procesos arrancados
		// gestionListaProcesosArrancados(elProceso,false);
	}

	/**
	 *  Inicia una instancia de un proceso. Usado en los casos en los que una instancia se paró por falta de 
	 *  datos para volver a ejecutarse y han entrado datos.
	 *  @param proceso Identificativo del proceso que se quiere levantar
	 *  @param indice Número de instancia
	 */
	public synchronized void arrancarProcesoIndividual(int proceso, int indice) throws SchedulerException {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		ProcesoBean elProceso = listaProcesos.get(proceso);
		if ((elProceso == null) || (!elProceso.isActivo())) {
			log.info("No existe ningún proceso de ese tipo");
			return;
		}

		//Si es un indice superior a lo que se permite, no se hace nada
		if (indice > elProceso.getNumero()) {
			log.info("El disparador " + indice + " es superior a lo configurado");
			return;
		}

		JobDetail trabajo = planificador.getJobDetail(elProceso.getClase().getSimpleName(), null);
		trabajo.getJobDataMap().put("proceso", elProceso.getOrden());

		Date startTime = utilesFecha.horaStringToDate(elProceso.getHoraInicio());

		SimpleTrigger disparador = (SimpleTrigger)planificador.getTrigger(generaNombreDisparador(elProceso, indice), null);
		if (disparador != null) {
			log.info("El disparador " + indice + " ya está levantado");
			return;
		}
		disparador = new SimpleTrigger();
		disparador.setStartTime(startTime!=null?startTime:new Date());
		disparador = estableceFechaFin(elProceso, disparador);
//			Integer intervalo = elProceso.getIntervalo()!=null && !elProceso.getIntervalo().equals("")?new Integer(elProceso.getIntervalo()):null;
		//disparador.setRepeatInterval(intervalo!=null?intervalo.longValue()*1000L:SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

//			disparador.setRepeatInterval(intervalo!=null?intervalo.longValue()*1000L:SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);//esta linea es la original
		disparador.setName(generaNombreDisparador(elProceso, indice + 1));
//			disparador.setRepeatCount(elProceso.getIntentosMax()!=null?elProceso.getIntentosMax().intValue():0);
		disparador.getJobDataMap().put("indice", indice + 1);
		if (indice == 0) {
			planificador.scheduleJob(trabajo, disparador);
		} else {
			disparador.setJobName(trabajo.getName());
			planificador.scheduleJob(disparador);
		}
	}

	public void cambiarNumeroMaxIntentos(int proceso, BigDecimal maxIntentos) {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		ProcesoBean elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			return;
		}

		log.info("Cambiando el numMaximo de intentos de " + elProceso.getIntentosMax() + " a " + maxIntentos);
		//Si el número es el mismo que ya tiene el proceso no hago nada
		if (elProceso.getIntentosMax()==null)
			elProceso.setIntentosMax(new BigDecimal(0));
		if (maxIntentos!=null && maxIntentos.equals(elProceso.getIntentosMax())) { // Recuperar el nummax de bbdd siempre.
			return;
		}

		elProceso.setIntentosMax(maxIntentos);
		getModeloProcesos().guardarProceso(elProceso);
	}

	public void cambiarHoraFin(int tipoProceso, String horaFin) {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		ProcesoBean elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}

		log.info("Cambiando HoraFin de " + elProceso.getHoraFin() + " a " + horaFin);
		//Si el número es el mismo que ya tiene el proceso no hago nada
		if (elProceso.getHoraFin()==null)
			elProceso.setHoraFin("");
		if(horaFin!=null && horaFin.equals(elProceso.getHoraFin())) { // Recuperar el nummax de bbdd siempre.
			return;
		}

		elProceso.setHoraFin(horaFin);
		getModeloProcesos().guardarProceso(elProceso);
	}

	public void cambiarHoraInicio(int tipoProceso, String horaInicio) {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		ProcesoBean elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}

		log.info("Cambiando HoraInicio de " + elProceso.getHoraInicio() + " a " + horaInicio);
		//Si el número es el mismo que ya tiene el proceso no hago nada
		if (elProceso.getHoraInicio()==null)
			elProceso.setHoraInicio("");
		if (horaInicio!=null && horaInicio.equals(elProceso.getHoraInicio())) { // Recuperar el nummax de bbdd siempre
			return;
		}

		elProceso.setHoraInicio(horaInicio);
		getModeloProcesos().guardarProceso(elProceso);
	}

	/**
	 * Cambia el número de ejecuciones simultáneas de un proceso
	 *
	 * @param proceso Proceso del que se quieren cambiar el número
	 * @param numero Nuevo número de procesos
	 * @throws SchedulerException
	 */
	public synchronized void cambiarNumeroEjecucionesProceso(int proceso, int numero) throws SchedulerException {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		ProcesoBean elProceso = listaProcesos.get(proceso);
		if (elProceso == null) {
			return;
		}

		log.info("Cambiando el tamaño de " + elProceso.getNumero() + " a " + numero);
		//Si el número es el mismo que ya tiene el proceso no hago nada
		if (numero == elProceso.getNumero()) {
			return;
		}

		elProceso.setNumero(numero);
		getModeloProcesos().guardarProceso(elProceso);
		// Se crea una cola para ese nuevo hilo de proceso.

		boolean activo = elProceso.isActivo();

		if(activo) {
			pararProceso(proceso);
		}

		try {
			if (IProceso.class.isAssignableFrom(elProceso.getClase())) {
				IProceso canonico = (IProceso)Class.forName(elProceso.getClase().getCanonicalName()).newInstance();
				canonico.cambioNumeroInstancias(numero);
			}
		} catch (Exception ex) {
			log.error("No ha sido posible gestionar el cambio de número de instancias");
		}

		if(activo) {
			arrancarProceso(proceso);
		}
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	/**
	 * Controla el resultado de la ejecución de una instancia y en función del valor de retorno decide el futuro 
	 * de la instancia. con cualquier resultado excepto RetornoProceso.SIN_DATOS la instancia se vuelve a planificar 
	 * con intervalos dependientes de la gravedad del error.
	 * Revisar los valores de TIEMPO_CORRECTO, TIEMPO_ERRONEO_RECUPERABLE y TIEMPO_ERRONEO_NO_RECUPERABLE
	 */
	public void triggerComplete(Trigger arg0, JobExecutionContext arg1, int arg2) {
		//log.info("trigger complete");
		RetornoProceso retorno = (RetornoProceso)arg1.getMergedJobDataMap().get(RETORNO);
		Integer proceso = arg1.getMergedJobDataMap().getIntValue("proceso");
		//if (proceso!=null) log.info("proceso: " + proceso);
		int indice = arg1.getMergedJobDataMap().getIntValue("indice");
		//log.info("indice: " + indice);
		ProcesoBean elProceso = listaProcesos.get(proceso);
		if (elProceso != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			java.util.Date ahora = new java.util.Date();
			String dateString = formatter.format(ahora);
			elProceso.setUltimaEjecucion(dateString);
		}

		if (arg0.getName()!=null)
			log.info("El disparador " + arg0.getName());
		else
			log.error("trigger name era null");

		if (retorno!=null) {
			if (retorno.name()!=null)
				log.info("Ha terminado con retorno: " + retorno.name());
			else
				log.error("retorno era null");
		}

		if (indice > elProceso.getNumero()) {
			log.info("El disparador " + arg0.getName() + " ya no tiene que ejecutarse porque hay menos colas");
			return;
		}
		// Si ha ido mal genero un nuevo disparador que comienza un poco después
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
				} else
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoCorrecto().longValue()*1000L);
			} else {
				startTime = new Date(System.currentTimeMillis() + getModeloProcesos().getTiempoPorDefecto(MENSAJE_TIEMPO_CORRECTO) * 1000L);
			}
		}
		if (retorno.equals(RetornoProceso.ERROR_RECUPERABLE)) {
			if (elProceso.getTiempoRecuperable()!=null && !elProceso.getTiempoRecuperable().equals(new BigDecimal(0))) {
				long longTiempoRecuperable = elProceso.getTiempoRecuperable().longValue()*1000L;
				long tiempoSiguienteEjecucion=System.currentTimeMillis()+longTiempoRecuperable;
				if (tiempoSiguienteEjecucion>longHoraFin) {
					startTime = new Date(utilesFecha.horaStringToDate(elProceso.getHoraInicio()).getTime() + 86400*1000L);
				} else
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoRecuperable().longValue()*1000L);
			} else {
				startTime = new Date(System.currentTimeMillis() + getModeloProcesos().getTiempoPorDefecto(MENSAJE_TIEMPO_ERRONEO_RECUPERABLE) * 1000L);
			}
		}
		if (retorno.equals(RetornoProceso.ERROR_RECUPERABLE_TIMEOUT)) {
			if (elProceso.getTiempoRecuperable()!=null && !elProceso.getTiempoRecuperable().equals(new BigDecimal(0))) {
				long longTiempoRecuperable = elProceso.getTiempoRecuperable().longValue()*100L;
				long tiempoSiguienteEjecucion=System.currentTimeMillis()+longTiempoRecuperable;
				if (tiempoSiguienteEjecucion>longHoraFin) {
					startTime = new Date(utilesFecha.horaStringToDate(elProceso.getHoraInicio()).getTime() + 86400*1000L);
				} else
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoRecuperable().longValue()*1000L);
			} else {
				startTime = new Date(System.currentTimeMillis() + getModeloProcesos().getTiempoPorDefecto(MENSAJE_TIEMPO_ERRONEO_RECUPERABLE_TIMEOUT) * 1000L);
			}
		}

		if (retorno.equals(RetornoProceso.ERROR_NO_RECUPERABLE)) {
			if (elProceso.getTiempoNoRecuperable()!=null && !elProceso.getTiempoNoRecuperable().equals(new BigDecimal(0))) {
				long longTiempoNoRecuperable = elProceso.getTiempoNoRecuperable().longValue()*1000L;
				long tiempoSiguienteEjecucion=System.currentTimeMillis()+longTiempoNoRecuperable;
				if (tiempoSiguienteEjecucion>longHoraFin) {
					startTime = new Date(utilesFecha.horaStringToDate(elProceso.getHoraInicio()).getTime() + 86400*1000L);
				} else
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoNoRecuperable().longValue()*1000L);
			} else {
				startTime = new Date(System.currentTimeMillis() + getModeloProcesos().getTiempoPorDefecto(MENSAJE_TIEMPO_ERRONEO_NO_RECUPERABLE) * 1000L);
			}
		}
		if (retorno.equals(RetornoProceso.SIN_DATOS)) {
			if (elProceso.getTiempoSinDatos()!=null && !elProceso.getTiempoSinDatos().equals(new BigDecimal(0))) {
				long longTiempoSinDatos = elProceso.getTiempoSinDatos().longValue()*1000L;
				long tiempoSiguienteEjecucion=System.currentTimeMillis()+longTiempoSinDatos;
				if (tiempoSiguienteEjecucion>longHoraFin) {
					startTime = new Date(utilesFecha.horaStringToDate(elProceso.getHoraInicio()).getTime() + 86400*1000L);
				} else
					startTime = new Date(System.currentTimeMillis() + elProceso.getTiempoSinDatos().longValue()*1000L);
			} else {
				startTime = new Date(System.currentTimeMillis() + getModeloProcesos().getTiempoPorDefecto(MENSAJE_TIEMPO_SIN_DATOS) * 1000L);
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
			if (planificador.getTrigger(arg0.getName(), arg0.getGroup()) != null) {
				planificador.unscheduleJob(arg0.getName(), arg0.getGroup());
			}
			planificador.scheduleJob(disparador);
		} catch (SchedulerException e) {
			log.error(arg0.getJobName() + " -- " + e.getMessage());
		}
	}

	private SimpleTrigger estableceFechaFin(ProcesoBean elProceso, SimpleTrigger disparador) {
		return disparador;
	}

	/**
	 * Genera el nombre del disparador a partir del proceso y del índice de la instancia
	 * @param proceso
	 * @param indice
	 * @return
	 */
	private String generaNombreDisparador(ProcesoBean proceso, int indice) {
		return "Disparador_" + indice + "_de_" + proceso.getClase().getSimpleName();
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
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		ProcesoBean elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}

		log.info("Cambiando el tiempo Correcto " + elProceso.getTiempoCorrecto() + " a " + tiempoCorrecto);
		//Si el número es el mismo que ya tiene el proceso no hago nada
		if (elProceso.getTiempoCorrecto()==null)
			elProceso.setTiempoCorrecto(new BigDecimal(getModeloProcesos().getTiempoPorDefecto(MENSAJE_TIEMPO_CORRECTO)));
		if (tiempoCorrecto!=null && tiempoCorrecto.equals(elProceso.getTiempoCorrecto())) {
			return;
		}
		elProceso.setTiempoCorrecto(tiempoCorrecto);
		getModeloProcesos().guardarProceso(elProceso);
	}

	public void cambiarTiempoRecuperable(int tipoProceso,
		BigDecimal tiempoRecuperable) {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		ProcesoBean elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}

		log.info("Cambiando el tiempo Recuperable " + elProceso.getTiempoRecuperable() + " a " + tiempoRecuperable);
		//Si el número es el mismo que ya tiene el proceso no hago nada
		if (elProceso.getTiempoRecuperable()==null)
			elProceso.setTiempoRecuperable(new BigDecimal(getModeloProcesos().getTiempoPorDefecto(MENSAJE_TIEMPO_ERRONEO_RECUPERABLE)));
		if (tiempoRecuperable!=null && tiempoRecuperable.equals(elProceso.getTiempoRecuperable())) {
			return;
		}

		elProceso.setTiempoRecuperable(tiempoRecuperable);
		getModeloProcesos().guardarProceso(elProceso);
	}

	public void cambiarTiempoNoRecuperable(int tipoProceso,
		BigDecimal tiempoNoRecuperable) {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		ProcesoBean elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}

		log.info("Cambiando el tiempo Sin datos " + elProceso.getTiempoNoRecuperable() + " a " + tiempoNoRecuperable);
		//Si el número es el mismo que ya tiene el proceso no hago nada
		if (elProceso.getTiempoNoRecuperable()==null)
			elProceso.setTiempoNoRecuperable(new BigDecimal(getModeloProcesos().getTiempoPorDefecto(MENSAJE_TIEMPO_ERRONEO_NO_RECUPERABLE)));
		if (tiempoNoRecuperable!=null && tiempoNoRecuperable.equals(elProceso.getTiempoNoRecuperable())) {
			return;
		}

		elProceso.setTiempoNoRecuperable(tiempoNoRecuperable);
		getModeloProcesos().guardarProceso(elProceso);
	}

	public void cambiarTiempoSinDatos(int tipoProceso,
		BigDecimal tiempoSinDatos) {
		//Si el proceso requerido no se encuentra en la lista me olvido.
		//Quizás sería mejor dar un error.
		ProcesoBean elProceso = listaProcesos.get(tipoProceso);
		if (elProceso == null) {
			return;
		}

		log.info("Cambiando el tiempo Sin datos " + elProceso.getTiempoSinDatos() + " a " + tiempoSinDatos);
		//Si el número es el mismo que ya tiene el proceso no hago nada
		if (elProceso.getTiempoSinDatos()==null)
			elProceso.setTiempoSinDatos(new BigDecimal(getModeloProcesos().getTiempoPorDefecto(MENSAJE_TIEMPO_SIN_DATOS)));
		if (tiempoSinDatos!=null && tiempoSinDatos.equals(elProceso.getTiempoSinDatos())) {
			return;
		}
		elProceso.setTiempoSinDatos(tiempoSinDatos);
		getModeloProcesos().guardarProceso(elProceso);
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloProcesos getModeloProcesos() {
		if (modeloProcesos == null) {
			modeloProcesos = new ModeloProcesos();
		}
		return modeloProcesos;
	}

	public void setModeloProcesos(ModeloProcesos modeloProcesos) {
		this.modeloProcesos = modeloProcesos;
	}

	public void resetearListaProcesos(){
		this.listaProcesos = new HashMap<Integer, ProcesoBean>();
	}

}