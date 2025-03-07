package administracion.cache.servlet;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public abstract class RecargaCacheServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private static final String PROPERTY_IP = "recargar.cache.ip.permitidas";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public RecargaCacheServlet() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected boolean validarAcceso(HttpServletRequest request) {
		//TODO ASG: se deja abierto a futuras comprobaciones de seguridad
		return ipValida(request.getRemoteAddr());
	}

	private boolean ipValida(String ip) {
		boolean esValida = false;
		for (String ipAux : getIpPermitidas()) {
			if (ipAux.equals(ip)) {
				esValida = true;
				break;
			}
		}
		return esValida;
	}

	private List<String> getIpPermitidas() {
		String[] rawList = gestorPropiedades.valorPropertie(PROPERTY_IP).split(";");
		return Arrays.asList(rawList);
	}
}