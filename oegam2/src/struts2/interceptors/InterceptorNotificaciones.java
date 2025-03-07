package struts2.interceptors;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class InterceptorNotificaciones implements Interceptor {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(InterceptorNotificaciones.class);

	public void destroy() {
	}

	public void init() {
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession(true);
		Long ultimo = (Long)session.getAttribute("ultimoAcceso");
		if (ultimo != null) {
			int dif = Long.valueOf(session.getLastAccessedTime()).intValue() - ultimo.intValue();
			if (dif > session.getMaxInactiveInterval()*1000) {
				log.info("Sesion " + session.getId() + " expirada manualmente desde las notificaciones");
				session.invalidate();
				session.setAttribute("sesion","expirada");
			}
		}
		return actionInvocation.invoke();
	}

}