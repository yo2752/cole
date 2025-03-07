package org.gestoresmadrid.core.pagination.model.component.impl;


import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.transform.ResultTransformer;




public class PaginatedListImpl implements PaginatedList {

	/**
	* Lista completa de registros
	*/
	protected List<?> lista;
	protected int numElementosTotales =-1;
	protected static final String ORDEN_ASC = "asc";
	protected int objectsPerPage;// equivalente a resultados por pagina
	/**
	* Current page (starting from 1)
	*/
	protected int currentPage;//equivalente a la pagina Actual
	protected Object beanCriterios;
	protected Object objeto;
	protected String dirorden;
	protected String campoOrden;
	protected ResultTransformer transformadorResultado;

	/**
	* Instanciación  la implementación de la clase
	*/
	public PaginatedListImpl(int objectsPerPage, int currentPage, String dirorden, String campoOrden, int numElementosTotales, List<?> lista){
		this.numElementosTotales = numElementosTotales;
		this.objectsPerPage = objectsPerPage;
		this.currentPage = currentPage;
		this.campoOrden= campoOrden;
		this.dirorden= dirorden;
		this.lista = lista;
	}
	
	/**
	* @see org.displaytag.pagination.PaginatedList#getList()
	*/
	public List<?> getList(){
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
	public int getObjectsPerPage(){
		return objectsPerPage;
	}

	/**
	* @see org.displaytag.pagination.PaginatedList#getFullListSize()
	*/
	public int getFullListSize(){
		return numElementosTotales;
	}

	/**
	* @see org.displaytag.pagination.PaginatedList#getSortCriterion()
	*/
	public String getSortCriterion(){
		return campoOrden;
	}
      
	/**
	* @see org.displaytag.pagination.PaginatedList#getSortDirection()
	*/
	public SortOrderEnum getSortDirection(){   
		if ( dirorden!=null && !"".equals(dirorden)){
			if(dirorden.equals(ORDEN_ASC)){
				return SortOrderEnum.ASCENDING;
			}else{
				return SortOrderEnum.DESCENDING;
			}
		}else{	      			
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
