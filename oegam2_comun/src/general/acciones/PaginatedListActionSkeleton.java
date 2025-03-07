package general.acciones;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;
import oegam.constantes.ConstantesAplicacion;
import trafico.beans.CriterioPaginatedListBean;
import utilidades.logger.ILoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 *
 * Action genérico para los buscadores. Cualquier action que tenga un buscador, debe extender de este.
 * @author ext_mpc
 *
 * @param <T> Se corresponde con el bean de pantalla, pero no se está utilizando como debería ser, ya que se usa el de la factoría. En una versión posterior debe eliminarse.
 */
public abstract class PaginatedListActionSkeleton<T> extends ActionBase implements ServletRequestAware, SessionAware {

		private static final long serialVersionUID = 2493821010144061437L;
		
		/**
		 * Factoria concreta asociada al action, y por tanto, al buscador.
		 */
		protected AbstractFactoryPaginatedList factoria;
		
		/* ********************** Parámetros de las peticiones del displayTag ************ */
		/**
		 * Lista que se va a mostrar en la vista. Es de tipo PaginatedList para el uso por displayTag
		 */
		protected PaginatedList lista;
		/**
		 * Página a mostrar
		 */
		protected int page;
		/**
		 * Columna por la que se ordena
		 */
		protected String sort;
		/**
		 * Orden de la ordenación
		 */
		protected String dir;
		/**
		 * La cantidad de elementos a mostrar por página
		 */
		protected String resultadosPorPagina;
		/**
		 * Criterio de ordenación ascendente (valor por defecto)
		 */
		public static final String ORDEN_ASC = "asc";
		/**
		 * Criterio de ordenación descente
		 */
		public static final String ORDEN_DESC = "desc";
		
		/* ********************** Fin de los parámetros de las peticiones ************ */
		
		
		/**
		 * Modelo utilizado para realizar la búsqueda.
		 */
		protected ModeloSkeletonPaginatedList modelo;
		
		/**
		 * Bean con los criterios de la búsqueda
		 */
		protected T beanCriterios;
		
		/**
		 * boolean para saber si tenemos que buscar o no en el inicio (dependerá del Action de donde vengamos)
		 */
		protected boolean busquedaInicial = true;

		
		@Autowired
		private UtilesColegiado utilesColegiado;
		
		/* ****************************************************/
		/* *				Constructor							**/
		/* ****************************************************/

		public PaginatedListActionSkeleton(){
			generaAbstractFactoryPaginatedList();
			this.beanCriterios = ((T)getFactoria().crearCriterios()); //Indicamos de que clase es T.
		}
		
		/* ****************************************************/
		/* * 			Getters and setters 				**/
		/* ****************************************************/
		
		public void generaAbstractFactoryPaginatedList(){
			getFactoria();
			if (modelo == null){
				modelo = factoria.crearModelo();
			}
		}
		
		/**
		 * Devuelve la factoria a utilizar para el action correspondiente
		 * @return
		 */
		public abstract AbstractFactoryPaginatedList getFactory();
		
		public PaginatedList getLista() {
			return lista;
		}
		
		public void setLista(PaginatedList lista) {
			this.lista = lista;
		}
		
		public int getPage() {
			if (page>0){
				return page;
			}
			return 1;
		}
		
		public void setPage(int page) {
			this.page = page;
		}
		
		public String getSort() {
			if (sort!=null && !"".equals(sort)){
				return sort;			
			}else{
				return getColumnaPorDefecto();
			}
		}
		
		public void setSort(String sort) {
			this.sort = sort;
		}
		
		public String getDir() {
			if (dir!=null && !"".equals(dir)){
				return dir;
			}else{
				return getOrdenPorDefecto();
			}
		}
		
		public void setDir(String dir) {
			this.dir = dir;
		}
		
		public String getResultadosPorPagina() {
			try{
				if (resultadosPorPagina!=null){
					return Integer.valueOf(resultadosPorPagina).intValue()+"";			
				}else{
					return "0";
				}
			} catch (NumberFormatException e){
				return "0";
			}
		}
		
		public void setResultadosPorPagina(String resultadosPorPagina) {
			this.resultadosPorPagina = resultadosPorPagina;
		}
		
