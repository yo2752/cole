package org.gestoresmadrid.oegam2.pagination.controller.action;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import oegam.constantes.ConstantesAplicacion;
import trafico.beans.CriterioPaginatedListBean;
import utilidades.logger.ILoggerOegam;

/**
 * Action abstracto que aporta lógica de negocio para realizar consultas
 * paginadas mediante Spring y ModelPagination
 * 
 */
public abstract class AbstractPaginatedListAction extends ActionBase {

	private static final long serialVersionUID = -4238630142796832428L;

	// Lista que se va a mostrar en la vista. Es de tipo PaginatedList para el
	// uso por displayTag
	private PaginatedList lista;
	// Página a mostrar
	private int page;
	// Columna por la que se ordena
	private String sort;
	// Orden de la ordenación
	private String dir;
	// La cantidad de elementos a mostrar por página
	private String resultadosPorPagina;
	
	// boolean para saber si tenemos que buscar o no en el inicio (dependerá del Action de donde vengamos)
	private boolean busquedaInicial = true;

	// Criterio de ordenación ascendente (valor por defecto)
	private static final String ORDEN_ASC = "asc";
	private static final String ZERO = "0";

	/* ********************************************************* */
	/* Entradas al action */
	/* ********************************************************* */
	
	@Autowired
	UtilesColegiado utilesColegiado;

	/**
	 * Método por defecto de entrada al action, puede sobreescribirse o usar
	 * otro
	 * 
	 * @return
	 */
	public String inicio() {
		// Si se realizo una busqueda previamente, se recupera el filtro empleado
		Object beanCriterios = getSession().get(getKeyCriteriosSession());
		if (beanCriterios != null) {
			setBusquedaInicial(true);
			try {
				setBeanCriterios(beanCriterios);
			} catch (ClassCastException e) {
				getLog().error("Error recuperando el filtro del action. La clave \""
								+ getKeyCriteriosSession()
								+ "\" parece que está siendo reutilizada", e);
			}
		} else { //Si no hay filtro cargado, lo inicio.
			setBusquedaInicial(isBuscarInicio());
			cargarFiltroInicial();
		}
		return actualizarPaginatedList();
	}

	/**
	 * Método por defecto al que se llama desde el displayTable para paginar,
	 * ordenar... puede sobreescribirse o usar otro
	 * 
	 * @return
	 */
	public String navegar() {
		// Debe tener en cuenta el filtro almacenado en sesión y no el actual en pantalla
		setBusquedaInicial(true);
		try {
			setBeanCriterios(getSession().get(getKeyCriteriosSession()));
		} catch (ClassCastException e) {
			getLog().error("Error recuperando el filtro del action. La clave \""
							+ getKeyCriteriosSession()
							+ "\" parece que está siendo reutilizada", e);
		}
		return actualizarPaginatedList();
	}

	/**
	 * Método por defecto al que se llama desde el buscador, puede sobreescribirse o usar
	 * otro
	 * @return
	 */
	public String buscar() {
		setBusquedaInicial(true);
		return actualizarPaginatedList();
	}

	/* ************************************************ */
	/* Métodos                                          */
	/* ************************************************ */

	/**
	 * 
	 * @return
	 */
	protected String actualizarPaginatedList() {
		if(utilesColegiado.tienePermisoAdmin() || validarFechas(getBeanCriterios())){
			// Carga de JavaScripts y CSS en la página que se sirve
			cargarScriptsYCSS();

			// Aplica restriciones a los criterios de búsqueda
			cargaRestricciones();

			// Recupera la paginación y ordenación que estuviese establecida previamente (si lo estuviese)
			recoverCriterionPagination();

			// Obtiene la paginatedList mediante el modelo y los criterios recuperados
			if (isBusquedaInicial() || !utilesColegiado.tienePermisoAdmin()) {
				try {
					setLista(getModelo().buscarPag(getBeanCriterios(), Integer.parseInt(getResultadosPorPagina()), getPage(), getDir(), getSort(), getListInitializedOnePath(), getListInitializedAnyPath()));
				} catch (Exception e) {
					getLog().error("Ha habido un error recuperando el listado", e);
					addActionError("Debido a un error, no es posible mostrar resultados.");
				}
			}
		}
		return getResultadoPorDefecto();
	}

