package escrituras.utiles;

import general.modelo.ModeloBaseIF;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import utilidades.estructuras.ListaRegistros;


/**
 * Implementación de una lista para el DisplayTag.
 * @author TB·Solutions
 *
 */
public class PaginatedListImpl implements PaginatedList {

	private static final String PAGINA_1 = "1";
	private static final String ORDEN_ASC = "asc";
	
	//private static ILog log = ConfigurationManager.getLog();	//Log
	
	//PropertiesBean properties = (PropertiesBean)GeneradorBeanSpring.getBean(Constantes.ID_BEAN_PROPIEDADES);
	
	private HttpSession session;
	
	private ArrayList<Object> lista;	//Lista de elementos a mostrar en la página actual
	
	private int index;					//Indice de la página a mostrar (0..N)
	private String sortColumn;			//Columna por la que se ha ordenado la búsqueda
	private SortOrderEnum order;		//Orden por el que se ha ordenado la búsqueda
	private int numeroElementosPagina;	//Numero de elementos a mostrar por página
	private boolean hayPaginacion;		//Indica si hay o no paginación en caso que se necesite en un futuro una vista sin paginación
	private boolean busquedaInicial;	//Indica si ya se buscó anteriormente esta lista en el tamanoLista
	private String columnaOrdenar;			//Columna por la que se ha ordenado la búsqueda
	
	private ModeloBaseIF baseDAO;
	private int tamanoLista;
	
	/**
	 * Construcutor
	 * @throws Throwable 
	 */
	public PaginatedListImpl() throws Throwable{
		setPaginaInicial();
		setOrder(SortOrderEnum.ASCENDING);
		setNumeroElementosPagina(Integer.parseInt(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO));
		hayPaginacion=true;
		busquedaInicial=false;
	}
	
	@Override
	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getObjectsPerPage()
	 */
	public int getObjectsPerPage() {
		return getNumeroElementosPagina();
	}

	
	@Override
	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getPageNumber()
	 */
	public int getPageNumber() {
		return index+1;
	}

	@Override
	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getSearchId()
	 */
	public String getSearchId() {
		return null;
	}

	@Override
	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getSortCriterion()
	 */
	public String getSortCriterion() {
		return sortColumn;
	}

	@Override
	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getSortDirection()
	 */
	public SortOrderEnum getSortDirection() {
		return order;
	}
	
	@Override
	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getFullListSize()
	 */
	public int getFullListSize() {
		return tamanoLista;
	}
	
	public int tamanoLista() {
		return tamanoLista;
	}
	
	@Override
	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getList()
	 */
	public List<Object> getList() {

		if(!busquedaInicial){
			//Si la lista es nula porque no busco el tamano primero Se llama al método del DAO que devuelve la lista de los objetos pertinentes a mostrar en la vista
			ListaRegistros listaRegistros = null;
			if(hayPaginacion){	
				listaRegistros = this.baseDAO.listarTabla(getPageNumber(), numeroElementosPagina, order, columnaOrdenar);
			}
			else{
				listaRegistros = this.baseDAO.listarTabla(0, null, order, columnaOrdenar);
			}
			lista = (ArrayList<Object>)listaRegistros.getLista();
			tamanoLista = listaRegistros.getTamano();
			asignarVariablesSession(listaRegistros);
		}

		if(!hayPaginacion){
			setNumeroElementosPagina(tamanoLista);
		}
		
		return lista;
	}

	public int calculaTamanoListaBD(){
		//Se llama a un método del DAO que devuelve la lista de los objetos pertinentes a mostrar en la vista
		ListaRegistros listaRegistros = null;
		if(hayPaginacion){	
			listaRegistros = this.baseDAO.listarTabla(getPageNumber(), numeroElementosPagina, order, columnaOrdenar);
		}
		else{
			listaRegistros = this.baseDAO.listarTabla(getPageNumber(), null, order, columnaOrdenar);
		}
		lista = (ArrayList<Object>)listaRegistros.getLista();
		tamanoLista = listaRegistros.getTamano();
		asignarVariablesSession(listaRegistros);
		busquedaInicial=true;
		
		return tamanoLista;
	}
	