		/**
		 * Devuelve la columna por defecto que se utiliza para ordenar
		 * @return
		 */
		public abstract String getColumnaPorDefecto();
		
		public String getOrdenPorDefecto() {
			return ORDEN_ASC;
		}
		
		/**
		 * Devuelve el resultado por defecto que espera struts. Se corresponde al resultado que se dirige a la pantalla del buscador.
		 * Según como está montado OEGAM, debería de ser la constante "SUCCESS".
		 * @return
		 */
		public abstract String getResultadoPorDefecto();


		public ModeloSkeletonPaginatedList getModelo(){
			return modelo;
		}

		public void setModelo(ModeloSkeletonPaginatedList modelo) {
			this.modelo = modelo;
		}
		
		/**
		 * Nombre con el que se guardará en sesión los criterios del buscador (campo orden, dirección,...)
		 * @return
		 */
		public abstract String getCriterioPaginatedList();

		/**
		 * Nombre con el que se guardará en sesión los criterios de búsqueda (el bean de busqueda).
		 * @return
		 */
		public abstract String getCriteriosSession();
		
		/**
		 * Nombre con el que se guardará en sesión el número de elementos a mostrar en este buscador.
		 * @return
		 */
		public abstract String getResultadosPorPaginaSession();
			
		public T getBeanCriterios(){
			return beanCriterios;
		}

		public void setBeanCriterios (T beanCriterios){
			this.beanCriterios=beanCriterios;
		}
		
		/** Log **/
		protected abstract ILoggerOegam getLog();

		@Override
		public void setServletRequest(HttpServletRequest request) {}
		
		/* ********************************************************************/
		/* * 					Métodos 									**/
		/* ********************************************************************/
		/**
		 * Método al que se llama por primera vez al acceder al buscador. Carga los datos por defecto.
		 * @return
		 */
		public String inicio() {
			beanCriterios = null; //Al indicar de que tipo es T, se ha creado el objeto ficticiamente, por lo que se borra.
			setBusquedaInicial(true);
			actualizarPaginatedList();
			return getResultadoPorDefecto(); 
		}

		/**
		 * Genera el paginatedList
		 */
		protected void rellenarCriteriosLista(){
			resultadosPagina();
			try {
				setLista(getFactory().crearModelo().buscarPag((BeanCriteriosSkeletonPaginatedList)getBeanCriterios(), Integer.parseInt(getResultadosPorPagina()), getPage(), getDir(), getSort()));
			} catch (OegamExcepcion e) {
				getLog().error("[ "+getClass()+" ]Ha habido un error en el método LlenarLista");
				getLog().error(e.getMessage());
			} catch (Exception e) {
				getLog().error("[ "+getClass()+" ]Ha habido un error en el método LlenarLista");
				getLog().error(e.getMessage());
			}
		}

		/**
		 * Prepara el número de elementos a mostrar, o bien por indicación, o bien porque está en sesión, o bien devolviendo un valor por defecto.
		 */
		protected void resultadosPagina(){ 		
			if(resultadosPorPagina!=null){			
				getSession().put(getResultadosPorPaginaSession(),resultadosPorPagina); 
			}else{
				resultadosPorPagina= (String)getSession().get(getResultadosPorPaginaSession()); 
				if (resultadosPorPagina==null){
					resultadosPorPagina =  ConstantesAplicacion.RESULTADOS_POR_PAGINA_POR_DEFECTO;
					getSession().put(getResultadosPorPaginaSession(), ConstantesAplicacion.RESULTADOS_POR_PAGINA_POR_DEFECTO);
				}
			}
		}

		/**
		 * Prepara la recarga del buscador
		 * @return
		 */
		public String recargarPaginatedList(){
			beanCriterios = null;
			setBusquedaInicial(true);
			return actualizarPaginatedList();
		}
		
