package org.gestoresmadrid.core.pagination.model.component;

import org.displaytag.pagination.PaginatedList;

/**
 * Interfaz utilizada en el modelo de paginación mediante Spring
 *
 */
public interface ModelPagination {

	/**
	 * Realiza una busqueda con los criterios pasados como parametros y devuelve la lista paginada
	 * 
	 * @param beanCriterios Bean con los criterios que debe cumplir el listado
	 * @param resPag numero de resultados por pagina
	 * @param page pagina que se muestra
	 * @param dir orden ascendente o descendente
	 * @param sort campo por el que se ordena
	 * @param listInitializedOnePath parametros de la entidad que deben inicializarse para evitar lazzysException
	 * @param listInitializedAnyPath parametros de la entidad que deben inicializarse antes de cerrar sesión (acarrea mas selects, cuidado!!)
	 * 
	 * @return PaginatedList que cumple con los criterios pasados como parametros
	 */
	PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath);

}
