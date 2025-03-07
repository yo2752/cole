package org.gestoresmadrid.core.pagination.model.component.impl;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Modelo abstracto que aporta la funcionalidad necesaria para realizar
 * consultas con paginación mediante Spring
 * 
 */
@Component
public abstract class AbstractModelPagination implements ModelPagination{

	/**
	 * @see ModelPagination#buscarPag(Object, int, int, String, String, String[], String[])
	 */
	@Override
	@Transactional(readOnly=true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {

		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

		List<AliasQueryBean> alias = crearListaAlias();

		// Obtener numero total de resultados según los criterios de busqueda
		int numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);

		// Comprobar si hay resultados
		List<?> list;
		if (numElementosTotales > 0) {
			
			// Calcular primer y ultimo resultado
			int firstResult = 0;
			int maxResults = resPag;
			if (resPag <= 0 || page <= 0) {
				maxResults = numElementosTotales;
			} else {
				firstResult = resPag * (page - 1) ;
				if (firstResult>=numElementosTotales){
					if (numElementosTotales % resPag == 0) {
						page = (numElementosTotales / resPag);
					} else {
						page = (numElementosTotales / resPag)+1;
					}
					firstResult = resPag * (page - 1) ;
				}
			}

			
			
			// Llamar al dao para obtener el listado
			list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);

		} else {
			// Si no hay resultados, lista vacia
			list = Collections.emptyList();
		}

		// Devuelve una instancia de PaginatedList con el listado encontrado
		return new PaginatedListImpl(resPag,page,dir, sort, numElementosTotales, list);
	}

	/**
	 * Monta la lista de proyeciones a través del vector aportado por el objeto
	 * BeanResultTransformPaginatedList y su metodo crearProyecciones
	 * 
	 * @param consultaTramiteTraficoFilaTablaResultadoBean
	 * @return null si el vector proyecciones del BeanResultTransformPaginatedList es nulo. Una instancia de ProjectionList en caso contrario 
	 */
	protected ProjectionList crearListaProyecciones(BeanResultTransformPaginatedList consultaTramiteTraficoFilaTablaResultadoBean){
		ProjectionList result = null;

		if (consultaTramiteTraficoFilaTablaResultadoBean!=null){
			Vector<String> proyecciones = consultaTramiteTraficoFilaTablaResultadoBean.crearProyecciones();

			if (proyecciones != null) {
				result = Projections.projectionList();
				for (String campo: proyecciones){
					result.add(Projections.property(campo));
				}
			}
		}
		return result;
	}

	/**
	 * Aporta un objeto de tipo List<AliasQueryBean> para incluir la lista de alias. 
	 * Es un método protected para sobreescribirse en el modelo cuando sea necesario
	 * 
	 * @return
	 */
	protected List<AliasQueryBean> crearListaAlias() {
		return null;
	}

	/**
	 * Aporta un objeto de tipo BeanResultTransformPaginatedList para realizar
	 * las proyecciones y transformación del resultado. Es un método protected
	 * para sobreescribirse en el modelo cuando sea necesario
	 * 
	 * @return
	 */
	protected BeanResultTransformPaginatedList crearTransformer() {
		return null;
	}	
	
	/**
	 * Debe devolver la instancia de genericDao inyectada en la implementación
	 * del modelo
	 * 
	 * @return
	 */
	protected abstract GenericDao<?> getDao();

}