		/**
		 * Actualiza el paginatedlist
		 * @return
		 */
		public String actualizarPaginatedList(){
			cargarScriptsYCSS();
			if (null == getSession().get(getCriteriosSession())){
				/* Se inicializa el bean con los criterios inciales de la búsqueda */
				setBeanCriterios ((T)getFactoria().crearCriterios());
				((BeanCriteriosSkeletonPaginatedList)getBeanCriterios()).iniciarBean();
				setBusquedaInicial(isBuscarInicio());
			} else if (null == getBeanCriterios()){
				setBeanCriterios ((T) getSession().get(getCriteriosSession()));
			}
			cargaRestricciones();
			
			CriterioPaginatedListBean criterioPaginatedList = (CriterioPaginatedListBean)getSession().get(getCriterioPaginatedList());
			if (criterioPaginatedList!=null){
				actualizarCamposBusqueda(criterioPaginatedList);
			} else {
				criterioPaginatedList = new CriterioPaginatedListBean();
			}
			if(utilesColegiado.tienePermisoAdmin()){
				rellenarCriteriosLista();
			} else if(validarFechas(getBeanCriterios()) && isBusquedaInicial()){
				rellenarCriteriosLista();
			}
			getSession().put(getCriteriosSession(), getBeanCriterios());
			actualizarCriteriosList(criterioPaginatedList);
			getSession().put(getCriterioPaginatedList(), criterioPaginatedList);
			return getResultadoPorDefecto();
		}
		
		protected AbstractFactoryPaginatedList getFactoria() {
			if (factoria == null){
				factoria = getFactory();
			}
			return factoria;
		}

		/**
		 * Prepara restricciones a las busqueda, generándolas en el bean de búsqueda.
		 * La más habitual es que si no es administrador, solo busque para el colegiado logado.
		 */
		protected abstract void cargaRestricciones();
		
		
		/**
		 * Actualiza los criterios de la búsqueda a partir de criterios indicados por la navegación
		 * @param criterioPaginatedList
		 */
		protected void actualizarCriteriosList(CriterioPaginatedListBean criterioPaginatedList) {
			if(sort!=null){
				criterioPaginatedList.setSort(sort);
			}
			if(dir!=null){
				criterioPaginatedList.setDir(dir);
			}
			if (page>0){
				criterioPaginatedList.setPage(page);
			}
		}
		
		/**
		 * Actualiza los criterios de la búsqueda a partir de criterios indicados por la navegación
		 * @param criterioPaginatedList
		 */
		protected void actualizarCamposBusqueda(CriterioPaginatedListBean criterioPaginatedList) {
			if ((sort==null || sort.equals("")) && criterioPaginatedList.getSort()!=null){
				sort = criterioPaginatedList.getSort();
			}
			if ((dir==null || dir.equals("")) && criterioPaginatedList.getDir()!=null){
				dir = criterioPaginatedList.getDir();
			}
			if (page<=0 && criterioPaginatedList.getPage()>0){
				page = criterioPaginatedList.getPage();
			}
		}
		
		/**
		 * Para realizar la búsqueda en el inicio
		 * Si no se quiere realizar la búsqueda, sobreescribir este método en el Action devolviendo false.
		 * @return
		 */
		protected boolean isBuscarInicio() {
			return true;
		}
		
		/**
		 * Realiza la navegación por la tabla de resultados
		 * @return
		 */
		public String navegar() {
			recargarPaginatedList();
			return getResultadoPorDefecto();		
		}
		
		/**
		 * Realiza la búsqueda a partir de los criterios indicados
		 * @return
		 */
		public String buscar() {
			setBusquedaInicial(true);
			actualizarPaginatedList();
			return getResultadoPorDefecto();		
		}
		
		/**
		* Metodo que valida las fechas de las consultas
		*/
		public Boolean validarFechas(Object object) {
			return Boolean.TRUE;
		}
		
		/**
		 * Devuelve el decorator a utilizar
		 * @return
		 */
		public String getDecorator(){
			return factoria.decorator();
		}
		
		/**
		 * Devuelve el action para navegar
		 * @return
		 */
		public String getAction(){
			return factoria.getAction();
		}
		
		/**
		 * Sirve para cargar los scripts (javascripts y css) que se pretenden mostrar). Aquí no lleva código, pero se puede sobreescribir en cada action que herede de éste.
		 */
		protected void cargarScriptsYCSS(){	
		}

		public boolean isBusquedaInicial() {
			return busquedaInicial;
		}

		public void setBusquedaInicial(boolean busquedaInicial) {
			this.busquedaInicial = busquedaInicial;
		}
}
