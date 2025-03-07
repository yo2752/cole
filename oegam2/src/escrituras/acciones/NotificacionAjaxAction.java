package escrituras.acciones;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import escrituras.utiles.PaginatedListImpl;
import general.acciones.ActionBase;
import general.modelo.ModeloNotificaciones;

public class NotificacionAjaxAction extends ActionBase implements
ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	private String colorSeleccionado;
	private PaginatedListImpl listaNotificacion;
	private String page; // Página a mostrar
	private String sort; // Columna por la que se ordena
	private String dir; // Orden de la ordenación
	private String resultadosPorPagina; // La cantidad de elementos a mostrar
										// por página

	HashMap<String, Object> parametrosBusqueda; // Se u

	public String verTodas() throws Throwable {
		return "verTodas";
	}

	public void update() {
		String idUsuarioParametro = null;
		try {
			idUsuarioParametro = ServletActionContext.getRequest().getParameter("idUsuario");
		} catch(Exception e) {
			idUsuarioParametro = null;
		}

		BigDecimal renovar = new BigDecimal(0);
		if (idUsuarioParametro != null) {
			BigDecimal idUsuario = new BigDecimal(idUsuarioParametro);
			renovar = ModeloNotificaciones.notificar(idUsuario); 
		}

		setColorSeleccionado(renovar.equals(new BigDecimal(0)) ? "yellow" : "red");

		ServletActionContext.getResponse().setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = ServletActionContext.getResponse().getWriter();
			out.print(getColorSeleccionado());
		} catch (IOException e) {
		}
	}

	private HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public String getColorSeleccionado() {
		return colorSeleccionado;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public void setColorSeleccionado(String colorSeleccionado) {
		this.colorSeleccionado = colorSeleccionado;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public HashMap<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	public void setParametrosBusqueda(HashMap<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

}