	/**
	 * Dados los parámetros necesarios para mostrar una página se comprueba si ha cambiado la ordenación.
	 * En caso de que haya cambiado algún parámetro de ordenación, se actualiza el searchBean y se le establece
	 * al sortable list, para realizar una nueva petición de datos.
	 * Finalmente se actualizan los parámetros del propio objeto para mostrar correctamten el DisplayTag
	 * 
	 * @param columnaOrdenar
	 * @param orden
	 * @param pagina
	 */
	public void establecerParametros(String columnaOrdenar, String orden, String pagina, String resultadosPorPagina){
		
		//Establecer los parametros de la página a mostrar
		
		int resultadosNuevos = 0;
		try{
			resultadosNuevos = Integer.parseInt(resultadosPorPagina);
		}catch (Exception e) {
			resultadosNuevos = Integer.parseInt(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);
		}
		
		if(resultadosNuevos>getNumeroElementosPagina()){
			int cambiarPagina = (getNumeroElementosPagina()*getPageNumber())/resultadosNuevos;
			if(cambiarPagina>0)
				pagina = String.valueOf(cambiarPagina);
			else
				pagina = PAGINA_1;
		}
		setNumeroElementosPagina(resultadosNuevos);
		
		if (pagina != null && !"".equals(pagina)){
			setPage(pagina);
		}
		
		if (columnaOrdenar != null && !"".equals(columnaOrdenar)){
			setSortColumn(columnaOrdenar);
			this.columnaOrdenar = nombreBusquedaColumna(columnaOrdenar);
		}
		
		if ( orden!=null && !"".equals(orden)){
			if(orden.equals(ORDEN_ASC)){
				setOrder(SortOrderEnum.ASCENDING);
			}
			else{
				setOrder(SortOrderEnum.DESCENDING);
			}
		}
	}

	/**
	 * Método que pasándole el campo de búsqueda determina el campo que hay que pasar a la base de datos
	 * para realizar la ordenación de la tabla correctamente.
	 * @param columnaOrdenar2
	 * @return
	 */
	
	private String nombreBusquedaColumna(String columnaOrdenar2) {
		String columnaCorrecta = "";
		columnaCorrecta = columnaOrdenar2;
		if(columnaOrdenar2.contains(".")){
			if(columnaOrdenar2.contains(".nombreEnum")||columnaOrdenar2.contains(".valorEnum")){
				columnaCorrecta = columnaOrdenar2.substring(0,columnaOrdenar2.lastIndexOf("."));
			}
			else{
				columnaCorrecta = columnaOrdenar2.substring(columnaOrdenar2.lastIndexOf(".")+1);	
			}
			
		}
		return columnaCorrecta;
	}

	/**
	 * Establecer la página a mostrar
	 * @param page
	 */
	public void setPage(String page) {
		if(page==null){
			index = 0;
		}else{
			index = Integer.parseInt(page)-1;
		}
	}
	
	public void setPage(int page) {
		setPage(String.valueOf(page));
	}
	
	public void setPaginaInicial(){
		this.setPage(PAGINA_1);
	}
	
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setOrder(SortOrderEnum order) {
		this.order = order;
	}
	
//	public void setNumeroElementosPagina(String numeroElementosPagina) {
//		try{
//			this.numeroElementosPagina = Integer.parseInt(numeroElementosPagina);
//		}
//		catch (Exception e) {
//			this.numeroElementosPagina = Integer.parseInt(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);
//		}
//	}
	
	public void setNumeroElementosPagina(int numeroElementosPagina) {
		if(numeroElementosPagina>0)
			this.numeroElementosPagina = numeroElementosPagina;
		else
			this.numeroElementosPagina = Integer.parseInt(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);
	}

	public int getNumeroElementosPagina(){
		return this.numeroElementosPagina;
	}

	public void setHayPaginacion(boolean hayPaginacion) {
		this.hayPaginacion = hayPaginacion;
	}

	public boolean isHayPaginacion() {
		return hayPaginacion;
	}

	public ModeloBaseIF getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(ModeloBaseIF baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * Valida que la página actual no sea mayor que la máxima que puede tener en el rango establecido
	 * Ejm: Estoy en la página 35 de 50 mostrando 5 registros. Cambio a mostrar 10 registros por página ahora el tam máximo de página sería 25
	 * Se debe llamar a esta función solo si es estrictamente necesario
	 */

	@SuppressWarnings("unused")
	private void validarPaginaMaxima(){
		
		int paginaMaxima = 0;
		if(getNumeroElementosPagina()>=getFullListSize()){
			setPaginaInicial();
		}
		else{
			int restoPaginas = getFullListSize()%getNumeroElementosPagina();
			if(restoPaginas==0){
				paginaMaxima = getFullListSize()/getNumeroElementosPagina();
			}
			else{
				paginaMaxima = getFullListSize()/getNumeroElementosPagina()+1;
			}
			
			if(getPageNumber()>paginaMaxima){
				setPage(paginaMaxima);
			}
		}
		
	}

	public void asignarVariablesSession(ListaRegistros listaRegistros){
		Map<String,Object> mapa = listaRegistros.getMapa();
		setSession(ServletActionContext.getRequest().getSession(true));
		if(mapa!=null){
			Set<String> keys = mapa.keySet();
			for (String key : keys) {
				if (mapa.get(key) != null){
					getSession().setAttribute(key, mapa.get(key));
				}
			}
		}
	}
	
	public void setSession(HttpSession session) {
		this.session = session;
	}
	
	public HttpSession getSession() {
		return session;
	}

}