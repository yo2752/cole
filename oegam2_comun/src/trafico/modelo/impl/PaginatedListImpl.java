package trafico.modelo.impl;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.transform.ResultTransformer;

import trafico.dao.GenericDao;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.dao.implHibernate.GenericDaoImplHibernate;

	/**
	 * Una implementación de paginatedList
	 * @author Globaltms
	 *
	 */
public class PaginatedListImpl implements PaginatedList {

	/**
	* Lista completa de registros
	*/
	protected List lista;
	/**
	 * Número de elementos que cumplen los criterios
	 */
	protected int numElementosTotales =-1;
	protected static final String ORDEN_ASC = "asc";
	protected int objectsPerPage;// Equivalente a resultados por página
	/**
	* Current page (starting from 1)
	*/
	protected int currentPage;//equivalente a la pagina Actual
	protected GenericDao genericDao;
	protected Object objeto;
	protected List<Criterion> criterion;
	protected String dirorden;
	protected String campoOrden;
	protected List<AliasQueryBean> listaAlias;
	protected ProjectionList listaProyecciones;
	protected ResultTransformer transformadorResultado;

	public PaginatedListImpl(){
	}

	/**
	* Instanciación  la implementación de la clase
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PaginatedListImpl(boolean consultaInicial, Object objeto, int objectsPerPage, int currentPage, List<Criterion> criterion, String dirorden, String campoOrden, List<AliasQueryBean> listaAlias, ProjectionList listaProyecciones, ResultTransformer transformadorResultado){
		init(consultaInicial, objeto, objectsPerPage, currentPage, criterion, dirorden, campoOrden, listaAlias, listaProyecciones, transformadorResultado);
	}

	protected void init(boolean consultaInicial, Object objeto, int objectsPerPage, int currentPage, List<Criterion> criterion, String dirorden, String campoOrden, List<AliasQueryBean> listaAlias, ProjectionList listaProyecciones, ResultTransformer transformadorResultado){
		genericDao = new GenericDaoImplHibernate(objeto);
		this.objeto = objeto;
		this.criterion= criterion;
		this.objectsPerPage = objectsPerPage;
		this.currentPage = currentPage;
		this.campoOrden = campoOrden;
		this.dirorden = dirorden;
		this.listaAlias = listaAlias;
		this.listaProyecciones = listaProyecciones;
		this.transformadorResultado = transformadorResultado;
		if (consultaInicial) {
			obtenerNumElementosTotales();
			obtenerLista();
		}
	}

	private void obtenerNumElementosTotales() {
		numElementosTotales = genericDao.numeroElementos(criterion, listaAlias);
	}

	protected void obtenerLista() {
		if (numElementosTotales > 0) {
			if (objectsPerPage <= 0 || currentPage <= 0) {
				lista = genericDao.buscarPorCriteria(0, numElementosTotales, criterion, dirorden, campoOrden, listaAlias,listaProyecciones, transformadorResultado);
			} else {
				int primerRes = objectsPerPage * (currentPage - 1); // Aquí obtenemos el primer resultado o fila actual
				if (primerRes>=numElementosTotales){
					if (numElementosTotales % objectsPerPage == 0) {
						setCurrentPage((numElementosTotales / objectsPerPage));
					} else {
						setCurrentPage((numElementosTotales / objectsPerPage)+1);
					}
					primerRes = objectsPerPage * (currentPage - 1);
				}
				lista = genericDao.buscarPorCriteria(primerRes, objectsPerPage, criterion,dirorden,campoOrden, listaAlias,listaProyecciones, transformadorResultado);
			}
		} else {
			lista = new ArrayList();
		}
	}

	/**
	* @see org.displaytag.pagination.PaginatedList#getList()
	*/
	public List getList() {
		if (lista == null) {
			if (numElementosTotales < 0) {
				obtenerNumElementosTotales();
			}
			obtenerLista();
		}
		return lista;
	}

	/**
	* @see org.displaytag.pagination.PaginatedList#getPageNumber()
	*/
	public int getPageNumber(){
		return currentPage;
	}

	/**
	* @see org.displaytag.pagination.PaginatedList#getObjectsPerPage()
	*/
	public int getObjectsPerPage() {
		return objectsPerPage;
	}

	/**
	* @see org.displaytag.pagination.PaginatedList#getFullListSize()
	*/
	public int getFullListSize(){
		if (numElementosTotales < 0) {
			obtenerNumElementosTotales();
		}
		return numElementosTotales;
	}

	/**
	* @see org.displaytag.pagination.PaginatedList#getSortCriterion()
	*/
	public String getSortCriterion() {
		return campoOrden;
	}

	/**
	* @see org.displaytag.pagination.PaginatedList#getSortDirection()
	*/
	public SortOrderEnum getSortDirection() {
		if (dirorden != null && !"".equals(dirorden)) {
			if (dirorden.equals(ORDEN_ASC)) {
				return SortOrderEnum.ASCENDING;
			} else {
				return SortOrderEnum.DESCENDING;
			}
		} else {
			return SortOrderEnum.ASCENDING;
		}
	}

	/**
	* @see org.displaytag.pagination.PaginatedList#getSearchId()
	*/
	public String getSearchId(){
		return Integer.toHexString(objectsPerPage * 10000 + currentPage);
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}

	public List<Criterion> getCriterion() {
		return criterion;
	}

	public void setCriterion(List<Criterion> criterion) {
		this.criterion = criterion;
	}

	public String getCampoOrden() {
		return campoOrden;
	}

	public void setCampoOrden(String campoOrden) {
		this.campoOrden = campoOrden;
	}

	public String getDirorden() {
		return dirorden;
	}

	public void setDirorden(String dirorden) {
		this.dirorden = dirorden;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}