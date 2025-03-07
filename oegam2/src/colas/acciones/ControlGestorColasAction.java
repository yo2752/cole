package colas.acciones;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import colas.procesos.gestor.GestorColas;
import general.acciones.ActionBase;
import procesos.beans.ProcesoBean;
import procesos.modelo.ModeloProcesos;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ControlGestorColasAction extends ActionBase implements ServletRequestAware {
	
	/* INICIO CONSTANTES */
	
	public static final String GESTOR = "Gestor";
	private static final String LISTAR = "listar";
	private static final String DATA = "data";
	
	/* FIN CONSTANTES */
	
	/* INICIO ATRIBUTOS */
	
	private HttpServletRequest servletRequest; // para ajax
	private int tipoProceso;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
	private Date fechaActualizacion;
	private ProcesoBean[] listaProcesos = new ProcesoBean[0];
	private int numero;
	private BigDecimal intentos;
	//private String intervalo;
	private String horaInicio;
	private String horaFin;
	
	private BigDecimal tiempoCorrecto; 
	private BigDecimal tiempoRecuperable;
	private BigDecimal tiempoNoRecuperable;
	private BigDecimal tiempoSinDatos;
	
	private int indice;
	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ControlGestorColasAction.class);
	private List<EnvioDataVO> listaEnvioData; 
	private String nombreProceso;
	private String patron;
	// Mantis 13692
	private String patronSeleccionado;	
	List <ProcesoBean> listaProcesoBean;
	
	private String passValidado;
	private String password;
	
	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;
	
	/* FIN ATRIBUTOS */
	
	private ModeloProcesos modeloProcesos;

	@Autowired
	private ServicioProcesos servicioProcesos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	/* INICIO MÉTODOS LÓGICOS */
	public String parar() throws Throwable {
		log.info("parar"); 
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.parar();
			listaProcesos =  gestor.listarProcesosOrdenados();
		}
		
		return LISTAR;
	}

	
	public String pararProceso() throws Throwable {
		log.info("pararProceso"); 
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.pararProceso(tipoProceso);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}
		
		// Mantis 13692: David Sierra: Filtrado de la tabla de procesos en funcion del patron que se seleccione en el combo de patrones
		
		// A la recuperacion por properties se le pasa el patron seleccionado en el combo
		String procesosPatron = gestorPropiedades.valorPropertie("procesos.patron." + patronSeleccionado);
		// Para que no se pierda el elemento seleccionado en el combro
		setPatron(patronSeleccionado);
		
		if(procesosPatron==null || procesosPatron.equals("")){
			procesosPatron = "TODOS";
		}
		// Si en el combo no se ha seleccionado el elemento TODOS
		if (!procesosPatron.equals("TODOS")) {
			
			listaProcesoBean = new ArrayList<ProcesoBean>();
			// Se recorre el array con todos los procesos y se incluyen en la lista los que su nombre coincida con el patron seleccionado 
			for (int i = 0; i< listaProcesos.length; i++) {	
				
				if(procesosPatron.contains(listaProcesos[i].getNombre())) {
					listaProcesoBean.add(listaProcesos[i]);
				} 
			}			
			// Lista vacia			
			if (listaProcesoBean.size() == 0) {
				listaProcesos =  gestor.listarProcesosOrdenados();
			}
			else {
				listaProcesos = new ProcesoBean[listaProcesoBean.size()];
				// Al array listaProcesos se le asignan los valores de la lista
				for(int i =0; i < listaProcesoBean.size(); i++) {
					 listaProcesos[i]=listaProcesoBean.get(i);
				}
			}			
		// En el combo se ha seleccionado el elemento TODOS
		}else {					
				listaProcesos =  gestor.listarProcesosOrdenados();
		}
		// Fin Mantis
		
		return LISTAR;
	}

	public String actualizar() throws Throwable{

		log.info("actualizar procesos"); 
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) 
		{
			gestor = new GestorColas();
			ArrayList <ProcesoBean> procesos = getModeloProcesos().obtenerConfiguracionDesdeBD();

			for (Iterator<?> iterator = procesos.iterator(); iterator.hasNext();) 
			{
				ProcesoBean procesoBean = (ProcesoBean) iterator.next();
				try{
					gestor.nuevoProceso(procesoBean);
				}catch(SchedulerException se){
					// Ese proceso ya está en el gestor.
					continue;
				}
			}
			
			listaProcesos =  gestor.listarProcesosOrdenados();
			servletRequest.getSession().getServletContext().setAttribute(GESTOR, gestor);						
			
			// Mantis 13692: David Sierra: Filtrado de la tabla de procesos en funcion del patron que se seleccione en el combo de patrones
			
			// A la recuperacion por properties se le pasa el patron seleccionado en el combo
			String procesosPatron = gestorPropiedades.valorPropertie("procesos.patron." + patronSeleccionado);
			// Para que no se pierda el elemento seleccionado en el combo
			setPatron(patronSeleccionado);
						
			if(procesosPatron==null || procesosPatron.equals("")){
				procesosPatron = "TODOS";
			}
			// Si en el combo no se ha seleccionado el elemento TODOS
			if (!procesosPatron.equals("TODOS")) {
				
				listaProcesoBean = new ArrayList<ProcesoBean>();
				// Se recorre el array con todos los procesos y se incluyen en la lista los que su nombre coincida con el patron seleccionado 
				for (int i = 0; i< listaProcesos.length; i++) {	
					
					if(procesosPatron.contains(listaProcesos[i].getNombre())) {
						listaProcesoBean.add(listaProcesos[i]);
					} 
				}				
				// Lista vacia			
				if (listaProcesoBean.size() == 0) {
					listaProcesos =  gestor.listarProcesosOrdenados();
				}
				else {
					listaProcesos = new ProcesoBean[listaProcesoBean.size()];
					// Al array listaProcesos se le asignan los valores de la lista
					for(int i =0; i < listaProcesoBean.size(); i++) {
						 listaProcesos[i]=listaProcesoBean.get(i);
					}
				}			
			// En el combo se ha seleccionado el elemento TODOS
			}else {					
					listaProcesos =  gestor.listarProcesosOrdenados();
			}
		}
		// Fin Mantis
		return LISTAR;

	}
	
	public String arrancarProceso() throws Throwable {
		log.info("arrancarProceso");
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.arrancarProceso(tipoProceso);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}
		// Mantis 13692: David Sierra: Filtrado de la tabla de procesos en funcion del patron que se seleccione en el combo de patrones
		
		// A la recuperacion por properties se le pasa el patron seleccionado en el combo
		String procesosPatron = gestorPropiedades.valorPropertie("procesos.patron." + patronSeleccionado);
		// Para que no se pierda el elemento seleccionado en el combo
		setPatron(patronSeleccionado);
		
		if(procesosPatron==null || procesosPatron.equals("")){
			procesosPatron = "TODOS";
		}
		// Si en el combo no se ha seleccionado el elemento TODOS
		if (!procesosPatron.equals("TODOS")) {
			
			listaProcesoBean = new ArrayList<ProcesoBean>();
			// Se recorre el array con todos los procesos y se incluyen en la lista los que su nombre coincida con el patron seleccionado 
			for (int i = 0; i< listaProcesos.length; i++) {	
				
				if(procesosPatron.contains(listaProcesos[i].getNombre())) {
					listaProcesoBean.add(listaProcesos[i]);
				} 
			}		
			// Lista vacia			
			if (listaProcesoBean.size() == 0) {
				listaProcesos =  gestor.listarProcesosOrdenados();
			}
			else {
				listaProcesos = new ProcesoBean[listaProcesoBean.size()];
				// Al array listaProcesos se le asignan los valores de la lista
				for(int i =0; i < listaProcesoBean.size(); i++) {
					 listaProcesos[i]=listaProcesoBean.get(i);
				}
			}			
		// En el combo se ha seleccionado el elemento TODOS
		}else {					
				listaProcesos =  gestor.listarProcesosOrdenados();
		}
		// Fin Mantis
		return LISTAR;
	}

	public String arrancarProcesoIndividual() throws Throwable {
		log.info("arrancarProcesoIndividual");
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.arrancarProcesoIndividual(tipoProceso, indice);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}	
		return LISTAR;
	}

	public String arrancar() throws Throwable {
		log.info("arrancar"); 
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.arrancar();
			listaProcesos =  gestor.listarProcesosOrdenados();
		}
		
		return LISTAR;
	}
	
	
	public String modificar() throws Throwable{
		cambioIntentosMax();
		cambioNumero();
		//cambioIntervalo();
		cambioHoraInicio();
		cambioHoraFin();
		cambioTiempoCorrecto();
		cambioTiempoRecuperable();
		cambioTiempoNoRecuperable();
		cambioTiempoSinDatos();
		
		
		return LISTAR; 
		
	}
	
	/*public String cambioIntervalo() throws Throwable {
		log.info("cambioIntervalo"); 
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.cambiarIntervalo(tipoProceso, intervalo);
			listaProcesos =  gestor.listarProcesos();
		}	
		return LISTAR;
	}*/
	
	
	public String cambioNumero() throws Throwable {
		log.info("cambioNumero"); 
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.cambiarNumeroEjecucionesProceso(tipoProceso, numero);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}	
		return LISTAR;
	}
		
	public String lista() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return LISTAR;
	}
	
	public String comprobarPassword() throws Throwable {
		String passwordPropiedades = gestorPropiedades.valorPropertie("gestion.procesos.password");
		if (passwordPropiedades != null && passwordPropiedades.equals(password)) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
			listaProcesos = new ProcesoBean[0];
			fechaActualizacion = new Date();
			GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
			if (gestor != null) {
				listaProcesos =  gestor.listarProcesosOrdenados();
			}
			return LISTAR;
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
			return LISTAR;
		}
	}
	
	public String cambioIntentosMax() throws SchedulerException {
		log.info("cambioIntentosMax");
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.cambiarNumeroMaxIntentos(tipoProceso, intentos);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}	
		return LISTAR;
	}
	
	
	public String cambioHoraInicio() throws SchedulerException {
		log.info("cambioHoraInicio");
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.cambiarHoraInicio(tipoProceso, horaInicio);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}	
		return LISTAR;
	}
	
	public String cambioHoraFin() throws SchedulerException {
		log.info("cambioHoraFin");
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.cambiarHoraFin(tipoProceso, horaFin);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}	
		return LISTAR;
	}
	
	
	public String cambioTiempoCorrecto() throws SchedulerException {
		log.info("cambioTiempoCorrecto");
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.cambiarTiempoCorrecto(tipoProceso, tiempoCorrecto);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}	
		return LISTAR;
	}
	
	
	public String cambioTiempoRecuperable() throws SchedulerException {
		log.info("cambioTiempoRecuperable");
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.cambiarTiempoRecuperable(tipoProceso, tiempoRecuperable);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}	
		return LISTAR;
	}
	
	
	
	public String cambioTiempoNoRecuperable() throws SchedulerException {
		log.info("cambioTiempoRecuperable");
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.cambiarTiempoNoRecuperable(tipoProceso, tiempoNoRecuperable);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}	
		return LISTAR;
	}
	
	
	public String cambioTiempoSinDatos() throws SchedulerException {
		log.info("cambioTiempoRecuperable");
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			gestor.cambiarTiempoSinDatos(tipoProceso, tiempoSinDatos);
			listaProcesos =  gestor.listarProcesosOrdenados();
		}	
		return LISTAR;
	}
	
	
	/**
	 * Metodo que nos devuelve la lista de procesos que tengan la ejecucion correcta
	 * @return
	 * @throws Throwable
	 */
	public String envioData() throws Throwable{
		
		log.info("envioData"); 
		listaEnvioData = ejecucionesProcesosDAO.listaEnvioData(ConstantesProcesos.EJECUCION_CORRECTA);
		setListaEnvioData(listaEnvioData);
		getSession().put("listaEnvioData", listaEnvioData);
		log.debug("fin envioData");
		return DATA;
	}

	public String envioDataHoy() throws Throwable {
		try {
			ejecucionesProcesosDAO.cambiarFechaEnvioData(nombreProceso, "", ConstantesProcesos.EJECUCION_CORRECTA, "Hoy");
		} catch (Exception ex) {
			log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.FIRSTMATENUEVO.getNombreEnum());
		}
		return envioData();

	}

	public String envioDataAnterior() throws Throwable {
		try {
			ejecucionesProcesosDAO.cambiarFechaEnvioData(nombreProceso, "", ConstantesProcesos.EJECUCION_CORRECTA, "Anterior");
		} catch (Exception ex) {
			log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.FIRSTMATENUEVO.getNombreEnum());
		}
		return envioData();
	}
	
	/**
	 * Inicia la lista de procesos según el patrón seleccionado desde la pantalla de procesos, y recarga el listado
	 * @return listar
	 */
	public String iniciarLista(){
		String nodo = null;
		String procesosPatron = null;
		nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
		procesosPatron =gestorPropiedades.valorPropertie("procesos.patron." + patron);
		
		if(procesosPatron==null || procesosPatron.equals("")){
			procesosPatron = "TODOS";
		}
		
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);

		if (gestor != null) {
			try {
				listaProcesos =  gestor.listarProcesosOrdenados();
			} catch (SchedulerException e1) {
				log.error("Error recuperando lista de procesos", e1);
			}
			
			if(patron!=null && nodo!=null){
				ArrayList<String> arrayProcesosPatron = new ArrayList<String>();
				
				if(procesosPatron==null || procesosPatron.equals("TODOS")){
					arrayProcesosPatron = null;
				} else {
					String[] procesos = procesosPatron.split(",");
					for (String proceso : procesos) {
						arrayProcesosPatron.add(proceso);
					}
				}

				List<ProcesoVO> listaProcesosPatron = servicioProcesos.getProcesosPatron(nodo, arrayProcesosPatron);
				
				if(listaProcesosPatron!=null && listaProcesosPatron.size() > 0){
					for (ProcesoVO proceso : listaProcesosPatron) {
						try {
							gestor.arrancarProceso(proceso.getOrden().intValue());
						} catch (Throwable e) {
							log.error("Imposible arrancar el proceso " + proceso.getId().getProceso(), e);
						}
					}
				}
				
			}
			
			try {
				// Mantis 13692: David Sierra: Si en el combo no se ha seleccionado el elemento TODOS
				if (!procesosPatron.equals("TODOS")) {
					
					listaProcesoBean = new ArrayList<ProcesoBean>();
					// Se recorre el array con todos los procesos y se incluyen en la lista los que su nombre coincida con el patron seleccionado 
					for (int i = 0; i< listaProcesos.length; i++) {	
						
						if(procesosPatron.contains(listaProcesos[i].getNombre())) {
							listaProcesoBean.add(listaProcesos[i]);
						} 
					}					
					// Lista vacia			
					if (listaProcesoBean.size() == 0) {
						listaProcesos =  gestor.listarProcesosOrdenados();
					}
					else {
						listaProcesos = new ProcesoBean[listaProcesoBean.size()];
						// Al array listaProcesos se le asignan los valores de la lista
						for(int i =0; i < listaProcesoBean.size(); i++) {
							 listaProcesos[i]=listaProcesoBean.get(i);
						}
					}			
				// En el combo se ha seleccionado el elemento TODOS
				}else {					
						listaProcesos =  gestor.listarProcesosOrdenados();
				}
				
			} catch (SchedulerException e) {
				log.error("Error al obtener la lista de procesos", e);
			}
			
			fechaActualizacion = new Date();
		}
		
		return LISTAR;
		
	}
	
	/**
	 * Detiene la lista de procesos según el patrón seleccionado desde la pantalla de procesos, y recarga el listado
	 * @return listar
	 */
	public String pararLista(){
		
		String nodo = null;
		String procesosPatron = null;
		nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
		procesosPatron = gestorPropiedades.valorPropertie("procesos.patron." + patron);
		// Mantis 16049. David Sierra: Control de null para el patron TODOS
		if(procesosPatron==null || procesosPatron.equals("")){
			procesosPatron = "TODOS";
		}
		
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		
		if (gestor != null) {
			try {
				listaProcesos =  gestor.listarProcesos();
			} catch (SchedulerException e1) {
				log.error("Error recuperando lista de procesos", e1);
			}
			
			if(patron!=null && nodo!=null){
				ArrayList<String> arrayProcesosPatron = new ArrayList<String>();
				
				if(procesosPatron==null || procesosPatron.equals("TODOS")){
					arrayProcesosPatron = null;
				} else {
					String[] procesos = procesosPatron.split(",");
					for (String proceso : procesos) {
						arrayProcesosPatron.add(proceso);
					}
				}

				List<ProcesoVO> listaProcesosPatron = servicioProcesos.getProcesosPatron(nodo, arrayProcesosPatron);
				
				if(listaProcesosPatron!=null && listaProcesosPatron.size() > 0){
					for (ProcesoVO proceso : listaProcesosPatron) {
						try {
							gestor.pararProceso(proceso.getOrden().intValue());
						} catch (Throwable e) {
							log.error("Imposible arrancar el proceso " + proceso.getId().getProceso(), e);
						}
					}
				}
				
			}
			
			try {
				// Si en el combo no se ha seleccionado el elemento TODOS
				 if (!procesosPatron.equals("TODOS")) {
					
					listaProcesoBean = new ArrayList<ProcesoBean>();
					// Se recorre el array con todos los procesos y se incluyen en la lista los que su nombre coincida con el patron seleccionado 
					for (int i = 0; i< listaProcesos.length; i++) {	
						
						if(procesosPatron.contains(listaProcesos[i].getNombre())) {
							listaProcesoBean.add(listaProcesos[i]);
						} 
					}					
					// Lista vacia			
					if (listaProcesoBean.size() == 0) {
						listaProcesos =  gestor.listarProcesosOrdenados();
					}
					else {
						listaProcesos = new ProcesoBean[listaProcesoBean.size()];
						// Al array listaProcesos se le asignan los valores de la lista
						for(int i =0; i < listaProcesoBean.size(); i++) {
							 listaProcesos[i]=listaProcesoBean.get(i);
						}
					}			
				// En el combo se ha seleccionado el elemento TODOS
				}else {					
					listaProcesos =  gestor.listarProcesosOrdenados();
				}
			} catch (SchedulerException e) {
				log.error("Error al obtener la lista de procesos", e);
			}
			
			fechaActualizacion = new Date();
		}
			
		return LISTAR;
		
	}
	
	public String listaFiltrada() throws Throwable {
		// Mantis 13692: David Sierra: Filtrado de la tabla de procesos en funcion del patron que se seleccione en el combo de patrones		
		log.info("listaProcesos"); 
		fechaActualizacion = new Date();
		GestorColas gestor = (GestorColas)servletRequest.getSession().getServletContext().getAttribute(GESTOR);
		if (gestor != null) {
			listaProcesos =  gestor.listarProcesosOrdenados();
		}
		// A la recuperacion por properties se le pasa el patron seleccionado en el combo
		String procesosPatron = gestorPropiedades.valorPropertie("procesos.patron." + patron);
		
		if(procesosPatron==null || procesosPatron.equals("")){
			procesosPatron = "TODOS";
		}
		// Si en el combo no se ha seleccionado el elemento TODOS
		if (!procesosPatron.equals("TODOS")) {
			
			listaProcesoBean = new ArrayList<ProcesoBean>();
			// Se recorre el array con todos los procesos y se incluyen en la lista los que su nombre coincida con el patron seleccionado 
			for (int i = 0; i< listaProcesos.length; i++) {				
				if(procesosPatron.contains(listaProcesos[i].getNombre())) {
					listaProcesoBean.add(listaProcesos[i]);
				} 
			} // Lista vacia			
			if (listaProcesoBean.size() == 0) {
				listaProcesos =  gestor.listarProcesosOrdenados();
			}
			else {
				listaProcesos = new ProcesoBean[listaProcesoBean.size()];
				// Al array listaProcesos se le asignan los valores de la lista
				for(int i =0; i < listaProcesoBean.size(); i++) {
					 listaProcesos[i]=listaProcesoBean.get(i);
				}
			}			
		// En el combo se ha seleccionado el elemento TODOS
		}else {					
			listaProcesos =  gestor.listarProcesosOrdenados();
		}		
		return LISTAR;			
	}
	
	/* FIN MÉTODOS LÓGICOS */
	
	/* INICIO GETTERS Y SETTERS */
	
	public int getIndice() {
		return indice;
	}
	
	public void setIndice(int indice) {
		this.indice = indice;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public ProcesoBean[] getListaProcesos() {
		return listaProcesos;
	}

	public String getFechaActualizacion() {
		return (fechaActualizacion == null ? "" : formatoFecha.format(fechaActualizacion));
	}

	public int getTipoProceso() {
		return tipoProceso;
	}
	
	public BigDecimal getTiempoCorrecto() {
		return tiempoCorrecto;
	}


	public void setTiempoCorrecto(BigDecimal tiempoCorrecto) {
		this.tiempoCorrecto = tiempoCorrecto;
	}


	public BigDecimal getTiempoRecuperable() {
		return tiempoRecuperable;
	}


	public void setTiempoRecuperable(BigDecimal tiempoRecuperable) {
		this.tiempoRecuperable = tiempoRecuperable;
	}


	public BigDecimal getTiempoNoRecuperable() {
		return tiempoNoRecuperable;
	}


	public void setTiempoNoRecuperable(BigDecimal tiempoNoRecuperable) {
		this.tiempoNoRecuperable = tiempoNoRecuperable;
	}


	public BigDecimal getTiempoSinDatos() {
		return tiempoSinDatos;
	}


	public void setTiempoSinDatos(BigDecimal tiempoSinDatos) {
		this.tiempoSinDatos = tiempoSinDatos;
	}


	public void setTipoProceso(int tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.servletRequest = arg0;	
	}


	public BigDecimal getIntentos() {
		return intentos;
	}


	public void setIntentos(BigDecimal intentos) {
		this.intentos = intentos;
	}


	/*public String getIntervalo() {
		return intervalo;
	}


	public void setIntervalo(String intervalo) {
		this.intervalo = intervalo;
	}*/


	public String getHoraInicio() {
		return horaInicio;
	}


	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}


	public String getHoraFin() {
		return horaFin;
	}


	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}


	public List<EnvioDataVO> getListaEnvioData() {
		return listaEnvioData;
	}


	public void setListaEnvioData(List<EnvioDataVO> listaEnvioData) {
		this.listaEnvioData = listaEnvioData;
	}


	public String getNombreProceso() {
		return nombreProceso;
	}


	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}


	public String getPatron() {
		return patron;
	}


	public void setPatron(String patron) {
		this.patron = patron;
	}
	
	public String getPassValidado() {
		return passValidado;
	}

	public void setPassValidado(String passValidado) {
		this.passValidado = passValidado;
	}
	
	/* FIN GETTERS Y SETTERS */


	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
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


	public String getPatronSeleccionado() {
		return patronSeleccionado;
	}


	public void setPatronSeleccionado(String patronSeleccionado) {
		this.patronSeleccionado = patronSeleccionado;
	}
}
