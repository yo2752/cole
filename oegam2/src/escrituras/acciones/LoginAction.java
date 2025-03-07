package escrituras.acciones;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.viafirma.cliente.ViafirmaClient;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.utiles.UtilesExcepciones;
import viafirma.utilidades.ConstantesViafirma;
import viafirma.utilidades.UtilesViafirma;

public class LoginAction extends ActionSupport implements ServletRequestAware,
	ServletResponseAware, SessionAware {

	/**
	 * El flujo llega desde la welcome-file. Debe redireccionar a una jsp que invoca la
	 * página que tiene embebido el applet de viafirma tras inicializar el cliente.
	 */
	private static final ILoggerOegam log = LoggerOegam.getLogger(LoginAction.class);
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String,Object> session;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public String execute() {
		try {
			// Carga el resource bundle por defecto para la 'use menu displayer tag'
			Locale local = new Locale("es");
			ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", local);
			session.put("org.apache.struts.action.MESSAGE", resourceBundle);
			UtilesViafirma utilesViaFirma = new UtilesViafirma();
			ViafirmaClient viafirmaClient= utilesViaFirma.getClient(ConstantesViafirma.OEGAM);
			String urlRespuestaViafirma = gestorPropiedades.valorPropertie("url_respuesta");
			/*
			 * Cookie[] cookies = request.getCookies(); String requestedSessionId = ""; for
			 * (Cookie cookie : cookies) {
			 * if(request.getSession().getId().equals(cookie.getValue())) {
			 * if("JSESSIONID".equals(cookie.getName())){ requestedSessionId =
			 * cookie.getValue(); response.setHeader("Set-Cookie",
			 * "JSESSIONID="+requestedSessionId + ";HttpOnly;Secure;SameSite=None"); } }else
			 * if("JSESSIONID".equals(cookie.getName())){ cookie.setMaxAge(0); } }
			 */
			viafirmaClient.solicitarAutenticacion(request, response, urlRespuestaViafirma);
			return "applet";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			addActionError(ex.toString());
			addActionError(UtilesExcepciones.stackTraceAcadena(ex, 1));
			return Action.ERROR;
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest requestParam) {
		request = requestParam;
	}

	@Override
	public void setServletResponse(HttpServletResponse responseParam) {
		response = responseParam;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

}