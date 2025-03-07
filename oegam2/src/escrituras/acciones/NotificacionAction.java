package escrituras.acciones;

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

public class NotificacionAction  extends ActionBase implements
ServletRequestAware, ServletResponseAware{

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

	public String verTodas()  throws Throwable{
		return "verTodas";
	}

/*	public String navegar() {
		asignarCamposDesdeSession();
		if (getListaNotificacion() == null)
			return ERROR;

		getListaNotificacion().establecerParametros(getSort(), getDir(),
				getPage(), getResultadosPorPagina());
		getListaNotificacion().getBaseDAO().setParametrosBusqueda(
				parametrosBusqueda);
		return "listaNotificaciones";
	}
	*/
	/*private void asignarCamposDesdeSession() {
		 GestorArbol gestorArbol = (GestorArbol) getSession().get(
					Claves.CLAVE_GESTOR_ARBOL);

		String resultadosPorPagina;
		if ((resultadosPorPagina = getResultadosPorPagina()) != null) {
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, resultadosPorPagina);
		}

		* PARAMETROS DE BUSQUEDA

		// Se inserta directo porque nunca debería ser nulo
		setListaNotificacion((PaginatedListImpl) getSession().get(ConstantesSession.LISTA_NOTIFICACION));
		setResultadosPorPagina((String) getSession().get(ConstantesSession.RESULTADOS_PAGINA));

		// String numColegiado = (String)getSession().get(NUM_COLEGIADO);

		parametrosBusqueda = new HashMap<String, Object>();
		parametrosBusqueda.put(ConstantesSession.ID_USUARIO, gestorArbol.getUsuario().getId_usuario());
	}
	*/

	/*public String buscar(boolean busquedaInicial) throws Throwable {
		GestorArbol gestorArbol = (GestorArbol) getSession().get(
					Claves.CLAVE_GESTOR_ARBOL);
			ModeloNotificacion notificacionDAO = new ModeloNotificacion(gestorArbol
					.get());
		if (busquedaInicial) {
			// Inicializacione
			limpiarCamposSession();
			setListaNotificacion(new PaginatedListImpl());

			getListaNotificacion().setBaseDAO(notificacionDAO);
			parametrosBusqueda = new HashMap<String, Object>();
			parametrosBusqueda.put(ConstantesSession.ID_USUARIO, gestorArbol.getUsuario().getId_usuario());

			// Usuario usuario = gestorArbol.getUsuario();
			// parametrosBusqueda.put(NUM_COLEGIADO,
			// usuario.getNum_colegiado());
			// getSession().put(NUM_COLEGIADO, usuario.getNum_colegiado());

			// Colocar en la sessión las cosas básicas ya que es la primera vez
			getSession().put(ConstantesSession.LISTA_NOTIFICACION, getListaNotificacion());
			getSession().put(ConstantesSession.RESULTADOS_PAGINA,
					UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);
		} else {
			asignarCamposDesdeSession();
			if (getListaNotificacion() == null)
				return ERROR;
		}
		getListaNotificacion().setPaginaInicial();
		getListaNotificacion().getBaseDAO().setParametrosBusqueda(
				parametrosBusqueda);

		return "listaNotificaciones";
	}*/
	public String update() throws Throwable {
		String idUsuarioParametro = null;
		try {
			idUsuarioParametro = ServletActionContext.getRequest().getParameter("idUsuario");
		} catch(Exception e) {
			idUsuarioParametro = null;
		}

		BigDecimal renovar = new BigDecimal(0);
		if (idUsuarioParametro!=null) {
			BigDecimal idUsuario = new BigDecimal(idUsuarioParametro);
			renovar= ModeloNotificaciones.notificar(idUsuario);
		}
		if(renovar.equals(new BigDecimal(0))){
			setColorSeleccionado("yellow");
		}else{
			setColorSeleccionado("red");
		}
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.print(getColorSeleccionado());

		return null;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}
	public String getColorSeleccionado() {
		return colorSeleccionado;
	}
	public void setColorSeleccionado(String colorSeleccionado) {
		this.colorSeleccionado = colorSeleccionado;
	}
	public HttpServletResponse getServletResponse() {
		return servletResponse;
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