	/**
	 * Método que recupera, actualiza y guarda en sesión, los criterios de
	 * búsqueda para la paginación
	 */
	private void recoverCriterionPagination() {
		// Recuera el criterioPaginatedList guardado en sesion (si estuviera)
		CriterioPaginatedListBean criterioPaginatedList = (CriterioPaginatedListBean) getSession()
				.get(getKeyCriterioPaginatedList());
		if (criterioPaginatedList == null) {
			criterioPaginatedList = new CriterioPaginatedListBean();
		}

		// Actualiza los criterios de paginación
		if (sort != null && !sort.isEmpty()) {
			criterioPaginatedList.setSort(sort);
		} else {
			sort = criterioPaginatedList.getSort();
		}

		if (dir != null && !dir.isEmpty()) {
			criterioPaginatedList.setDir(dir);
		} else {
			dir = criterioPaginatedList.getDir();
		}
		if (page > 0) {
			criterioPaginatedList.setPage(page);
		} else {
			page = criterioPaginatedList.getPage();
		}

		// Actualiza el número de resultados por página, con lo guardado en
		// sesión, o con el número por defecto
		if (resultadosPorPagina == null || ZERO.equals(resultadosPorPagina)) {
			resultadosPorPagina = (String) getSession().get(getKeyResultadosPorPaginaSession());
		}
		if (resultadosPorPagina == null || ZERO.equals(resultadosPorPagina)) {
			resultadosPorPagina = ConstantesAplicacion.RESULTADOS_POR_PAGINA_POR_DEFECTO;
		}

		// Guarda en sesión la configuración obtenida
		getSession().put(getKeyCriterioPaginatedList(), criterioPaginatedList);
		getSession().put(getKeyCriteriosSession(), getBeanCriterios());
		getSession().put(getKeyResultadosPorPaginaSession(), resultadosPorPagina);

	}

	/* *************************************************************** */
	/* Métodos abstractos o con probabilidad de ser sobreescritos para */
	/* personalizar los actions con ModelPagination                    */
	/* *****************************************************************/

	/**
	 * Método que valida las fechas de las consultas
	 */
	public Boolean validarFechas(Object object) {
		return Boolean.TRUE;
	}

	/**
	 * Método que indica el orden por defecto en la paginación, se puede
	 * sobreescribir en la implementación del action
	 * 
	 * @return
	 */
	public String getOrdenPorDefecto() {
		return ORDEN_ASC;
	}

	/**
	 * Método que debe indicar la columna por la que se ordena por defecto. Si
	 * no se sobreescribe, por defecto no vendrá ordenado
	 * 
	 * @return
	 */
	public String getColumnaPorDefecto() {
		return null;
	}

	/**
	 * Resultado de struts, que retorna la paginacion
	 * @return
	 */
	protected abstract String getResultadoPorDefecto();

	/**
	 * Debe devolver la instancia de ModelPagination inyectada en el action
	 * 
	 * @return
	 */
	protected abstract ModelPagination getModelo();

	/**
	 * Clave usada para mantener en sesión el CriterioPaginatedListBean
	 * @return
	 */
	protected String getKeyCriterioPaginatedList(){
		return "keyCriterioParignatedList"+this.getClass().getName();
	}

	/**
	 * Clave usada para mantener en sesion el filtro de busqueda
	 * @return
	 */
	protected String getKeyCriteriosSession(){
		return "keyCriteriosSession"+this.getClass().getName();
	}

	/**
	 * Clave usada para mantener en sesion el número de resultados por pagina
	 * @return
	 */
	protected String getKeyResultadosPorPaginaSession(){
		return "keyResultadosPorPaginaSession"+this.getClass().getName();
	}

	/**
	 * Método abstracto que se usa para utilizar el mismo appender de log en
	 * todo el action
	 */
	protected abstract ILoggerOegam getLog();

