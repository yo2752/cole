package arbol.acciones;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;
import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.general.model.vo.IpNoPermitidasVO;
import org.gestoresmadrid.oegam2.controller.security.authentication.OegamAuthenticationToken;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.model.service.ServicioIpNoValida;
import org.gestoresmadrid.oegamComun.acceso.model.bean.ResultadoAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioGestionAcceso;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;
import org.gestoresmadrid.oegamComun.session.service.ServicioSesion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import escrituras.beans.ResultBean;
import escrituras.beans.dao.NotificacionDao;
import escrituras.modelo.ModeloNotificacion;
import escrituras.utiles.PaginatedListImpl;
import general.acciones.ActionBase;
import net.sf.navigator.menu.MenuRepository;
import oegam.constantes.ConstantesSession;
import trafico.modelo.ModeloAccesos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.Contrato;
import utilidades.web.GestorArbol;
import utilidades.web.OegamExcepcion;

public class RegistrarAction extends ActionBase implements ApplicationAware, SessionAware{

	private static final ILoggerOegam log = LoggerOegam.getLogger(RegistrarAction.class);
	private static final long serialVersionUID = 1L;

	private String[]IdNotificaciones;
	private PaginatedListImpl listaNotificacion;
	private BigDecimal id_Notificacion;
	private NotificacionDao notificacionDao;

	HashMap<String, Object> parametrosBusqueda;
	private Map<String, Object> session;
	private Map<String, Object> application;
	private ModeloAccesos modeloAccesos;

	// Display table:
	private String page;
	private String sort;
	private String dir;
	private SortOrderEnum order;
	private String resultadosPorPagina;

	private static Boolean springSecurityEnabled;

	private static final String CONTEXT_PARAM_AUTENTICACION_SISTEMA = "AutenticacionSISTEMA";
	private static final String CONTEXT_PARAM_CONFIG_LOCATION = "contextConfigLocation";
	private static final String APPLICATION_CONTEXT_SECURITY = "applicationContext-security.xml";
	private static final String CLIENT_IP = "client-ip";
	private static final String LISTA_NOTIFICACIONES = "listaNotificaciones";

	/*
	 * Para entrar como:
	 * 
	 * Presidente ICOGAM: NIF = "00821563A";
	 * Natividad: NIF = "05270245W";
	 * Usuario clínica: NIF = "00654654M";
	 * Usuario financiación: NIF = "48505575R";
	 * Usuario policia: NIF = "02670778H";
	 * Usuario Pradilla NIF = "29019580C";
	 */
	private static final String NIF = "00821563A";
	private AuthenticationManager authenticationManager;
	private SessionAuthenticationStrategy sessionAuthenticationStrategy;

	@Autowired
	private ServicioIpNoValida servicioIpNoValida;

