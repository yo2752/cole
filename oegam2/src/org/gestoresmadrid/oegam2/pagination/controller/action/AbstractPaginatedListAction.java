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
 * Action abstracto que aporta l�gica de negocio para realizar consultas
 * paginadas mediante Spring y ModelPagination
 * 
 */
public abstract class AbstractPaginatedListAction extends ActionBase {

	private static final long serialVersionUID = -4238630142796832428L;

	// Lista que se va a mostrar en la vista. Es de tipo PaginatedList para el
	// uso por displayTag
	private PaginatedList lista;
	// P�gina a mostrar
	private int page;
	// Columna por la que se ordena
	private String sort;
	// Orden de la ordenaci�n
	private String dir;
	// La cantidad de elementos a mostrar por p�gina
	private String resultadosPorPagina;
	
	// boolean para saber si tenemos que buscar o no en el inicio (depender� del Action de donde vengamos)
	private boolean busquedaInicial = true;

	// Criterio de ordenaci�n ascendente (valor por defecto)
	private static final String ORDEN_ASC = "asc";
	private static final String ZERO = "0";

	/* ********************************************************* */
	/* Entradas al action */
	/* ********************************************************* */
	
	@Autowired
	UtilesColegiado utilesColegiado;

	/**
	 * M�todo por defecto de entrada al action, puede sobreescribirse o usar
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
								+ "\" parece que est� siendo reutilizada", e);
			}
		} else { //Si no hay filtro cargado, lo inicio.
			setBusquedaInicial(isBuscarInicio());
			cargarFiltroInicial();
		}
		return actualizarPaginatedList();
	}

	/**
	 * M�todo por defecto al que se llama desde el displayTable para paginar,
	 * ordenar... puede sobreescribirse o usar otro
	 * 
	 * @return
	 */
	public String navegar() {
		// Debe tener en cuenta el filtro almacenado en sesi�n y no el actual en pantalla
		setBusquedaInicial(true);
		try {
			setBeanCriterios(getSession().get(getKeyCriteriosSession()));
		} catch (ClassCastException e) {
			getLog().error("Error recuperando el filtro del action. La clave \""
							+ getKeyCriteriosSession()
							+ "\" parece que est� siendo reutilizada", e);
		}
		return actualizarPaginatedList();
	}

	/**
	 * M�todo por defecto al que se llama desde el buscador, puede sobreescribirse o usar
	 * otro
	 * @return
	 */
	public String buscar() {
		setBusquedaInicial(true);
		return actualizarPaginatedList();
	}

	/* ************************************************ */
	/* M�todos                                          */
	/* ************************************************ */

	/**
	 * 
	 * @return
	 */
	protected String actualizarPaginatedList() {
		if(utilesColegiado.tienePermisoAdmin() || validarFechas(getBeanCriterios())){
			// Carga de JavaScripts y CSS en la p�gina que se sirve
			cargarScriptsYCSS();

			// Aplica restriciones a los criterios de b�squeda
			cargaRestricciones();

			// Recupera la paginaci�n y ordenaci�n que estuviese establecida previamente (si lo estuviese)
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
	 * M�todo que recupera, actualiza y guarda en sesi�n, los criterios de
	 * b�squeda para la paginaci�n
	 */
	private void recoverCriterionPagination() {
		// Recuera el criterioPaginatedList guardado en sesion (si estuviera)
		CriterioPaginatedListBean criterioPaginatedList = (CriterioPaginatedListBean) getSession()
				.get(getKeyCriterioPaginatedList());
		if (criterioPaginatedList == null) {
			criterioPaginatedList = new CriterioPaginatedListBean();
		}

		// Actualiza los criterios de paginaci�n
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

		// Actualiza el n�mero de resultados por p�gina, con lo guardado en
		// sesi�n, o con el n�mero por defecto
		if (resultadosPorPagina == null || ZERO.equals(resultadosPorPagina)) {
			resultadosPorPagina = (String) getSession().get(getKeyResultadosPorPaginaSession());
		}
		if (resultadosPorPagina == null || ZERO.equals(resultadosPorPagina)) {
			resultadosPorPagina = ConstantesAplicacion.RESULTADOS_POR_PAGINA_POR_DEFECTO;
		}

		// Guarda en sesi�n la configuraci�n obtenida
		getSession().put(getKeyCriterioPaginatedList(), criterioPaginatedList);
		getSession().put(getKeyCriteriosSession(), getBeanCriterios());
		getSession().put(getKeyResultadosPorPaginaSession(), resultadosPorPagina);

	}

	/* *************************************************************** */
	/* M�todos abstractos o con probabilidad de ser sobreescritos para */
	/* personalizar los actions con ModelPagination                    */
	/* *****************************************************************/

	/**
	 * M�todo que valida las fechas de las consultas
	 */
	public Boolean validarFechas(Object object) {
		return Boolean.TRUE;
	}

	/**
	 * M�todo que indica el orden por defecto en la paginaci�n, se puede
	 * sobreescribir en la implementaci�n del action
	 * 
	 * @return
	 */
	public String getOrdenPorDefecto() {
		return ORDEN_ASC;
	}

	/**
	 * M�todo que debe indicar la columna por la que se ordena por defecto. Si
	 * no se sobreescribe, por defecto no vendr� ordenado
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
	 * Clave usada para mantener en sesi�n el CriterioPaginatedListBean
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
	 * Clave usada para mantener en sesion el n�mero de resultados por pagina
	 * @return
	 */
	protected String getKeyResultadosPorPaginaSession(){
		return "keyResultadosPorPaginaSession"+this.getClass().getName();
	}

	/**
	 * M�todo abstracto que se usa para utilizar el mismo appender de log en
	 * todo el action
	 */
	protected abstract ILoggerOegam getLog();

	/**
	 * Si es necesario aportar un decorator a la tabla, sobreescribir este
	 * metodo en la implementaci�n para devolver el nombre de la clase que
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
	 * Este es el m�todo que debe sobreescribirse. Devuelve la clase del decorator
	 * @return
	 */
	protected Class<?> getClaseDecorator() {
		return null;
	}

	/**
	 * Sirve para cargar los scripts (javascripts y css) que se pretenden
	 * mostrar). Aqu� no lleva c�digo, pero se puede sobreescribir en cada
	 * action que herede de �ste.
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
	 * Carga los parametros necesarios para la primera b�squeda.
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
	 * M�todo que devuelve la lista con los atributos que deben inicializarse en
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
	 * Devuelve el nombre el action al que se llama desde la vista, y que est� definido en struts.xml.
	 * Por defecto es el nombre de la clase, puede puede sobrescribirse si fuese otro.
	 * @return
	 */
	public String getAction(){
		return this.getClass().getSimpleName().replace("Action", "");
	}

	/**
	 * Para realizar la b�squeda en el inicio
	 * Si no se quiere realizar la b�squeda, sobreescribir este m�todo en el Action devolviendo false.
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