package struts2.interceptors;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.oegamComun.acceso.model.bean.MenuFuncionBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.Interceptor;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;
import trafico.utiles.constantes.Constantes;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.mensajes.GestorFicherosMensajes;
import utilidades.validaciones.CadenasInvalidas;
import utilidades.validaciones.FiltroJavascript;
import utilidades.web.GestorArbol;
import utilidades.web.Menu;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

public class InterceptorSesiones implements Interceptor {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(InterceptorSesiones.class);

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	Conversor conversor;

	public void destroy() {
	}

	public void init() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession(true);

		log.debug("boolean, identificador de sesión válido: " + 
				ServletActionContext.getRequest().isRequestedSessionIdValid());

		session.setAttribute("ultimoAcceso",session.getLastAccessedTime());

		// Si hay una factura en sesión es que se ha descargado. La borra
		File factura = (File)session.getAttribute("factura");
		if (factura != null) {
			factura.delete();
		}

		// LA PRIMERA VEZ ES NULL SIEMPRE
		HttpServletRequest request = ServletActionContext.getRequest();
		String servletPath = request.getServletPath();
		//HttpServletResponse response=ServletActionContext.getResponse()
		request.setCharacterEncoding("UTF-8");
		List<String>peticionesFirmadas = new ArrayList<>();
		List<String>parametrosViafirma = new ArrayList<>();
		List<String>parametrosInfoCertificado = new ArrayList<>();
		Enumeration<String> nombresParametros = request.getParameterNames();
		while(nombresParametros.hasMoreElements()){
			String nombreParametro = nombresParametros.nextElement();
			//System.out.println("Parametro: " + nombreParametro);
			//System.out.println("Valor: " + request.getParameter(nombreParametro));
			if (nombreParametro != null && nombreParametro.contains("openid")) {
				parametrosViafirma.add(nombreParametro);
			}
			if (nombreParametro!=null && (nombreParametro.contains("peticion") || "documentoXML".equals(nombreParametro) || nombreParametro.contains("datosFirmar"))){
				peticionesFirmadas.add(nombreParametro);
			}
			if (nombreParametro != null && (nombreParametro.contains("aliasCertificado") || nombreParametro.contains("listaChecksCertificados") )) {
				parametrosInfoCertificado.add(nombreParametro);
			}
		}

		Map<String, String[]> parametersMap = request.getParameterMap();
		Collection<String[]> parametersValues = parametersMap.values();
		for (String[] parameters: parametersValues) {
			for (String parameter: parameters) {
				boolean petFirmada = false;
				for (String pet: peticionesFirmadas) {
					// Si es una peticionFirmada se deja pasar porque contiene caracteres especiales.
					if (parameter!=null && parameter.equals(request.getParameter(pet))) {
						log.debug("Valor pet firmada ::::::::::: " + parameter);
						petFirmada = true;
					}
				}
				// Control parametros viafirma:
				boolean parViafirma = false;
				for (String par: parametrosViafirma){
					//Si es un parametro de viafirma se deja pasar porque puede contener caracteres especiales.
					if (parameter != null && parameter.equals(request.getParameter(par))) {
						log.debug("Valor parametro viafirma ::::::::::: " + parameter);
						parViafirma = true;
					}
				}
				boolean infoCertificado = false;
				for (String par: parametrosInfoCertificado){
					//Si es un parametro de información de un certificado se deja pasar porque puede contener caracteres especiales.
					if (parameter!=null && parameter.equals(request.getParameter(par))) {
						log.debug("Valor parametro informacion de certificado ::::::::::: " + parameter);
						infoCertificado = true;
					}
				}
				// Fin control parametros viafirma:
				if (!petFirmada && !parViafirma && !infoCertificado && FiltroJavascript.findScriptCodeWhitelistPlus(parameter)) {
					addFieldError(actionInvocation, "Caracter no válido: "+parameter);
					return Action.ERROR;
				}
			}
		}

		String optionSelected = request.getParameter("selectedOption");

		//GAYAO: controlar este parametro para que no puedan meter cadenas "maliciosas" (SELECT, INSERT, DELETE, ETC.)
		ArrayList<String>  cadenasInvalidas = new CadenasInvalidas();
		if (cadenasInvalidas.contains(optionSelected)) {
			return Action.ERROR;
		}