	@Autowired
	private ServicioSesion servicioSesion;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private ServicioComunSemaforos servicioSemaforo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioGestionAcceso servicioGestionAcceso;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@SuppressWarnings("unchecked")
	public String execute() {
		String nif = null;
		try {
			log.info("Inicio");
			session = ActionContext.getContext().getSession();

			if (usuarioAutenticadoConViafirma()) {
				nif = (String) session.get(utilidades.mensajes.Claves.CLAVE_NIF_Viafirma);
			} else {
				String nifRequest = ServletActionContext.getRequest().getParameter("NIF");
				if (nifRequest != null && !nifRequest.isEmpty()) {
					nif = nifRequest;
				} else {
					nif = NIF;
				}
			}
			if (nif == null) {
				addActionError("No se ha podido recuperar el NIF del usuario de su sesión...");
				return Action.ERROR;
			}
			log.info("Recuperado NIF del usuario de la sesion");
			doAutoLogin(nif);

			InetAddress address = InetAddress.getLocalHost();
			String ipAccesso = address.getHostAddress();

			log.info("IP de acceso:" + ipAccesso);
			List<IpNoPermitidasVO> listaIPNoPermitidas = servicioIpNoValida.getListaIPNoPermitidas();

			log.info("Lista IPs no permitidas: " + listaIPNoPermitidas.toString());
			boolean valido = servicioIpNoValida.validarIP(ipAccesso, listaIPNoPermitidas);

			if (!valido) {
				addActionError("Los datos del certificado no coinciden con ningún usuario válido del sistema.");
				return Action.ERROR;
			}

			String ip = ServletActionContext.getRequest().getRemoteAddr();
			if(ServletActionContext.getRequest().getHeader(ConstantesSession.CLIENTE_IP) != null){
				ip = ServletActionContext.getRequest().getHeader(ConstantesSession.CLIENTE_IP);
			}

			session.put(ConstantesSession.CLIENTE_IP, ip);

			Long idContrato = null;
			if ("SI".equals(gestorPropiedades.valorPropertie("nuevo.gestorAccesos"))) {
				ResultadoAccesoBean resultadoAcceso = servicioGestionAcceso.gestionAcceso(nif, null);
				if (!resultadoAcceso.getError()) {
					UsuarioAccesoBean usuarioAcceso = resultadoAcceso.getUsuarioAcceso();
					resultadoAcceso = servicioGestionAcceso.gestionFavoritos(resultadoAcceso.getUsuarioAcceso());
					if (!resultadoAcceso.getError()) {
						usuarioAcceso.setListaFavoritos(resultadoAcceso.getListaFavoritos());
					}
					resultadoAcceso = servicioGestionAcceso.montarMenuRepository(usuarioAcceso);
					if (!resultadoAcceso.getError()) {
						MenuRepository menuArbol = resultadoAcceso.getMenuArbol();
						resultadoAcceso = servicioGestionAcceso.guardarDatosAccesoSession(usuarioAcceso);
						if (resultadoAcceso.getError()) {
							addActionError(resultadoAcceso.getMensaje());
							return Action.ERROR;
						}
						Claves.setObjetoDeContextoSesion(Claves.CLAVE_ARBOL, menuArbol);
						Claves.setObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO, usuarioAcceso);
						session.put(ConstantesSession.RAZON_SOCIAL_CONTRATOP, usuarioAcceso.getRazonSocialContrato());
						session.put(ConstantesSession.CIFP,usuarioAcceso.getCifContrato());
						idContrato = usuarioAcceso.getIdContrato();
						if(usuarioAcceso.getListaFavoritos() != null && !usuarioAcceso.getListaFavoritos().isEmpty()){
							Claves.setObjetoDeContextoSesion("favoritos", usuarioAcceso.getListaFavoritos());
						}
						StringBuilder logEvidencia = new StringBuilder("El usuario ").append(nif).append(" del colegiado ").append(usuarioAcceso.getNumColegiado()).append(" ha accedido desde la maquina ").append(ip).append(" con la sesion ").append(ServletActionContext.getRequest().getSession(true).getId()).append(" al Frontal ").append(ipAccesso).append(".");
						log.error(logEvidencia.toString());
					}
				} else {
					addActionError(resultadoAcceso.getMensaje());
					return Action.ERROR;
				}
			} else {
				boolean usuarioValido = false;
				//Mantis 12974 - ASG
				try {
					usuarioValido = getModeloAccesos().crearArbol(nif);
				} catch (OegamExcepcion oe) {
					if (oe.getMensajeError1().contains("Contrato deshabilitado")) {
						addActionError("No puede acceder al sistema ya que su contrato está deshabilitado actualmente.");
						return Action.ERROR;
					} else if (oe.getMensajeError1().contains("Usuario deshabilitado")) {
						addActionError("No puede acceder al sistema ya que su usuario está deshabilitado actualmente.");
						return Action.ERROR;
					} else
						throw oe;
				}
				//Fin Mantis 12974 - ASG
				if (!usuarioValido) {
					addActionError("Los datos del certificado no coinciden con ningún usuario válido del sistema.");
					return Action.ERROR;
				}

				GestorArbol gestorArbol = (GestorArbol) getSession().get(Claves.CLAVE_GESTOR_ARBOL);
				Contrato contrato = gestorArbol.getContratos().get(0);
				idContrato = contrato.getId_contrato().longValue();
				session.put(ConstantesSession.RAZON_SOCIAL_CONTRATOP, contrato.getRazon_social());
				session.put(ConstantesSession.CIFP, contrato.getCif());

				StringBuilder logEvidencia = new StringBuilder("El usuario ").append(nif).append(" del colegiado ").append(gestorArbol.getUsuario().getNum_colegiado()).append(" ha accedido desde la maquina ").append(ip).append(" con la sesion ").append(ServletActionContext.getRequest().getSession(true).getId()).append(" al Frontal ").append(ipAccesso).append(".");
				log.error(logEvidencia.toString());
				guardarDatosDeLaSesion(gestorArbol);
			}
			log.info("Fin");
			if ("SI".equals(gestorPropiedades.valorPropertie("firmar.lopd"))) {
				// Comprueba que sea el colegiado y que tenga la LOPD firmada
				Boolean tieneFirmadoLopd = servicioContrato.tieneFirmadoLopd(idContrato);
				if (!tieneFirmadoLopd) {
					return "popUpModalLopd";
				}
			}

			// Mantis 34940 Semáforos
			if (session.get(ConstantesSession.LISTA_SEMAFOROS_NODO) == null) {
				String nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
				super.setListaSemaforosSesion(servicioSemaforo.obtenerListaSemaforosSes(nodo));
				session.put(ConstantesSession.LISTA_SEMAFOROS_NODO, getListaSemaforosSesion());
			} else {
				super.setListaSemaforosSesion((List<SemaforoDto>)session.get(ConstantesSession.LISTA_SEMAFOROS_NODO));
			}
			// Fin Mantis 34940

			return buscar();
		} catch (Throwable e) {
			addActionError("Ha ocurrido un error inesperado, disculpe las molestias. Vuelva a intentarlo más tarde, si el error persiste, pongase en contacto con el administrador del sistema.");
			log.error("Error en el login del usuario " + nif, e);
			return Action.ERROR;
		}
	}

	public String firmarLopd(){
		try {
			ResultBean resultado = servicioContrato.firmarLopd(utilesColegiado.getIdContratoSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
				return Action.ERROR;
			}
			return buscar();
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de firmar el Documento de LOPD para el colegiado: " + utilesColegiado.getNumColegiadoSession() + ", error: ",e);
			addActionError("Ha sucedido un error a la hroa de firmar el Documento de LOPD.");
			return Action.ERROR;
		}
	}

	/**
	 * Método que realiza la autenticación con Spring Security
	 * 
	 */
	private void doAutoLogin(String nif) {
		if (springSecurityEnabled == null) {
			String contextParamConfigLocation = (String)application.get(CONTEXT_PARAM_CONFIG_LOCATION);	
			springSecurityEnabled = contextParamConfigLocation != null && contextParamConfigLocation.contains(APPLICATION_CONTEXT_SECURITY);
		}
		if (nif != null && !nif.isEmpty() && springSecurityEnabled) {
			log.debug("Realizando autenticacion Spring");
			try {
				OegamAuthenticationToken user = new OegamAuthenticationToken(null);
				user.setCredentials(nif);
				Authentication result = this.getAuthenticationManager().authenticate(user);
				SecurityContextHolder.getContext().setAuthentication(result);
				sessionAuthenticationStrategy.onAuthentication(result, ServletActionContext.getRequest(), ServletActionContext.getResponse());
			} catch(AuthenticationException e) {
				log.info("Authentication failed!!", e);
			}
		}
	}

	private boolean usuarioAutenticadoConViafirma(){
		boolean viafirmaActivado = false;
		String autenticacionActivada = (String)application.get(CONTEXT_PARAM_AUTENTICACION_SISTEMA);
		if (autenticacionActivada == null || !autenticacionActivada.equalsIgnoreCase(Boolean.FALSE.toString())) {
			viafirmaActivado = true;
		} else {
			viafirmaActivado = false;
		}
		return viafirmaActivado;
	}

	private void guardarDatosDeLaSesion(GestorArbol gestorArbol){
		HttpSession currentSession = ServletActionContext.getRequest().getSession(true);
		String ip = ServletActionContext.getRequest().getRemoteAddr();
		if(ServletActionContext.getRequest().getHeader(CLIENT_IP) != null){
			ip = ServletActionContext.getRequest().getHeader(CLIENT_IP);
		}

		String ipFrontal = "";
		try {
			int num = InetAddress.getLocalHost().toString().indexOf('/');
			if (num > 0){
				ipFrontal = InetAddress.getLocalHost().toString().substring(num + 1);
			}
		} catch (UnknownHostException e1) {
			log.error("Error UnknownHostException: ",e1);
		}
		servicioSesion.insert(currentSession.getId(), gestorArbol.getUsuario().getNum_colegiado(), ip, ipFrontal, gestorArbol.getUsuario().getId_usuario(), gestorArbol.getUsuario().getApellidos_nombre());
	}

	public String buscar() throws Throwable {
		return LISTA_NOTIFICACIONES;
	}

	public String eliminar() throws Throwable {
		ModeloNotificacion modeloNotificacion = new ModeloNotificacion();
		// Mantis 14043. David Sierra: Para controlar que el array IdNotificaciones no tenga valores nulos.
		// En caso de que sea null, se muestra un mensaje al usuario y se agrega una traza de error al log.
		if (null != getIdNotificaciones()) {
			for(String IdNotificacion: getIdNotificaciones()){
				notificacionDao = modeloNotificacion.buscarByIdTramite(IdNotificacion);
				String sqlerrm = modeloNotificacion.eliminar(notificacionDao.getId_Notificacion());
				if(!sqlerrm.equalsIgnoreCase(Claves.CLAVE_EJECUCION_CORRECTA_PROCESO)){
					addActionMessage("La notificacion con id Tramite "+notificacionDao.getId_Tramite()+" no se pudo eliminar Causa: "+sqlerrm);
				}
			}
			return buscar();
		} else {
			addActionError("No hay una notificación que pueda ser eliminada.");
			log.error("Error producido por la eliminacion de una notificacion con el idNotificaciones con valor null");
			return ERROR;
		} // Fin Mantis
	}

	public String navegar() {
		asignarCamposDesdeSession();
		if (getListaNotificacion() == null){
			return ERROR;
		}
		getListaNotificacion().establecerParametros(getSort(), getDir(),getPage(), getResultadosPorPagina());
		getListaNotificacion().getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		return LISTA_NOTIFICACIONES;
	}

	private void asignarCamposDesdeSession() {
		GestorArbol gestorArbol = (GestorArbol) getSession().get(
				Claves.CLAVE_GESTOR_ARBOL);
		String resultadosPorPagina;
		if ((resultadosPorPagina = getResultadosPorPagina()) != null) {
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, resultadosPorPagina);
		}

		setListaNotificacion((PaginatedListImpl) getSession().get(ConstantesSession.LISTA_NOTIFICACION));
		setResultadosPorPagina((String) getSession().get(ConstantesSession.RESULTADOS_PAGINA));

		parametrosBusqueda = new HashMap<String, Object>();
		parametrosBusqueda.put(ConstantesSession.ID_USUARIO, gestorArbol.getUsuario().getId_usuario());
	}

	public BigDecimal getId_Notificacion() {
		return id_Notificacion;
	}

	public void setId_Notificacion(BigDecimal idNotificacion) {
		id_Notificacion = idNotificacion;
	}

	public PaginatedListImpl getListaNotificacion() {
		return listaNotificacion;
	}

	public void setListaNotificacion(PaginatedListImpl listaNotificacion) {
		this.listaNotificacion = listaNotificacion;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public NotificacionDao getNotificacionDao() {
		return notificacionDao;
	}

	public void setNotificacionDao(NotificacionDao notificacionDao) {
		this.notificacionDao = notificacionDao;
	}

	public SortOrderEnum getOrder() {
		return order;
	}

	public void setOrder(SortOrderEnum order) {
		this.order = order;
	}

	@SuppressWarnings("unused")
	private boolean isInvalid(String value) {
		return (value == null || value.length() == 0);
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	public Map<String, Object> getApplication() {
		return this.application;
	}

	public String notificar() throws Throwable{
		return buscar();
	}

	public String[] getIdNotificaciones() {
		return IdNotificaciones;
	}

	public void setIdNotificaciones(String[] idNotificaciones) {
		IdNotificaciones = idNotificaciones;
	}

	public ModeloAccesos getModeloAccesos() {
		if (modeloAccesos == null) {
			modeloAccesos = new ModeloAccesos();
		}
		return modeloAccesos;
	}

	public void setModeloAccesos(ModeloAccesos modeloAccesos) {
		this.modeloAccesos = modeloAccesos;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public SessionAuthenticationStrategy getSessionAuthenticationStrategy() {
		return sessionAuthenticationStrategy;
	}

	public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionAuthenticationStrategy) {
		this.sessionAuthenticationStrategy = sessionAuthenticationStrategy;
	}

}