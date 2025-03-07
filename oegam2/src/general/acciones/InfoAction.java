package general.acciones;

import java.io.PrintWriter;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegamComun.mensajesProcesos.view.dto.MensajesProcesosDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * La clase está diseñada para recuperar vía AJAX, constantes con texto
 * explicativos de ciertas partes del aplicativo. Para cada texto se debe
 * declarar una constante y llamar a las función de Javascript dameInfo(),
 * pasándole en minúscula el nombre de la constante de la que queramos recuperar
 * su valor.
 * 
 * @author santiago.cuenca
 * @version 1.0
 *
 */
public class InfoAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	public static final String INFO_CREDITOS_BLOQUEADOS = "Los créditos bloqueados son aquellos que reflejan los trámites que aún están pendientes de envío o dieron error de servicio y deben reiniciarse o borrarse.";

	private HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	@Autowired
	private ServicioMensajesProcesos servicioMensajesProcesos;

	public void recuperar() throws Throwable {

		String nombreConstante = getServletRequest().getParameter("elemento") != null
				? getServletRequest().getParameter("elemento").toUpperCase()
				: null;

		Field constante = InfoAction.class.getDeclaredField(nombreConstante);
		String resultado = constante.get(nombreConstante).toString();

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();

		out.print(resultado);
	}

	public void recuperarModelos600601() throws Throwable {
		String codigo = getServletRequest().getParameter("elemento") != null
				? getServletRequest().getParameter("elemento").toUpperCase()
				: null;

		if (codigo != null && !codigo.equals("")) {
			MensajesProcesosDto mensajeDto = servicioMensajesProcesos.getMensaje("ESC" + codigo);

			if (mensajeDto != null) {
				codigo = mensajeDto.getDescripcion();

				servletResponse.setContentType("text/html; charset=iso-8859-1");
				PrintWriter out = servletResponse.getWriter();

				out.print(codigo);
			}
		}
	}

}