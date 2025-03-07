package general.modelo;

import general.beans.BeanCriteriosSkeletonPaginatedList;
import org.displaytag.pagination.PaginatedList;
import utilidades.web.OegamExcepcion;

/**
 * Interfaz del modelo del buscador
 * @author ext_mpc
 *
 */
public interface ModeloSkeletonPaginatedList {

	/**
	 * Realiza una búsqueda en función de los criterios indicados.
	 * @param beanCriterios
	 * @param resPag
	 * @param page
	 * @param dir
	 * @param sort
	 * @return
	 * @throws OegamExcepcion
	 */
	PaginatedList buscarPag(BeanCriteriosSkeletonPaginatedList beanCriterios, int resPag, int page, String dir, String sort) throws OegamExcepcion;

}
