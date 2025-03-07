package trafico.acciones;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oegam.constantes.ConstantesSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import trafico.modelo.ModeloEvolucionTasa;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import escrituras.utiles.PaginatedListImpl;
import escrituras.utiles.UtilesVista;
import general.acciones.ActionBase;

public class TasaAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(TasaAction.class);
	private HttpServletRequest request;
	private HttpServletResponse response;

	//Lista de la consulta
	private PaginatedListImpl listaConsultaEvolucionTasa;

	//Variables de la paginación de la evolución.
	private PaginatedListImpl listaEstadosEvolucion;	//Lista que se va a mostrar en la vista es de tipo PaginatedList para el uso por displayTags
	private String page;								//Página a mostrar
	private String sort;								//Columna por la que se ordena
	private String dir;									//Orden de la ordenación
	private String resultadosPorPagina; 				//La cantidad de elementos a mostrar por página

	HashMap<String, Object> parametrosBusqueda;			//Se utiliza para asignar los parámetros de búsqueda al objeto DAO

	private String codTasa	;						//Recibirá el codTasa que se pasa por parámetro GET en la cabecera de la evolución de la tasa

	/**
	 * Método que mostrará el contenido de la evolucion de un vehículo en un 
	 * popup de la pestaña vehículo
	 * @return
	 * @throws Throwable 
	 */
	public String evolucion() throws Throwable {
		parametrosBusqueda = new HashMap<String, Object>();
		listaEstadosEvolucion = new PaginatedListImpl();
		ModeloEvolucionTasa modeloEvolucionTasa = new ModeloEvolucionTasa();

//		TramiteTraficoBean linea = new TramiteTraficoBean(true);

		// Colocar en la sessión las cosas básicas ya que es la primera vez
		getSession().put(ConstantesSession.RESULTADOS_PAGINA_EVOLUCION, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADOS);
		getSession().put(ConstantesSession.COD_TASA, codTasa);

		actualizaParametros(codTasa);

		listaEstadosEvolucion.setBaseDAO(modeloEvolucionTasa);
		listaEstadosEvolucion.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		listaEstadosEvolucion.establecerParametros("FECHA_HORA", "asc", "0", UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADOS);
		getSession().put(ConstantesSession.LISTA_EVOLUCION_TASA, listaEstadosEvolucion);

		log.info("Tasa Evolución.");

		return "evolucionTasa";
	}

	/**
	 * Función de búsqueda para la paginación. Generará la búsqueda limitada por los parámetros de
	 * paginación.
	 * @return
	 */
	public String navegar() {
		mantenimientoCamposNavegar();
		if (listaEstadosEvolucion == null) {
			log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"al obtener lista de estados de tasa.");
			log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+" navegar.");
			addActionError("Error al recuperar la lista de estados.");
			return "evolucionTasa";
		}

		listaEstadosEvolucion.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
		listaEstadosEvolucion.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);

		return "evolucionTasa";
	}

	/**
	 * Método que actualiza los parámetros de búsqueda a partir de un bean de búsqueda que le pasamos
	 */
	private void actualizaParametros(String idTasa){
		parametrosBusqueda = new HashMap<String, Object>();
		parametrosBusqueda.put(ConstantesSession.COD_TASA, codTasa);
	}

	/**
	 * Método para actualizar los parámetros de paginación. Proceso:
	 * 
	 * -Recupero el bean de búsqueda de sesión.
	 * -Actualizo los parámetros de búsqueda.
	 * -Actualizo en sesión los de la paginación.
	 * 
	 */
	private void mantenimientoCamposNavegar() {
		if (resultadosPorPagina != null) {
			getSession().put("resultadosPorPaginaEvolucion", resultadosPorPagina);
		}

		codTasa = (String) getSession().get(ConstantesSession.COD_TASA);

		setListaEstadosEvolucion((PaginatedListImpl)getSession().get(ConstantesSession.LISTA_EVOLUCION_TASA));
		setResultadosPorPagina((String)getSession().get(ConstantesSession.RESULTADOS_PAGINA_EVOLUCION));

		actualizaParametros(codTasa);
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public PaginatedListImpl getListaEstadosEvolucion() {
		return listaEstadosEvolucion;
	}

	public void setListaEstadosEvolucion(PaginatedListImpl listaEstadosEvolucion) {
		this.listaEstadosEvolucion = listaEstadosEvolucion;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
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

	public PaginatedListImpl getListaConsultaEvolucionTasa() {
		return listaConsultaEvolucionTasa;
	}

	public void setListaConsultaEvolucionTasa(
			PaginatedListImpl listaConsultaEvolucionTasa) {
		this.listaConsultaEvolucionTasa = listaConsultaEvolucionTasa;
	}

	public String getCodTasa() {
		return codTasa;
	}

	public void setCodTasa(String codTasa) {
		this.codTasa = codTasa;
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

}