	/**
	 * Si es necesario aportar un decorator a la tabla, sobreescribir este
	 * metodo en la implementación para devolver el nombre de la clase que
	 * implementa el decorator (ej.
	 * "org.gestoresmadrid.oegam2.view.decorator.DecoratorEvolucionVehiculo")
	 * 
	 * @return
	 */
	public String getDecorator() {
		Class<?> decorator = getClaseDecorator();
		return decorator==null?null:decorator.getName();
	}

	/**
	 * Este es el método que debe sobreescribirse. Devuelve la clase del decorator
	 * @return
	 */
	protected Class<?> getClaseDecorator() {
		return null;
	}

	/**
	 * Sirve para cargar los scripts (javascripts y css) que se pretenden
	 * mostrar). Aquí no lleva código, pero se puede sobreescribir en cada
	 * action que herede de éste.
	 */
	protected void cargarScriptsYCSS() {
		inicializarScripts();
	}

	/**
	 * Sirve para incluir restriciones en el filtro, del tipo comprobar si no es
	 * administrador que acceda a tramite de su numColegiado.
	 * 
	 */
	protected abstract void cargaRestricciones();
	
	/**
	 * Carga los parametros necesarios para la primera búsqueda.
	 */
	protected abstract void cargarFiltroInicial();

	/**
	 * Devuelve el Objeto empleado para guardar el filtro de pantalla
	 * 
	 * @return
	 */
	protected abstract Object getBeanCriterios();

	/**
	 * Establece el objeto empleado para guardar el filtro de pantalla
	 * 
	 * @param object
	 */
	protected abstract void setBeanCriterios(Object object);

	/**
	 * Metodo que devuelve la lista con los atributos que deben inicializarse en
	 * la busqueda, evitando lazzyExceptions. Cada path incluido debe coincidir
	 * con el nombre en la entidad (Ej.en VehiculoVO "persona.evolucionPersona"
	 * para traer las personas y la evolucion persona. Es de tipo protected por
	 * si necesita ser sobreescrito.
	 * 
	 * @return
	 */
	protected String[] getListInitializedOnePath() {
		return null;
	}

	/**
	 * Método que devuelve la lista con los atributos que deben inicializarse en
	 * la busqueda, evitando lazzyExceptions. Cada path incluido debe coincidir
	 * con el nombre en la entidad (Ej.en VehiculoVO "persona.evolucionPersona"
	 * para traer las personas y la evolucion persona. Es de tipo protected por
	 * si necesita ser sobreescrito.
	 * 
	 * @return
	 */
	protected String[] getListInitializedAnyPath() {
		return null;
	}
	
	/**
	 * Devuelve el nombre el action al que se llama desde la vista, y que está definido en struts.xml.
	 * Por defecto es el nombre de la clase, puede puede sobrescribirse si fuese otro.
	 * @return
	 */
	public String getAction(){
		return this.getClass().getSimpleName().replace("Action", "");
	}

	/**
	 * Para realizar la búsqueda en el inicio
	 * Si no se quiere realizar la búsqueda, sobreescribir este método en el Action devolviendo false.
	 * @return
	 */
	protected boolean isBuscarInicio() {
		return true;
	}

	/* **************************************************** */
	/* Getters and setters                                  */
	/* **************************************************** */

	public PaginatedList getLista() {
		return lista;
	}

	public void setLista(PaginatedList lista) {
		this.lista = lista;
	}

	public int getPage() {
		return page > 0 ? page : 1;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort != null && !"".equals(sort) ? sort : getColumnaPorDefecto();
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir != null && !"".equals(dir) ? dir : getOrdenPorDefecto();
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getResultadosPorPagina() {
		try {
			return resultadosPorPagina != null ? Integer.valueOf(resultadosPorPagina).toString() : ZERO;
		} catch (NumberFormatException e) {
			return ZERO;
		}
	}

	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public boolean isBusquedaInicial() {
		return busquedaInicial;
	}

	public void setBusquedaInicial(boolean busquedaInicial) {
		this.busquedaInicial = busquedaInicial;
	}

}