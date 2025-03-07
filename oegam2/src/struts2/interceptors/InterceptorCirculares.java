package struts2.interceptors;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.oegam2comun.circular.model.service.ServicioCircular;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ResultadoCircularBean;
import org.gestoresmadrid.oegam2comun.circular.view.dto.CircularDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class InterceptorCirculares implements Interceptor{

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(InterceptorCirculares.class);

	@Autowired
	ServicioCircular servicioCircular;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesColegiado utilesColegiado;

	public void destroy() {

	}

	public void init() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession(true);
		if (!actionInvocation.getAction().toString().contains("RegistrarAction")) {
			if ("SI".equals(gestorPropiedades.valorPropertie("comprobar.circulares"))
					&& session.getAttribute(Claves.CLAVE_GESTOR_ARBOL) != null) {
				ResultadoCircularBean resultado = servicioCircular.gestionarCircularesContrato(utilesColegiado.getIdContratoSession(), Boolean.TRUE);
				if (!resultado.getError()) {
					CircularDto circularDto = resultado.getCircular();
					resultado = servicioCircular.aceptarCircular(circularDto, utilesColegiado.getIdContratoSession(), utilesColegiado.getIdUsuarioSession());
					if (!resultado.getError()) {
						session.setAttribute("textoCircular", circularDto.getTexto());
						session.setAttribute("circularActiva", "SI");
					} else {
						session.setAttribute("circularActiva", "NO");
						session.setAttribute("textoCircular", null);
					}
				} else {
					session.setAttribute("circularActiva", "NO");
					session.setAttribute("textoCircular", null);
				}
			} else {
				session.setAttribute("circularActiva", "NO");
				session.setAttribute("textoCircular", null);
			}
		}
		return actionInvocation.invoke();
	}

}