		if (servletPath.contains(Claves.CLAVE_Login_ACTION)) {
			return actionInvocation.invoke();
		} else if (servletPath.contains(Claves.CLAVE_Arbol_ACTION)) {
			return actionInvocation.invoke();
		} else if (servletPath.contains(Claves.CLAVE_SECURITY_ACTION)) {
			//Mantis 23758
			session.setAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY", null);
			return actionInvocation.invoke();
		}
		/*else if(servletPath.contains(Claves.CLAVE_Afirma_Registrar_ACTION)){
			return actionInvocation.invoke();
		}*/
		else if (servletPath.contains(Claves.CLAVE_Cerrar_Session)) {
			session.setAttribute("validada", "false");
			session.removeAttribute(Claves.CLAVE_GESTOR_ARBOL);
			session.removeAttribute(Claves.CLAVE_GESTOR_ACCESO);
			return actionInvocation.invoke();
		} else {
			boolean eArbol = false;
			if ("SI".equals(gestorPropiedades.valorPropertie("nuevo.gestorAccesos"))) {
				eArbol = session.getAttribute(Claves.CLAVE_GESTOR_ACCESO) != null;
			} else {
				eArbol = session.getAttribute(Claves.CLAVE_GESTOR_ARBOL) != null;
			}
			String  strValidada = (String) session.getAttribute("validada");
			if ((null == strValidada) || strValidada.equalsIgnoreCase("false") || (!eArbol && !servletPath.contains(Claves.CLAVE_Registrar_ACTION))) {
				String autenticacionSistema = request.getSession().getServletContext().getInitParameter("AutenticacionSISTEMA");
				log.info(Claves.CLAVE_LOG_CONTEXT_PARAM + "AutenticacionSistema: " + autenticacionSistema);
				if (!"false".equals(autenticacionSistema)) {
					Object action = actionInvocation.getAction();
					if (action instanceof ValidationAware) {
						session.setAttribute("mensajeError", "No ha iniciado sesión de trabajo o ha expirado. Reinicie para iniciar una nueva.");
						// Atributo en session para indicarle a la página de error que muestre el botón de reiniciar aunque no sea error viafirma
						session.setAttribute("sesion","expirada");
						return Constantes.SESION_EXPIRADA;
					}
				}
			}

			GestorFicherosMensajes gfmErrores = (GestorFicherosMensajes)Claves.getObjetoDeContextoAplicacion(Claves.CLAVE_GESTOR_ERRORES);
			if (null == gfmErrores) {
				try {
					gfmErrores=new GestorFicherosMensajes("erroresOegam");
					Claves.setObjetoDeContextoAplicacion(Claves.CLAVE_GESTOR_ERRORES, gfmErrores);
				} catch (Throwable e) {
					OegamExcepcion oe = new OegamExcepcion("No se encuentra el fichero de errores",EnumError.error_00001);
					return Action.ERROR;
				}
			}
			LinkedList<Menu> recientes;
			if (Claves.getObjetoDeContextoSesion("recientes") != null) {
				recientes = (LinkedList<Menu>) Claves.getObjetoDeContextoSesion("recientes");
			} else {
				recientes = new LinkedList<Menu>();
			}
			boolean added = false;
			if ("SI".equals(gestorPropiedades.valorPropertie("nuevo.gestorAccesos"))) {
				UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
				if (usuarioAccesoBean != null && usuarioAccesoBean.getListaMenu() != null && !usuarioAccesoBean.getListaMenu().isEmpty()) {
					for (MenuFuncionBean menuFuncionBean : usuarioAccesoBean.getListaMenu()){
						if (StringUtils.isNotBlank(menuFuncionBean.getUrl()) && menuFuncionBean.getUrl().equals(servletPath.replace("/", ""))) {
							Menu menu = conversor.transform(menuFuncionBean, Menu.class);
							if (recientes.contains(menu)) {
								recientes.remove(menu);
							}
							recientes.addFirst(menu);
							added = true;

							while(recientes.size() > 8){
								recientes.removeLast();
							}
						}
					}
				}
			} else {
				MenuRepository menuRepository = (MenuRepository)Claves.getObjetoDeContextoSesion(Claves.CLAVE_ARBOL);
				GestorArbol ga = (GestorArbol)Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
				if (ga != null && menuRepository!=null){
					// Iteramos para ver las 8 aplicaciones del menú usadas más recientemente
					List<Menu> menus = ga.get_listaMenus();
					for (Menu menu : menus) {
						if (menu.getUrl() != null && menu.getUrl().equals(servletPath.replace("/", ""))) {
							// Borramos la ocurrencia que pueda haber ya del elemento
							// en otra posición que quizá no sea la primera
							if (recientes.contains(menu)) {
								recientes.remove(menu);
							}
							// Añadimos a la lista de recientes en el primer lugar
							recientes.addFirst(menu);
							added = true;
							// Borramos los que superen los 8 registros de recientes
							while (recientes.size() > 8) {
								recientes.removeLast();
							}
						}
					}
				}
			}

			// Añadimos en sesión la lista de recientes
			Claves.setObjetoDeContextoSesion("recientes", recientes);
			return actionInvocation.invoke();
		}
	}

	private void addFieldError(ActionInvocation invocation, String message) {
		Object action = invocation.getAction();
		if (action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}

	private boolean existeURL(MenuComponent[]lista,String miURL) {
		if (lista == null || lista.length == 0){
			return false;
		}
		for (int i = 0; i<lista.length; i++) {
			MenuComponent mc = lista[i];

			if (null != mc.getParent()) {
	//			log.debug("Nombre: "+ mc.getName() + ", Location: " + mc.getLocation()+ ", Padre: " + mc.getParent().getName());
			} else {
	//			log.debug("Nombre: "+ mc.getName() + ", Location: " + mc.getLocation()+ ", Padre: NO");
			}
			String url=  mc.getLocation();
			if (null != url && (url.trim().length() > 0) && url.endsWith(miURL)) {
				return true;
			}
			boolean resultado = existeURL(mc.getMenuComponents(), miURL);
			if (resultado) {
				return true;
			}

		}
		return false;
	}

}