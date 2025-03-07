package struts2.interceptors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioUrl;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.GestorArbol;
import utilidades.web.UserAuthorizations;

public class SecurityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private static ILoggerOegam log = LoggerOegam.getLogger(SecurityInterceptor.class);

	private static final String CHECK_ACCESS = "check.authorized.access";
	private static final String ACTIVE = "SI";
	private static final String FORBIDDEN_RESULT = "forbidden";
	private static final String[] EXPEDIENT_NAMES = {"numExpediente", "numExpedientes", "numsExpediente", "listaExpedientes", "listaChecksConsultaTramite"};
	private static final String[] EXPEDIENT_TOKENS = {"-", ","};

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioUrl servicioUrl;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		// Mediante wildcard comprobar que se tiene permiso para acceder a donde sea.
		try {
			if ("SI".equals(gestorPropiedades.valorPropertie("nuevo.gestorAccesos"))) {
				UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
				if (usuarioAccesoBean == null) {
					// Si no hay sesión, no es asunto de este interceptor
					return actionInvocation.invoke();
				}
				// Obtener URL a la que se quiere acceder
				HttpServletRequest request = ServletActionContext.getRequest();
				String servletPath = request.getServletPath();

				if (!servletPath.endsWith(".action")) {
					return actionInvocation.invoke();
				}

				// Comprobar que se tiene acceso a la URL
				if (!usuarioAccesoBean.getUserAuthorizations().isAllowed(servletPath) 
						|| !checkExpedientOwner(usuarioAccesoBean.getTienePermisoAdministracion(),
								usuarioAccesoBean.getTienePermisoEspecial(), usuarioAccesoBean.getIdContrato(),
								usuarioAccesoBean.getIdUsuario(), request)) {
					return FORBIDDEN_RESULT;
				} else {
					return actionInvocation.invoke();
				}
			} else {
				GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
				if (gestorArbol == null) {
					// Si no hay sesión, no es asunto de este interceptor
					return actionInvocation.invoke();
				}

				if (gestorArbol.getUserAuthorizations() == null) {
					// Inicar userAuthorization
					gestorArbol.setUserAuthorizations(new UserAuthorizations(new ArrayList<String>(), new ArrayList<String>()));

					try {
						// Comprobar si en el properties está activo el flag de la comprobación de autorización de usuarios
						if (ACTIVE.equals(gestorPropiedades.valorPropertie(CHECK_ACCESS))) {

							// Se cargan todas las URLs que son accesibles para
							// todas las aplicaciones
							gestorArbol.getUserAuthorizations().getAuthorizedUrls().addAll(servicioUrl.getListaPatronUrlsAplicacion(hibernate.entities.general.Aplicacion.ALL));

							// Se cargan las URLs accesibles para las aplicaciones
							// sobre las que tenga acceso el usuario
							gestorArbol.getUserAuthorizations().getAuthorizedUrls()
									.addAll(servicioUrl.getListaPatronUrlsContrato(gestorArbol.getIdContrato(),
											gestorArbol.getUsuario().getId_usuario()));

							// Se cargan todas las URLs para las que se debe tener autorización de acceso
							gestorArbol.getUserAuthorizations().getSecuralizedUrls().addAll(servicioUrl.getListaUrlsSecuralized());
						} else {
							log.debug("SecurityInterceptor desactivado");
						}
					} catch (Throwable t) {
						log.error("Error iniciando userAuthorization", t);
					}
				}
				// Obtener url a la que se quiere acceder
				HttpServletRequest request = ServletActionContext.getRequest();
				String servletPath = request.getServletPath();

				if (servletPath.endsWith(".action")) {
					servletPath = servletPath.substring(1, servletPath.length() - 7);
				} else {
					return actionInvocation.invoke();
				}

				// Comprobar que se tiene acceso a la URL
				if (!gestorArbol.getUserAuthorizations().isAllowed(servletPath) ||
						!checkExpedientOwner(gestorArbol.getTienePermisoAdministracion(), gestorArbol.getTienePermisoEspecial(),
								gestorArbol.getIdContrato().longValue(), gestorArbol.getUsuario().getId_usuario().longValue(),	request)) {
					return FORBIDDEN_RESULT;
				} else {
					return actionInvocation.invoke();
				}
			}
		} catch (Exception e) {
			log.error("Error en SecurityIncterceptor", e);
			return actionInvocation.invoke();
		}
	}

	/**
	 * Busca números de expedientes en la request para comprobar que el
	 * colegiado (si no es admin) tiene permisos para acceder al expediente
	 * 
	 * @param tienePermisoAdmin
	 * @param tienePermisoEspecial
	 * @param idContratoSession
	 * @param idUsuarioSession
	 * @param request
	 * @return
	 */
	private boolean checkExpedientOwner(Boolean tienePermisoAdmin, Boolean tienePermisoEspecial,
			Long idContratoSession, Long idUsuarioSession, HttpServletRequest request) {
		// Si el usuario es administrador, no se comprueba nada
		// Si alguno de estos campos es nulo... es que se está llamando a este método desde donde no se debe
		if (!tienePermisoAdmin && !tienePermisoEspecial && request != null) {
			// Se buscan los parametros/atributos que pueden contener números de expediente
			List<Object> candidatos = new ArrayList<Object>();
			for (String name : EXPEDIENT_NAMES) {
				Object candidato = request.getAttribute(name);
				if (candidato != null) {
					candidatos.add(candidato);
				}
				candidato = request.getParameter(name);
				if (candidato != null) {
					candidatos.add(candidato);
				}
			}
			// Recorrer los posibles números de expedientes
			List<String> expedients = new ArrayList<String>();
			for (Object candidato : candidatos) {
				if (candidato != null) {
					// Se encuentra número de expediente, hay que comprobar si es array, string...
					if (candidato instanceof String) {
						// Comprobar que no vengan varios tokenizados
						boolean concatenado = false;
						String sCandidato = ((String) candidato).replaceAll(" ", "");
						for (String token : EXPEDIENT_TOKENS) {
							if (sCandidato.contains(token)) {
								expedients.addAll(Arrays.asList(sCandidato.split(token)));
								concatenado = true;
								break;
							}
						}
						if (!concatenado) {
							expedients.add(sCandidato);
						}
					} else if (candidato instanceof Object[]) {
						// Es un array
						for (Object o : (Object[]) candidato) {
							expedients.add(o.toString());
						}
					} else if (candidato instanceof Iterable<?>) {
						// Es una colección
						Iterator<?> it = ((Iterable<?>) candidato).iterator();
						while (it.hasNext()) {
							expedients.add(it.next().toString());
						}
					} else {
						// No es un array ni una colección, puede ser BigDecimal, Long...
						expedients.add(candidato.toString());
					}
				}
			}

			if (!expedients.isEmpty()) {
				String[] arrayExpedientes = new String[expedients.size()];
				arrayExpedientes = Arrays.copyOf(expedients.toArray(arrayExpedientes), expedients.size());
	
				if (log.isDebugEnabled()) {
					log.debug("Compruebo que el contrato "
							+ " tiene permisos para sobre los expedientes contenidos en "
							+ Arrays.toString(arrayExpedientes));
				}
				// Llamar al método que comprueba los expedientes por contrato del servicio de permisos
				ServicioPermisos servicioPermisos = ContextoSpring.getInstance().getBean(ServicioPermisos.class);
				boolean tienePermiso = servicioPermisos.usuarioTienePermisoSobreTramites(arrayExpedientes, idContratoSession);

				if (!tienePermiso) {
					log.error("El usuario "
							+ idUsuarioSession
							+ " está intentando acceder a "
							+ Arrays.toString(arrayExpedientes));
				}
				return tienePermiso;
			}
		}

		return true;
	}

}