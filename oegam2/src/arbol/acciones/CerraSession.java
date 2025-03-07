package arbol.acciones;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.viafirma.cliente.ViafirmaClient;
import org.viafirma.cliente.ViafirmaClientFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@SuppressWarnings("serial")
public class CerraSession extends ActionSupport implements ServletResponseAware {

	private static final ILoggerOegam log = LoggerOegam.getLogger(CerraSession.class);
	private HttpServletResponse response;
	HttpSession session;
	Boolean validate;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		try {
			log.info("execute de CerraSession");
			HttpServletRequest request = ServletActionContext.getRequest();
			Map application = ActionContext.getContext().getApplication();
			session = request.getSession();

			if (session != null) {
				session.invalidate();
				application.put("sessionInavalida", session);
				log.info("Sesion invalida");

				try{
					if ("false".equals(ServletActionContext.getServletContext().getInitParameter("AutenticacionSISTEMA"))) {
						return Action.ERROR;
					}
				} catch (Throwable t) {}

				ViafirmaClient viafirmaClient = ViafirmaClientFactory.getInstance();
				String urlRespuestaViafirma = gestorPropiedades.valorPropertie("url_respuesta");
				viafirmaClient.solicitarAutenticacion(request,response,urlRespuestaViafirma);
				return "applet";
			} else {
				return null;
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
	}

	@SuppressWarnings("unchecked")
	public String invalida() throws Exception {
		try {
			log.info("invalida de CerraSession");
			HttpServletRequest request = ServletActionContext.getRequest();
			session = request.getSession();
			Map application = ActionContext.getContext().getApplication();
			//session.invalidate
			if (session != null) {
				session.invalidate();
				application.put("sessionInavalida", session);
				log.info("Sesion invalidada");

				try{
					if ("false".equals(ServletActionContext.getServletContext().getInitParameter("AutenticacionSISTEMA"))) {
						return Action.ERROR;
					}
				} catch (Throwable t) {}

				ViafirmaClient viafirmaClient = ViafirmaClientFactory.getInstance();
				String urlRespuestaViafirma = gestorPropiedades.valorPropertie("url_respuesta");
				viafirmaClient.solicitarAutenticacion(request, response, urlRespuestaViafirma);
				return "applet";
			} else {
				return null;
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
	}

	public Boolean getValidate() {
		return validate;
	}
	public void setValidate(Boolean validate) {
		this.validate = validate;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}

}